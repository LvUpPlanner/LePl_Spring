package com.lvpl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Lists {
    @Id @GeneratedValue
    @Column(name = "lists_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // FK
    private Member member;

    private LocalDateTime listsDate;

    @OneToMany(mappedBy = "lists") // 양방향
    private List<ListsTask> listsTasks = new ArrayList<>();

}
