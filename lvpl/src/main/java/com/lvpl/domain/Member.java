package com.lvpl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id") // PK
    private Long id;

    private String nickname;

    @OneToMany(mappedBy = "member") // 양방향
    private List<Lists> lists = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id") // FK
    private Profile profile;
}
