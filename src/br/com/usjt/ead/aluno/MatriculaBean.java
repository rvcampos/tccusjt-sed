package br.com.usjt.ead.aluno;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.usjt.ead.curso.ModuloBean;

@Table
@Entity
public class MatriculaBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_matricula")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer    id_matricula; // integer NOT NULL,
    @OneToOne
    @JoinColumn(name = "id_modulo")
    private ModuloBean modulo;      // integer,
    @OneToOne
    @JoinColumn(name = "id_aluno")
    private AlunoBean  aluno;       // integer,
    @Column
    private Boolean    concluido;

    @Override
    public String toString() {
        return "MatriculaBean [id_matricula=" + id_matricula + ", modulo=" + modulo + "]";
    }

    public Integer getId_matricula() {
        return id_matricula;
    }

    public void setId_matricula(Integer id_matricula) {
        this.id_matricula = id_matricula;
    }

    public ModuloBean getModulo() {
        return modulo;
    }

    public void setModulo(ModuloBean modulo) {
        this.modulo = modulo;
    }

    public AlunoBean getAluno() {
        return aluno;
    }

    public void setAluno(AlunoBean aluno) {
        this.aluno = aluno;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

}
