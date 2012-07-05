package br.com.usjt.usuario;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Bean de usuário
 */
@Entity
@Table(name = "tb_usuario")
public class PessoaBean {

	@Id
	@SequenceGenerator(name = "SEQ_UNIT", sequenceName = "sq_pk_usuario")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
	private Long usuario_id;

	@Column
	@Valid
	private String login;
	@Column
	private String password;

	@Column
	private Timestamp dt_ultimologin;

	@Column
	private Timestamp dt_trocapassword;

	@Valid
	private Long contato;
	@OneToMany
	@JoinTable(name = "tb_rel_usuario_perfil", joinColumns = { @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "perfil_id", referencedColumnName = "perfil_id", updatable = false) })
	private Collection<PerfilBean> perfis;

	@Override
	public String toString() {
		return "UsuarioBean [usuario_id=" + usuario_id + ", login=" + login
				+ ", password=" + password + ", dt_ultimologin="
				+ dt_ultimologin + ", dt_trocapassword=" + dt_trocapassword
				+ ", contato=" + contato + "]";
	}

	public Long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getDt_ultimologin() {
		return dt_ultimologin;
	}

	public void setDt_ultimologin(Timestamp dt_ultimologin) {
		this.dt_ultimologin = dt_ultimologin;
	}

	public Timestamp getDt_trocapassword() {
		return dt_trocapassword;
	}

	public void setDt_trocapassword(Timestamp dt_trocapassword) {
		this.dt_trocapassword = dt_trocapassword;
	}

	public Long getContact_id() {
		return contato;
	}

	public void setContato(Long contato) {
		this.contato = contato;
	}

	/**
	 * @return the perfis
	 */
	public Collection<PerfilBean> getPerfis() {
		return perfis;
	}

	/**
	 * @param perfis
	 *            the perfis to set
	 */
	public void setPerfis(Collection<PerfilBean> perfis) {
		this.perfis = perfis;
	}

}
