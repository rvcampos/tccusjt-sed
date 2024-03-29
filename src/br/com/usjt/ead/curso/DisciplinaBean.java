package br.com.usjt.ead.curso;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.NotEmpty;

import br.com.usjt.ead.DateValidation;
import br.com.usjt.ead.DateValidation.WHEN;
import br.com.usjt.ead.professor.ProfessorBean;

@Entity
@Table(name = "DISCIPLINA")
public class DisciplinaBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_disciplina")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer           id_disciplina;                         // integer,
    @Column
    private String            nome_disciplina;                       // varchar(100),
    @ManyToOne
    @JoinColumn(name = "id_professor")
    private ProfessorBean     professor;                             // integer
    @Column
    @NotNull(message="Data de início deve ser preenchida")
    @DateValidation(period = WHEN.BEFORE, properties = "data_termino", message = "Data de inicio deve ser menor que data de Término")
    private Date              data_inicio;                           // date,
    @Column
    @NotNull(message="Data de término deve ser preenchida")
    private Date              data_termino;                          // date,
    @Column
    private String            descricao;
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)
    @NotEmpty(message = "Ocorreu um erro ao gravas as alterações")
    @Valid
    @OrderBy(clause = "nivel_modulo asc")
    private Set<ModuloBean>   modulos  = new HashSet<ModuloBean>();

    @OneToMany(mappedBy = "disciplina")
    private Set<BloqueioBean> bloqueio = new HashSet<BloqueioBean>();

    @Override
    public String toString() {
        return "DisciplinaBean [id_disciplina=" + id_disciplina + ", nome_disciplina=" + nome_disciplina + ", professor="
                + professor.getContato().getNome() + ", data_inicio=" + data_inicio + ", data_termino=" + data_termino + "]";
    }

    public Set<BloqueioBean> getBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(Set<BloqueioBean> bloqueio) {
        this.bloqueio = bloqueio;
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

    public ModuloBean getModuloByLevel(Integer nivel) {
        for (ModuloBean b : getModulos()) {
            if (b.getNivel_modulo().intValue() == nivel.intValue()) {
                return b;
            }
        }

        return null;
    }
}
