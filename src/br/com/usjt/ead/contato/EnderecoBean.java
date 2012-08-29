package br.com.usjt.ead.contato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.usjt.ead.cidadestado.CidadeBean;

@Entity
@Table(name = "ENDERECO")
public class EnderecoBean
{
    @Id
    @SequenceGenerator(name="gen", initialValue=1, sequenceName="seq_endereco")
    @GeneratedValue(generator="gen", strategy=GenerationType.AUTO)
    private Integer id_endereco; // integer PRIMARY KEY,
    @ManyToOne
    @JoinColumn(name="id_cidade")
    @NotNull(message="Favor informar a cidade")
    private CidadeBean cidade;   // integer,
    @Column
    @NotNull(message="Favor informar o bairro")
    private String  bairro;      // varchar(100),
    @Column
    @NotNull(message="Favor informar o Endereço")
    private String  logradouro;  // varchar(120),
    @Column
    @NotNull(message="Favor informar o cep")
    private Integer cep;         // integer);

    @Override
    public String toString() {
        return "EnderecoBean [id_endereco=" + id_endereco + ", cidade=" + cidade + ", bairro=" + bairro + ", logradouro="
                + logradouro + ", cep=" + cep + "]";
    }

    public Integer getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(Integer id_endereco) {
        this.id_endereco = id_endereco;
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

    public CidadeBean getCidade() {
        return cidade;
    }

    public void setCidade(CidadeBean cidade) {
        this.cidade = cidade;
    }
}
