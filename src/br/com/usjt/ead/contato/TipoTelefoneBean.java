package br.com.usjt.ead.contato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tp_telefone")
public class TipoTelefoneBean
{
    @Id
    private Integer id_tipo; // integer PRIMARY KEY,
    @Column
    private String  nome;   // integer,

    public Integer getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Integer id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "TipoTelefoneBean [id_tipo=" + id_tipo + ", nome=" + nome + "]";
    }
}
