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
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.EntityDAO;
import br.com.usjt.ead.cidadestado.CidadeBean;
import br.com.usjt.ead.cidadestado.CidadeEstadoRest;
import br.com.usjt.ead.cidadestado.EstadoUFBean;
import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;
import br.com.usjt.ead.contato.TelefoneBean;
import br.com.usjt.ead.contato.TipoTelefoneBean;
import br.com.usjt.ead.curso.ModuloBean;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.jaxrs.security.SecurityPublic;
import br.com.usjt.shiro.Security;
import br.com.usjt.shiro.SecurityShiro;
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
    @SecurityPrivate(role = SecType.ADMIN)
    public void read() {
    }

    @Override
    @Path("alterarDados")
    @GET
    @Stylesheet(href = "aluno/alterar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.ALUNO })
    public void edit_update() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Security sh = SecurityShiro.init();
        EntityDAO dao = new EntityDAO();
        try {
            AlunoBean aluno = dao.searchID(sh.getUserId(), session, AlunoBean.class);
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            populaEditUpdate(j, aluno);
        }
        catch (Exception e) {
            LOG.error("Falha ao carregar", e);
        }
        finally {
            session.close();
        }
    }

    private void populaEditUpdate(JSPAttr j, AlunoBean aluno) {
        j.set("txtEmail", aluno.getEmail());
        j.set("txtCPF",  Utils.padding(aluno.getCpf(),14,"0"));
        j.set("txtCep", Utils.padding(aluno.getEndereco().getCep(), 8, "0"));
        j.set("txtEndereco", aluno.getEndereco().getLogradouro());
        j.set("txtBairro", aluno.getEndereco().getBairro());
        j.set("txtNascimento", aluno.getContato().getData_nascimento());
        j.set("txtRG", aluno.getContato().getRg());
        j.set("txtNome", aluno.getContato().getNome());

        for (TelefoneBean tel : aluno.getContato().getTelefones()) {
            if (tel.getTipo().getId_tipo() == 1) {
                j.set("txtTelefone", tel.getTelefone());
                j.set("txtTelefoneDDD", tel.getDdd());
            }
            else {
                j.set("txtCelularDDD", tel.getDdd());
                j.set("txtCelular", tel.getTelefone());
            }
        }
        j.set("uf_id", aluno.getEndereco().getCidade().getEstado().getId_estado());
        j.set("list_city", aluno.getEndereco().getCidade().getEstado().getCidades());
        j.set("cidade", aluno.getEndereco().getCidade().getId_cidade());

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
    @SecurityPrivate(role = { SecType.ADMIN, SecType.ALUNO })
    public void delete() {
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
            AlunoBean b = popula(j, session, false);
            if (Utils.isValid(b)) {
                b.setAtivo(false);
                session.save(b);
                tx.commit();
                Utils.sendMail(b.getEmail(), b.getContato().getNome());
                j.sucessMsg("Cadastro realizado com sucesso. Em instantes você receberá um e-mail para ativar seu cadastro e poderá usufruir de nossos cursos");
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

    private AlunoBean popula(JSPAttr j, Session session, boolean update) {
        AlunoBean b = new AlunoBean();
        if (!Utils.isEmpty(j.getParameter("aluno_id"))) {
            b = (AlunoBean) session.get(AlunoBean.class, Integer.parseInt(j.getParameter("aluno_id")));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (!update) {
                b.setEmail(j.getParameter("txtEmail"));
            }
            b.setSenha(CryptoXFacade.crypt(j.getParameter("txtSenha")));
            b.setCpf(Long.parseLong(j.getParameter("txtCPF").replaceAll("[^0-9]", "")));
            EnderecoBean end = b.getEndereco();
            if (end == null) {
                end = new EnderecoBean();
                b.setEndereco(end);
            }
            end.setCep(Integer.parseInt(j.getParameter("txtCep").replaceAll("[^0-9]", "")));
            end.setLogradouro(j.getParameter("txtEndereco"));
            end.setBairro(j.getParameter("txtBairro"));
            end.setCidade((CidadeBean) session.load(CidadeBean.class, Integer.parseInt(j.getParameter("cidade"))));
            ContatoBean contato = b.getContato();
            if (contato == null) {
                contato = new ContatoBean();
            }
            contato.setData_nascimento(sdf.parse(j.getParameter("txtNascimento")));
            contato.setRg(j.getParameter("txtRG"));
            contato.setNome(j.getParameter("txtNome"));
            if (!update) {
                TelefoneBean phone = new TelefoneBean();
                phone.setDdd(Integer.parseInt(j.getParameter("txtTelefoneDDD")));
                phone.setTelefone(Long.parseLong(j.getParameter("txtTelefone").replaceAll("[^0-9]", "")));
                phone.setTipo((TipoTelefoneBean) session.load(TipoTelefoneBean.class, 1));
                phone.setContato(contato);
                contato.getTelefones().add(phone);
                TelefoneBean celular = new TelefoneBean();
                celular.setDdd(Integer.parseInt(j.getParameter("txtCelularDDD")));
                celular.setTelefone(Long.parseLong(j.getParameter("txtCelular").replaceAll("[^0-9]", "")));
                celular.setTipo((TipoTelefoneBean) session.load(TipoTelefoneBean.class, 2));
                celular.setContato(contato);
                contato.getTelefones().add(celular);
                b.setContato(contato);
            }
            else {
                for (TelefoneBean phone : b.getContato().getTelefones()) {
                    if (phone.getTipo().getId_tipo() == 1) {
                        phone.setDdd(Integer.parseInt(j.getParameter("txtTelefoneDDD")));
                        phone.setTelefone(Long.parseLong(j.getParameter("txtTelefone").replaceAll("[^0-9]", "")));
                    }
                    else {
                        phone.setDdd(Integer.parseInt(j.getParameter("txtCelularDDD")));
                        phone.setTelefone(Long.parseLong(j.getParameter("txtCelular").replaceAll("[^0-9]", "")));
                    }
                }
            }
        }
        catch (Exception e) {
            LOG.error("Falha ao popular aluno", e);
        }

        return b;
    }

    @Override
    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.ALUNO })
    public void update() {
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
        try {
            String email = j.getParameter("txtEmail");
            AlunoBean aluno = (AlunoBean) session.createCriteria(AlunoBean.class).add(Restrictions.eq("email", email))
                    .uniqueResult();
            if (aluno == null) {
                j.errorMsg("Nenhum cadastro encontrado para o e-mail: " + email);
                throw new Exception();
            }

            if (!aluno.getAtivo()) {
                Utils.sendMail(aluno.getEmail(), aluno.getContato().getNome());
                j.sucessMsg("Email enviado com sucesso para o e-mail: " + email);
            }
            else {
                j.errorMsg("O cadastro para o e-mail: " + email + " já está ativo");
            }
        }
        catch (Exception e) {
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
                j.set("canPost", true);

                Criteria c = session.createCriteria(AlunoBean.class);
                c.add(Restrictions.eq("email", email));

                if (c.list().size() > 0) {
                    j.set("labelemail", "E-mail já cadastrado");
                    j.set("label", "label label-info");
                    j.set("canPost", false);
                }
            }
            else {
                j.set("labelemail", "E-mail Inválido");
                j.set("label", "label label-info");
                j.set("canPost", false);
            }
        }
        catch (Exception e) {
            LOG.error("", e);
        }
        finally {
            session.clear();
            session.close();
        }
        // String out = "";
        // try
        // {
        // URLConnection connection = new URL("http://192.168.1.123:10055" + "?"
        // + "api.Time").openConnection();
        // connection.setRequestProperty("Accept-Charset", "UTF-8");
        // InputStream response = connection.getInputStream();
        // while(response.available() != 0)
        // {
        // out += (char) response.read();
        // }
        // }catch(Exception e)
        // {
        //
        // }
        // System.out.println(out);
    }

    @Path("meusCursos")
    @GET
    @POST
    @Stylesheet(href = "aluno/meuscursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ALUNO)
    public void meusCursos() {
        JSPAttr j = new JSPAttr();
        Security sh = SecurityShiro.init();
        Integer id = sh.getUserId();
        Session session = HS.getSession();
        EntityDAO dao = new EntityDAO();
        try {
            AlunoBean aluno = dao.searchID(id, session, AlunoBean.class);
            j.set("cursos", aluno.getMatriculas().keySet());
        }
        catch (Exception e) {
            LOG.error("Falha ao buscar cursos", e);
        }
        finally {
            session.clear();
            session.close();
        }
    }

    @Path("matricular")
    @POST
    @Stylesheet(href = "aluno/meuscursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ALUNO)
    public void matricular() {
        JSPAttr j = new JSPAttr();
        Security sh = SecurityShiro.init();
        Integer id = sh.getUserId();
        Integer disciplinaId = null;
        Session session = HS.getSession();
        EntityDAO dao = new EntityDAO();
        Transaction tx = session.beginTransaction();
        try {
            disciplinaId = Integer.parseInt(j.getParameter("id_disciplina"));
            AlunoBean aluno = dao.searchID(id, session, AlunoBean.class);
            ModuloBean modulo = (ModuloBean) session
                    .createQuery(
                            " from " + ModuloBean.class.getSimpleName()
                                    + " m where m.disciplina.id_disciplina = :disciplina order by m.id_modulo")
                    .setInteger("disciplina", disciplinaId).list().get(0);

            if (!aluno.getMatriculas().containsKey(modulo)) {
                MatriculaBean matricula = new MatriculaBean();
                matricula.setModulo(modulo);
                matricula.setAluno(aluno);
                session.save(matricula);
                aluno.getMatriculas().put(modulo, matricula);
                tx.commit();
                j.sucessMsg("Matricula Efetuada com sucesso");
            }
            else {
                j.errorMsg("Você já está matriculado no curso " + modulo.getDisciplina().getNome_disciplina());
            }
        }
        catch (Exception e) {
            LOG.error("Falha ao efetuar matricula cursos", e);
            tx.rollback();
        }
        finally {
            session.clear();
            session.close();
            meusCursos();
        }
    }

}
