package br.com.usjt.ead.curso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.helpers.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ead.EntityDAO;
import br.com.usjt.ead.aluno.AlunoRest;
import br.com.usjt.ead.aluno.EmailDuvidasBean;
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
                    populaEditUpdate(j, 1, mod);
                    populaChatEditUpdate(j, mod, "txtBasic");
                    break;

                case 2:
                    j.set("intermediario", mod);
                    populaEditUpdate(j, 2, mod);
                    populaChatEditUpdate(j, mod, "txtInt");
                    break;

                case 3:
                    j.set("avancado", mod);
                    populaEditUpdate(j, 3, mod);
                    populaChatEditUpdate(j, mod, "txtAdv");
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

    private void populaChatEditUpdate(JSPAttr j, ModuloBean mod, String txtString) {
        SalaChatBean sala = mod.getChat();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (sala.getInicio_domingo() != null) {
            j.set(txtString + "HoraInicioDomingo", sdf.format(sala.getInicio_domingo()));
            j.set(txtString + "HoraTerminoDomingo", sdf.format(sala.getTermino_domingo()));
        }
        if (sala.getInicio_segunda() != null) {
            j.set(txtString + "HoraInicioSegunda", sdf.format(sala.getInicio_segunda()));
            j.set(txtString + "HoraTerminoSegunda", sdf.format(sala.getTermino_segunda()));
        }
        if (sala.getInicio_terca() != null) {
            j.set(txtString + "HoraInicioTerca", sdf.format(sala.getInicio_terca()));
            j.set(txtString + "HoraTerminoTerca", sdf.format(sala.getTermino_terca()));
        }
        if (sala.getInicio_quarta() != null) {
            j.set(txtString + "HoraInicioQuarta", sdf.format(sala.getInicio_quarta()));
            j.set(txtString + "HoraTerminoQuarta", sdf.format(sala.getTermino_quarta()));
        }
        if (sala.getInicio_quinta() != null) {
            j.set(txtString + "HoraInicioQuinta", sdf.format(sala.getInicio_quinta()));
            j.set(txtString + "HoraTerminoQuinta", sdf.format(sala.getTermino_quinta()));
        }
        if (sala.getInicio_sexta() != null) {
            j.set(txtString + "HoraInicioSexta", sdf.format(sala.getInicio_sexta()));
            j.set(txtString + "HoraTerminoSexta", sdf.format(sala.getTermino_sexta()));
        }
        if (sala.getInicio_sabado() != null) {
            j.set(txtString + "HoraInicioSabado", sdf.format(sala.getInicio_sabado()));
            j.set(txtString + "HoraTerminoSabado", sdf.format(sala.getTermino_sabado()));
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
    public void create() {
        JSPAttr j = new JSPAttr();
        DisciplinaBean objDisciplina = new DisciplinaBean();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
        Security sh = SecurityShiro.init();
        try {
            objDisciplina.setNome_disciplina(j.getParameter("txtNomeDisciplina"));
            try {
                objDisciplina.setProfessor((ProfessorBean) session.load(ProfessorBean.class, sh.getUserId()));
                objDisciplina.setDescricao(j.getParameter("txtDesc"));
                objDisciplina.setData_inicio(dtFormat.parse(j.getParameter("txtDataInicio")));
                objDisciplina.setData_termino(dtFormat.parse(j.getParameter("txtDataTermino")));
                objDisciplina = populaOsModulosCreate(j, objDisciplina);
            }
            catch (Exception e) {
                LOG.error("Erro ao criar o curso", e);
            }
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

    private ModuloBean criaChat(ModuloBean mod, HttpClient httpClient, String chkString, String txtString, JSPAttr j)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SalaChatBean sala = new SalaChatBean();
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

        if(mod.getChat() != null)
        {
            sala = mod.getChat();
        }
        if (!Utils.isEmpty(j.getParameter(chkString + "Domingo"))) {
            sala.setInicio_domingo(new Time(sdf.parse(j.getParameter(txtString + "HoraInicioDomingo")).getTime()));
            sala.setTermino_domingo(new Time(sdf.parse(j.getParameter(txtString + "HoraTerminoDomingo")).getTime()));
        }
        if (!Utils.isEmpty(j.getParameter(chkString + "Segunda"))) {
            sala.setInicio_segunda(new Time(sdf.parse(j.getParameter(txtString + "HoraInicioSegunda")).getTime()));
            sala.setTermino_segunda(new Time(sdf.parse(j.getParameter(txtString + "HoraTerminoSegunda")).getTime()));
        }
        if (!Utils.isEmpty(j.getParameter(chkString + "Terca"))) {
            sala.setInicio_terca(new Time(sdf.parse(j.getParameter(txtString + "HoraInicioTerca")).getTime()));
            sala.setTermino_terca(new Time(sdf.parse(j.getParameter(txtString + "HoraTerminoTerca")).getTime()));
        }
        if (!Utils.isEmpty(j.getParameter(chkString + "Quarta"))) {
            sala.setInicio_quarta(new Time(sdf.parse(j.getParameter(txtString + "HoraInicioQuarta")).getTime()));
            sala.setTermino_quarta(new Time(sdf.parse(j.getParameter(txtString + "HoraTerminoQuarta")).getTime()));
        }
        if (!Utils.isEmpty(j.getParameter(chkString + "Quinta"))) {
            sala.setInicio_quinta(new Time(sdf.parse(j.getParameter(txtString + "HoraInicioQuinta")).getTime()));
            sala.setTermino_quinta(new Time(sdf.parse(j.getParameter(txtString + "HoraTerminoQuinta")).getTime()));
        }
        if (!Utils.isEmpty(j.getParameter(chkString + "Sexta"))) {
            sala.setInicio_sexta(new Time(sdf.parse(j.getParameter(txtString + "HoraInicioSexta")).getTime()));
            sala.setTermino_sexta(new Time(sdf.parse(j.getParameter(txtString + "HoraTerminoSexta")).getTime()));
        }
        if (!Utils.isEmpty(j.getParameter(chkString + "Sabado"))) {
            sala.setInicio_sabado(new Time(sdf.parse(j.getParameter(txtString + "HoraInicioSabado")).getTime()));
            sala.setTermino_sabado(new Time(sdf.parse(j.getParameter(txtString + "HoraTerminoSabado")).getTime()));
        }
        if (httpClient != null) {
            sala.setUri("www.google.com.br");
            sala.setId_chat("1234");
            sala.setModulo(mod);
        }
        mod.setChat(sala);
        return mod;
    }

    private void adicionaMaterial(ModuloBean mod, List<InputPart> inputParts) {
        if (inputParts != null && !inputParts.isEmpty()) {
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
    
    private void populaEditUpdate(JSPAttr j, Integer nivel, ModuloBean mod)
    {
        String quest = "txtquestoesBasicoquest";
        String qtdQuest = "qtdquestoesBas";
        String qtdAlt = "qtdaltBas";
        String opt = "optquestoesBasico";
        String qtdMostra = "txtQtdQuestoesExibidasBas";
        String nmChkChat = "chkBasic";
        String txtChat = "txtBasic";
        switch (nivel)
        {
        case 2:
            quest = "txtquestoesIntermediarioquest";
            qtdQuest = "qtdquestoesInt";
            qtdAlt = "qtdaltInt";
            opt = "optquestoesIntermediario";
            qtdMostra = "txtQtdQuestoesExibidasInt";
            nmChkChat = "chkInt";
            txtChat = "txtInt";
            break;

        case 3:
            quest = "txtquestoesAvancadoquest";
            qtdQuest = "qtdquestoesAdv";
            qtdAlt = "qtdaltAdv";
            opt = "optquestoesAvancado";
            qtdMostra = "txtQtdQuestoesExibidasAdv";
            nmChkChat = "chkAdv";
            txtChat = "txtAdv";
            break;
        }
        if (mod.getAvaliacao() != null) {
            AvaliacaoBean b = mod.getAvaliacao();
            j.set(qtdAlt, mod.getAvaliacao().getQuestoes().get(0).getAlternativas().size());
            j.set(qtdQuest, b.getQuestoes().size());
            int i = 1;
            for (QuestaoBean questao : mod.getAvaliacao().getQuestoes()) {
                j.set(quest + i, questao.getConteudo());
                int k = 1;
                for (AlternativaBean alternativa : questao.getAlternativas()) {
                    j.set(quest + i + "alt" + k,alternativa.getConteudo());
                    if (alternativa.getCorreta()) {
                       j.set(opt + i, k);
                    }
                }
                i++;
            }
            // Setando a quantidade de questoes que serão exibidas na avaliação
            j.set(qtdMostra,b.getQtde_questoes());
        }
    }

    private void criaModulo(DisciplinaBean d, Integer nivel, JSPAttr j, HttpClient httpclient) throws Exception {
        String quest = "txtquestoesBasicoquest";
        String qtdQuest = "qtdquestoesBas";
        String qtdAlt = "qtdaltBas";
        String opt = "optquestoesBasico";
        String nivelstr = "basico";
        String qtdMostra = "txtQtdQuestoesExibidasBas";
        String nmChkChat = "chkBasic";
        String txtChat = "txtBasic";
        switch (nivel)
        {
        case 2:
            quest = "txtquestoesIntermediarioquest";
            qtdQuest = "qtdquestoesInt";
            qtdAlt = "qtdaltInt";
            opt = "optquestoesIntermediario";
            nivelstr = "intermediario";
            qtdMostra = "txtQtdQuestoesExibidasInt";
            nmChkChat = "chkInt";
            txtChat = "txtInt";
            break;

        case 3:
            quest = "txtquestoesAvancadoquest";
            qtdQuest = "qtdquestoesAdv";
            qtdAlt = "qtdaltAdv";
            opt = "optquestoesAvancado";
            nivelstr = "avançado";
            qtdMostra = "txtQtdQuestoesExibidasAdv";
            nmChkChat = "chkAdv";
            txtChat = "txtAdv";
            break;
        }
        ModuloBean mod = new ModuloBean();
        mod.setDisciplina(d);
        mod.setNivel_modulo(nivel);
        mod.setData_inicio(d.getData_inicio());
        mod.setData_termino(d.getData_termino());
        if (((!Utils.isEmpty(j.getParameter(qtdQuest)) && !Utils.isEmpty(j.getParameter(qtdAlt))))) {
            AvaliacaoBean b = new AvaliacaoBean();
            b.setModulo(mod);
            int qtdAlti = Integer.parseInt(j.getParameter(qtdAlt));
            for (int i = 1; i <= Integer.parseInt(j.getParameter(qtdQuest)); i++) {
                String questaoCont = j.getParameter(quest + i);
                QuestaoBean questao = new QuestaoBean();
                questao.setAvaliacao(b);
                questao.setConteudo(questaoCont);
                int certa = Integer.parseInt(j.getParameter(opt + i));
                for (int k = 1; k <= qtdAlti; k++) {
                    AlternativaBean alternativa = new AlternativaBean();
                    alternativa.setConteudo(j.getParameter(quest + i + "alt" + k));
                    alternativa.setQuestao(questao);
                    if (k == certa) {
                        alternativa.setCorreta(true);
                    }
                    questao.getAlternativas().add(alternativa);
                }
                b.getQuestoes().add(questao);
            }
            // adicionaMaterial(basico, j.getParameter("matBasico"));

            // Setando a quantidade de questoes que serão exibidas na avaliação
            b.setQtde_questoes(Integer.parseInt(j.getParameter(qtdMostra)));

            mod.setAvaliacao(b);
            mod = criaChat(mod, httpclient, nmChkChat, txtChat, j);
        }
        d.getModulos().add(mod);
    }

    private void updModulo(DisciplinaBean d, Integer nivel, JSPAttr j) throws Exception {
        String quest = "txtquestoesBasicoquest";
        String qtdQuest = "qtdquestoesBas";
        String qtdAlt = "qtdaltBas";
        String opt = "optquestoesBasico";
        String qtdMostra = "txtQtdQuestoesExibidasBas";
        String nmChkChat = "chkBasic";
        String txtChat = "txtBasic";
        switch (nivel)
        {
        case 2:
            quest = "txtquestoesIntermediarioquest";
            qtdQuest = "qtdquestoesInt";
            qtdAlt = "qtdaltInt";
            opt = "optquestoesIntermediario";
            qtdMostra = "txtQtdQuestoesExibidasInt";
            nmChkChat = "chkInt";
            txtChat = "txtInt";
            break;

        case 3:
            quest = "txtquestoesAvancadoquest";
            qtdQuest = "qtdquestoesAdv";
            qtdAlt = "qtdaltAdv";
            opt = "optquestoesAvancado";
            qtdMostra = "txtQtdQuestoesExibidasAdv";
            nmChkChat = "chkAdv";
            txtChat = "txtAdv";
            break;
        }
        ModuloBean mod = d.getModuloByLevel(nivel);
        mod.setData_inicio(d.getData_inicio());
        mod.setData_termino(d.getData_termino());
        if (((!Utils.isEmpty(j.getParameter(qtdQuest)) && !Utils.isEmpty(j.getParameter(qtdAlt))))) {
            AvaliacaoBean b = mod.getAvaliacao();
            b.getQuestoes().clear();
            int qtdAlti = Integer.parseInt(j.getParameter(qtdAlt));
            for (int i = 1; i <= Integer.parseInt(j.getParameter(qtdQuest)); i++) {
                String questaoCont = j.getParameter(quest + i);
                QuestaoBean questao = new QuestaoBean();
                questao.setAvaliacao(b);
                questao.setConteudo(questaoCont);
                int certa = Integer.parseInt(j.getParameter(opt + i));
                for (int k = 1; k <= qtdAlti; k++) {
                    AlternativaBean alternativa = new AlternativaBean();
                    alternativa.setConteudo(j.getParameter(quest + i + "alt" + k));
                    alternativa.setQuestao(questao);
                    if (k == certa) {
                        alternativa.setCorreta(true);
                    }
                    questao.getAlternativas().add(alternativa);
                }
                b.getQuestoes().add(questao);
            }
            // adicionaMaterial(basico, j.getParameter("matBasico"));

            // Setando a quantidade de questoes que serão exibidas na avaliação
            b.setQtde_questoes(Integer.parseInt(j.getParameter(qtdMostra)));
            mod.setAvaliacao(b);
            mod = criaChat(mod, null, nmChkChat, txtChat, j);
        }
        d.getModulos().add(mod);
    }

    private DisciplinaBean populaOsModulosCreate(JSPAttr j, DisciplinaBean objDisciplina) throws Exception {
        Iterator<ModuloBean> it = objDisciplina.getModulos().iterator();
        ModuloBean basico = new ModuloBean();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // Popula o módulo basico
        criaModulo(objDisciplina, 1, j, httpclient);
        // Popula o módulo Intermediário
        criaModulo(objDisciplina, 2, j, httpclient);
        // Popula o módulo Avançado
        criaModulo(objDisciplina, 3, j, httpclient);
        return objDisciplina;
    }

    private DisciplinaBean populaOsModulosUpdate(JSPAttr j, DisciplinaBean objDisciplina, Session session) throws Exception {
        updModulo(objDisciplina, 1, j);
        updModulo(objDisciplina, 2, j);
        updModulo(objDisciplina, 3, j);
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

            for (int questoesAdicionadas = 0; questoesAdicionadas < m.getModulo().getAvaliacao().getQtde_questoes(); questoesAdicionadas++) {
                listQuestoesRandomizadas.add(listQuestoes.get(questoesAdicionadas));
            }

            j.set("questoes", listQuestoesRandomizadas);
            j.set("modulo", m.getModulo());
            j.set("matricula", m);

            // Salvo na sessão para quando for corrigir a prova utilizar a mesma
            // lista
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

    @Path("materiais")
    @POST
    @Stylesheet(href = "curso/adicionarMateriais.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void materiais(@FormParam("id_disciplina") Integer id) {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        try {
            DisciplinaBean d = (DisciplinaBean) session.get(DisciplinaBean.class, id);
            j.set("disciplina", d);
            j.set("basico", d.getModuloByLevel(1).getMaterial());
            j.set("intermediario", d.getModuloByLevel(2).getMaterial());
            j.set("avancado", d.getModuloByLevel(3).getMaterial());
        }
        catch (Exception e) {
            LOG.error("Falha ao deletar curso", e);
            j.errorMsg();
        }
        finally {
            session.close();
        }
    }

    @Path("upload")
    @POST
    @Stylesheet(href = "curso/adicionarMateriais.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    @Consumes("multipart/form-data")
    public void uploadMaterial(MultipartFormDataInput input) {
        Session session = HS.getSession();
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        Transaction tx = session.beginTransaction();
        Integer id = null;
        try {
            id = Integer.parseInt(uploadForm.get("id_disciplina").get(0).getBodyAsString());
            DisciplinaBean b = (DisciplinaBean) session.get(DisciplinaBean.class, id);
            for (ModuloBean mod : b.getModulos()) {
                switch (mod.getNivel_modulo())
                {
                case 1:
                    adicionaMaterial(mod, uploadForm.get("matBasico"));
                    break;
                case 2:
                    adicionaMaterial(mod, uploadForm.get("matIntermediario"));
                    break;
                case 3:
                    adicionaMaterial(mod, uploadForm.get("matAvancado"));
                    break;
                default:
                    break;
                }
            }
            session.update(b);
            tx.commit();
            new JSPAttr().sucessMsg("Materiais adicionados com sucesso");
        }
        catch (Exception e) {
            tx.rollback();
            LOG.error("Falha na operação", e);
        }
        finally {
            materiais(id);
            session.close();

        }
    }

    @Path("email/criaEmail")
    @POST
    @GET
    @Stylesheet(href = "curso/email/enviaEmail.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ALUNO, SecType.PROFESSOR })
    public void criarEmail(@FormParam("id_matricula") Integer id_matricula, @FormParam("id_email") String id_email) {
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        JSPAttr j = new JSPAttr();
        Security sh = SecurityShiro.init();
        try {
            if (sh.getTipo() == 2) {
                MatriculaBean b = (MatriculaBean) session.get(MatriculaBean.class, id_matricula);
                j.set("de", b.getAluno().getContato().getNome());
                j.set("para", b.getModulo().getDisciplina().getProfessor().getContato().getNome());
                j.set("id_matricula", id_matricula);
                j.set("id_email", j.getParameter("id_email"));
            }
            else {
                EmailDuvidasBean b = (EmailDuvidasBean) session.get(EmailDuvidasBean.class, Long.parseLong(id_email));
                j.set("de", b.getMatricula().getModulo().getDisciplina().getProfessor().getContato().getNome());
                j.set("para", b.getMatricula().getAluno().getContato().getNome());
                j.set("id_curso", b.getMatricula().getModulo().getDisciplina().getId_disciplina());
                j.set("id_email", b.getId_email());
            }
        }
        catch (Exception e) {
            LOG.error("Falha ao listar e-mails", e);
            tx.rollback();
        }
        finally {
            session.close();
        }
    }

    @Path("email/sendMail")
    @POST
    @Stylesheet(href = "curso/email/listarEmails.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ALUNO, SecType.PROFESSOR })
    public void sendEmail(@FormParam("id_matricula") String id_matricula, @FormParam("id_curso") String id_curso,
            @FormParam("txtAssunto") String assunto, @FormParam("id_email") String id_email) {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        Transaction tx = session.beginTransaction();
        Security sh = SecurityShiro.init();
        try {
            EmailDuvidasBean email = new EmailDuvidasBean();
            if (!Utils.isEmpty(id_email)) {
                EmailDuvidasBean principal = (EmailDuvidasBean) session.get(EmailDuvidasBean.class, Long.parseLong(id_email));
                principal.setRespondido(Boolean.FALSE);
                if (sh.getTipo() == 1) {
                    principal.setRespondido(Boolean.TRUE);
                }
                principal.setData(new Date());
                session.update(principal);
                email.setMatricula(principal.getMatricula());
                email.setEmail_origem(principal);
                email.setTop_mail(false);
            }
            else {
                email.setEmail_origem(email);
                email.setTop_mail(true);
            }
            email.setTitulo(assunto);
            email.setConteudo(j.getParameter("txtConteudo"));
            email.setRespondido(false);
            if (email.getMatricula() == null) {
                email.setMatricula((MatriculaBean) session.load(MatriculaBean.class, Integer.parseInt(id_matricula)));
            }
            email.setData(new Date());
            if (Utils.isValid(email)) {
                Utils.sendMail(email);
                session.save(email);
                tx.commit();
            }
            else {
                j.repopular();
                j.set("override", "curso/email/enviaEmail.jsp");
                tx.rollback();
            }
        }
        catch (Exception e) {
            tx.rollback();
            LOG.error("Falha ao listar e-mails", e);
            j.repopular();
        }
        finally {
            session.close();
            if (sh.getTipo() == 1) {
                listarEmail(null, id_curso);
            }
            else {
                listarEmail(id_matricula, null);
            }
        }
    }

    @Path("email/listarEmails")
    @POST
    @GET
    @Stylesheet(href = "curso/email/listarEmails.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ALUNO, SecType.PROFESSOR })
    public void listarEmail(@FormParam("id_matricula") String id_matricula, @FormParam("id_curso") String id_curso) {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        List<EmailDuvidasBean> emails = new ArrayList<EmailDuvidasBean>();
        try {
            Criteria c = session.createCriteria(EmailDuvidasBean.class);
            if (!Utils.isEmpty(id_matricula)) {
                c.add(Restrictions.eq("matricula.id_matricula", Integer.parseInt(id_matricula)));
            }
            else {
                c.createCriteria("matricula", "mat").createCriteria("mat.modulo", "mod")
                        .add(Restrictions.eq("mod.disciplina.id_disciplina", Integer.parseInt(id_curso)));
            }
            emails = c.add(Restrictions.eq("top_mail", Boolean.TRUE)).list();
            j.set("lista_emails", emails);
            j.set("id_matricula", id_matricula);
            j.set("id_curso", id_curso);
        }
        catch (Exception e) {
            LOG.error("Falha ao listar e-mails", e);
        }
        finally {
            session.close();
        }
    }

    @Path("professor/listarEmails")
    @POST
    @GET
    @Stylesheet(href = "curso/email/listarEmails.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void listarEmailsProf(@FormParam("id_curso") Long id_curso) {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        try {
            EmailDuvidasBean e = new EmailDuvidasBean();
            Criteria c = session.createCriteria(EmailDuvidasBean.class);
            c.add(Restrictions.eq("matricula.modulo.disciplina.professor.id_professor", SecurityShiro.init().getUserId()));
            c.add(Restrictions.eq("top_mail", true)).add(Restrictions.eq("respondido", Boolean.FALSE));
            c.addOrder(Order.desc("data"));
        }
        catch (Exception e) {
            LOG.error("Falha ao listar e-mails", e);
        }
        finally {
            session.close();
        }
    }

    @Path("email/detalharEmail")
    @POST
    @GET
    @Stylesheet(href = "curso/email/detalharEmail.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void detalharEmail(@FormParam("id_email") Long id_email) {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        try {
            Criteria c = session.createCriteria(EmailDuvidasBean.class);
            c.add(Restrictions.or(Restrictions.eq("id_email", id_email), Restrictions.eq("email_origem.id_email", id_email)));
            c.setProjection(Projections.rowCount());
            Long size = (Long) c.uniqueResult();
            Integer d = new BigDecimal(size).divide(BigDecimal.valueOf(2), BigDecimal.ROUND_UP).intValue();
            c.setProjection(null);
            c.setResultTransformer(Criteria.ROOT_ENTITY);
            c.addOrder(Order.asc("id_email"));
            j.set("numpages", d);
            j.set("lista_emails", c.list());
            j.set("size", size);
        }
        catch (Exception e) {
            LOG.error("Falha ao listar e-mails", e);
        }
        finally {
            session.close();
        }
    }
}
