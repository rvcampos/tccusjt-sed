package br.com.usjt.shiro;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.jboss.resteasy.spi.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPublic;

/**
 * JAXRS Login
 */
@Path ("/")
public class ShiroLogin {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroLogin.class);

    /**
     * Exibi login
     */
    @GET
    @Stylesheet (href = "login/login.jsp", type = MediaTypeMore.APP_JSP)
    @Path ("{entidade}/login")
    @SecurityPublic
    public void getLogin(@PathParam("entidade") String entidade) {}

    private static final String MSG = "SHIRO JAXRS LOGIN";

    /**
     * Autenticar login
     * 
     * @param username
     * @param password
     */
    @POST
    @Path ("{entidade}/login")
    @Stylesheet (href = "login/login.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void doLogin(@PathParam("entidade") String entidade , @FormParam ("username") String username, @FormParam ("password") String password) {
        String rt = ShiroLogin.MSG;
        try {
            Security sh = SecurityShiro.init();
            int ent = 2;
            if(entidade.equals("professor"))
            {
                ent = 1;
            }
            if (!sh.login(username, password,ent)) { throw new Failure(HttpServletResponse.SC_UNAUTHORIZED); }
            rt = "Logado";
        }
        catch (Failure e) {
            rt = "Não autorizado";
        }
        catch (Exception e) {
            ShiroLogin.LOG.error(ShiroLogin.MSG, e);
            rt += "|" + e.getMessage();
        }
        new JSPAttr("msg", rt);
    }

    /**
     * Logout
     */
    @GET
    @Path ("/logout")
    @Stylesheet (href = "login/login.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void doLogout() {
        String rt = ShiroLogin.MSG;
        try {
            SecurityShiro.init().logout();
            rt = "Logout";
        }
        catch (Exception e) {
            ShiroLogin.LOG.error(ShiroLogin.MSG, e);
            rt += "|" + e.getMessage();
        }
        new JSPAttr("msg", rt);
    }
    
}
