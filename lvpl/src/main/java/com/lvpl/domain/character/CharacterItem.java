package com.lvpl.domain.character;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "CHARACTER_ITEM")
public class CharacterItem {
    @Id @GeneratedValue
    @Column(name = "character_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    private Long itemId; // 실제 아이템이 가지는 고유값(Null 불가)
    private Boolean wearingStatus; // 착용 유무 T/F
}