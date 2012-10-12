package br.com.usjt.ead.curso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.shiro.io.ResourceUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ead.EntityDAO;
import br.com.usjt.ead.aluno.AlunoBean;
import br.com.usjt.ead.aluno.AlunoRest;
import br.com.usjt.ead.aluno.MatriculaBean;
import br.com.usjt.ead.material.MaterialDidaticoBean;
import br.com.usjt.ead.professor.ProfessorBean;
import br.com.usjt.ead.professor.ProfessorRest;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.jaxrs.security.SecurityPublic;
import br.com.usjt.shiro.Security;
import br.com.usjt.shiro.SecurityShiro;
import br.com.usjt.util.HS;
import br.com.usjt.util.Utils;

import com.aspose.pdf.kit.PdfContentEditor;

@Path("/curso")
public class DisciplinaRest
{

    private static final long   oneDay             = 86400000L;
    private static final String CHATURI            = "http://127.0.0.1:443/?0,%s,0,13,2";
    private static final String UPLOADED_FILE_PATH = "C:" + File.separator + "Users" + File.separator + "Public" + File.separator
                                                           + "Documents" + File.separator + "tcc" + File.separator;
    private static final String CERTIFICADO_URL    = "C:" + File.separator + "Users" + File.separator + "Public" + File.separator
                                                           + "Documents" + File.separator + "tcc" + File.separator
                                                           + "certificados" + File.separator;
    private static final Logger LOG                = LoggerFactory.getLogger(DisciplinaRest.class);

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

    @Path("cadastrar")
    @GET
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_insert() {
        JSPAttr j = new JSPAttr("metodo", "create");
    }

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

    @Path("create")
    @POST
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    @Consumes("multipart/form-data")
    public void create(MultipartFormDataInput input) {
        JSPAttr j = new JSPAttr();
        DisciplinaBean objDisciplina = new DisciplinaBean();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
        Security sh = SecurityShiro.init();
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        try {
            objDisciplina.setNome_disciplina(uploadForm.get("txtNomeDisciplina").get(0).getBodyAsString());
            objDisciplina.setData_inicio(dtFormat.parse(uploadForm.get("txtDataInicio").get(0).getBodyAsString()));
            objDisciplina.setData_termino(dtFormat.parse(uploadForm.get("txtDataTermino").get(0).getBodyAsString()));
            objDisciplina.setDescricao(uploadForm.get("txtDesc").get(0).getBodyAsString());
            objDisciplina.setProfessor((ProfessorBean) session.load(ProfessorBean.class, sh.getUserId()));
            objDisciplina = populaOsModulosCreate(j, objDisciplina, uploadForm);
            if (Utils.isValid(objDisciplina)) {
                // XXX Descomentar
                // DefaultHttpClient httpclient = new DefaultHttpClient();
                // HttpGet get = new
                // HttpGet("http://127.0.0.1:443/?api.SaveConfiguration");
                // HttpResponse resp = httpclient.execute(get);
                // HttpEntity entity = resp.getEntity();
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

    private ModuloBean criaChat(ModuloBean mod, HttpClient httpClient, String nivel) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // HttpGet get = new HttpGet("http://127.0.0.1:443/?api.AddRoom="
        // + mod.getDisciplina().getNome_disciplina().replaceAll(" ", "%20") +
        // "," + nivel + ",,true");
        // HttpResponse resp = httpClient.execute(get);
        // HttpEntity entity = resp.getEntity();
        // SalaChatBean sala = new SalaChatBean();
        // String roomId = EntityUtils.toString(entity).replaceAll("ID: ",
        // "").trim();
        // String uri = String.format(CHATURI, roomId);
        // sala.setModulo(mod);
        // sala.setId_chat(roomId);
        // sala.setUri(uri);
        // sala.setDias("seg, ter, qua, qui, sex, sab, dom");
        // sala.setHorario(new Time(sdf.parse("19:00:00").getTime()));
        // sala.setHorario_termino(new Time(sdf.parse("23:59:00").getTime()));
        // mod.setChat(sala);

        return mod;
    }

    private void adicionaMaterial(ModuloBean mod, List<InputPart> inputParts) {
        for (InputPart inputPart : inputParts) {
            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);

                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.readBytesFromStream(inputStream);

                // constructs upload file path
                fileName = UPLOADED_FILE_PATH + mod.getDisciplina().getNome_disciplina() + File.separator + fileName;
                MaterialDidaticoBean mat = writeFile(bytes, fileName, UPLOADED_FILE_PATH
                        + mod.getDisciplina().getNome_disciplina());
                mat.setModulo(mod);
                mod.getMaterial().add(mat);
            }
            catch (IOException e) {
                LOG.error("Falha ao salvar arquivo", e);
            }
        }
    }

