package br.com.usjt.ead.aluno;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.usjt.ead.curso.ModuloBean;

@Entity
@Table(name = "MATRICULA")
public class MatriculaBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_matricula")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer    id_matricula;                    // integer NOT NULL,
    @ManyToOne
    @JoinColumn(name = "id_modulo")
    private ModuloBean modulo;                          // integer,
    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private AlunoBean  aluno;                           // integer,
    @Column
    private Boolean    concluido   = new Boolean(false);
    @Column
    private Date       dt_avaliacao;
    @Column
    private Boolean    certificado = new Boolean(false);
    @Column
    private int        qtde_falha;
    @Column
    private Date       dt_matricula;
    @Column
    private Date       dt_termino;

    public int getQtde_falha() {
        return qtde_falha;
    }

    public void setQtde_falha(int qtde_falha) {
        this.qtde_falha = qtde_falha;
    }

    /**
     * @return the dt_avaliacao
     */
    public Date getDt_avaliacao() {
        return dt_avaliacao;
    }

    /**
     * @param dt_avaliacao
     *            the dt_avaliacao to set
     */
    public void setDt_avaliacao(Date dt_avaliacao) {
        this.dt_avaliacao = dt_avaliacao;
    }

    /**
     * @return the certificado
     */
    public Boolean getCertificado() {
        return certificado;
    }

    /**
     * @param certificado
     *            the certificado to set
     */
    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

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

    public Date getDt_matricula() {
        return dt_matricula;
    }

    public void setDt_matricula(Date dt_matricula) {
        this.dt_matricula = dt_matricula;
    }

    public Date getDt_termino() {
        return dt_termino;
    }

    public void setDt_termino(Date dt_termino) {
        this.dt_termino = dt_termino;
    }

}
