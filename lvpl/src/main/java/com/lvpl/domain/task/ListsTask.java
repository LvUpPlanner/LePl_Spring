package com.lvpl.domain.task;

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
    private Lists lists; // 리스트(하루단위) => 주문, 상품중에 주문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id") // FK 
    private Task task; // 일정 => 주문, 상품중에 상품

    // 필요없을듯(Lists에서 일정 개수를 파악해야하고, Member에서 전체 일정수를 파악해야하기 때문)
    // private Integer count;
}
