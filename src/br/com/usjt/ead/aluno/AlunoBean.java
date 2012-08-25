package br.com.usjt.ead.aluno;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;

@Entity
@Table(name = "ALUNO")
public class AlunoBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_aluno")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer      id_aluno; // integer PRIMARY KEY,
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_contato")
    private ContatoBean  contato; // integer// REFERENCES contato(id_contato),
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private EnderecoBean endereco; // integer// references
    @Column
    private String       senha;   // varchar(20),
    @Column
    private String       email;   // varchar(30),
    @Column
    private Long         cpf;     // numeric(12)

    @Override
    public String toString() {
        return "AlunoBean [id_aluno=" + id_aluno + ", contato=" + contato + ", endereco=" + endereco + ", senha=" + senha
                + ", email=" + email + ", cpf=" + cpf + "]";
    }

    public Integer getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(Integer id_aluno) {
        this.id_aluno = id_aluno;
    }

    public ContatoBean getContato() {
        return contato;
    }

    public void setContato(ContatoBean contato) {
        this.contato = contato;
    }

    public EnderecoBean getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoBean endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }
}
