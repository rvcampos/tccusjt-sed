package br.com.usjt.ead.curso;

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
@Table(name = "MATERIAL_DIDATICO")
public class MaterialDidaticoBean
{
    @Id
    @SequenceGenerator(name = "gen", initialValue = 1, sequenceName = "seq_matdidatico")
    @GeneratedValue(generator = "gen", strategy = GenerationType.AUTO)
    private Integer    id_material;      // integer,
    @Column
    private String     endereco_material; // varchar(100),
    @OneToOne
    @JoinColumn(name = "id_modulo")
    private ModuloBean modulo;           // integer references

    @Column
    private Integer    tipo_material;

    @Override
    public String toString() {
        return "MaterialDidaticoBean [id_material=" + id_material + ", endereco_material=" + endereco_material + ", modulo="
                + modulo + ", tipo_material=" + tipo_material + "]";
    }

    public Integer getId_material() {
        return id_material;
    }

    public void setId_material(Integer id_material) {
        this.id_material = id_material;
    }

    public String getEndereco_material() {
        return endereco_material;
    }

    public void setEndereco_material(String endereco_material) {
        this.endereco_material = endereco_material;
    }

    public ModuloBean getModulo() {
        return modulo;
    }

    public void setModulo(ModuloBean modulo) {
        this.modulo = modulo;
    }

    public Integer getTipo_material() {
        return tipo_material;
    }

    public void setTipo_material(Integer tipo_material) {
        this.tipo_material = tipo_material;
    }

}
