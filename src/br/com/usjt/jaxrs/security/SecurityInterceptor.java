package br.com.usjt.jaxrs.security;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.SecurityPrecedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.security.SecurityPrivate.Entidade;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.shiro.SecurityShiro;

/**
 * Controlador de seguranca para o site. Padrão é bloquear.
 */
@Provider
@ServerInterceptor
@SecurityPrecedence
public class SecurityInterceptor implements PreProcessInterceptor
{

    private static Logger LOG = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Override
    public ServerResponse preProcess(HttpRequest r, ResourceMethod m) throws Failure, WebApplicationException {
        // Security sh = SecurityShiro.init();
        br.com.usjt.shiro.Security sh = SecurityShiro.init();
        if (m.getMethod().isAnnotationPresent(SecurityPublic.class)) {
            SecurityInterceptor.LOG.info("Publico:" + m.getMethod());
        }
        else {
            if (!sh.isAuthenticated()) {
                new JSPAttr("msgsession", "Sem autorização. Favor efetuar o login!");
                throw new Failure("Sem autorização", HttpServletResponse.SC_UNAUTHORIZED);
            }
            else {
                UriInfo u = r.getUri();
                String method = r.getHttpMethod().toUpperCase();
                String path = u.getPath();
                SecType[] per = null;

                if (m.getMethod().isAnnotationPresent(SecurityPrivate.class)) {
                    per = m.getMethod().getAnnotation(SecurityPrivate.class).role();
                }

                SecurityInterceptor.LOG.info("Checando|" + method + ":" + path);

                if (per != null) {
                    for (SecType sec : per) {
                        String pp = sec.toString();
                        SecurityInterceptor.LOG.info("Checando|" + pp);
                        if (sh.hasRole(pp)) {
                            return null;
                        }
                    }
                }

                new JSPAttr("msgsession",
                        "Sem autorização para acessar o recurso solicitado. Caso necessite, entre em contato com um administrador");
                throw new Failure("Sem autorização", HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return null;
    }

}
