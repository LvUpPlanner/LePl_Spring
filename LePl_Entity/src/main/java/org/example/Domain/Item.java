package org.example.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;

    private Long item_one_id;

    @Enumerated(EnumType.STRING)
    private WearingStatus wearingStatus; //아이템 착용여부, YES NO
}
