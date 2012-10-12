package br.com.usjt.ead.material;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Session;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.usjt.ICrud;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
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
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR })
    public void delete() {
        // TODO Auto-generated method stub

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
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public Response download() {
        JSPAttr j = new JSPAttr();
        Integer id_material = Integer.parseInt(j.getParameter("material"));
        Session session = HS.getSession();
        MaterialDidaticoBean b = (MaterialDidaticoBean) session.get(MaterialDidaticoBean.class, id_material);
        File file = new File(b.getEndereco_material());

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=" + b.getNome());
        return response.build();
    }

}
