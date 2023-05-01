package org.example.Domain;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Character {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID")
    private Long id;
    private Long level;

    @OneToOne
    @JoinColumn(name = "COIN_ID")
    private Coin coin;

    @OneToOne
    @JoinColumn(name = "EXP_ID")
    private Exp exp;

    @OneToMany
    @JoinColumn(name = "ITEM_ID")
    private ArrayList<Item> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
