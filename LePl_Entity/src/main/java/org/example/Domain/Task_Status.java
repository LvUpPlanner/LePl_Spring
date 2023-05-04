package org.example.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Task_Status {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_STATUS_ID")
    private Long id;

    private Boolean status;
    private Boolean timerOnOff;

}
