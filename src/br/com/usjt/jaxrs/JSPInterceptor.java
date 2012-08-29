package br.com.usjt.jaxrs;

import java.lang.reflect.Method;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.RedirectPrecedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSP / JSTL
 */
@Provider
@ServerInterceptor
@RedirectPrecedence
public class JSPInterceptor implements AcceptedByMethod, PostProcessInterceptor {

    private static Logger LOG = LoggerFactory.getLogger(JSPInterceptor.class);

    @Override
    public void postProcess(ServerResponse r) {
        JSPInterceptor.LOG.info("JSP:" + r.getResourceMethod());
        String page = "/WEB-INF/main.jsp";
        HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        if(r.getResourceMethod().getAnnotation(Stylesheet.class).href().contains("ufcidade"))
        {
            page = "/WEB-INF/" + r.getResourceMethod().getAnnotation(Stylesheet.class).href();
        }
        request.setAttribute("pagina", "/WEB-INF/" + r.getResourceMethod().getAnnotation(Stylesheet.class).href());
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);
        JSPInterceptor.render(request, response, page);
    }

    /**
     * Redirecionar para outra página
     */
    public static void render(HttpServletRequest r, HttpServletResponse p, String page) {
        JSPInterceptor.LOG.info("Render:" + page);
        RequestDispatcher dd = r.getRequestDispatcher(page);
        if (dd == null) {
            JSPInterceptor.LOG.error("[" + page + "] nao tem dispatcher");
            p.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            try {
                dd.forward(r, p);
            }
            catch (Exception e) {
                JSPInterceptor.LOG.error("dispatcher", e);
                p.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public boolean accept(@SuppressWarnings ("rawtypes") Class c, Method m) {
        if (m.isAnnotationPresent(Stylesheet.class)
                && m.getAnnotation(Stylesheet.class).type().equals(MediaTypeMore.APP_JSP)) {
            JSPInterceptor.LOG.info("ACCEPTING JSP:" + m.getAnnotation(Stylesheet.class).href() + ":" + m);
            return true;
        }
        return false;
    }

}
