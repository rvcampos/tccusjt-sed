package br.com.usjt.util;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;

public final class MensagemErro {

    private static Properties p = new Properties();
    static {
        try {
            MensagemErro.p.load(new FileInputStream("/home/renan.campos/Workspaces/projects/corban.cadastro/error.properties"));
        }
        catch (Exception e) {}
    }

    /**
     * @param key
     * @param params
     * @return
     */
    public static String getMessage(String key, Object params) {
        String msg = MensagemErro.p.getProperty(key);
        if (msg.contains("{")) {
            MessageFormat m = new MessageFormat(msg);
            return m.format(params);
        }
        return msg;
    }
}
