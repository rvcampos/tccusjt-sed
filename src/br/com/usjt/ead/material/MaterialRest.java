package br.com.usjt.ead.material;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.usjt.ICrud;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;

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
    @SecurityPrivate(role={SecType.ADMIN, SecType.PROFESSOR})
    public void edit_update() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "material/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role={SecType.ADMIN, SecType.PROFESSOR})
    public void edit_insert() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("delete")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role={SecType.ADMIN, SecType.PROFESSOR})
    public void delete() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role={SecType.ADMIN, SecType.PROFESSOR})
    public void create() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role={SecType.ADMIN, SecType.PROFESSOR})
    public void update() {
        // TODO Auto-generated method stub

    }

}
