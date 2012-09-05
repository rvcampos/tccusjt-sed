package br.com.usjt.jaxrs;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

/**
 * Medias types usados
 */
public interface MediaTypeMore {

    /**
     * Html normal
     */
    static final String TEXT_HTML      = MediaType.TEXT_HTML;
    /**
     * XML nao sendo aplicativo
     */
    static final String TEXT_XML       = MediaType.TEXT_XML;

    /**
     * Cascade Stylesheet
     */
    static final String TEXT_CSS       = Stylesheet.CSS;
    /**
     * XML Stylesheet Language
     */
    static final String TEXT_XSL       = Stylesheet.XSL;

    /**
     * Retorno do tipo XML Builder usado para o processador de XSL
     */
    static final String APP_XMLBUILDER = "application/xmlbuilder";

    /**
     * Java Server Page
     */
    static final String APP_JSP        = "application/jsp";

    /**
     * JSON
     */
    static final String APP_JSON       = "application/json";

}
