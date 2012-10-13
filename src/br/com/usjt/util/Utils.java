package br.com.usjt.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ead.PropertiesBean;
import br.com.usjt.jaxrs.JSPAttr;

/**
 * Classe utilitária
 */
public final class Utils
{
    private static final ValidatorFactory      factory               = Validation.buildDefaultValidatorFactory();
    private static Map<String, PropertiesBean> mapa                  = new HashMap<String, PropertiesBean>();
    private static final String                MSG_ATIVACAO_CADASTRO = "Prezado(a) %s, <br><br>Seja bem-vindo(a) ao sistema de ensino à distância SYSCOM<br><br>"
                                                                             + "Acesse o link abaixo para ativar sua conta <br><br> <a href=\"http://localhost:8080/tccusjt-sed/aluno/ativar?key=%s\"> Ativação </a>";

    private static final String                MSG_ESQUECI_SENHA = "Prezado(a) %s, <br><br>Foi solicitado o reenvio da senha de acesso ao sistema de ensino à distância SYSCOM<br><br>"
            + "Acesse o sistema com a senha abaixo para utilizar o sistema: <br><br> %s";
    private static final Logger                LOG                   = LoggerFactory.getLogger(Utils.class);
    private static final BigDecimal            registrosPagina       = BigDecimal.TEN;

    private Utils()
    {

    }

    public static String tipoString(Integer tipo) {
        switch (tipo)
        {
        case 0:
            return "admin";
        case 1:
            return "professor";
        case 3:
            return "aluno";
        }
        return "aluno";
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

        try
        {
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
        }catch (Exception e) {
            LOG.error("Falha ao validar", e);
            valid = false;
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
    
    public static void sendMailEsqueceuSenha(String to, String nome, String senha) {
        try {
            SendMail.enviarEmail("[EAD] Esqueci Minha Senha",
                    String.format(MSG_ESQUECI_SENHA, nome, CryptoXFacade.decrypt(senha)), to);
        }
        catch (MessagingException e) {
            LOG.error("Falha ao enviar e-mail de esqueci senha", e);
        }
    }

    public static boolean isEmpty(Object string) {
        if (string == null || string.toString().trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isValidMail(String email) {
        try {
            InternetAddress add = new InternetAddress(email);
            add.validate();
        }
        catch (AddressException e) {
            return false;
        }
        return true;
    }

    public static String padding(Object string, int tamanhoDesejado, String caracter) {
        String toPad = string.toString();

        if (toPad == null) {
            toPad = "";
        }

        if (toPad.length() == tamanhoDesejado) {
            return toPad;
        }
        StringBuilder b = new StringBuilder(toPad);
        while (b.length() < tamanhoDesejado) {
            b.insert(0, caracter);
        }
        return b.toString();
    }

    /**
     * Metodo padrao para paginação
     * 
     * @param j
     * @param criteria
     */
    public static void paginar(JSPAttr j, Criteria criteria) {
        criteria.setProjection(Projections.rowCount());
        Long qtd = (Long) criteria.uniqueResult();
        int qtdpag = BigDecimal.valueOf(qtd).divide(registrosPagina, BigDecimal.ROUND_CEILING).intValue();
        int pag = 1;

        if (!isEmpty(j.getParameter("page"))) {
            pag = Integer.parseInt(j.getParameter("page"));
            if (pag < 1) {
                pag = 1;
            }

            if (pag > qtdpag) {
                pag = qtdpag;
            }
        }
        int ini = 0;
        if (pag > 1) {
            ini = registrosPagina.intValue() * (pag - 1);
        }

        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        criteria.setFirstResult(ini);
        criteria.setMaxResults(registrosPagina.intValue());
        j.set("page", pag);
        j.set("qtdpag", qtdpag);
    }

}
