package org.example.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String nickname;

    @OneToOne(mappedBy = "member")
    private Character character;

    @OneToMany(mappedBy = "member")
    private List<Lists> lists;

}
