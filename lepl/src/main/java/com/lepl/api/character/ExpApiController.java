package com.lepl.api.character;


import com.lepl.Service.character.CharacterService;
import com.lepl.Service.character.ExpService;
import com.lepl.Service.member.MemberService;
import com.lepl.Service.task.ListsService;
import com.lepl.Service.task.TaskService;
import com.lepl.api.argumentresolver.Login;
import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import com.lepl.domain.member.Member;
import com.lepl.domain.task.Lists;
import com.lepl.domain.task.Task;
import com.lepl.domain.task.TaskStatus;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/exp")
public class ExpApiController {
    private final ExpService expService;
    private final TaskService taskService;
    private final ListsService listsService;

    private final MemberService memberService;
    private final CharacterService characterService;

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
     * 경험치 업데이트 - 일반 일정 완료
     * List 형태로 받기
     */
    @PostMapping("/tasks")
    public ResponseEntity<String> expTask(@Login Long memberId, @RequestBody List<TaskDto> taskDtos) {
        log.debug("exp/tasks 입장");
        Member member = expService.findOneWithMember(memberId); // 영속 exp
        if(member == null) return null;
        Exp exp = member.getCharacter().getExp();;
        Long pointTimer = 0L;
        Long pointTask = 0L;
        log.debug("기존 exp? : {}",exp.getExpAll());
        List<Task> tasks = new ArrayList<>();
        for(TaskDto taskDto : taskDtos) {
            Task t = taskService.findOne(taskDto.getTaskId());
            tasks.add(t);
        }
        for(Task task : tasks) {
            if(task.getTaskStatus().getCompletedStatus()) continue; // 이미 이전에 완료했던 일정이라 pass
            if(!task.getTaskStatus().getTimerOnOff()) { // 타이머가 아닌지 추가 점검
                TaskStatus taskStatus = TaskStatus.createTaskStatus(true, false);
                taskService.updateStatus(task, taskStatus, null);
                pointTask++;
                log.debug("확인용 pointTask : {}",pointTask);
            }
        }
        // 경험치 업데이트
        expService.update(exp, pointTask, pointTimer); // 더티체킹
        return ResponseEntity.status(HttpStatus.OK).body("일정을 완료하였습니다."); // 200
    }

