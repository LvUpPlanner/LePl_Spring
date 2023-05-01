package org.example.Domain;

import jakarta.persistence.*;

@Entity
public class Coin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COIN_ID")
    private Long id;

    @OneToOne(mappedBy = "coin")
    private Character character;
    private Long coin_all;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Long getCoin_all() {
        return coin_all;
    }

    public void setCoin_all(Long coin_all) {
        this.coin_all = coin_all;
    }
}
