package com.lepl.domain.character;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter @Setter
@Slf4j
public class Exp {
    @Id @GeneratedValue
    @Column(name = "exp_id")
    private Long id;

    private Double expAll; // 누적
    private Double expValue; // 현재
    private Double reqExp= 1D; // 필요경험치(초기값 1)
    private Long level= 1L;

    //== 비지니스 편의 메서드 ==//
    public void updateExp(Double point) {
        this.expAll += point;
        this.expValue += point;
        log.debug("updateExp 통과");
        log.debug("처음 : expValue {}, reqExp {}, level {}",expValue,reqExp,level);
        while(this.expValue >= reqExp) {
            this.level++;
            this.expValue = this.expValue-reqExp;
            this.reqExp = Math.pow(this.level,2) * 1; // 필요경험치 update
            log.debug("expValue {}, reqExp {}, level {}",expValue,reqExp,level);
        }
    }
}
