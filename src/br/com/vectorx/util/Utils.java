package br.com.vectorx.util;

import java.util.Date;

/**
 * Classe utilitária
 */
public final class Utils {

    private Utils () {

    }

    /**
     * Checa se String é número (podendo ser inteiro ou real)
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isLong(str) || isDouble(str)) { return true; }

        return false;
    }

    private static boolean isLong(String str) {
        try {
            Long.parseLong(str);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Recebe um {@link Date} como parametro e retorna o tempo em milissegundos
     */
    public static long getDateTime(Date d) {
        if (d != null) { return d.getTime(); }
        return 0L;
    }

}
