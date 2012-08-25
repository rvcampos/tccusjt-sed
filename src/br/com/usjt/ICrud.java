package br.com.usjt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.Entidade;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;

public interface ICrud
{

    @Path("read")
    @POST
    @GET
    @Stylesheet(href="/read.jsp", type=MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.LER, entity=Entidade.DUMMY)
    public void read();

    @Path("detalha")
    @POST
    @Stylesheet(href="/read.jsp", type=MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.DETALHAR, entity=Entidade.DUMMY)
    public void edit_update();

    @Path("adicionar")
    @GET
    @Stylesheet(href="/adicionar.jsp", type=MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.CRIAR, entity=Entidade.DUMMY)
    public void edit_insert();

    @Path("delete")
    @POST
    @Stylesheet(href="/read.jsp", type=MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.DELETAR, entity=Entidade.DUMMY)
    public void delete();

    @Path("create")
    @POST
    @Stylesheet(href="/read.jsp", type=MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.CRIAR, entity=Entidade.DUMMY)
    public void create();

    @Path("update")
    @POST
    @Stylesheet(href="/read.jsp", type=MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.UPDATE, entity=Entidade.DUMMY)
    public void update();
}
