package edu.badpals.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="t_ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ord_id")
    private Long id;

    @Column(name="ord_user")
    private String user;

    @Column(name="ord_item")
    private String item;

    public Orden() {
    }

    public Orden(String user, String item) {
        this.user = user;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }



}