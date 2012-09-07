package br.com.usjt.ead.curso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "AVALIACAO")
public class AvaliacaoBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_avaliacao")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer    id_avaliacao; // integer,
    @OneToOne
    @JoinColumn(name = "id_modulo")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private ModuloBean modulo;      // varchar(100),
    @OneToMany(mappedBy="avaliacao")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    @NotEmpty(message="As questões devem ser preenchidas")
    @Valid
    private List<QuestaoBean> questoes = new ArrayList<QuestaoBean>();

    @Override
    public String toString() {
        return "AvaliacaoBean [id_avaliacao=" + id_avaliacao + ", questoes=" + questoes +"]";
    }

    public Integer getId_avaliacao() {
        return id_avaliacao;
    }

    public void setId_avaliacao(Integer id_avaliacao) {
        this.id_avaliacao = id_avaliacao;
    }

    public ModuloBean getModulo() {
        return modulo;
    }

    public void setModulo(ModuloBean modulo) {
        this.modulo = modulo;
    }

    public List<QuestaoBean> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<QuestaoBean> questoes) {
        this.questoes = questoes;
    }

}
