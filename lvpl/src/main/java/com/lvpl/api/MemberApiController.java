package com.lvpl.api;


import com.lvpl.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원가입
     * uid를 필수로 받아서 회원가입을 한다.
     */

    /**
     * 로그인(조회)
     * uid로 조회를 한다.
     */

}
