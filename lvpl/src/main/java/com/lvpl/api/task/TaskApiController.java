package com.lvpl.api.task;

import com.lvpl.Service.member.MemberService;
import com.lvpl.Service.task.ListsService;
import com.lvpl.Service.task.TaskService;
import com.lvpl.Service.task.TaskStatusService;
import com.lvpl.Service.task.timer.TimerService;
import com.lvpl.api.argumentresolver.Login;
import com.lvpl.domain.member.Member;
import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.Task;
import com.lvpl.domain.task.TaskStatus;
import com.lvpl.domain.task.timer.Timer;
import com.lvpl.domain.task.timer.TimerStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.StyledEditorKit;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskApiController {
    private final TaskService taskService;
    private final TaskStatusService taskStatusService;
    private final ListsService listsService;
    private final MemberService memberService;

    /**
     * 일정 추가
     * 요청 형식(json) : CreateTaskRequestDto
     */
    @PostMapping(value = "/new")
    public String create(@Login Long memberId, @RequestBody CreateTaskRequestDto request) {
        Lists lists = null;
        List<Lists> listsList = listsService.findByCurrent(memberId, request.startTime); // db 에 기존 lists+member 가 있나 확인 (startTime, id 로 확인)
        // lists 가 없을 경우
        if(listsList.isEmpty()) { 
            Member member = memberService.findOne(memberId);
            lists = Lists.createLists(member, request.startTime, new ArrayList<Task>());
        }
        // lists 가 있을 경우
        else lists = listsList.get(0);
        listsService.join(lists);

        TaskStatus taskStatus = TaskStatus.createTaskStatus(false, false);
        Task task = Task.createTask(request.content, request.startTime, request.endTime, taskStatus);
        lists.addTask(task); // 일정 추가

        taskStatusService.join(taskStatus);
        taskService.join(task);
        return task.getId().toString(); // task id 반 환
    }

    /**
     * 일정 조회
     * 필요없어 보임
     */
//    @GetMapping(value = "")
//    public List<FindTaskResponseDto> findAll() {
//        List<Task> findTasks = taskService.findTasks();
//        List<FindTaskResponseDto> result = findTasks.stream()
//                .map(o -> new FindTaskResponseDto(o))
//                .collect(Collectors.toList());
//        return result;
//    }

    /**
     * 일정 삭제
     */


    /**
     * 일정 수정
     */



    // DTO => 엔티티 외부노출 금지 + 필요한것만 담아서 반환할 수 있어서 효과적
    @Getter
    static class CreateTaskRequestDto {
        private String content;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
    @Getter
    static class FindTaskResponseDto {
        private String content;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private List<Timer> timers;
        private Boolean completedStatus;
        private Boolean timerOnOff;
        // 생성자 꼭 작성
        public FindTaskResponseDto(Task task) {
            this.content = task.getContent();
            this.startTime = task.getStartTime();
            this.endTime = task.getEndTime();
            this.timers = task.getTimers();
            this.completedStatus = task.getTaskStatus().getCompletedStatus(); // lazy 강제 초기화 반드시
            this.timerOnOff = task.getTaskStatus().getTimerOnOff(); // lazy 강제 초기화 반드시(에러뜸 안하면)
        }
    }
}