    /**
     * 경험치 업데이트 - 일정 타이머 종료
     * 1개씩 받기
     */
    @PostMapping("/timers")
    public ResponseEntity<String> expTimer(@Login Long memberId, @RequestBody TimerDto timerDto) {
        Member member = expService.findOneWithMember(memberId); // 영속 exp
        Task task = taskService.findOne(timerDto.getTaskId());
        if(member == null || task == null) return null;
        Exp exp = member.getCharacter().getExp();
        Long pointTimer = 0L;
        Long pointTask = 0L;
        // 이미 이전에 완료했던 일정은 아무처리 없이 null
        if(task.getTaskStatus().getCompletedStatus()) return null;
        if(!task.getTaskStatus().getTimerOnOff()) { // 첫 타이머 종료
            log.debug("첫 타이머 종료(일정완료)");
            // 일정상태{타이머상태ON}, 일정{잔여시간}, 리스트{총사용시간}, 리스트{현재시간} 기록
            // timerDto.getUseTime() -> 밀리세컨 단위 "사용시간"
            Date end = Timestamp.valueOf(task.getEndTime());
            Date start = Timestamp.valueOf(task.getStartTime());
            Long remainTime = (end.getTime()-start.getTime())-timerDto.getUseTime(); // 잔여시간 - 밀리세컨 단위
            TaskStatus taskStatus;
            if (remainTime <= 0) {
                taskStatus = TaskStatus.createTaskStatus(true, true); // 잔여시간 <= 0
                remainTime = 0L; // 음수는 사용X
            }
            else taskStatus = TaskStatus.createTaskStatus(false, true); // 잔여시간 > 0
            Long timerAllUseTime = task.getLists().getTimerAllUseTime() + timerDto.getUseTime(); // 타이머총사용시간 - 밀리세컨 단위
            Long curTime =task.getLists().getCurTime() + timerDto.getUseTime(); // 현재시간 - 밀리세컨 단위

            Long testTime = remainTime; // 디버깅용 hour, minute, second 계산 -> 주석처리 할 것
            Long hour = testTime/(60*60*1000);
            testTime %= (60*60*1000);
            Long minute = testTime / (60*1000);
            testTime %= (60*1000);
            Long second = testTime / (1000);
            log.debug("remainTime -> {}:{}:{}", hour, minute, second);
            // remainTime -> 519:31189:1871391
            // 시간계산
            if(curTime/(60*60*1000) != 0) {
                Long expTime = curTime/(60*60*1000);
                curTime = curTime%(60*60*1000);
                pointTimer = expTime; // 경험치용 시간
            }
            taskService.updateStatus(task, taskStatus, remainTime); // 더티체킹
            listsService.updateTime(task.getLists(), timerAllUseTime, curTime); // 더티체킹
        }else {
            log.debug("처음이후 타이머 종료(일정완료)");
            Long remainTime = task.getRemainTime() - timerDto.getUseTime(); // 잔여시간 - 밀리세컨 단위
            Long timerAllUseTime = task.getLists().getTimerAllUseTime() + timerDto.getUseTime(); // 타이머총사용시간 - 밀리세컨 단위
            TaskStatus taskStatus;
            if (remainTime <= 0) {
                taskStatus = TaskStatus.createTaskStatus(true, true); // 잔여시간 <= 0
                remainTime = 0L; // 음수는 사용X
            }
            else taskStatus = TaskStatus.createTaskStatus(false, true); // 잔여시간 > 0
            Long curTime = task.getLists().getCurTime() + timerDto.getUseTime(); // 현재시간 - 밀리세컨 단위
            Long testTime = remainTime; // 디버깅용 hour, minute, second 계산 -> 주석처리 할 것
            Long hour = testTime/(60*60*1000);
            testTime %= (60*60*1000);
            Long minute = testTime / (60*1000);
            testTime %= (60*1000);
            Long second = testTime / (1000);
            log.debug("remainTime -> {}:{}:{}", hour, minute, second);
            // 시간계산
            if(curTime/(60*60*1000) != 0) {
                Long expTime = curTime/(60*60*1000);
                curTime = curTime%(60*60*1000);
                pointTimer = expTime; // 경험치용 시간
            }
            taskService.updateStatus(task, taskStatus, remainTime); // 더티체킹
            listsService.updateTime(task.getLists(), timerAllUseTime, curTime); // 더티체킹
        }
        // 경험치 업데이트
        expService.update(exp, pointTask, pointTimer); // 더티체킹
        return ResponseEntity.status(HttpStatus.OK).body("일정을 완료하였습니다."); // 200
    }


    // DTO
    @Getter
    static class TaskDto {
        private Long taskId;
    }
    @Getter
    static class TimerDto {
        private Long taskId;
        private Long useTime; // 밀리세컨 단위
    }

    @PostConstruct
    @Transactional
    public void init() {
        log.info("PostConstruct 테스트 " );
        // 테스트용 데이터 삽입
        Exp exp = new Exp();
        expService.join(exp);
        Character character = new Character();
        character.setExp(exp);
        characterService.join(character);

        Member member = Member.createMember("123", "사용자1");
        member.setCharacter(character);
        memberService.join(member);

        // Task 3개정도
        LocalDateTime today = LocalDateTime.now();
        Lists lists = Lists.createLists(member, today, new ArrayList<>());
        listsService.join(lists); // 먼저 lists init
        for(long i = 1 ; i<=3; i++) {
            LocalDateTime end = LocalDateTime.now();
            LocalDateTime start = LocalDateTime.of(2023, Month.SEPTEMBER, 21, 20, 30);
            TaskStatus taskStatus = TaskStatus.createTaskStatus(false, false); // default
            Task t = Task.createTask("test", start, end, taskStatus);
            t.setLists(lists);
            taskService.join(t);
        }

        // 번외) 시간 계산 테스트용
        LocalDateTime startTime = LocalDateTime.of(2023, Month.OCTOBER, 13, 13, 10);
        LocalDateTime endTime = LocalDateTime.of(2023, Month.OCTOBER, 13, 15, 10);
        Date end = Timestamp.valueOf(endTime);
        Date start = Timestamp.valueOf(startTime);
        Long diff = (end.getTime()-start.getTime());
        log.debug("시간 계산 테스트용 밀리세컨단위 : {}", diff);
    }
}
