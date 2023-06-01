package com.lvpl.api.task;

import com.lvpl.Service.member.MemberService;
import com.lvpl.Service.task.ListsService;
import com.lvpl.Service.task.TaskService;
import com.lvpl.api.argumentresolver.Login;
import com.lvpl.domain.member.Member;
import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.Task;
import com.lvpl.domain.task.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lists")
public class ListsApiController {
    private final ListsService listsService;
    private final MemberService memberService;
    private final TaskService taskService;

    /**
     * 일정 조회 1과 2는 프론트에서 사용할 일 없음. 프론트는 일정 조회 3과 4 사용
     */
    
    /**
     * 일정 조회(1) - 모든 Lists(=하루단위 일정모음) 조회
     */
    @GetMapping(value = "/all")
    public List<ListsDto> findAllWithTask() {
        List<Lists> lists = listsService.findAllWithTask();
        List<ListsDto> result = lists.stream()
                .map(o -> new ListsDto(o))
                .collect(Collectors.toList());
        return result;
    }
    /**
     * 일정 조회(2) - 특정 Lists(=하루단위 일정모음) 조회 - listsId 를 통해서
     */
    @GetMapping(value = "/{listsId}")
    public List<TaskDto> findOneWithTask(@PathVariable("listsId") Long listsId) {
        List<Lists> lists = listsService.findOneWithTask(listsId);
        if(lists.isEmpty()) return null;
        List<TaskDto> result = lists.get(0).getTasks().stream()
                .map(o -> new TaskDto(o))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 일정 조회(3) - 모든 Lists(=하루단위 일정모음) 조회 -> 해당 회원꺼만
     */
    @GetMapping(value = "/member/all")
    public List<ListsDto> findAllWithMemberTask(@Login Long memberId) {
        List<Lists> lists = listsService.findAllWithMemberTask(memberId);
        List<ListsDto> result =lists.stream()
                .map(o -> new ListsDto(o))
                .collect(Collectors.toList());
        return result;
    }
    /**
     * 일정 조회(4) - 날짜범위로 Lists(=하루단위 일정모음) 조회 -> 해당 회원꺼만
     * 하루, 한달, 1년 등등 원하는 날짜 범위만큼 사용 가능
     */
    @PostMapping(value = "/member/date")
    public List<ListsDto> findByDateWithMemberTask(@Login Long memberId, @RequestBody CreateListsRequestDto request) {
        List<Lists> lists = listsService.findByDateWithMemberTask(memberId, request.startTime, request.endTime);
        List<ListsDto> result =lists.stream()
                .map(o -> new ListsDto(o))
                .collect(Collectors.toList());
        return result;
    }


    /**
     * 일정 수정
     */

    /**
     * 일정 삭제
     */

    // DTO
    @Getter
    static class ListsDto {
        private Long listsId;
        private LocalDateTime listsDate; // 등록 날짜
        private List<TaskDto> listsTasks;
        public ListsDto(Lists lists) { // lazy 강제 초기화
            listsId = lists.getId();
            listsDate = lists.getListsDate();
            listsTasks = lists.getTasks().stream()
                    .map(o -> new TaskDto(o))
                    .collect(Collectors.toList());
        }
    }
    @Getter
    static class TaskDto {
        private Long taskId;
        private String content;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Boolean completedStatus;
        private Boolean timerOnOff;
        public TaskDto(Task task) { // lazy 강제 초기화
            taskId = task.getId();
            content = task.getContent();
            startTime = task.getStartTime();
            endTime = task.getEndTime();
            completedStatus = task.getTaskStatus().getCompletedStatus();
            timerOnOff = task.getTaskStatus().getTimerOnOff();
        }
    }

    @Getter
    static class CreateListsRequestDto {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
