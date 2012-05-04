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
import br.com.vectorx.crud.annotations.Pesquisa;

/**
 * Recursos (URL's x Verbos)
 */
@Entity
@Table (name = "rbac_resource")
@CRUDescritivo (nome = "Recursos", descricao = "Recursos (URL's x Verbos)")
public class RbacResourceBean {

    @Id
    @SequenceGenerator (name = "SEQ_UNIT", sequenceName = "sq_pk_cad_rbac_resource")
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
    private Long                        resource_id;
    @Valid
    @NotNull (message = "URL não pode estar vazio")
    @Column
    @Pesquisa (pesquisavel = false, visivel = true)
    @CRUDescritivo (nome = "URL", descricao = "URL relativa para recurso", tamanho = 100)
    private String                         url;
    @Valid
    @NotNull (message = "Verbo não pode estar vazio")
    @Column
    @Pesquisa (pesquisavel = false, visivel = true)
    @CRUDescritivo (nome = "Verbo", descricao = "Verbo (GET, POST, PUT, DELETE, HEAD)", tamanho = 20)
    private String                         verb;
    
    @Valid
    @Column
    @NotNull (message = "Descrição do privilégio não pode estar vazio")
    @Pesquisa (pesquisavel = false, visivel = true)
    @CRUDescritivo (nome = "Descrição", descricao = "Descrição do privilégio", tamanho = 40)
    private String                         pretty_name;
    
    
    @Override
    public String toString() {
        return "RbacResourceBean [resource_id=" + resource_id + ", url=" + url + ", verb=" + verb + ", pretty_name="
                + pretty_name + "]";
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

    /**
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the verb
     */
    public String getVerb() {
        return this.verb;
    }

    /**
     * @param verb
     *            the verb to set
     */
    public void setVerb(String verb) {
        this.verb = verb;
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
