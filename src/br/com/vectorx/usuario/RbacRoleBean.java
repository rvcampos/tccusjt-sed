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

import br.com.vectorx.crud.annotations.CRUDescritivo;
import br.com.vectorx.crud.annotations.CRUDescritivo.Tipo;
import br.com.vectorx.crud.annotations.Pesquisa;


/**
 * papeis de usuários
 */
@Entity
@Table (name = "rbac_role")
@CRUDescritivo(nome="Papéis", descricao="Papel")
public class RbacRoleBean {

    @Id
    @SequenceGenerator (name = "SEQ_UNIT", sequenceName = "sq_pk_rbac_role")
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
    private Long   role_id;
  
    @Column
    @Valid
    @NotNull (message = "Perfil/Papel do usuário não pode estar vazio")
    @CRUDescritivo (nome = "Perfil/Papel de usuário", descricao = "Perfil/Papel do usuário", tamanho = 40, tipo = Tipo.TEXT)
    @Pesquisa (visivel = true, pesquisavel = true)
    private String name;
    
    @Override
    public String toString() {
        return "RbacRoleBean [role_id=" + role_id + ", name=" + name + "]";
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
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
  

}
