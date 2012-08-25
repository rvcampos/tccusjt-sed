package br.com.usjt.usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Privilegio sobre um recurso = permissao
 */
@Entity
@Table(name = "tb_permissao")
public class PermissaoBean {

	@Id
	@SequenceGenerator(name = "SEQ_UNIT", sequenceName = "sq_pk_permissao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
	private Long permissao_id;

	@ManyToOne
	@JoinColumn(name="privilegio_id")
	private PrivilegioBean privilegio;

	@ManyToOne
	@JoinColumn(name="recurso_id")
	private RecursoBean recurso;

	@Override
	public String toString() {
		return "PermissaoBean [permissao_id=" + permissao_id + ", privilegio="
				+ privilegio + ", recurso=" + recurso + "]";
	}

	/**
	 * @return the permissao_id
	 */
	public Long getPermissao_id() {
		return permissao_id;
	}

	/**
	 * @param permissao_id
	 *            the permissao_id to set
	 */
	public void setPermissao_id(Long permissao_id) {
		this.permissao_id = permissao_id;
	}

	/**
	 * @return the privilegio
	 */
	public PrivilegioBean getPrivilegio() {
		return privilegio;
	}

	/**
	 * @param privilegio
	 *            the privilegio to set
	 */
	public void setPrivilegio(PrivilegioBean privilegio) {
		this.privilegio = privilegio;
	}

	/**
	 * @return the recurso
	 */
	public RecursoBean getRecurso() {
		return recurso;
	}

	/**
	 * @param recurso
	 *            the recurso to set
	 */
	public void setRecurso(RecursoBean recurso) {
		this.recurso = recurso;
	}

}
