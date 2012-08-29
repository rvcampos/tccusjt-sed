package br.com.usjt.ead;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "properties")
public class PropertiesBean
{
    @Id
    private Integer id_properties;
    @Column
    private String  nome;
    @Column
    private String  valor;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Properties [id_properties=" + id_properties + ", nome=" + nome + ", valor=" + valor + "]";
    }

    /**
     * @return the id_properties
     */
    public Integer getId_properties() {
        return id_properties;
    }

    /**
     * @param id_properties
     *            the id_properties to set
     */
    public void setId_properties(Integer id_properties) {
        this.id_properties = id_properties;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor
     *            the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
