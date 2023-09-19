package com.lepl.domain.character;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Follow {
    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    private Long followerId; // from
    private Long followingId; // to

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id") // FK
    private Character character;
}
