package org.example.Domain;

import jakarta.persistence.*;

@Entity
public class Lists_Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LISTS_TASK_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LISTS_ID")
    private Lists lists;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Task task;

    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
