package br.com.usjt.ead.professor;

import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.aluno.AlunoBean;
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
import br.com.usjt.jaxrs.security.SecurityPublic;
import br.com.usjt.jaxrs.security.SecurityPrivate.Entidade;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.util.CryptoXFacade;
import br.com.usjt.util.HS;
import br.com.usjt.util.Utils;

@Path("/professor")
public class ProfessorRest implements ICrud
{
    private static final Logger LOG = LoggerFactory.getLogger(ProfessorRest.class);

    @Override
    @Path("listar")
    @POST
    @GET
    @Stylesheet(href = "professor/listar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ADMIN)
    public void read() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
            j.set("profs", session.createCriteria(ProfessorBean.class).list());
        }
        catch (Exception e) {
            LOG.error("Erro ao listar professores", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("detalha")
    @POST
    @Stylesheet(href = "professor/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_update() {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr("metodo", "update");
        try {
            ProfessorBean bean = (ProfessorBean) session.get(ProfessorBean.class, Integer.parseInt(j.getParameter("id_prof")));
            j.set("prof", bean);
            j.set("txtEmail", bean.getEmail());
            j.set("txtNome", bean.getContato().getNome());
        }
        catch (Exception e) {
            LOG.error("Falha ao detalhar professor", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "professor/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ADMIN)
    public void edit_insert() {
        JSPAttr j = new JSPAttr("metodo", "create");
        Session session = HS.getSession();
        try {
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            j.set("uf_id", 25);
            j.set("list_city", session.createCriteria(CidadeBean.class).add(Restrictions.eq("estado.id_estado", 25)).list());

        }
        catch (Exception e) {
            // TODO: handle exception
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("delete")
    @POST
    @Stylesheet(href = "professor/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ADMIN)
    public void delete() {
        JSPAttr j = new JSPAttr("metodo", "delete");
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            ProfessorBean bean = (ProfessorBean) session.get(ProfessorBean.class, Integer.parseInt(j.getParameter("id_prof")));
            session.save(bean);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "professor/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ADMIN)
    public void create() {
        JSPAttr j = new JSPAttr("metodo", "create");
        ProfessorBean b = new ProfessorBean();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            b = popula(j, session);
            if (Utils.isValid(b)) {
                session.save(b);
                tx.commit();
                j.sucessMsg("Professor inserido com sucesso");
            }
            else {
                j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
                CidadeEstadoRest.carregaUf();
                j.repopular();
            }
        }
        catch (Exception e) {
            tx.rollback();
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            CidadeEstadoRest.carregaUf();
            j.repopular();
            j.errorMsg("Falha ao inserir professor");
        }
        finally {
            session.close();
        }
    }

    private ProfessorBean popula(JSPAttr j, Session session) {
        ProfessorBean b = new ProfessorBean();
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
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void update() {
        // TODO Auto-generated method stub

    }

    /**
     * Carrega Cidade
     */
    @POST
    @Path("checaEmail")
    @Stylesheet(href = "aluno/label.jsp", type = MediaTypeMore.APP_JSP)
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

                Criteria c = session.createCriteria(ProfessorBean.class);
                c.add(Restrictions.eq("email", email));

                if (c.list().size() > 0) {
                    j.set("labelemail", "E-mail já cadastrado");
                    j.set("label", "label label-info");
                }
            }
            else {
                j.set("labelemail", "E-mail Inválido");
                j.set("label", "label label-info");
            }
        }
        catch (Exception e) {
            LOG.error("", e);
        }
        finally {
            session.clear();
            session.close();
        }

    }

}
