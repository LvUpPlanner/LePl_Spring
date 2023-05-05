package com.lvpl.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    
    // 추후에 필요할까봐 미리 생성
}
