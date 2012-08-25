package br.com.usjt.ead.curso;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MODULO")
public class ModuloBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_disciplina")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer        id_modulo;   // integer,
    @Column(length = 1)
    @NotNull
    private Integer        nivel_modulo; // varchar(100),
    @ManyToOne
    @JoinColumn(name = "id_disciplina")
    private DisciplinaBean disciplina;  // integer references
    @Column
    private Date           data_inicio; // date,
    @Column
    private Date           data_termino; // date,
    @OneToOne(mappedBy = "modulo")
    private AvaliacaoBean  avaliacao;

    @Override
    public String toString() {
        return "ModuloBean [id_modulo=" + id_modulo + ", nivel_modulo=" + nivel_modulo + ", disciplina=" + disciplina
                + ", data_inicio=" + data_inicio + ", data_termino=" + data_termino + "]";
    }

    public Integer getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(Integer id_modulo) {
        this.id_modulo = id_modulo;
    }

    public Integer getNivel_modulo() {
        return nivel_modulo;
    }

    public void setNivel_modulo(Integer nivel_modulo) {
        this.nivel_modulo = nivel_modulo;
    }

    public DisciplinaBean getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaBean disciplina) {
        this.disciplina = disciplina;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Date getData_termino() {
        return data_termino;
    }

    public void setData_termino(Date data_termino) {
        this.data_termino = data_termino;
    }

}