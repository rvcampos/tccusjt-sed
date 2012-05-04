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
 * Privil�gios de usu�rio
 */
@Entity
@Table (name = "rbac_privilege")
public class RbacPrivilegeBean {

    @Id
    @SequenceGenerator (name = "SEQ_UNIT", sequenceName = "sq_pk_cad_rbac_privilege")
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
    private Long   privilege_id;
    @Valid
    @NotNull (message = "Endere�o Host n�o pode estar vazio")
    @Column
    private String ipv4_host_addr;
    @Valid
    @NotNull (message = "Network Mask n�o pode estar vazio")
    @Column
    private String ipv4_net_addr;
    @Valid
    @NotNull (message = "Tipo do Privil�gio n�o pode estar vazio")
    @Column
    private String tp_privilege;
    @Valid
    @NotNull (message = "Descri��o do privil�gio n�o pode estar vazio")
    @Column
    private String pretty_name;

    // XXX anota��o com exemplo de implementa��o com relacionamento hibernate
    // @OneToMany
    // @JoinTable (name = "rbac_permission", joinColumns = { @JoinColumn (name =
    // "privilege_id") }, inverseJoinColumns = { @JoinColumn (name =
    // "permission_id") })
    // @LazyCollection (LazyCollectionOption.FALSE)
    // @Cascade (value = CascadeType.ALL)
    // private Collection<RbacPermissionBean> permissions;

    @Override
    public String toString() {
        return "RbacPrivilegeBean [privilege_id=" + privilege_id + ", ipv4_host_addr=" + ipv4_host_addr
                + ", ipv4_net_addr=" + ipv4_net_addr + ", tp_privilege=" + tp_privilege + ", pretty_name="
                + pretty_name + "]";
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
     * @return the ipv4_host_addr
     */
    public String getIpv4_host_addr() {
        return this.ipv4_host_addr;
    }

    /**
     * @param ipv4_host_addr
     *            the ipv4_host_addr to set
     */
    public void setIpv4_host_addr(String ipv4_host_addr) {
        this.ipv4_host_addr = ipv4_host_addr;
    }

    /**
     * @return the ipv4_net_addr
     */
    public String getIpv4_net_addr() {
        return this.ipv4_net_addr;
    }

    /**
     * @param ipv4_net_addr
     *            the ipv4_net_addr to set
     */
    public void setIpv4_net_addr(String ipv4_net_addr) {
        this.ipv4_net_addr = ipv4_net_addr;
    }

    /**
     * @return the tp_privilege
     */
    public String getTp_privilege() {
        return this.tp_privilege;
    }

    /**
     * @param tp_privilege
     *            the tp_privilege to set
     */
    public void setTp_privilege(String tp_privilege) {
        this.tp_privilege = tp_privilege;
    }

    /**
     * @return the pretty_name
     */
    public String getPretty_name() {
        return this.pretty_name;
    }

    /**
     * @param pretty_name
     *            the pretty_name to set
     */
    public void setPretty_name(String pretty_name) {
        this.pretty_name = pretty_name;
    }

}
