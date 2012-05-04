package br.com.vectorx.usuario;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.vectorx.corban.cadastro.contato.CadContactBean;
import br.com.vectorx.corban.cadastro.servicos.CadBusinessUnit;
import br.com.vectorx.crud.annotations.CRUDescritivo;
import br.com.vectorx.crud.annotations.CRUDescritivo.Tipo;
import br.com.vectorx.crud.annotations.Pesquisa;
import br.com.vectorx.crud.annotations.Pesquisa.Filtro;
import br.com.vectorx.crud.annotations.Relacionamento;

/**
 * Cadastro de usuários
 */
@Entity
@Table (name = "rbac_user")
@CRUDescritivo (nome = "Usuário", descricao = "Cadastro de usuários")
public class RbacUserBean {

    @Id
    @SequenceGenerator (name = "SEQ_UNIT", sequenceName = "sq_pk_cad_rbac_user")
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
    private Long      user_id;

    @Column
    @NotNull (message = "Unidade de Negócio não pode estar vazio")
    @Relacionamento (classe = CadBusinessUnit.class, atributo = "name")
    @CRUDescritivo (tipo = Tipo.SELECT, descricao = "Unidade de Negócio", nome = "Unidade de Negócio")
    @Pesquisa (visivel = true, pesquisavel = true)
    private Long      unit_id;

    @Valid
    @NotNull (message = "Login não pode estar vazio")
    @Column
    @Pesquisa (pesquisavel = true, visivel = true, filtro = { Filtro.IGUAL, Filtro.CONTEM, Filtro.COMECA,
            Filtro.TERMINA })
    @CRUDescritivo (nome = "Login", descricao = "Login", tamanho = 40)
    private String    login;
    @Valid
    @NotNull (message = "Senha não pode estar vazio")
    @Column
    @Pesquisa (pesquisavel = false, visivel = false)
    @Size (min = 6, message = "A senha deve possuir no mí­nimo 6 e o máximo 12 caracteres")
    @CRUDescritivo (nome = "Senha", descricao = "Senha", tamanho = 12, tipo = Tipo.PASSWORD)
    private String    password;

    @Column
    @Pesquisa (pesquisavel = false, visivel = true)
    @Valid
    @CRUDescritivo (nome = "Último login", descricao = "Data do último login", mascara = "39/19/99 29:59", mascaraInterna = "dd/MM/yy HH:mm", ligado = false, campoInvisivel = true)
    private Timestamp dt_lastlogin;

    @Column
    @Pesquisa (pesquisavel = false, visivel = true)
    @CRUDescritivo (nome = "Última alteração de senha", descricao = "Data da última alteração de senha", mascaraInterna = "dd/MM/yy", mascara = "39/19/99", ligado = false, campoInvisivel = true)
    private Timestamp dt_lastpasswordchange;

    @Valid
    @NotNull (message = "Contato não pode estar vazio")
    @Column
    @Pesquisa (pesquisavel = false, visivel = true)
    @Relacionamento (classe = CadContactBean.class, atributo = "name")
    @CRUDescritivo (nome = "Contato", descricao = "Contato", tamanho = 10, tipo = Tipo.SELECT)
    private Long      contact_id;

    @Override
    public String toString() {
        return "RbacUserBean [user_id=" + this.user_id + ", unit_id=" + this.unit_id + ", login=" + this.login
                + ", password=" + this.password + ", dt_lastlogin=" + this.dt_lastlogin + ", dt_lastpasswordchange="
                + this.dt_lastpasswordchange + ", contact_id=" + this.contact_id + "]";
    }

    /**
     * @return the unit_id
     */
    public Long getUnit_id() {
        return this.unit_id;
    }

    /**
     * @param unit_id
     *            the unit_id to set
     */
    public void setUnit_id(Long unit_id) {
        this.unit_id = unit_id;
    }

    /**
     * @return the user_id
     */
    public Long getUser_id() {
        return this.user_id;
    }

    /**
     * @param user_id
     *            the user_id to set
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the dt_lastlogin
     */
    public Timestamp getDt_lastlogin() {
        return this.dt_lastlogin;
    }

    /**
     * @param dt_lastlogin
     *            the dt_lastlogin to set
     */
    public void setDt_lastlogin(Timestamp dt_lastlogin) {
        this.dt_lastlogin = dt_lastlogin;
    }

    /**
     * @return the dt_lastpasswordchange
     */
    public Timestamp getDt_lastpasswordchange() {
        return this.dt_lastpasswordchange;
    }

    /**
     * @param dt_lastpasswordchange
     *            the dt_lastpasswordchange to set
     */
    public void setDt_lastpasswordchange(Timestamp dt_lastpasswordchange) {
        this.dt_lastpasswordchange = dt_lastpasswordchange;
    }

    /**
     * @return the contact_id
     */
    public Long getContact_id() {
        return this.contact_id;
    }

    /**
     * @param contact_id
     *            the contact_id to set
     */
    public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }
}
