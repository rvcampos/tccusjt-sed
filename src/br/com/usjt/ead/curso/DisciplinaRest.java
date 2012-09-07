package br.com.usjt.ead.curso;

import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.EntityDAO;
import br.com.usjt.ead.professor.ProfessorBean;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.shiro.Security;
import br.com.usjt.shiro.SecurityShiro;
import br.com.usjt.util.HS;
import br.com.usjt.util.Utils;

@Path("/curso")
public class DisciplinaRest implements ICrud
{

    private static final Logger LOG = LoggerFactory.getLogger(DisciplinaRest.class);

    @Override
    @Path("listar")
    @POST
    @GET
    @Stylesheet(href = "curso/listarCursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void read() {
        JSPAttr j = new JSPAttr();
        Security sh = SecurityShiro.init();
        Session session = HS.getSession();
        EntityDAO dao = new EntityDAO();
        try {
            j.set("disciplinas", dao.searchAll(session, DisciplinaBean.class));
        }
        catch (Exception e) {
            LOG.error("Falha ao buscar cursos", e);
        }
        finally {
            session.clear();
            session.close();
        }
    }

    @Override
    @Path("detalha")
    @POST
    @Stylesheet(href = "pag.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void edit_update() {
    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_insert() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
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
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void delete() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void create() {
        JSPAttr j = new JSPAttr();
        DisciplinaBean objDisciplina = new DisciplinaBean();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
        Security sh = SecurityShiro.init();
        try {
            objDisciplina.setNome_disciplina(j.getParameter("txtNomeDisciplina").toString());
            objDisciplina.setData_inicio(dtFormat.parse(j.getParameter("txtDataInicio").toString()));
            objDisciplina.setData_termino(dtFormat.parse(j.getParameter("txtDataTermino").toString()));
            objDisciplina.setDescricao(j.getParameter("txtDesc"));
            objDisciplina.setProfessor((ProfessorBean) session.load(ProfessorBean.class, sh.getUserId()));
            if (((!Utils.isEmpty(j.getParameter("qtdquestoesBas")) && !Utils.isEmpty(j.getParameter("qtdaltBas"))))
                    && ((!Utils.isEmpty(j.getParameter("qtdquestoesInt")) && !Utils.isEmpty(j.getParameter("qtdaltInt"))))
                    && ((!Utils.isEmpty(j.getParameter("qtdquestoesAdv")) && !Utils.isEmpty(j.getParameter("qtdaltAdv"))))) {
                
                objDisciplina = populaOsModulos(j, objDisciplina);

                session.save(objDisciplina);
                tx.commit();
                j.sucessMsg("Disciplina criada com sucesso");
            }
            else {

            }
        }
        catch (Exception e) {
            LOG.error("Falha ao inserir disciplina", e);
            tx.rollback();
            j.repopular();
            j.errorMsg();
        }
        finally {
            session.close();
        }
    }

    private DisciplinaBean populaOsModulos(JSPAttr j, DisciplinaBean objDisciplina) {
        ModuloBean basico = new ModuloBean();
        basico.setDisciplina(objDisciplina);
        basico.setNivel_modulo(1);
        basico.setData_inicio(objDisciplina.getData_inicio());
        basico.setData_termino(objDisciplina.getData_termino());
        AvaliacaoBean b = new AvaliacaoBean();
        for (int i = 1; i <= Integer.parseInt(j.getParameter("qtdquestoesBas")); i++) {
            QuestaoBean questao = new QuestaoBean();
            questao.setAvaliacao(b);
            questao.setConteudo(j.getParameter("txtquestoesBasicoquest" + i));
            int certa = Integer.parseInt(j.getParameter("optquestoesBasico" + i));
            for (int k = 1; k <= Integer.parseInt(j.getParameter("qtdaltBas")); k++) {
                AlternativaBean alternativa = new AlternativaBean();
                alternativa.setConteudo(j.getParameter("txtquestoesBasicoquest" + i + "alt" + k));
                alternativa.setQuestao(questao);
                if (k == certa) {
                    alternativa.setCorreta(true);
                }
            }
            b.getQuestoes().add(questao);
        }

        basico.setAvaliacao(b);

        ModuloBean intermediario = new ModuloBean();
        intermediario.setDisciplina(objDisciplina);
        intermediario.setNivel_modulo(2);
        intermediario.setData_inicio(objDisciplina.getData_inicio());
        intermediario.setData_termino(objDisciplina.getData_termino());
        AvaliacaoBean inte = new AvaliacaoBean();
        for (int i = 1; i <= Integer.parseInt(j.getParameter("qtdquestoesInt")); i++) {
            QuestaoBean questao = new QuestaoBean();
            questao.setAvaliacao(inte);
            questao.setConteudo(j.getParameter("txtquestoesIntermediarioquest" + i));
            int certa = Integer.parseInt(j.getParameter("optquestoesIntermediario" + i));
            for (int k = 1; k <= Integer.parseInt(j.getParameter("qtdaltInt")); k++) {
                AlternativaBean alternativa = new AlternativaBean();
                alternativa.setConteudo(j.getParameter("txtquestoesIntermediarioquest" + i + "alt" + k));
                alternativa.setQuestao(questao);
                if (k == certa) {
                    alternativa.setCorreta(true);
                }
            }
            inte.getQuestoes().add(questao);
        }

        intermediario.setAvaliacao(inte);

        ModuloBean avancado = new ModuloBean();
        avancado.setDisciplina(objDisciplina);
        avancado.setNivel_modulo(3);
        avancado.setData_inicio(objDisciplina.getData_inicio());
        avancado.setData_termino(objDisciplina.getData_termino());
        AvaliacaoBean adv = new AvaliacaoBean();
        for (int i = 1; i <= Integer.parseInt(j.getParameter("qtdquestoesInt")); i++) {
            QuestaoBean questao = new QuestaoBean();
            questao.setAvaliacao(adv);
            questao.setConteudo(j.getParameter("txtquestoesAvancadoquest" + i));
            int certa = Integer.parseInt(j.getParameter("optquestoesAvancado" + i));
            for (int k = 1; k <= Integer.parseInt(j.getParameter("qtdaltInt")); k++) {
                AlternativaBean alternativa = new AlternativaBean();
                alternativa.setConteudo(j.getParameter("txtquestoesAvancadoquest" + i + "alt" + k));
                alternativa.setQuestao(questao);
                if (k == certa) {
                    alternativa.setCorreta(true);
                }
            }
            adv.getQuestoes().add(questao);
        }

        avancado.setAvaliacao(adv);

        objDisciplina.getModulos().add(basico);
        objDisciplina.getModulos().add(intermediario);
        objDisciplina.getModulos().add(avancado);
        return objDisciplina;
    }

    @Override
    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void update() {
        // TODO Auto-generated method stub

    }
}
