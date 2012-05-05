package br.com.usjt.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.shiro.io.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Carrega a versao
 * TODO Lembrar sempre de mudar a versao no properties
 */
public final class Versao {

    private static final Logger     LOG  = LoggerFactory.getLogger(Versao.class);
    private static final Properties prop = new Properties();

    static {
        try {
            InputStream in = ResourceUtils.getInputStreamForPath("classpath:versao.properties");
            Versao.prop.load(in);
            in.close();
        }
        catch (Exception e) {
            Versao.LOG.error("Versao nao carregada", e);
            Versao.prop.setProperty("versao", "N/A");
        }
    }

    /**
     * @return a versao
     */
    public static String getVersao() {
        return Versao.prop.getProperty("versao");
    }

}
