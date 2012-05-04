package br.com.vectorx.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Envio de Email
 */
public class SendMail {

    private static final Logger LOG = LoggerFactory.getLogger(SendMail.class);

    private String              smtp;
    private Integer             smtpPort;
    private String              user;
    private String              password;
    private String              recipient;
    private String              subject;
    private String              message;
    private String              from;

    /**
     */
    public String getRecipient() {
        return this.recipient;
    }

    /**
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     */
    public String getMessage() {
        return this.message;
    }

    /**
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     */
    public String getFrom() {
        return this.from;
    }

    /**
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     */
    public String getSmtp() {
        return this.smtp;
    }

    /**
     */
    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    /**
     */
    public Integer getSmtpPort() {
        return this.smtpPort;
    }

    /**
     */
    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     */
    public String getUser() {
        return this.user;
    }

    /**
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     */
    public String getPassword() {
        return this.password;
    }

    /**
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Envio
     * 
     * @throws MessagingException
     */
    public void send() throws MessagingException {
        // Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtp);
        props.put("mail.smtp.port", this.smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // create some properties and get the default Session
        Session session = Session.getDefaultInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SendMail.this.user, SendMail.this.password);
            }
        });
        session.setDebug(SendMail.LOG.isDebugEnabled());

        // create a message
        Message msg = new MimeMessage(session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(this.from);
        msg.setFrom(addressFrom);

        InternetAddress addressTo = new InternetAddress();
        addressTo.setAddress(this.recipient);
        msg.setRecipient(Message.RecipientType.TO, addressTo);

        // Optional : You can also set your custom headers in the Email if you
        // Want
        // msg.addHeader("X-JAVA", "VectorX");

        // Setting the Subject and Content Type
        msg.setSubject(this.subject);
        msg.setContent(this.message, "text/plain");
        Transport.send(msg);
    }

}
