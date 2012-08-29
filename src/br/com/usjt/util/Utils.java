package br.com.usjt.util;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.engine.ValidatorFactoryImpl;
import org.jboss.jpa.impl.beanvalidation.ValidatorFactoryProvider;

import br.com.usjt.jaxrs.JSPAttr;

/**
 * Classe utilit�ria
 */
public final class Utils
{
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private Utils()
    {

    }

    /**
     * Checa se String � n�mero (podendo ser inteiro ou real)
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isLong(str) || isDouble(str)) {
            return true;
        }

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
        if (d != null) {
            return d.getTime();
        }
        return 0L;
    }

    public static <T> boolean isValid(T bean) {
        boolean valid = true;

        Validator validator = factory.getValidator();
        StringBuilder builder = new StringBuilder();

        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        String sep = "";
        if (!violations.isEmpty()) {
            valid = false;
            for (ConstraintViolation<T> violation : violations) {
                builder.append(sep + violation.getMessage());
                sep = "\n";
            }
            new JSPAttr("msgerro", builder.toString());
        }

        return valid;
    }

}
