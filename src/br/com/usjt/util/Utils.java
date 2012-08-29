package br.com.usjt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ead.PropertiesBean;
import br.com.usjt.jaxrs.JSPAttr;

/**
 * Classe utilit�ria
 */
public final class Utils
{
    private static final ValidatorFactory  factory               = Validation.buildDefaultValidatorFactory();
    private static Map<String, PropertiesBean> mapa                  = new HashMap<String, PropertiesBean>();
    private static final String            MSG_ATIVACAO_CADASTRO = "Prezado(a) %s, <br><br>Seja bem-vindo(a) ao sistema de ensino à distância SYSCOM<br><br>"
                                                                         + "Acesse o link abaixo para ativar sua conta <br><br> <a href=\"http://localhost:8080/tccusjt-sed/aluno/ativar?key=%s\"> Ativação </a>";

    private static final Logger            LOG                   = LoggerFactory.getLogger(Utils.class);

    private Utils()
    {

    }

    private static void carregaProperties() {
        synchronized (mapa) {
            try {
                mapa.clear();
                for (PropertiesBean bean : HS.searchAll(PropertiesBean.class)) {
                    mapa.put(bean.getNome(), bean);
                }
            }
            catch (HibernateException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static {
        carregaProperties();
    }

    /**
     * Checa se String é número (podendo ser inteiro ou real)
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

    public static String getSendEmail() {
        return mapa.get("email").getValor();
    }

    public static String getSendPass() {
        return mapa.get("senha").getValor();
    }

    public static String getSmtp() {
        return mapa.get("smtp").getValor();
    }

    public static Integer getSmtpPort() {
        return Integer.parseInt(mapa.get("portaSmtp").getValor());
    }

    public static void sendMail(String to, String nome) {
        try {
            SendMail.enviarEmail("[EAD] Ativação de Cadastro",
                    String.format(MSG_ATIVACAO_CADASTRO, nome, CryptoXFacade.crypt(to)), to);
        }
        catch (MessagingException e) {
            LOG.error("Falha ao enviar e-mail de ativação", e);
        }
    }

}
