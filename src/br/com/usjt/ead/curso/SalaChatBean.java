package br.com.usjt.ead.curso;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.usjt.ead.DateValidation;
import br.com.usjt.ead.DateValidation.WHEN;

@Entity
@Table(name = "sala_chat")
public class SalaChatBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_chat")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Long       id_sala;
    @OneToOne(optional = false)
    @JoinColumn(name = "id_modulo")
    private ModuloBean modulo;
    @Column
    @NotNull
    private String     id_chat;
    @Column
    @DateValidation(period = WHEN.BEFORE, properties = "termino_domingo", message = "Hora de Termino no Domingo, deve ser maior que inicio")
    private Time       inicio_domingo;
    @Column
    private Time       termino_domingo;
    @Column
    @DateValidation(period = WHEN.BEFORE, properties = "termino_segunda", message = "Hora de Termino na Segunda, deve ser maior que inicio")
    private Time       inicio_segunda;
    @Column
    private Time       termino_segunda;
    @Column
    @DateValidation(period = WHEN.BEFORE, properties = "termino_terca", message = "Hora de Termino na Terça, deve ser maior que inicio")
    private Time       inicio_terca;
    @Column
    private Time       termino_terca;
    @Column
    @DateValidation(period = WHEN.BEFORE, properties = "termino_quarta", message = "Hora de Termino na Quarta, deve ser maior que inicio")
    private Time       inicio_quarta;
    @Column
    private Time       termino_quarta;
    @Column
    @DateValidation(period = WHEN.BEFORE, properties = "termino_quinta", message = "Hora de Termino na Quinta, deve ser maior que inicio")
    private Time       inicio_quinta;
    @Column
    private Time       termino_quinta;
    @Column
    @DateValidation(period = WHEN.BEFORE, properties = "termino_sexta", message = "Hora de Termino na Sexta, deve ser maior que inicio")
    private Time       inicio_sexta;
    @Column
    private Time       termino_sexta;
    @Column
    @DateValidation(period = WHEN.BEFORE, properties = "termino_sabado", message = "Hora de Termino no Sábado, deve ser maior que inicio")
    private Time       inicio_sabado;
    @Column
    private Time       termino_sabado;
    @Column
    private String     uri;

    @Override
    public String toString() {
        return "SalaChatBean [id_sala=" + id_sala + ", modulo=" + modulo + ", id_chat=" + id_chat
                + ", inicio_domingo=" + inicio_domingo + ", termino_domingo=" + termino_domingo + ", inicio_segunda="
                + inicio_segunda + ", termino_segunda=" + termino_segunda + ", inicio_terca=" + inicio_terca + ", termino_terca="
                + termino_terca + ", inicio_quarta=" + inicio_quarta + ", termino_quarta=" + termino_quarta + ", inicio_quinta="
                + inicio_quinta + ", termino_quinta=" + termino_quinta + ", inicio_sexta=" + inicio_sexta + ", termino_sexta="
                + termino_sexta + ", inicio_sabado=" + inicio_sabado + ", termino_sabado=" + termino_sabado + "]";
    }

    public Long getId_sala() {
        return id_sala;
    }

    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
    }

    public ModuloBean getModulo() {
        return modulo;
    }

    public void setModulo(ModuloBean modulo) {
        this.modulo = modulo;
    }

    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

    public Time getInicio_domingo() {
        return inicio_domingo;
    }

    public void setInicio_domingo(Time inicio_domingo) {
        this.inicio_domingo = inicio_domingo;
    }

    public Time getTermino_domingo() {
        return termino_domingo;
    }

    public void setTermino_domingo(Time termino_domingo) {
        this.termino_domingo = termino_domingo;
    }

    public Time getInicio_segunda() {
        return inicio_segunda;
    }

    public void setInicio_segunda(Time inicio_segunda) {
        this.inicio_segunda = inicio_segunda;
    }

    public Time getTermino_segunda() {
        return termino_segunda;
    }

    public void setTermino_segunda(Time termino_segunda) {
        this.termino_segunda = termino_segunda;
    }

    public Time getInicio_terca() {
        return inicio_terca;
    }

    public void setInicio_terca(Time inicio_terca) {
        this.inicio_terca = inicio_terca;
    }

    public Time getTermino_terca() {
        return termino_terca;
    }

    public void setTermino_terca(Time termino_terca) {
        this.termino_terca = termino_terca;
    }

    public Time getInicio_quarta() {
        return inicio_quarta;
    }

    public void setInicio_quarta(Time inicio_quarta) {
        this.inicio_quarta = inicio_quarta;
    }

    public Time getTermino_quarta() {
        return termino_quarta;
    }

    public void setTermino_quarta(Time termino_quarta) {
        this.termino_quarta = termino_quarta;
    }

    public Time getInicio_quinta() {
        return inicio_quinta;
    }

    public void setInicio_quinta(Time inicio_quinta) {
        this.inicio_quinta = inicio_quinta;
    }

    public Time getTermino_quinta() {
        return termino_quinta;
    }

    public void setTermino_quinta(Time termino_quinta) {
        this.termino_quinta = termino_quinta;
    }

    public Time getInicio_sexta() {
        return inicio_sexta;
    }

    public void setInicio_sexta(Time inicio_sexta) {
        this.inicio_sexta = inicio_sexta;
    }

    public Time getTermino_sexta() {
        return termino_sexta;
    }

    public void setTermino_sexta(Time termino_sexta) {
        this.termino_sexta = termino_sexta;
    }

    public Time getInicio_sabado() {
        return inicio_sabado;
    }

    public void setInicio_sabado(Time inicio_sabado) {
        this.inicio_sabado = inicio_sabado;
    }

    public Time getTermino_sabado() {
        return termino_sabado;
    }

    public void setTermino_sabado(Time termino_sabado) {
        this.termino_sabado = termino_sabado;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}