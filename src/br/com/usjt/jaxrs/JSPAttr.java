package br.com.usjt.jaxrs;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Para retornos e variáveis de request {@link HttpServletRequest}
 */
public class JSPAttr
{

    private static Logger      LOG = LoggerFactory.getLogger(JSPAttr.class);
    private HttpServletRequest request;

    /**
     * Construtor padrao
     */
    public JSPAttr()
    {
        this.request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        try {
            request.setCharacterEncoding("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Construtor. Adiciona parameter
     */
    public JSPAttr(String key, Object value)
    {
        this();
        this.set(key, value);
    }

    /**
     * Adicionar parameter
     */
    public JSPAttr set(String key, Object value) {
        this.request.setAttribute(key, value);
        JSPAttr.LOG.info("set:" + key + "=" + value);
        return this;
    }

    /**
     * Remove atributo
     */
    public JSPAttr remove(String key) {
        this.request.removeAttribute(key);
        JSPAttr.LOG.info("remove:" + key);
        return this;
    }

    /**
     * GET Atribute
     */
    public Object get(String key) {
        JSPAttr.LOG.info("get:" + key);
        return this.request.getAttribute(key);
    }

    /**
     * GET Parameter
     */
    public String getParameter(String key) {
        return this.request.getParameter(key);
    }

    /**
     * GET Parameter
     */
    public String getParameter(String key, Charset charset) {
        return this.request.getParameter(key);
    }

    /**
     * Pegar lista multipla
     */
    public String[] getParameterValues(String key) {
        return this.request.getParameterValues(key);
    }

    public void repopular() {
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String x = en.nextElement();
            if ("page".equals(x) || "qtdpag".equals(x)) {
                continue;
            }
            set(x, getParameter(x));
        }
    }

    public void errorMsg(String error) {
        set("msgerro", error);
    }

    public void errorMsg() {
        errorMsg("Falha na operação");
    }

    public void sucessMsg() {
        sucessMsg("Sucesso!");
    }

    public void sucessMsg(String msg) {
        set("msgok", msg);
    }

}
