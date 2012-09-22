package br.com.usjt.ead.curso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="alternativa")
public class AlternativaBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_alternativa")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Long        id_alternativa;
    @ManyToOne
    @JoinColumn(name="id_questao")
    @Cascade(value=CascadeType.ALL)
    private QuestaoBean questao;
    @Column
    private String      conteudo;
    @Column
    private Boolean     correta = new Boolean(false);

    public Long getId_alternativa() {
        return id_alternativa;
    }

    public void setId_alternativa(Long id_alternativa) {
        this.id_alternativa = id_alternativa;
    }

    public QuestaoBean getQuestao() {
        return questao;
    }

    public void setQuestao(QuestaoBean questao) {
        this.questao = questao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }

    @Override
    public String toString() {
        return "AlternativaBean [id_alternativa=" + id_alternativa + ", conteudo=" + conteudo + ", correta=" + correta + "]";
    }

}
