package org.example.Domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private Long id;

    private String content;
    private String start_task;
    private String end_task;

    @OneToMany(mappedBy = "task")
    private List<Timer> timer;

    @OneToOne
    @JoinColumn(name = "TASK_STATUS_ID")
    private Task_Status task_status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart_task() {
        return start_task;
    }

    public void setStart_task(String start_task) {
        this.start_task = start_task;
    }

    public String getEnd_task() {
        return end_task;
    }

    public void setEnd_task(String end_task) {
        this.end_task = end_task;
    }

    public Task_Status getTask_status() {
        return task_status;
    }

    public void setTask_status(Task_Status task_status) {
        this.task_status = task_status;
    }
}
