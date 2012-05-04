package br.com.vectorx.usuario;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.vectorx.crud.annotations.CRUDescritivo;
import br.com.vectorx.crud.annotations.CRUDescritivo.Tipo;
import br.com.vectorx.crud.annotations.Pesquisa;
import br.com.vectorx.crud.annotations.Relacionamento;
import br.com.vectorx.usuario.compositekey.RelRoleUserCompoundKey;

/**
 * papeis de usuários
 */
@Entity
@Table (name = "rbac_rel_role_user", uniqueConstraints = @UniqueConstraint (columnNames = { "user_id", "role_id" }))
@IdClass (RelRoleUserCompoundKey.class)
public class RbacRelRoleUserBean {

    @Id
    @Pesquisa (pesquisavel = false, visivel = true)
    @Relacionamento (classe = RbacUserBean.class, atributo = "login")
    @Valid
    @NotNull (message = "Usuário não pode estar vazio")
    @CRUDescritivo (nome = "Usuário", descricao = "Usuário", tamanho = 10, tipo = Tipo.SELECT)
    private Long user_id;
    @Id
    @Pesquisa (pesquisavel = false, visivel = true)
    @Valid
    @NotNull (message = "Perfil não pode estar vazio")
    @Relacionamento (classe = RbacRoleBean.class, atributo = "name")
    @CRUDescritivo (nome = "Perfil", descricao = "Perfil", tamanho = 5, tipo = Tipo.SELECT)
    private Long role_id;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RbacRelRoleUserBean [user_id=" + this.user_id + ", role_id=" + this.role_id + "]";
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
     * @return the role_id
     */
    public Long getRole_id() {
        return this.role_id;
    }

    /**
     * @param role_id
     *            the role_id to set
     */
    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = true;
        if (!(obj instanceof RbacRelRoleUserBean)) { return false; }
        Long otherUserId = ((RbacRelRoleUserBean) obj).getUser_id();
        Long otherRoleId = ((RbacRelRoleUserBean) obj).getRole_id();
        if (this.role_id == null || otherRoleId == null || this.user_id == null || otherUserId == null) {
            result = false;
        } else {
            if (!(this.user_id.equals(otherUserId) && this.role_id.equals(otherRoleId))) {
                result = false;
            }
        }
        return result;
    }
}
