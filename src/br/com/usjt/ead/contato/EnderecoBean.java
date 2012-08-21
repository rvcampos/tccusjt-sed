package br.com.usjt.ead.contato;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ENDERECO")
public class EnderecoBean
{
    private Integer id_endereco; // integer PRIMARY KEY,
    private Integer id_cidade;   // integer,
    private String  bairro;      // varchar(100),
    private String  logradouro;  // varchar(120),
    private Integer cep;         // integer);

    @Override
    public String toString() {
        return "EnderecoBean [id_endereco=" + id_endereco + ", id_cidade=" + id_cidade + ", bairro=" + bairro + ", logradouro="
                + logradouro + ", cep=" + cep + "]";
    }

    public Integer getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(Integer id_endereco) {
        this.id_endereco = id_endereco;
    }

    public Integer getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(Integer id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }
}
