package br.com.usjt.ead.curso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

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
import br.com.usjt.ead.aluno.AlunoBean;
import br.com.usjt.ead.aluno.AlunoRest;
import br.com.usjt.ead.aluno.MatriculaBean;
import br.com.usjt.ead.professor.ProfessorBean;
import br.com.usjt.ead.professor.ProfessorRest;
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

    private static final long   oneDay = 86400000L;

    private static final Logger LOG    = LoggerFactory.getLogger(DisciplinaRest.class);

    @Override
    @Path("listar")
    @POST
    @GET
    @Stylesheet(href = "curso/listarCursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void read() {
        JSPAttr j = new JSPAttr();
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
        JSPAttr j = new JSPAttr("metodo", "update");
        Session session = HS.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Integer id = Integer.parseInt(j.getParameter("id_disciplina"));
            DisciplinaBean disciplina = (DisciplinaBean) session.get(DisciplinaBean.class, id);
            j.set("id_disciplina", id);
            j.set("txtNomeDisciplina", disciplina.getNome_disciplina());
            j.set("txtDesc", disciplina.getDescricao());
            j.set("txtDataInicio", sdf.format(disciplina.getData_inicio()));
            j.set("txtDataTermino", sdf.format(disciplina.getData_termino()));
            for (ModuloBean mod : disciplina.getModulos()) {
                switch (mod.getNivel_modulo())
                {
                case 1:
                    j.set("basico", mod);
                    j.set("qtdquestoesBas", mod.getAvaliacao().getQuestoes().size());
                    j.set("qtdaltBas", mod.getAvaliacao().getQuestoes().get(0).getAlternativas().size());
                    break;

                case 2:
                    j.set("intermediario", mod);
                    j.set("qtdquestoesInt", mod.getAvaliacao().getQuestoes().size());
                    j.set("qtdaltInt", mod.getAvaliacao().getQuestoes().get(0).getAlternativas().size());
                    break;

                case 3:
                    j.set("avancado", mod);
                    j.set("qtdquestoesAdv", mod.getAvaliacao().getQuestoes().size());
                    j.set("qtdaltAdv", mod.getAvaliacao().getQuestoes().get(0).getAlternativas().size());
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
    @Stylesheet(href = "professor/meusCursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void delete() {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        Transaction tx = session.beginTransaction();
        try {
            DisciplinaBean d = (DisciplinaBean) session.get(DisciplinaBean.class,
                    Integer.parseInt(j.getParameter("id_disciplina")));
            session.delete(d);
            tx.commit();
            j.sucessMsg("Curso deletado com sucesso");
        }
        catch (Exception e) {
            tx.rollback();
            LOG.error("Falha ao deletar curso", e);
            j.errorMsg();
        }
        finally {
            session.close();
            new ProfessorRest().meusCursos();
        }
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
        Iterator<ModuloBean> it = objDisciplina.getModulos().iterator();
        ModuloBean basico = new ModuloBean();
        if (it.hasNext()) {
            basico = it.next();
        }
        else {
            basico.setDisciplina(objDisciplina);
            basico.setNivel_modulo(1);
            basico.setData_inicio(objDisciplina.getData_inicio());
            basico.setData_termino(objDisciplina.getData_termino());
        }
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
        if (it.hasNext()) {
            intermediario = it.next();
        }
        else {
            intermediario.setDisciplina(objDisciplina);
            intermediario.setNivel_modulo(2);
            intermediario.setData_inicio(objDisciplina.getData_inicio());
            intermediario.setData_termino(objDisciplina.getData_termino());
        }
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
        if (it.hasNext()) {
            avancado = it.next();
        }
        else {
            avancado.setDisciplina(objDisciplina);
            avancado.setNivel_modulo(3);
            avancado.setData_inicio(objDisciplina.getData_inicio());
            avancado.setData_termino(objDisciplina.getData_termino());
        }
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
        objDisciplina.getModulos().clear();
        ModuloBean basico = new ModuloBean();
        basico.setDisciplina(objDisciplina);
        basico.setNivel_modulo(1);
        basico.setData_inicio(objDisciplina.getData_inicio());
        basico.setData_termino(objDisciplina.getData_termino());
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesBas")) && !Utils.isEmpty(j.getParameter("qtdaltBas"))))) {
            AvaliacaoBean b = new AvaliacaoBean();
            b.setModulo(basico);
            b.getQuestoes().clear();
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
    @Stylesheet(href = "professor/meusCursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void update() {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        DisciplinaBean disciplina = new DisciplinaBean();
        Transaction tx = session.beginTransaction();
        try {
            disciplina = (DisciplinaBean) session.get(DisciplinaBean.class, Integer.parseInt(j.getParameter("id_disciplina")));
            disciplina = populaOsModulosUpdate(j, disciplina);
            disciplina.setNome_disciplina(j.getParameter("txtNomeDisciplina"));
            if (Utils.isValid(disciplina)) {
                session.update(disciplina);
                tx.commit();
                j.sucessMsg();
            }
            else {

            }
        }
        catch (Exception e) {
            j.errorMsg();
            LOG.error("Falha ao alterar curso", e);
        }
        finally {
            session.close();
            new ProfessorRest().meusCursos();
        }
    }

    @Path("realizaAvaliacao")
    @POST
    @Stylesheet(href = "curso/avaliacao.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ALUNO })
    public void realizaAvaliacao() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
            MatriculaBean m = (MatriculaBean) session.get(MatriculaBean.class, Integer.parseInt(j.getParameter("matricula")));
            j.set("modulo", m.getModulo());
            j.set("matricula", m);
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

    @Path("corrigeProva")
    @POST
    @Stylesheet(href = "aluno/meuscursos.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ALUNO })
    public void corrigeProva() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        boolean last = false;
        try {
            MatriculaBean m = (MatriculaBean) session.get(MatriculaBean.class, Integer.parseInt(j.getParameter("matricula")));

            int acertos = 0;
            int qtdAlt = m.getModulo().getAvaliacao().getQuestoes().size();
            double pctNec = 100.00d;
            String mod = "Intermediário";
            switch (m.getModulo().getNivel_modulo())
            {
            case 1:
                pctNec *= 0.65;
                break;
            case 2:
                pctNec *= 0.75;
                mod = "Avançado";
                break;

            case 3:
                pctNec *= 0.85;
                last = true;
                break;
            }
            for (QuestaoBean questao : m.getModulo().getAvaliacao().getQuestoes()) {
                String selecionada = j.getParameter("questao" + questao.getId_questao());
                if (!Utils.isEmpty(selecionada)) {
                    if (questao.isCorrectAlternativa(Long.parseLong(selecionada))) {
                        acertos++;
                    }
                }
            }

            double pct = (acertos / qtdAlt) * 100;
            if (pct >= pctNec) {
                m.setConcluido(true);
                if (last) {
                    j.sucessMsg("Parabéns! Você foi aprovado e pode emitir seu certificado de conclusão!");
                    m.setCertificado(true);
                }
                else {
                    j.sucessMsg("Parabéns! Você passou para o nível " + mod + " do curso "
                            + m.getModulo().getDisciplina().getNome_disciplina());
                    MatriculaBean b = new MatriculaBean();
                    b.setAluno(m.getAluno());
                    b.setDt_avaliacao(new Date());
                    int nivel = m.getModulo().getNivel_modulo() + 1;
                    b.setModulo((ModuloBean) session.createQuery("from " + ModuloBean.class.getSimpleName()
                                    + " m where m.disciplina.id_disciplina = :disciplina and m.nivel_modulo = :nivel").setInteger("disciplina", m.getModulo().getDisciplina().getId_disciplina()).setInteger("nivel", nivel).uniqueResult());
                    session.save(b);
                }
            }
            else {
                m.setDt_avaliacao(new Date(new Date().getTime() + 2 * oneDay));
                j.errorMsg("Você falhou em obter a porcentagem mínima de acerto("+pctNec+"%). Não se desanime, estude mais e tente novamente em 48 horas");
            }
            session.update(m);
            tx.commit();

        }
        catch (Exception e) {
            LOG.error("Falha na operação", e);
        }
        finally {
            if (session.isOpen()) {
                session.clear();
                session.close();
            }
            new AlunoRest().meusCursos();
        }
    }

}
