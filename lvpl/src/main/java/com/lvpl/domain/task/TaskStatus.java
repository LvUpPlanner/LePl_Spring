package com.lvpl.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "TASK_STATUS")
public class TaskStatus {
    @Id @GeneratedValue
    @Column(name = "task_status_id")
    private Long id;

    private Boolean completedStatus; // 일정 완료 유무
    private Boolean timerOnOff; // 타이머 사용 유무

}
