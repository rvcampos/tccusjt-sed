package br.com.usjt.ead.curso;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.usjt.ead.aluno.AlunoBean;

@Entity
@Table(name = "Bloqueio_Disciplina")
public class BloqueioBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_bloqueio")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer         id_bloqueio;                      // integer,
    

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private AlunoBean aluno;
   
    @ManyToOne
    @JoinColumn(name = "id_disciplina")
    private DisciplinaBean disciplina;
    
    
    @Override
    public String toString() {
        return "BloqueioBean [id_aluno=" + aluno.getId_aluno() + ", id_disciplina=" + disciplina.getId_disciplina()  + "]";
    }


    public AlunoBean getAluno() {
        return aluno;
    }


    public DisciplinaBean getDisciplina() {
        return disciplina;
    }


    public void setAluno(AlunoBean aluno) {
        this.aluno = aluno;
    }


    public void setDisciplina(DisciplinaBean disciplina) {
        this.disciplina = disciplina;
    }
    
    public Integer getId_bloqueio() {
        return id_bloqueio;
    }


    public void setId_bloqueio(Integer id_bloqueio) {
        this.id_bloqueio = id_bloqueio;
    }
}
