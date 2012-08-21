package br.com.usjt.ead.curso;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
    private ModuloBean modulo;      // varchar(100),
    @Column
    private Date       data_inicio; // date,
    @Column
    private Date       data_termino; // date,

    @Override
    public String toString() {
        return "AvaliacaoBean [id_avaliacao=" + id_avaliacao + ", modulo=" + modulo + ", data_inicio=" + data_inicio
                + ", data_termino=" + data_termino + "]";
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
