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
    private String     dias;
    @Column
    private Time       horario;
    @Column
    private Time       horario_termino;
    @Column
    private String     uri;

    @Override
    public String toString() {
        return "SalaChatBean [id_sala=" + id_sala + ", id_chat=" + id_chat + ", horario=" + horario + ", horario_termino="
                + horario_termino + ", uri=" + uri + "]";
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

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public Time getHorario_termino() {
        return horario_termino;
    }

    public void setHorario_termino(Time horario_termino) {
        this.horario_termino = horario_termino;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

}