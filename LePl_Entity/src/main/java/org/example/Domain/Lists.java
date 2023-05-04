package org.example.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
public class Lists {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LISTS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    private LocalDateTime lists_date; //Date -> LocalDateTime

    @OneToMany(mappedBy = "lists")
    private List<Lists_Task> lists_tasks = new ArrayList<>(); //List 초기화, null 값 대비

}
