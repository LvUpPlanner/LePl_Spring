package org.example.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Lists_Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LISTS_TASK_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LISTS_ID")
    private Lists lists;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    private Task task;

    private int count;
}
