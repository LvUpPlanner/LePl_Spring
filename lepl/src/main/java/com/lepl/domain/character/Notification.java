package com.lepl.domain.character;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Notification {
    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id") // FK
    private Character character;
}
