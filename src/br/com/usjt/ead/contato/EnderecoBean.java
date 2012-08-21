package br.com.usjt.ead.contato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ENDERECO")
public class EnderecoBean
{
    @Id
    @SequenceGenerator(name="gen", initialValue=1, sequenceName="seq_endereco")
    @GeneratedValue(generator="gen", strategy=GenerationType.AUTO)
    private Integer id_endereco; // integer PRIMARY KEY,
    private Integer id_cidade;   // integer,
    @Column
    private String  bairro;      // varchar(100),
    @Column
    private String  logradouro;  // varchar(120),
    @Column
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
