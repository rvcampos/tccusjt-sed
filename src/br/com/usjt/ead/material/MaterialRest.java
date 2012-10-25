package br.com.usjt.ead.material;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.curso.DisciplinaRest;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.jaxrs.security.SecurityPublic;
import br.com.usjt.util.HS;

@Path("/material")
public class MaterialRest implements ICrud
{

    @Override
    @Path("read")
    @POST
    @GET
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.ALUNO, SecType.PROFESSOR })
    public void read() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("detalha")
    @POST
    @Stylesheet(href = "/detalhar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_update() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "material/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void edit_insert() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("delete")
    @POST
    @Stylesheet(href = "curso/adicionarMateriais.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void delete() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        Integer id = Integer.parseInt(j.getParameter("material"));
        try {
            MaterialDidaticoBean mat = (MaterialDidaticoBean) session.get(MaterialDidaticoBean.class, id);
            File f = new File(mat.getEndereco_material());
            session.delete(mat);
            tx.commit();
            f.delete();
            j.sucessMsg("Material deletado com sucesso");
        }
        catch (Exception e) {
            tx.rollback();
        }
        finally {
            session.close();
            new DisciplinaRest().materiais(Integer.parseInt(j.getParameter("id_disciplina")));
        }
    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void create() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void update() {
        // TODO Auto-generated method stub
    }

    @Path("download")
    @POST
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    // @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO
    // })
    @SecurityPublic
    public Response download() {
        JSPAttr j = new JSPAttr();
        Integer id_material = Integer.parseInt(j.getParameter("material"));
        Session session = HS.getSession();

        try {
            MaterialDidaticoBean b = (MaterialDidaticoBean) session.get(MaterialDidaticoBean.class, id_material);
            File file = new File(b.getEndereco_material());

            ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment; filename=" + b.getNome());
            return response.build();
        }
        catch (Exception e) {
            // TODO LOGAR
        }
        finally {
            session.close();
        }

        return null;
    }

    @Path("visualizar")
    @POST
    @GET
    // @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO
    // })
    @SecurityPublic
    @Produces("*/*")
    public InputStream visualizar() throws FileNotFoundException {
        JSPAttr j = new JSPAttr();
        Integer id_material = Integer.parseInt(j.getParameter("material"));
        Session session = HS.getSession();
        try {
            MaterialDidaticoBean b = (MaterialDidaticoBean) session.get(MaterialDidaticoBean.class, id_material);
            File file = new File(b.getEndereco_material());

            HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);
            response.addHeader("Content-Length", "" + file.length());

            response.setHeader("Content-Disposition", "inline; filename=" + b.getNome());
            if (b.getNome().endsWith("pdf")) {
                response.setContentType("application/pdf");
            }
            else if (b.getNome().endsWith("doc") || b.getNome().endsWith("docx") || b.getNome().endsWith("odt")) {
                response.setContentType("application/pdf");
            }
            else if (b.getNome().endsWith("ppt")) {
                response.setContentType("application/vnd.ms-powerpoint");
            }
            else {
                response.setContentType("application/octet-stream");
            }

            InputStream st = new FileInputStream(file);
            return st;
        }
        catch (Exception e) {
            // TODO LOGAR
        }
        finally {
            session.close();
        }

        return null;
    }
}
