package br.com.usjt.ead.contato;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "CONTATO")
public class ContatoBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_contato")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Long              id_contato;                             // integer
    @Column
    @NotNull(message="Preencha a data de nascimento")
    private Date              data_nascimento;                        // Date,
    @Column
    @NotNull(message="Preencha o Nome Completo")
    private String            nome;                                   // varchar(100),
    @Column
    @NotNull(message="Preencha o RG")
    private String            rg;                                     // varchar(12));
    @OneToMany(mappedBy = "contato", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "Por favor, preencha ao menos um telefone")
    @Valid
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
