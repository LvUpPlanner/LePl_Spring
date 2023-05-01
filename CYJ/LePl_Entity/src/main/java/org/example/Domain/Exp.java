package org.example.Domain;

import jakarta.persistence.*;

@Entity
public class Exp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXP_ID")
    private Long id;

    @OneToOne(mappedBy = "exp")
    private Character character;
    private Long exp_all;

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

    public Long getExp_all() {
        return exp_all;
    }

    public void setExp_all(Long exp_all) {
        this.exp_all = exp_all;
    }
}
