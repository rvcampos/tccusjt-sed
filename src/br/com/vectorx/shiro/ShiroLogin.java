package br.com.vectorx.shiro;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.jboss.resteasy.spi.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vectorx.jaxrs.JSPAttr;
import br.com.vectorx.jaxrs.MediaTypeMore;
import br.com.vectorx.jaxrs.security.Security;
import br.com.vectorx.jaxrs.security.SecurityPublic;

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
    @Path ("/login")
    @SecurityPublic
    public void getLogin() {}

    private static final String MSG = "SHIRO JAXRS LOGIN";

    /**
     * Autenticar login
     * 
     * @param username
     * @param password
     */
    @POST
    @Path ("/login")
    @Stylesheet (href = "login/login.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void doLogin(@FormParam ("username") String username, @FormParam ("password") String password) {
        String rt = ShiroLogin.MSG;
        try {
            Security sh = SecurityShiro.init();
            if (!sh.login(username, password)) { throw new Failure(HttpServletResponse.SC_UNAUTHORIZED); }
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
