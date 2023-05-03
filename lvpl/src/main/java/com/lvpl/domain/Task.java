package com.lvpl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Task {
    @Id @GeneratedValue
    @Column(name = "task_id")
    private Long id;

    private String content;
    private LocalDateTime start;
    private LocalDateTime end;

    @OneToMany(mappedBy = "task") // 양방향
    private List<Timer> timers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_status_id")
    private TaskStatus taskStatus;
}
