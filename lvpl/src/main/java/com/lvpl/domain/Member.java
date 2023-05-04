package com.lvpl.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; // DB PK
    @Column(nullable = false) // Not Null
    private String uid; // Entity ID => 대체키

    private String nickname;

    @OneToMany(mappedBy = "member") // 양방향
    private List<Lists> lists = new ArrayList<>(); // 컬렉션은 필드에서 바로 초기화

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id") // FK
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id") // FK
    private Character character;

    /**
     * 연관관계 편의 메서드
     */
    public void addLists(Lists lists) {
        lists.setMember(this); // Lists(엔티티)에 Member(엔티티)참조
        this.lists.add(lists); // Member(엔티티)의 lists 리스트에 Lists(엔티티)추가
    }

}