    /**
     * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
     * name="file"; filename="filename.extension"] }
     **/
    // get uploaded filename, is there a easy way in RESTEasy?
    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    // save to somewhere
    private MaterialDidaticoBean writeFile(byte[] content, String filename, String folder) throws IOException {
        MaterialDidaticoBean b = new MaterialDidaticoBean();
        File folderd = new File(folder);
        if (!folderd.exists()) {
            folderd.mkdir();
        }
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

        b.setEndereco_material(file.getAbsolutePath());
        b.setNome(file.getName());
        return b;
    }

    @SecurityPublic
    @GET
    @POST
    @Stylesheet(href = "curso/video.jsp", type = MediaTypeMore.APP_JSP)
    @Path("video")
    public void video() {
        JSPAttr j = new JSPAttr();
        j.set("video", "http://flightpass.higherplaneproductions.com/gallery2/g2data/albums/FreeAll/freewmv/JoeNall10_3DH-_W.wmv");
    }

    private int tipoArquivo(String filename) {
        if (filename.endsWith("pdf")) {

        }
        else if (filename.endsWith("doc") || filename.endsWith("docx") || filename.endsWith("odf")) {

        }
        else if (filename.endsWith("ogv") || filename.endsWith("webm") || filename.endsWith("mp4")) {

        }
        return 0;
    }

