package com.lvpl.api.task;

import com.lvpl.Service.member.MemberService;
import com.lvpl.Service.task.ListsService;
import com.lvpl.api.argumentresolver.Login;
import com.lvpl.domain.member.Member;
import com.lvpl.domain.task.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lists")
public class ListsApiController {
    private final ListsService listsService;
    private final MemberService memberService;

    /**
     * 일정 저장
     */
//    @PostMapping("/new")
//    public String create(@Login Long id, @RequestBody CreateListsRequestDto request) {
//        Member member = memberService.findOne(id);
//        Lists lists = Lists.createLists(member, request.listsDate, tasks);
//
//        listsService.join(lists);
//
//        return "일정 등록 완료";
//    }


    /**
     * 일정 조회
     */




    /**
     * 일정 수정
     */

    /**
     * 일정 삭제
     */

    // DTO
//    @Getter
//    static class CreateListsRequestDto {
//        private LocalDateTime listsDate; // 등록 날짜
//
//    }
}
