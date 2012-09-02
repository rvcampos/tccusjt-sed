package br.com.usjt.ead.curso;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.usjt.ead.professor.ProfessorBean;

@Entity
@Table(name = "DISCIPLINA")
public class DisciplinaBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_disciplina")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer         id_disciplina;                      // integer,
    @Column
    private String          nome_disciplina;                    // varchar(100),
    @ManyToOne
    @JoinColumn(name = "id_professor")
    private ProfessorBean   professor;                          // integer
    @Column
    private Date            data_inicio;                        // date,
    @Column
    private Date            data_termino;                       // date,
    @Column
    private String          descricao;
    @OneToMany(mappedBy = "disciplina")
    private Set<ModuloBean> modulos = new HashSet<ModuloBean>();

    @Override
    public String toString() {
        return "DisciplinaBean [id_disciplina=" + id_disciplina + ", nome_disciplina=" + nome_disciplina + ", professor="
                + professor + ", data_inicio=" + data_inicio + ", data_termino=" + data_termino + "]";
    }

    public Integer getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Integer id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getNome_disciplina() {
        return nome_disciplina;
    }

    public void setNome_disciplina(String nome_disciplina) {
        this.nome_disciplina = nome_disciplina;
    }

    public ProfessorBean getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorBean professor) {
        this.professor = professor;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<ModuloBean> getModulos() {
        return modulos;
    }

    public void setModulos(Set<ModuloBean> modulos) {
        this.modulos = modulos;
    }
}
