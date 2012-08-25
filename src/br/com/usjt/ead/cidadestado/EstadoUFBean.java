package br.com.usjt.ead.cidadestado;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class EstadoUFBean
{
    @Id
    private Integer id_estado;
    @Column
    private String  uf;
    @OneToMany(mappedBy="estado")
    private Set<CidadeBean> cidades = new HashSet<CidadeBean>();

    @Override
    public String toString() {
        return "EstadoUFBean [id_estado=" + id_estado + ", uf=" + uf + "]";
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Set<CidadeBean> getCidades() {
        return cidades;
    }

    public void setCidades(Set<CidadeBean> cidades) {
        this.cidades = cidades;
    }

}
