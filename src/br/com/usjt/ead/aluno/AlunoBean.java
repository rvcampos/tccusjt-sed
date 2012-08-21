package br.com.usjt.ead.aluno;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;

@Entity
@Table(name = "ALUNO")
public class AlunoBean
{

    @Id
    private Integer      id_aluno; // integer PRIMARY KEY,
    @OneToOne
    @JoinColumn(name = "id_contato")
    private ContatoBean  contato; // integer// REFERENCES contato(id_contato),
    @OneToOne
    @JoinColumn(name = "id_endereco")
    private EnderecoBean endereco; // integer// references
                                   // ENDERECO(id_endereco),
    private String       senha;   // varchar(20),
    private String       email;   // varchar(30),
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
