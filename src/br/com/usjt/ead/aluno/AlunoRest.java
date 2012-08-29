package br.com.usjt.ead.aluno;

import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.EmailValidator;
import org.hibernate.validator.engine.ValidatorFactoryImpl;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.cidadestado.CidadeBean;
import br.com.usjt.ead.cidadestado.CidadeEstadoRest;
import br.com.usjt.ead.cidadestado.EstadoUFBean;
import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;
import br.com.usjt.ead.contato.TelefoneBean;
import br.com.usjt.ead.contato.TipoTelefoneBean;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.Entidade;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.jaxrs.security.SecurityPublic;
import br.com.usjt.util.CryptoXFacade;
import br.com.usjt.util.HS;
import br.com.usjt.util.Utils;

@Path("/aluno")
public class AlunoRest implements ICrud
{

    private static final Logger LOG = LoggerFactory.getLogger(AlunoRest.class);

    @Override
    @Path("")
    @POST
    @GET
    @Stylesheet(href = "pag.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.LER, entity = Entidade.ALUNO)
    public void read() {
    }

    @Override
    @Path("detalha")
    @POST
    @Stylesheet(href = "pag.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.DETALHAR, entity = Entidade.DUMMY)
    public void edit_update() {
    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "aluno/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void edit_insert() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            j.set("uf_id", 26);
            j.set("list_city", session.createCriteria(CidadeBean.class).add(Restrictions.eq("estado.id_estado", 26)).list());
        }
        catch (Exception e) {
            LOG.error("Falha ao carregar", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("delete")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.DELETAR, entity = Entidade.DUMMY)
    public void delete() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "aluno/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void create() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            AlunoBean b = popula(j, session);
            if (Utils.isValid(b)) {
                b.setAtivo(false);
                session.save(b);
                tx.commit();
                Utils.sendMail(b.getEmail(), b.getContato().getNome());
            }
            else {
                j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
                CidadeEstadoRest.carregaUf();
                j.repopular();
            }
        }
        catch (Exception e) {
            tx.rollback();
            j.repopular();
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            CidadeEstadoRest.carregaUf();
        }
        finally {
            session.close();
        }
    }

    private AlunoBean popula(JSPAttr j, Session session) {
        AlunoBean b = new AlunoBean();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            b.setEmail(j.getParameter("txtEmail"));
            b.setSenha(CryptoXFacade.crypt(j.getParameter("txtSenha")));
            b.setCpf(Long.parseLong(j.getParameter("txtCPF").replaceAll("[^0-9]", "")));
            EnderecoBean end = new EnderecoBean();
            end.setCep(Integer.parseInt(j.getParameter("txtCep").replaceAll("[^0-9]", "")));
            end.setLogradouro(j.getParameter("txtEndereco"));
            end.setBairro(j.getParameter("txtBairro"));
            end.setCidade((CidadeBean) session.load(CidadeBean.class, Integer.parseInt(j.getParameter("cidade"))));
            b.setEndereco(end);
            ContatoBean contato = new ContatoBean();
            contato.setData_nascimento(sdf.parse(j.getParameter("txtNascimento")));
            contato.setRg(j.getParameter("txtRG"));
            contato.setNome(j.getParameter("txtNome"));
            TelefoneBean phone = new TelefoneBean();
            phone.setDdd(Integer.parseInt(j.getParameter("txtTelefoneDDD")));
            phone.setTelefone(Long.parseLong(j.getParameter("txtTelefone").replaceAll("[^0-9]", "")));
            phone.setTipo((TipoTelefoneBean) session.load(TipoTelefoneBean.class, 1));
            contato.getTelefones().add(phone);
            TelefoneBean celular = new TelefoneBean();
            celular.setDdd(Integer.parseInt(j.getParameter("txtCelularDDD")));
            celular.setTelefone(Long.parseLong(j.getParameter("txtCelular").replaceAll("[^0-9]", "")));
            celular.setTipo((TipoTelefoneBean) session.load(TipoTelefoneBean.class, 2));
            contato.getTelefones().add(celular);
            b.setContato(contato);
        }
        catch (Exception e) {

        }

        return b;
    }

    @Override
    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.UPDATE, entity = Entidade.DUMMY)
    public void update() {
        // TODO Auto-generated method stub

    }

    @Path("ativar")
    @GET
    @Stylesheet(href = "welcome.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void ativar() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            String email = j.getParameter("key").replaceAll(" ", "+");
            AlunoBean aluno = (AlunoBean) session.createCriteria(AlunoBean.class)
                    .add(Restrictions.eq("email", CryptoXFacade.decrypt(email))).uniqueResult();
            if (aluno == null) {
                throw new Exception();
            }
            aluno.setAtivo(true);
            session.save(aluno);
            tx.commit();
            j.sucessMsg("Cadastro ativado com sucesso");

        }
        catch (Exception e) {
            tx.rollback();
            LOG.error("Falha na operação", e);
        }
        finally {
            if (session.isOpen()) {
                session.clear();
                session.close();
            }
        }
    }

    @Path("reenviaAtivacao")
    @GET
    @Stylesheet(href = "aluno/reenviarativacao.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void reenviarAtivacao() {
    }

    @Path("reenviaAtivacao/confirma")
    @POST
    @Stylesheet(href = "aluno/reenviarativacao.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void reenviarAtivacaoConfirma() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            String email = j.getParameter("txtEmail");
            AlunoBean aluno = (AlunoBean) session.createCriteria(AlunoBean.class).add(Restrictions.eq("email", email))
                    .uniqueResult();
            if (aluno == null) {
                j.errorMsg("Nenhum cadastro encontrado para o e-mail: " + email);
                throw new Exception();
            }

            if (!aluno.isAtivo()) {
                Utils.sendMail(aluno.getEmail(), aluno.getContato().getNome());
                j.sucessMsg("Email enviado com sucesso para o e-mail: " + email);
            }
            else {
                j.errorMsg("O cadastro para o e-mail: " + email + " já está ativo");
            }
        }
        catch (Exception e) {
            tx.rollback();
            LOG.error("Falha na operação", e);
        }
        finally {
            if (session.isOpen()) {
                session.clear();
                session.close();
            }
        }
    }
    
    /**
     * Carrega Cidade
     */
    @POST
    @Path ("checaEmail")
    @Stylesheet (href = "aluno/label.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void validaEmail() {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        String email = null;
        try {
            email = j.getParameter("email");
            if (Utils.isValidMail(email)) {
                j.set("labelemail", "E-mail válido");
                j.set("label", "label label-success");

                Criteria c = session.createCriteria(AlunoBean.class);
                c.add(Restrictions.eq("email", email));

                if (c.list().size() > 0) {
                        j.set("labelemail", "E-mail já cadastrado");
                        j.set("label", "label label-info");
                }
            } else {
                j.set("labelemail", "E-mail Inválido");
                j.set("label", "label label-info");
            }
        }
        catch (Exception e) {
            LOG.error("",e);
        }
        finally {
            session.clear();
            session.close();
        }

    }

}
