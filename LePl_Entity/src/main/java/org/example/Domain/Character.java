package org.example.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
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

    @OneToMany(mappedBy = "character")
    private List<Item> items;

}