    private DisciplinaBean populaOsModulosCreate(JSPAttr j, DisciplinaBean objDisciplina, Map<String, List<InputPart>> uploadForm)
            throws Exception {
        Iterator<ModuloBean> it = objDisciplina.getModulos().iterator();
        ModuloBean basico = new ModuloBean();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if (it.hasNext()) {
            basico = it.next();
        }
        else {
            basico.setDisciplina(objDisciplina);
            basico.setNivel_modulo(1);
            basico.setData_inicio(objDisciplina.getData_inicio());
            basico.setData_termino(objDisciplina.getData_termino());
        }
        if (((!Utils.isEmpty(uploadForm.get("qtdquestoesBas").get(0).getBodyAsString()) && !Utils.isEmpty(uploadForm
                .get("qtdaltBas").get(0).getBodyAsString())))) {
            AvaliacaoBean b = new AvaliacaoBean();
            b.setModulo(basico);
            for (int i = 1; i <= Integer.parseInt(uploadForm.get("qtdquestoesBas").get(0).getBodyAsString()); i++) {
                QuestaoBean questao = new QuestaoBean();
                questao.setAvaliacao(b);
                questao.setConteudo(uploadForm.get("txtquestoesBasicoquest" + i).get(0).getBodyAsString());
                int certa = Integer.parseInt(uploadForm.get("optquestoesBasico" + i).get(0).getBodyAsString());
                for (int k = 1; k <= Integer.parseInt(uploadForm.get("qtdaltBas").get(0).getBodyAsString()); k++) {
                    AlternativaBean alternativa = new AlternativaBean();
                    alternativa.setConteudo(uploadForm.get("txtquestoesBasicoquest" + i + "alt" + k).get(0).getBodyAsString());
                    alternativa.setQuestao(questao);
                    if (k == certa) {
                        alternativa.setCorreta(true);
                    }
                    questao.getAlternativas().add(alternativa);
                }
                b.getQuestoes().add(questao);
            }
            adicionaMaterial(basico, uploadForm.get("matBasico"));

            //Setando a quantidade de questoes que serão exibidas na avaliação
            b.setQtde_questoes(Integer.parseInt(uploadForm.get("txtQtdQuestoesExibidas").get(0).getBodyAsString()));
            
            basico.setAvaliacao(b);
            basico = criaChat(basico, httpclient, "basico");
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
        if (((!Utils.isEmpty(uploadForm.get("qtdquestoesInt").get(0).getBodyAsString()) && !Utils.isEmpty(uploadForm
                .get("qtdaltInt").get(0).getBodyAsString())))) {
            AvaliacaoBean inte = new AvaliacaoBean();
            inte.setModulo(intermediario);
            for (int i = 1; i <= Integer.parseInt(uploadForm.get("qtdquestoesInt").get(0).getBodyAsString()); i++) {
                QuestaoBean questao = new QuestaoBean();
                questao.setAvaliacao(inte);
                questao.setConteudo(uploadForm.get("txtquestoesIntermediarioquest" + i).get(0).getBodyAsString());
                int certa = Integer.parseInt(uploadForm.get("optquestoesIntermediario" + i).get(0).getBodyAsString());
                for (int k = 1; k <= Integer.parseInt(uploadForm.get("qtdaltInt").get(0).getBodyAsString()); k++) {
                    AlternativaBean alternativa = new AlternativaBean();
                    alternativa.setConteudo(uploadForm.get("txtquestoesIntermediarioquest" + i + "alt" + k).get(0)
                            .getBodyAsString());
                    alternativa.setQuestao(questao);
                    if (k == certa) {
                        alternativa.setCorreta(true);
                    }
                    questao.getAlternativas().add(alternativa);
                }
                inte.getQuestoes().add(questao);
            }
            adicionaMaterial(intermediario, uploadForm.get("matIntermediario"));
            intermediario = criaChat(intermediario, httpclient, "Intermediário");
            
            //Setando a quantidade de questoes que serão exibidas na avaliação
            inte.setQtde_questoes(Integer.parseInt(uploadForm.get("txtQtdQuestoesExibidas").get(0).getBodyAsString()));
            
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
        if (((!Utils.isEmpty(uploadForm.get("qtdquestoesAdv").get(0).getBodyAsString()) && !Utils.isEmpty(uploadForm
                .get("qtdaltAdv").get(0).getBodyAsString())))) {
            AvaliacaoBean adv = new AvaliacaoBean();
            adv.setModulo(avancado);
            for (int i = 1; i <= Integer.parseInt(uploadForm.get("qtdquestoesAdv").get(0).getBodyAsString()); i++) {
                QuestaoBean questao = new QuestaoBean();
                questao.setAvaliacao(adv);
                questao.setConteudo(uploadForm.get("txtquestoesAvancadoquest" + i).get(0).getBodyAsString());
                int certa = Integer.parseInt(uploadForm.get("optquestoesAvancado" + i).get(0).getBodyAsString());
                for (int k = 1; k <= Integer.parseInt(uploadForm.get("qtdaltInt").get(0).getBodyAsString()); k++) {
                    AlternativaBean alternativa = new AlternativaBean();
                    alternativa.setConteudo(uploadForm.get("txtquestoesAvancadoquest" + i + "alt" + k).get(0).getBodyAsString());
                    alternativa.setQuestao(questao);
                    if (k == certa) {
                        alternativa.setCorreta(true);
                    }
                    questao.getAlternativas().add(alternativa);
                }
                adv.getQuestoes().add(questao);
            }
            adicionaMaterial(avancado, uploadForm.get("matAvancado"));
            avancado = criaChat(avancado, httpclient, "Avançado");
            
            
            //Setando a quantidade de questoes que serão exibidas na avaliação
            adv.setQtde_questoes(Integer.parseInt(uploadForm.get("txtQtdQuestoesExibidas").get(0).getBodyAsString()));
            
            avancado.setAvaliacao(adv);
        }
        objDisciplina.getModulos().add(basico);
        objDisciplina.getModulos().add(intermediario);
        objDisciplina.getModulos().add(avancado);
        return objDisciplina;
    }

    private DisciplinaBean populaOsModulosUpdate(JSPAttr j, DisciplinaBean objDisciplina, Session session) {
        ModuloBean basico = objDisciplina.getModuloByLevel(1);
        if (basico == null) {
            basico = new ModuloBean();
            basico.setDisciplina(objDisciplina);
            basico.setNivel_modulo(1);
            basico.setData_inicio(objDisciplina.getData_inicio());
            basico.setData_termino(objDisciplina.getData_termino());
        }
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesBas")) && !Utils.isEmpty(j.getParameter("qtdaltBas"))))) {
            AvaliacaoBean b = basico.getAvaliacao();
            if (b == null) {
                b = new AvaliacaoBean();
                b.setModulo(basico);
            }
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

        ModuloBean intermediario = objDisciplina.getModuloByLevel(2);
        if (intermediario == null) {
            intermediario = new ModuloBean();
            intermediario.setDisciplina(objDisciplina);
            intermediario.setNivel_modulo(2);
            intermediario.setData_inicio(objDisciplina.getData_inicio());
            intermediario.setData_termino(objDisciplina.getData_termino());
        }
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesInt")) && !Utils.isEmpty(j.getParameter("qtdaltInt"))))) {
            AvaliacaoBean inte = intermediario.getAvaliacao();
            if (inte == null) {
                inte = new AvaliacaoBean();
                inte.setModulo(intermediario);
            }
            inte.getQuestoes().clear();
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
        ModuloBean avancado = objDisciplina.getModuloByLevel(3);
        if (avancado == null) {
            avancado = new ModuloBean();
            avancado.setDisciplina(objDisciplina);
            avancado.setNivel_modulo(3);
            avancado.setData_inicio(objDisciplina.getData_inicio());
            avancado.setData_termino(objDisciplina.getData_termino());
        }
        if (((!Utils.isEmpty(j.getParameter("qtdquestoesAdv")) && !Utils.isEmpty(j.getParameter("qtdaltAdv"))))) {
            AvaliacaoBean adv = avancado.getAvaliacao();
            if (adv == null) {
                adv = new AvaliacaoBean();
                adv.setModulo(avancado);
            }
            adv.getQuestoes().clear();
            for (int i = 1; i <= Integer.parseInt(j.getParameter("qtdquestoesAdv")); i++) {
                QuestaoBean questao = new QuestaoBean();
                questao.setAvaliacao(adv);
                questao.setConteudo(j.getParameter("txtquestoesAvancadoquest" + i));
                int certa = Integer.parseInt(j.getParameter("optquestoesAvancado" + i));
                for (int k = 1; k <= Integer.parseInt(j.getParameter("qtdaltAdv")); k++) {
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
        return objDisciplina;
    }

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
            disciplina = populaOsModulosUpdate(j, disciplina, session);
            disciplina.setNome_disciplina(j.getParameter("txtNomeDisciplina"));
            if (Utils.isValid(disciplina)) {
                session.update(disciplina);
                tx.commit();
                j.sucessMsg();
            }
            else {
                j.errorMsg();
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
            
            List<QuestaoBean> listQuestoes = m.getModulo().getAvaliacao().getQuestoes();
            Collections.shuffle(listQuestoes);
            
            List<QuestaoBean> listQuestoesRandomizadas = new ArrayList<QuestaoBean>();

            for (int questoesAdicionadas = 0; questoesAdicionadas < m.getModulo().getAvaliacao().getQtde_questoes(); questoesAdicionadas++)
            {
                listQuestoesRandomizadas.add(listQuestoes.get(questoesAdicionadas));
            }
            
            j.set("questoes", listQuestoesRandomizadas);
            j.set("modulo", m.getModulo());
            j.set("matricula", m);
            
            //Salvo na sessão para quando for corrigir a prova utilizar a mesma lista
            j.setInSession("questoes", listQuestoesRandomizadas);
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

    @Path("bloquear")
    @POST
    @Stylesheet(href = "curso/avaliacao.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ALUNO })
    public void BloquearAluno() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            MatriculaBean m = (MatriculaBean) session.get(MatriculaBean.class, Integer.parseInt(j.getParameter("matricula")));

            BloqueioBean bloqueio = new BloqueioBean();

            bloqueio.setAluno(m.getAluno());
            bloqueio.setDisciplina(m.getModulo().getDisciplina());

            session.save(bloqueio);
            tx.commit();
        }
        catch (Exception e) {
            LOG.error("Falha na operação", e);
            tx.rollback();
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
            List<QuestaoBean> listQuestoes = new ArrayList<QuestaoBean>();
            listQuestoes = (List<QuestaoBean>) j.getFromSession("questoes");
            
            MatriculaBean m = (MatriculaBean) session.get(MatriculaBean.class, Integer.parseInt(j.getParameter("matricula")));
            
            if (m.getDt_avaliacao().before(new Date())) {
                int acertos = 0;
                int qtdAlt = listQuestoes.size();
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
                                   
                for (QuestaoBean questao : listQuestoes) {
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
                    m.setDt_avaliacao(new Date());
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
                        b.setModulo((ModuloBean) session
                                .createQuery(
                                        "from " + ModuloBean.class.getSimpleName()
                                                + " m where m.disciplina.id_disciplina = :disciplina and m.nivel_modulo = :nivel")
                                .setInteger("disciplina", m.getModulo().getDisciplina().getId_disciplina())
                                .setInteger("nivel", nivel).uniqueResult());
                        session.save(b);
                    }
                }
                else {
                    m.setDt_avaliacao(new Date(new Date().getTime() + 2 * oneDay));

                    int qtde_falha = m.getQtde_falha();
                    if (qtde_falha >= 3) {
                        AlunoRest aluno = new AlunoRest();

                        BloquearAluno();

                        aluno.desmatricular(m.getModulo().getDisciplina().getId_disciplina());

                        j.errorMsg("Você falhou em obter a porcentagem mínima de acerto por 3 vezes, você será desmatrículado do curso");
                    }
                    else {
                        qtde_falha += 1;
                        m.setQtde_falha(qtde_falha);

                        j.errorMsg("Você falhou em obter a porcentagem mínima de acerto(" + pctNec
                                + "%). Não se desanime, estude mais e tente novamente em 48 horas");
                    }
                }
                session.update(m);
                tx.commit();
            }
            else {
                j.errorMsg("Não tente trapacear. Espere até o dia da prova =)");
            }
        }
        catch (Exception e) {
            LOG.error("Falha na operação", e);
        }
        finally {
            if (session.isOpen()) {
                session.clear();
                session.close();
                j.removeFromSession("questoes");
            }
            new AlunoRest().meusCursos();
        }
    }

    public static void main(String[] args) throws Exception{
        PdfContentEditor editor = new PdfContentEditor();
        editor.bindPdf(ResourceUtils.getInputStreamForPath("Template.pdf"));
        editor.replaceText("[Nome]", "Renan");
        editor.replaceText("[Nome do Curso]", "Java");
        editor.save(CERTIFICADO_URL + "replace.pdf");
    }
}
