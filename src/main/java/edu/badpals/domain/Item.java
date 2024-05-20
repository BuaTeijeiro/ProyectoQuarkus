package edu.badpals.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="t_items")
public class Item extends PanacheEntityBase{
    @Id
    @Column(name="item_nom")
    String nombre ="";

    @Column(name="item_prop")
    int quality;

    @Column(name="item_tipo")
    String tipo;

    public Item() {
    }

    public Item(String name, int quality, String tipo) {
        this.nombre = name;
        this.quality = quality;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
