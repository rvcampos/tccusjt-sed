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
@Table(name = "CONTATO")
public class ContatoBean
{
    @Id
    private Long              id_contato;                             // integer
                                                                       // PRIMARY
                                                                       // KEY,
    @Column
    private Date              data_nascimento;                        // Date,
    @Column
    private String            nome;                                   // varchar(100),
    @Column
    private String            rg;                                     // varchar(12));
    @OneToMany(mappedBy = "contato")
    @NotEmpty(message = "Por favor, preencha ao menos um telefone")
    private Set<TelefoneBean> telefones = new HashSet<TelefoneBean>();

    @Override
    public String toString() {
        return "ContatoBean [id_contato=" + id_contato + ", data_nascimento=" + data_nascimento + ", nome=" + nome + ", rg=" + rg
                + ", telefones=" + telefones + "]";
    }

    public Long getId_contato() {
        return id_contato;
    }

    public void setId_contato(Long id_contato) {
        this.id_contato = id_contato;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Set<TelefoneBean> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<TelefoneBean> telefones) {
        this.telefones = telefones;
    }
}
