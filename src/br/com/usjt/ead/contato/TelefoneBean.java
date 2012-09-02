package br.com.usjt.ead.contato;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TELEFONES")
public class TelefoneBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_telefone")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Long             id_telefone; // integer PRIMARY KEY,
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contato")
    @NotNull
    private ContatoBean      contato;    // integer references contato
    @ManyToOne
    @JoinColumn(name = "tipo", referencedColumnName = "id_tipo")
    private TipoTelefoneBean tipo;
    @Column
    @NotNull(message = "Favor informar o DDD")
    private Integer          ddd;        // integer,
    @Column
    @NotNull(message = "Favor informar o Telefone")
    private Long             telefone;   // numeric(9)

    @Override
    public String toString() {
        return "TelefoneBean [id_telefone=" + id_telefone + ", contato=" + contato + ", tipo=" + tipo + ", ddd=" + ddd
                + ", telefone=" + telefone + "]";
    }

    public Long getId_telefone() {
        return id_telefone;
    }

    public void setId_telefone(Long id_telefone) {
        this.id_telefone = id_telefone;
    }

    public ContatoBean getContato() {
        return contato;
    }

    public void setContato(ContatoBean contato) {
        this.contato = contato;
    }

    public TipoTelefoneBean getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefoneBean tipo) {
        this.tipo = tipo;
    }

    public Integer getDdd() {
        return ddd;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }
}
