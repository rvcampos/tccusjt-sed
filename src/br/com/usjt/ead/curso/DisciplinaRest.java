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
    @Path("editar")
    @POST
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_update() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
            Integer id = Integer.parseInt(j.getParameter("id_disciplina"));
            DisciplinaBean disciplina = (DisciplinaBean) session.get(DisciplinaBean.class, id);
            for (ModuloBean mod : disciplina.getModulos()) {
                switch (mod.getNivel_modulo())
                {
                case 1:
                    j.set("basico", mod);
                    break;

                case 2:
                    j.set("intermediario", mod);
                    break;

                case 3:
                    j.set("avancado", mod);
                    break;
                }
            }
        }
        catch (Exception e) {
            LOG.error("Falha ao carregar curso", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_insert() {
        JSPAttr j = new JSPAttr("metodo", "create");
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
            objDisciplina = populaOsModulosCreate(j, objDisciplina);
            if (Utils.isValid(objDisciplina)) {
                session.save(objDisciplina);
                tx.commit();
                j.sucessMsg("Disciplina criada com sucesso");
            }
            else {
                repopula(j, objDisciplina, "create");
            }
        }
        catch (Exception e) {
            LOG.error("Falha ao inserir disciplina", e);
            tx.rollback();
            repopula(j, objDisciplina, "create");
            j.errorMsg();
        }
        finally {
            session.close();
        }
    }

    private void repopula(JSPAttr j, DisciplinaBean objDisciplina, String metodo) {
        j.repopular();
        j.set("metodo", metodo);
        for (ModuloBean mod : objDisciplina.getModulos()) {
            switch (mod.getNivel_modulo())
            {
            case 1:
                j.set("basico", mod);
                break;

            case 2:
                j.set("intermediario", mod);
                break;

            case 3:
                j.set("avancado", mod);
                break;
            }
        }
    }

    private DisciplinaBean populaOsModulosCreate(JSPAttr j, DisciplinaBean objDisciplina) {
        ModuloBean basico = new ModuloBean();
        basico.setDisciplina(objDisciplina);
        basico.setNivel_modulo(1);
        basico.setData_inicio(objDisciplina.getData_inicio());
        basico.setData_termino(objDisciplina.getData_termino());
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesBas")) && !Utils.isEmpty(j.getParameter("qtdaltBas"))))) {
            AvaliacaoBean b = new AvaliacaoBean();
            b.setModulo(basico);
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
                    questao.getAlternativas().add(alternativa);
                }
                b.getQuestoes().add(questao);
            }

            basico.setAvaliacao(b);
        }

        ModuloBean intermediario = new ModuloBean();
        intermediario.setDisciplina(objDisciplina);
        intermediario.setNivel_modulo(2);
        intermediario.setData_inicio(objDisciplina.getData_inicio());
        intermediario.setData_termino(objDisciplina.getData_termino());
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesInt")) && !Utils.isEmpty(j.getParameter("qtdaltInt"))))) {
            AvaliacaoBean inte = new AvaliacaoBean();
            inte.setModulo(intermediario);
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
                    questao.getAlternativas().add(alternativa);
                }
                inte.getQuestoes().add(questao);
            }

            intermediario.setAvaliacao(inte);
        }
        ModuloBean avancado = new ModuloBean();
        avancado.setDisciplina(objDisciplina);
        avancado.setNivel_modulo(3);
        avancado.setData_inicio(objDisciplina.getData_inicio());
        avancado.setData_termino(objDisciplina.getData_termino());
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesAdv")) && !Utils.isEmpty(j.getParameter("qtdaltAdv"))))) {
            AvaliacaoBean adv = new AvaliacaoBean();
            adv.setModulo(avancado);
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
                    questao.getAlternativas().add(alternativa);
                }
                adv.getQuestoes().add(questao);
            }

            avancado.setAvaliacao(adv);
        }
        objDisciplina.getModulos().add(basico);
        objDisciplina.getModulos().add(intermediario);
        objDisciplina.getModulos().add(avancado);
        return objDisciplina;
    }

    private DisciplinaBean populaOsModulosUpdate(JSPAttr j, DisciplinaBean objDisciplina) {
        ModuloBean basico = new ModuloBean();
        basico.setDisciplina(objDisciplina);
        basico.setNivel_modulo(1);
        basico.setData_inicio(objDisciplina.getData_inicio());
        basico.setData_termino(objDisciplina.getData_termino());
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesBas")) && !Utils.isEmpty(j.getParameter("qtdaltBas"))))) {
            AvaliacaoBean b = new AvaliacaoBean();
            b.setModulo(basico);
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
                    questao.getAlternativas().add(alternativa);
                }
                b.getQuestoes().add(questao);
            }

            basico.setAvaliacao(b);
        }

        ModuloBean intermediario = new ModuloBean();
        intermediario.setDisciplina(objDisciplina);
        intermediario.setNivel_modulo(2);
        intermediario.setData_inicio(objDisciplina.getData_inicio());
        intermediario.setData_termino(objDisciplina.getData_termino());
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesInt")) && !Utils.isEmpty(j.getParameter("qtdaltInt"))))) {
            AvaliacaoBean inte = new AvaliacaoBean();
            inte.setModulo(intermediario);
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
                    questao.getAlternativas().add(alternativa);
                }
                inte.getQuestoes().add(questao);
            }

            intermediario.setAvaliacao(inte);
        }
        ModuloBean avancado = new ModuloBean();
        avancado.setDisciplina(objDisciplina);
        avancado.setNivel_modulo(3);
        avancado.setData_inicio(objDisciplina.getData_inicio());
        avancado.setData_termino(objDisciplina.getData_termino());
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesAdv")) && !Utils.isEmpty(j.getParameter("qtdaltAdv"))))) {
            AvaliacaoBean adv = new AvaliacaoBean();
            adv.setModulo(avancado);
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
                    questao.getAlternativas().add(alternativa);
                }
                adv.getQuestoes().add(questao);
            }

            avancado.setAvaliacao(adv);
        }
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
