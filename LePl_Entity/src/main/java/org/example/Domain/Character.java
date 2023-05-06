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
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(mappedBy = "character")
    private Coin coin;

    @OneToOne(mappedBy = "character")
    private Exp exp;

    @OneToMany(mappedBy = "character")
    private List<Item> items;

}
