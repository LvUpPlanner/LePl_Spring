package com.lvpl.api;


import com.lvpl.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 로그인
     * uid로 조회 후 세션Id 응답 쿠키
     */



    /**
     * 회원가입
     * uid를 필수로 받아서 DB 기록 및 세션Id 메모리에 기록
     * 이후 세션Id를 응답 쿠키
     */

    

    /**
     * 로그아웃
     * 세션 정보 메모리에서 제거
     */

}
