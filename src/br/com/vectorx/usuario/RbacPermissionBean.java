package br.com.vectorx.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Privilegio sobre um recurso = permissao
 */
@Entity
@Table (name = "rbac_permission")
public class RbacPermissionBean {

    @Id
    @SequenceGenerator (name = "SEQ_UNIT", sequenceName = "sq_pk_cad_rbac_permission")
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
    private Long permission_id;

    @Valid
    @NotNull (message = "Privil�gio n�o pode estar vazio")
    @Column
    private Long privilege_id;

    @Valid
    @NotNull (message = "Recurso n�o pode estar vazio")
    @Column
    private Long resource_id;

    // XXX verificar usabilidade relacionamento com anota��o @OneToMany
    // @OneToMany
    // @JoinColumn(name="privilege_id")

    // @ManyToOne
    // @JoinColumn (name = "privilege_id")
    // @Pesquisa (pesquisavel = false, visivel = true)
    // @Relacionamento (classe = RbacResourceBean.class, atributo =
    // "pretty_name")
    // @CRUDescritivo (nome = "Privil�gio", descricao = "Privil�gio", tamanho =
    // 10, tipo = Tipo.SELECT)
    // private RbacPrivilegeBean privileges;
    // @ManyToOne
    // @JoinColumn (name = "resource_id")
    // @Pesquisa (pesquisavel = false, visivel = true)
    // @Relacionamento (classe = RbacResourceBean.class, atributo =
    // "pretty_name")
    // @CRUDescritivo (nome = "Recurso", descricao = "Recurso", tamanho = 10,
    // tipo = Tipo.SELECT)
    // private RbacResourceBean resources;

    @Override
    public String toString() {
        return "RbacPermissionBean [permission_id=" + permission_id + ", privilege_id=" + privilege_id
                + ", resource_id=" + resource_id + "]";
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

    /**
     * @return the privilege_id
     */
    public Long getPrivilege_id() {
        return this.privilege_id;
    }

    /**
     * @param privilege_id
     *            the privilege_id to set
     */
    public void setPrivilege_id(Long privilege_id) {
        this.privilege_id = privilege_id;
    }

    /**
     * @return the resource_id
     */
    public Long getResource_id() {
        return this.resource_id;
    }

    /**
     * @param resource_id
     *            the resource_id to set
     */
    public void setResource_id(Long resource_id) {
        this.resource_id = resource_id;
    }

}
