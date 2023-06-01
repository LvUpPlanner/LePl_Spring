package com.lvpl.domain.task;

import com.lvpl.domain.task.timer.Timer;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "task") // 양방향
    private List<Timer> timers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_status_id")
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lists_id")
    private Lists lists;


    //==연관관계 편의 메서드==//
    public void addTimer(Timer timer) {
        timer.setTask(this); // Timer(엔티티)에 Task(엔티티)참조
        this.timers.add(timer); // Task(엔티티)의 timers 리스트에 Timer(엔티티)추가
    }
    //==생성 편의 메서드==//
    public static Task createTask(String content, LocalDateTime startTime, LocalDateTime endTime, TaskStatus taskStatus) {
        Task task = new Task();
        task.content = content;
        task.startTime = startTime;
        task.endTime = endTime;
        task.taskStatus = taskStatus;
        return task;
    }
}
