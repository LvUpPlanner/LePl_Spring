package com.lepl.api.character;


import com.lepl.Service.character.ExpService;
import com.lepl.Service.task.TaskService;
import com.lepl.Service.task.timer.TimerService;
import com.lepl.api.argumentresolver.Login;
import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import com.lepl.domain.member.Member;
import com.lepl.domain.task.Task;
import com.lepl.domain.task.TaskStatus;
import com.lepl.domain.task.timer.Timer;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/exp")
public class ExpApiController {
    private final ExpService expService;
    private final TimerService timerService;
    private final TaskService taskService;
    private final EntityManager em;

    /**
     * 경험치량 조회 API - 누적, 현재, 레벨 제공
     */
    @GetMapping("/all")
    public Exp findOneWithMember(@Login Long memberId) {
        Member member = expService.findOneWithMember(memberId);
        if(member == null) return null;
        return member.getCharacter().getExp();
    }

    /**
     * 경험치 업데이트
     */
    @PostMapping("/update")
    public Exp expUpdate(@Login Long memberId, @RequestBody List<TaskDto> taskDtos) {
        Member member = expService.findOneWithMember(memberId); // 영속 exp
        if(member == null) return null;

        Exp exp = member.getCharacter().getExp();
        Double pointTimerNo = 0D;
        Double pointTimerYes = 0D;
        for(TaskDto taskDto : taskDtos) {
            if(!taskDto.completedStatus) continue;
            if(!taskDto.timerOnOff) {
                pointTimerNo++;
                continue;
            }
            // 아래는 타이머 사용시 경험치 (타이머 상태 "집중,허용"은 일단 무시)
            List<Timer> timers = timerService.findAllWithTask(taskDto.taskId);
            log.debug("timers.get(0) : {}", timers.get(0).getId());
            pointTimerYes++; // 기본값 plus 1
            for(Timer timer : timers) {
                Date end = Timestamp.valueOf(timer.getEndTime());
                Date start = Timestamp.valueOf(timer.getStartTime());
                Long diff = end.getTime()-start.getTime();
                log.debug("diff ms : {}",diff);
                log.debug("diff Sec : {}",diff/1000);
                log.debug("diff hour : {}",diff/(60*60*1000));
                log.debug("diff day : {}",diff/(24*60*60*1000));
                Double hour = diff.doubleValue()/(60*60*1000);
                pointTimerYes+=hour; // 타이머 추가 보상
            }
        }
        // 경험치 업데이트
        if(pointTimerNo > 0){
            if(pointTimerNo > 10) pointTimerNo = 10D; // 최대 일일 경험치 허용량 10 (임의로 지정)
            expService.update(exp,pointTimerNo); // 더티체킹
        }
        if(pointTimerYes > 0) {
            if(pointTimerYes > 10) pointTimerYes = 10D; // 최대 타이머 허용시간 10 (임의로 지정)
            expService.update(exp, pointTimerYes);
        }
        return exp;
    }




    // DTO
    @Getter
    static class TaskDto {
        private Long taskId;
        private Boolean completedStatus;
        private Boolean timerOnOff;
//        public TaskDto(Task task) { // lazy 강제 초기화
//            taskId = task.getId();
//            completedStatus = task.getTaskStatus().getCompletedStatus();
//            timerOnOff = task.getTaskStatus().getTimerOnOff();
//        }
    }

    @PostConstruct
    @Transactional
    public void init() {
        log.info("PostConstruct 테스트 " );
        // 테스트용 데이터 삽입
        for(long i = 1 ; i<=3; i++) {
            Task t = new Task();
            Timer timer = new Timer();
            LocalDateTime end = LocalDateTime.now();
            LocalDateTime start = LocalDateTime.of(2023, Month.SEPTEMBER, 18, 10, 30);
            timer.setStartTime(start);
            timer.setEndTime(end);

            taskService.join(t);
            timer.setTask(t);

            timerService.join(timer);
            t.addTimer(timer); // fk 포함

        }
    }
}
