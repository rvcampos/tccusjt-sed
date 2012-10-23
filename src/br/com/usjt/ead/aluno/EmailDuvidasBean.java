package br.com.usjt.ead.aluno;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "email_duvidas")
public class EmailDuvidasBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_emails")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Long             id_email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_email_pai")
    private EmailDuvidasBean email_origem;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_matricula")
    private MatriculaBean    matricula;
    @Column
    private Date             data;
    @Column
    private String           titulo;
    @Column
    private String           conteudo;
    @Column
    private Boolean          top_mail;
    @Column
    private Boolean          respondido;

    @Override
    public String toString() {
        return "EmailDuvidasBean [id_email=" + id_email + ", email_origem=" + email_origem.getId_email() + ", matricula="
                + matricula.getId_matricula() + ", data=" + data + ", titulo=" + titulo + ", conteudo=" + conteudo
                + ", respondido=" + respondido + "]";
    }

    /**
     * @return the id_email
     */
    public Long getId_email() {
        return id_email;
    }

    /**
     * @param id_email
     *            the id_email to set
     */
    public void setId_email(Long id_email) {
        this.id_email = id_email;
    }

    /**
     * @return the email_origem
     */
    public EmailDuvidasBean getEmail_origem() {
        return email_origem;
    }

    /**
     * @param email_origem
     *            the email_origem to set
     */
    public void setEmail_origem(EmailDuvidasBean email_origem) {
        this.email_origem = email_origem;
    }

    /**
     * @return the matricula
     */
    public MatriculaBean getMatricula() {
        return matricula;
    }

    /**
     * @param matricula
     *            the matricula to set
     */
    public void setMatricula(MatriculaBean matricula) {
        this.matricula = matricula;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo
     *            the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the conteudo
     */
    public String getConteudo() {
        return conteudo;
    }

    /**
     * @param conteudo
     *            the conteudo to set
     */
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    /**
     * @return the respondido
     */
    public Boolean getRespondido() {
        return respondido;
    }

    /**
     * @param respondido
     *            the respondido to set
     */
    public void setRespondido(Boolean respondido) {
        this.respondido = respondido;
    }

    /**
     * @return the top_mail
     */
    public Boolean getTop_mail() {
        return top_mail;
    }

    /**
     * @param top_mail
     *            the top_mail to set
     */
    public void setTop_mail(Boolean top_mail) {
        this.top_mail = top_mail;
    }
}
