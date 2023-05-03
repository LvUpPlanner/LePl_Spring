package com.lvpl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "LISTS_TASK")
public class ListsTask {
    @Id @GeneratedValue
    @Column(name = "lists_task_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lists_id") // FK
    private Lists lists;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id") // FK
    private Task task;

    private Integer count;
}
