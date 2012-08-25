package br.com.usjt.usuario;

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

/**
 * papeis/perfil de usuários
 */
@Entity
@Table(name = "tb_perfil")
public class PerfilBean {

	@Id
	@SequenceGenerator(name = "SEQ_UNIT", sequenceName = "sq_pk_perfil")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
	private Long perfil_id;

	@Column
	private String nome;

	@OneToMany
	@JoinTable(name = "tb_rel_perfil_permissao", joinColumns = { @JoinColumn(name = "perfil_id", referencedColumnName = "perfil_id", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "permissao_id", referencedColumnName = "permissao_id", updatable = false) })
	private Collection<PermissaoBean> permissoes;

	@Override
	public String toString() {
		return "PerfilBean [perfil_id=" + perfil_id + ", nome=" + nome + "]";
	}

	/**
	 * @return the role_id
	 */
	public Long getPerfil_id() {
		return this.perfil_id;
	}

	/**
	 * @param perfil_id
	 *            the role_id to set
	 */
	public void setPerfil_id(Long perfil_id) {
		this.perfil_id = perfil_id;
	}

	/**
	 * @return the name
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * @param nome
	 *            the name to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the permissoes
	 */
	public Collection<PermissaoBean> getPermissoes() {
		return permissoes;
	}

	/**
	 * @param permissoes
	 *            the permissoes to set
	 */
	public void setPermissoes(Collection<PermissaoBean> permissoes) {
		this.permissoes = permissoes;
	}

}
