package org.example.Domain;

import jakarta.persistence.*;

@Entity
public class Task_Status {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_STATUS_ID")
    private Long id;

    private Boolean status;
    private Boolean timer_onoff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getTimer_onoff() {
        return timer_onoff;
    }

    public void setTimer_onoff(Boolean timer_onoff) {
        this.timer_onoff = timer_onoff;
    }
}
