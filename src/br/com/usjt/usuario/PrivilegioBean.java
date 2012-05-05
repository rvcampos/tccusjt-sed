package br.com.usjt.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Privilégios de usuário
 */
@Entity
@Table(name = "rbac_privilege")
public class PrivilegioBean {

	@Id
	@SequenceGenerator(name = "SEQ_UNIT", sequenceName = "sq_pk_privilege")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
	private Long privilegio_id;
	@Column
	private String tp_privilegio;
	@Column
	private String nome;

	@Override
	public String toString() {
		return "PrivilegioBean [privilegio_id=" + privilegio_id
				+ ", tp_privilegio=" + tp_privilegio + ", nome=" + nome + "]";
	}

	/**
	 * @return the privilegio_id
	 */
	public Long getPrivilegio_id() {
		return privilegio_id;
	}

	/**
	 * @param privilegio_id
	 *            the privilegio_id to set
	 */
	public void setPrivilegio_id(Long privilegio_id) {
		this.privilegio_id = privilegio_id;
	}

	/**
	 * @return the tp_privilegio
	 */
	public String getTp_privilegio() {
		return tp_privilegio;
	}

	/**
	 * @param tp_privilegio
	 *            the tp_privilegio to set
	 */
	public void setTp_privilegio(String tp_privilegio) {
		this.tp_privilegio = tp_privilegio;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

}
