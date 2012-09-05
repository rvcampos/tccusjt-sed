package br.com.usjt.ead.cidadestado;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cidade")
public class CidadeBean
{
    @Id
    private Integer      id_cidade;
    @Column
    private String       nome;
    @ManyToOne
    @JoinColumn(name = "id_estado")
    private EstadoUFBean estado;

    @Override
    public String toString() {
        return "CidadeBean [id_cidade=" + id_cidade + ", nome=" + nome + ", estado=" + estado + "]";
    }

    public Integer getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(Integer id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoUFBean getEstado() {
        return estado;
    }

    public void setEstado(EstadoUFBean estado) {
        this.estado = estado;
    }

}
