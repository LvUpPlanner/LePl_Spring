package com.lepl.domain.character;


import com.lepl.domain.task.timer.Timer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter @Setter
@Slf4j
public class Exp {
    @Id @GeneratedValue
    @Column(name = "exp_id")
    private Long id;

    private Long expAll=0L; // 누적
    private Long expValue=0L; // 현재
    private Long reqExp= 1L; // 필요경험치(초기값 1)
    private Long level= 1L;

    private Long pointTodayTimer=0L; // 일일 타이머 경험치량
    private Long pointTodayTask=0L; // 일일 일정 경험치량
    

    //== 비지니스 편의 메서드 ==//
    public Exp updateExp(Long pointTask, Long pointTimer) {
        if(pointTask>0 && pointTodayTask < 12) {
            Long checkMax = pointTodayTask+pointTask;
            if(checkMax > 12) { // 최대 일일 경험치 허용량 12
                pointTask = 12-pointTodayTask;
                pointTodayTask = 12l;
            }
            else pointTodayTask += pointTask;

            this.expAll += pointTask;
            this.expValue += pointTask;
            log.debug("처음 task : expValue {}, reqExp {}, level {}",expValue,reqExp,level);
            while(this.expValue >= reqExp) {
                this.level++;
                this.expValue = this.expValue-reqExp;
                this.reqExp = (long)(Math.pow(this.level,2) * 1.5); // 필요경험치 update
                log.debug("expValue {}, reqExp {}, level {}",expValue,reqExp,level);
            }
        }
        if(pointTimer>0 && pointTodayTimer < 12){
            Long checkMax = pointTodayTimer+pointTimer;
            if(checkMax > 12) { // 최대 일일 경험치 허용량 12
                pointTimer = 12-pointTodayTimer;
                pointTodayTimer = 12l;
            }else pointTodayTimer+=pointTimer;

            this.expAll += pointTimer;
            this.expValue += pointTimer;
            log.debug("처음 timer : expValue {}, reqExp {}, level {}",expValue,reqExp,level);
            while(this.expValue >= reqExp) {
                this.level++;
                this.expValue = this.expValue-reqExp;
                this.reqExp = (long)(Math.pow(this.level,2) * 1.5); // 필요경험치 update
                log.debug("expValue {}, reqExp {}, level {}",expValue,reqExp,level);
            }
        }

        return this;
    }
}
