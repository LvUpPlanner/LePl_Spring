package com.lvpl.domain.task;

import com.lvpl.domain.member.Member;
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

    /**
     * 연관관계 편의 메서드
     */
    public void addListsTask(ListsTask listsTask) {
        listsTask.setLists(this); // ListsTask(엔티티)에 Lists(엔티티)참조
        this.listsTasks.add(listsTask); // Lists(엔티티)의 listsTasks 리스트에 ListsTask(엔티티)추가
    }
}
