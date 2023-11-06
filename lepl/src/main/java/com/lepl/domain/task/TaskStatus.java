package com.lepl.domain.task;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TASK_STATUS")
public class TaskStatus {
    @Id
    @GeneratedValue
    @Column(name = "task_status_id")
    private Long id;

    private Boolean completedStatus; // 일정 완료 유무
    private Boolean timerOnOff; // 타이머 사용 유무

    //==생성 편의 메서드==//
    public static TaskStatus createTaskStatus(Boolean completedStatus, Boolean timerOnOff) {
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setCompletedStatus(completedStatus);
        taskStatus.setTimerOnOff(timerOnOff);
        return taskStatus;
    }

    //==비지니스 편의 메서드==//
    public TaskStatus update(Boolean completedStatus, Boolean timerOnOff) {
        this.completedStatus = completedStatus;
        this.timerOnOff = timerOnOff;
        return this;
    }
}
