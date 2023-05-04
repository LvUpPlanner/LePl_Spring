package org.example.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class Timer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    private Task task;

    private LocalDateTime start_task;
    private LocalDateTime end_task;

    @Enumerated(EnumType.STRING)
    private TimeStatus timeStatus; //ALLOW, FOCUS

}
