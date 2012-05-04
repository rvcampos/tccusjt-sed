package br.com.vectorx.usuario;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.vectorx.crud.annotations.CRUDescritivo;
import br.com.vectorx.crud.annotations.CRUDescritivo.Tipo;
import br.com.vectorx.crud.annotations.Pesquisa;
import br.com.vectorx.crud.annotations.Relacionamento;
import br.com.vectorx.usuario.compositekey.RelRolePermissionCompoundKey;

/**
 * papeis de usuários
 */
@Entity
@Table (name = "rbac_rel_role_permission")
@IdClass (RelRolePermissionCompoundKey.class)
@CRUDescritivo(nome="Regras de Permissões", descricao="Regras de Permissões")
public class RbacRelRolePermissionBean {

    @Id
    @Pesquisa (pesquisavel = false, visivel = true)
    @Valid
    @NotNull (message = "Papéis não pode estar vazio")
    @Relacionamento (classe = RbacRoleBean.class, atributo = "name")
    @CRUDescritivo (nome = "Papéis", descricao = "Papéis", tamanho = 5, tipo = Tipo.SELECT)
    private Long role_id;
    @Id
    @Pesquisa (pesquisavel = false, visivel = true)
    @Valid
    @NotNull (message = "Permissão não pode estar vazio")
    @Relacionamento (classe = RbacPermissionBean.class, atributo = "permission_id")
    @CRUDescritivo (nome = "Permissão", descricao = "Permissão", tamanho = 10, tipo = Tipo.SELECT)
    private Long permission_id;

    @Override
    public boolean equals(Object obj) {
        boolean result = true;
        if (!(obj instanceof RbacRelRolePermissionBean)) { return false; }
        Long otherPermissionId = ((RbacRelRolePermissionBean) obj).getPermission_id();
        Long otherRoleId = ((RbacRelRolePermissionBean) obj).getRole_id();
        if (this.role_id == null || otherRoleId == null || this.permission_id == null || otherPermissionId == null) {
            result = false;
        } else {
            if (!(this.permission_id.equals(otherPermissionId) && this.role_id.equals(otherRoleId))) {
                result = false;
            }
        }
        return result;
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

    /**
     * @return the permission_id
     */
    public Long getPermission_id() {
        return this.permission_id;
    }

    /**
     * @param permission_id
     *            the permission_id to set
     */
    public void setPermission_id(Long permission_id) {
        this.permission_id = permission_id;
    }

}
