package com.lvpl.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Character {
    @Id @GeneratedValue
    @Column(name = "character_id")
    private Long id;
    private Long level;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id") // FK
    private Coin coin;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exp_id") // FK
    private Exp exp;
    @OneToMany(mappedBy = "character") // 양방향
    private List<CharacterItem> characterItems = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    public void addCharacterItem(CharacterItem characterItem) {
        characterItem.setCharacter(this); // CharacterItem(엔티티)에 Character(엔티티)참조
        this.characterItems.add(characterItem); // Character(엔티티)의 characterItems 리스트에 CharacterItem(엔티티)추가
    }
}
