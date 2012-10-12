package br.com.usjt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;

public interface ICrud
{

    @Path("read")
    @POST
    @GET
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void read();

    @Path("detalha")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void edit_update();

    @Path("adicionar")
    @GET
    @Stylesheet(href = "/adicionar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void edit_insert();

    @Path("delete")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void delete();

    @Path("create")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void create();

    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void update();
}
