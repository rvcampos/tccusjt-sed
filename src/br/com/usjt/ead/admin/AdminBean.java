package br.com.usjt.ead.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "ADMINISTRADOR", uniqueConstraints = { @UniqueConstraint(columnNames = "email", name = "uk_email_administrador") })
public class AdminBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_admin")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer            id_admin;                                 // integer
                                                                          // PRIMARY
                                                                          // KEY,
    @Column
    @NotNull(message = "Preencha a senha")
    private String             senha;                                    // varchar(100),
    @Column
    @Email(message = "E-mail inválido")
    private String             email;                                    // varchar(100),
    @Column
    @NotNull(message = "Preencha a nome")
    private String             nome;                                    // varchar(100),

    @Override
    public String toString() {
        return "AdminBean [id_admin=" + id_admin + ", senha=" + senha + ", email=" + email + ", nome=" + nome + "]";
    }

    public Integer getId_admin() {
        return id_admin;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public void setId_admin(Integer id_admin) {
        this.id_admin = id_admin;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}