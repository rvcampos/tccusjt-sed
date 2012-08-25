package br.com.usjt.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Recursos (URL's x Verbos)
 */
@Entity
@Table (name = "tb_recurso")
public class RecursoBean {

    @Id
    @SequenceGenerator (name = "SEQ_UNIT", sequenceName = "sq_pk_recurso")
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "SEQ_UNIT")
    private Long                        recurso_id;
    @Column
     // URL do servlet
    private String                         url;
    @Column
    // (GET, POST, PUT, DELETE, HEAD)
    private String                         verb;
    
    @Column
    private String                         nome;
    
    
    @Override
    public String toString() {
        return "RecursoBean [recurso_id=" + recurso_id + ", url=" + url + ", verb=" + verb + ", nome="
                + nome + "]";
    }

    /**
     * @return the resource_id
     */
    public Long getRecurso_id() {
        return this.recurso_id;
    }

    /**
     * @param recurso_id
     *            the resource_id to set
     */
    public void setRecurso_id(Long recurso_id) {
        this.recurso_id = recurso_id;
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
     * @return the nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

}
