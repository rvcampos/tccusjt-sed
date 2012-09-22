package br.com.usjt.ead.professor;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.EntityDAO;
import br.com.usjt.ead.aluno.AlunoBean;
import br.com.usjt.ead.cidadestado.CidadeBean;
import br.com.usjt.ead.cidadestado.CidadeEstadoRest;
import br.com.usjt.ead.cidadestado.EstadoUFBean;
import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;
import br.com.usjt.ead.contato.TelefoneBean;
import br.com.usjt.ead.contato.TipoTelefoneBean;
import br.com.usjt.ead.curso.DisciplinaBean;
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
        Criteria c = session.createCriteria(ProfessorBean.class);
        try {
            // Usar um método para receber e devolver um CRITERIA
            c = filtrar(c, j);
            // Usar como paginação padrão
            Utils.paginar(j, c);
            j.set("profs", c.list());
        }
        catch (Exception e) {
            LOG.error("Erro ao listar professores", e);
            j.repopular();
        }
        finally {
            session.close();
            j.repopular();
        }
    }

    private Criteria filtrar(Criteria c, JSPAttr j) {
        // Quando o parametro de filtro for de outro bean, no caso um
        // relacionamento, utilizar 'createCriteria(nomeBean,alias)'
        // Restrictions.like = like
        // Restrictions.eq = igual
        // PS: Prestar atenção no tipo de dado. Deve ser igual ao do bean. No
        // caso, String com String, Integer com Integer
        if (!Utils.isEmpty(j.getParameter("filtro_nome"))) {
            c.createCriteria("contato", "contato").add(
                    Restrictions.like("contato.nome", j.getParameter("filtro_nome"), MatchMode.ANYWHERE));
        }

        if (!Utils.isEmpty(j.getParameter("filtro_email"))) {
            c.add(Restrictions.like("email", j.getParameter("filtro_email"), MatchMode.ANYWHERE));
        }
        return c;
    }

    @Override
    @Path("alterarDados")
    @GET
    @Stylesheet(href = "professor/alterar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_update() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Security sh = SecurityShiro.init();
        EntityDAO dao = new EntityDAO();
        try {
            ProfessorBean professor = dao.searchID(sh.getUserId(), session, ProfessorBean.class);
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            populaEditUpdate(j, professor);
        }
        catch (Exception e) {
            LOG.error("Falha ao carregar", e);
        }
        finally {
            session.close();
        }
    }

    private void populaEditUpdate(JSPAttr j, ProfessorBean professor) {
        j.set("txtEmail", professor.getEmail());
        j.set("txtCPF", Utils.padding(professor.getCpf(), 14, "0"));
        j.set("txtCep", Utils.padding(professor.getEndereco().getCep(), 8, "0"));
        j.set("txtEndereco", professor.getEndereco().getLogradouro());
        j.set("txtBairro", professor.getEndereco().getBairro());
        j.set("txtNascimento", professor.getContato().getData_nascimento());
        j.set("txtRG", professor.getContato().getRg());
        j.set("txtNome", professor.getContato().getNome());

        for (TelefoneBean tel : professor.getContato().getTelefones()) {
            if (tel.getTipo().getId_tipo() == 1) {
                j.set("txtTelefone", tel.getTelefone());
                j.set("txtTelefoneDDD", tel.getDdd());
            }
            else {
                j.set("txtCelularDDD", tel.getDdd());
                j.set("txtCelular", tel.getTelefone());
            }
        }
        j.set("uf_id", professor.getEndereco().getCidade().getEstado().getId_estado());
        j.set("list_city", professor.getEndereco().getCidade().getEstado().getCidades());
        j.set("cidade", professor.getEndereco().getCidade().getId_cidade());

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
            session.delete(bean);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
        }
        finally {
            session.close();
        }
    }

    @Path("deleteCurso")
    @POST
    @Stylesheet(href = "professor/meusCursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.PROFESSOR)
    public void deleteCurso() {
        JSPAttr j = new JSPAttr("metodo", "delete");
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            DisciplinaBean bean = (DisciplinaBean) session.get(DisciplinaBean.class,
                    Integer.parseInt(j.getParameter("id_disciplina")));
            session.delete(bean);
            tx.commit();

            // Carrego os cursos novamente para atualizar a página
            meusCursos();
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
            b = popula(j, session, false);
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

    private ProfessorBean popula(JSPAttr j, Session session, boolean update) {
        ProfessorBean b = new ProfessorBean();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (!update) {
                b.setEmail(j.getParameter("txtEmail"));
            }
            if(!Utils.isEmpty(j.getParameter("txtSenha")))
            {
                b.setSenha(CryptoXFacade.crypt(j.getParameter("txtSenha")));
            }
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
        catch (Exception e) {

        }

        return b;
    }

    @Override
    @Path("alterar")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void update() {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        Transaction tx = session.beginTransaction();
        ProfessorBean b = popula(j, session, true);
        try {
            if (Utils.isValid(b)) {
                session.update(b);
                tx.commit();
                j.sucessMsg("Dados Cadastrais alterados com sucesso!");
            }
            else {
                j.repopular();
            }
        }
        catch (Exception e) {
            LOG.error("Falha ao alterar dados cadastrais de professor", e);
            j.repopular();
        }
        finally {

        }
    }

    /**
     * Carrega Cidade
     */
    @POST
    @Path("checaEmail")
    @Stylesheet(href = "professor/label.jsp", type = MediaTypeMore.APP_JSP)
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

    @Path("meusCursos")
    @GET
    @POST
    @Stylesheet(href = "professor/meusCursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.PROFESSOR)
    public void meusCursos() {
        JSPAttr j = new JSPAttr();
        Security sh = SecurityShiro.init();
        Integer id = sh.getUserId();
        Session session = HS.getSession();
        EntityDAO dao = new EntityDAO();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("professor.id_professor", id);

            j.set("cursos", dao.searchByValue(session, DisciplinaBean.class, map));
        }
        catch (Exception e) {
            LOG.error("Falha ao buscar cursos", e);
        }
        finally {
            session.clear();
            session.close();
        }
    }
}
