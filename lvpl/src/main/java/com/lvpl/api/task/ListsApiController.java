package com.lvpl.api.task;

import com.lvpl.Service.member.MemberService;
import com.lvpl.Service.task.ListsService;
import com.lvpl.api.argumentresolver.Login;
import com.lvpl.domain.member.Member;
import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.Task;
import com.lvpl.domain.task.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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


    /**
     * 일정 조회 - 모든 Lists(하루단위 일정모음) 조회
     */
    @GetMapping(value = "")
    public List<ListsDto> findAll() {
        List<Lists> lists = listsService.findAllWithTask();
        List<ListsDto> result =lists.stream()
                .map(o -> new ListsDto(o))
                .collect(Collectors.toList());
        return result;
    }
    /**
     * 일정 조회 - 모든 Lists(하루단위 일정모음) 조회 -> 해당 회원꺼만
     */
    /**
     * 일정 조회 - 날짜범위로 Lists(하루단위 일정모음) 조회 -> 해당 회원꺼만
     */
    /**
     * 일정 조회 - 특정 Lists(하루단위 일정모음) 조회 - listsId 를 통해서
     */



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
        private List<ListsTaskDto> listsTasks;
        public ListsDto(Lists lists) { // lazy 강제 초기화
            listsId = lists.getId();
            listsDate = lists.getListsDate();
            listsTasks = lists.getTasks().stream()
                    .map(o -> new ListsTaskDto(o))
                    .collect(Collectors.toList());
        }
    }
    @Getter
    static class ListsTaskDto {
        private Long taskId;
        private String content;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Boolean completedStatus;
        private Boolean timerOnOff;
        public ListsTaskDto(Task task) { // lazy 강제 초기화
            taskId = task.getId();
            content = task.getContent();
            startTime = task.getStartTime();
            endTime = task.getEndTime();
            completedStatus = task.getTaskStatus().getCompletedStatus();
            timerOnOff = task.getTaskStatus().getTimerOnOff();
        }
    }
}
