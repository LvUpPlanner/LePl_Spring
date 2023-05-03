package org.example.Domain;

import jakarta.persistence.*;

@Entity
public class Timer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Task task;
    private String start_task;
    private String end_task;
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
