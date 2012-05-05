package br.com.usjt.usuario;

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
 * Privilégios de usuário
 */
@Entity
@Table (name = "rbac_privilege")
public class PrivilegioBean {

    @Id
    @SequenceGenerator (name = "SEQ_UNIT", sequenceName = "sq_pk_cad_rbac_privilege")
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
    private Long   privilege_id;
    @Valid
    @NotNull (message = "Endereço Host não pode estar vazio")
    @Column
    private String tp_privilege;
    @Valid
    @NotNull (message = "Descrição do privilégio não pode estar vazio")
    @Column
    private String pretty_name;

    @Override
    public String toString() {
        return "RbacPrivilegeBean [privilege_id=" + privilege_id + ", tp_privilege=" + tp_privilege + ", pretty_name="
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
