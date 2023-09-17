package com.lepl.domain.character;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter @Setter
public class Exp {
    @Id @GeneratedValue
    @Column(name = "exp_id")
    private Long id;

    private Long expAll; // 누적
    private Long expValue; // 현재
    private Long level; 

    //== 비지니스 편의 메서드 ==//
    public void updateExp(Long point) {
        this.expAll += point;
        this.expValue = point%10; // 나중에 경험치 도표에 맞게 적용되게끔 수정해야함. -> 임의로 경험치량 10으로 지정한 것
        this.level = expAll/10; // 이부분도 수정해야함.
    }
}
