package br.com.usjt.ead.curso;

import java.util.ArrayList;
import java.util.List;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="questao")
public class QuestaoBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_questao")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Long                       id_questao;
    @Column
    private String                     conteudo;
    @ManyToOne
    @JoinColumn(name="id_avaliacao")
    @Cascade(value=CascadeType.ALL)
    private AvaliacaoBean              avaliacao;
    @OneToMany(mappedBy="questao")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private List<AlternativaBean> alternativas = new ArrayList<AlternativaBean>();

    public Long getId_questao() {
        return id_questao;
    }

    public void setId_questao(Long id_questao) {
        this.id_questao = id_questao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public AvaliacaoBean getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoBean avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<AlternativaBean> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<AlternativaBean> alternativas) {
        this.alternativas = alternativas;
    }

}
