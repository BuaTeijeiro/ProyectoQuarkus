package edu.badpals.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="t_users")
public class Usuaria {
    @Id
    @Column(name="user_nom")
    String nombre = "";

    @Column(name="user_prop")
    int destreza;

    public Usuaria() {
    }

    public Usuaria(String nombre, int destreza) {
        this.nombre = nombre;
        this.destreza = destreza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }



}
