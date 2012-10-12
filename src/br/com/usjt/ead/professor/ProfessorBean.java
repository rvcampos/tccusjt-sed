package br.com.usjt.ead.professor;

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
@Table(name = "PROFESSOR")
public class ProfessorBean
{

    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_professor")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer      id_professor; // integer PRIMARY KEY,
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_contato")
    private ContatoBean  contato;     // integer// REFERENCES
                                       // contato(id_contato),
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_endereco")
    private EnderecoBean endereco;    // integer// references
                                       // ENDERECO(id_endereco),
    @Column
    private String       senha;       // varchar(20),
    @Column
    private String       email;       // varchar(30),
    @Column
    private Long         cpf;         // numeric(12)

    @Override
    public String toString() {
        return "ProfessorBean [id_professor=" + id_professor + ", contato=" + contato + ", endereco=" + endereco + ", senha="
                + senha + ", email=" + email + ", cpf=" + cpf + "]";
    }

    public Integer getId_professor() {
        return id_professor;
    }

    public void setId_professor(Integer id_professor) {
        this.id_professor = id_professor;
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
