package com.lvpl.api.member;


import com.lvpl.Service.member.MemberService;
import com.lvpl.api.argumentresolver.Login;
import com.lvpl.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 로그인
     * uid로 조회 후 세션Id 응답 쿠키
     */
    @PostMapping("/login") // 입력 => json 이용
    public String login(@RequestBody @Valid LoginMemberRequestDto LoginRequest, HttpServletRequest request){
        Member loginMember = memberService.findByUid(LoginRequest.getUid());
//        Member loginMember = memberService.findOne(Long.parseLong(uid));
        if(loginMember==null) { // 회원 아닌경우
            return "회원이 아닙니다."; // 에러 코드 날려주던지 등등
        }

        // 로그인 성공 처리  => 세션Id 응답 쿠키
        // 세션 있으면 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(); // UUID 형태로 알아서 생성 (기본값 : true)
        // 세션에 로그인 회원 정보 보관
        session.setAttribute("login_member", loginMember.getId());

        return "회원 인증 완료"; // 쿠키에 세션을 담아서 같이 전송하므로 클라는 인증서를 발급받은 효과
    }
    // test용 GET (웹에서 쿠키 확인)
    @GetMapping("/login/{uid}")
    public String loginTest(@PathVariable("uid") String uid, HttpServletRequest request) {
        Member loginMember = memberService.findByUid(uid);
        if (loginMember == null) { // 회원 아닌경우
            return "회원이 아닙니다."; // 에러 코드 날려주던지 등등
        }
        HttpSession session = request.getSession();
        session.setAttribute("login_member", loginMember.getId());
        return "회원 인증 완료"; // 쿠키에 세션을 담아서 같이 전송하므로 클라는 인증서를 발급받은 효과
    }

    /**
     * 회원가입
     * uid를 필수로 받아서 DB 기록 및 세션Id 메모리에 기록
     * 이후 세션Id를 응답 쿠키
     */
    @PostMapping("/register") // 입력 => json 이용
    public RegisterMemberResponseDto saveMember(@RequestBody @Valid RegisterMemberRequestDto request) {
        Member member = new Member();
        member.setUid(request.getUid());
        member.setNickname(request.getNickname());

        memberService.join(member);
        // 중복회원 처리는 나중에,, 하겠음,,

        return new RegisterMemberResponseDto(member);
    }

    /**
     * 로그아웃
     * 세션 정보 메모리에서 제거
     */
    @PostMapping("/logout") // 입력 => 쿠키의 세션정보 이용
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 없으면 null을 반환
        if (session != null) {
            session.invalidate(); // 세션 제거
        }
        return "로그아웃 성공";
    }
    // test용 GET (웹에서 쿠키 확인)
    @GetMapping("/logout/{uid}")
    public String logoutTest(@PathVariable("uid") String uid, HttpServletRequest request) {
        Member loginMember = memberService.findByUid(uid);
        if (loginMember == null) { // 회원 아닌경우
            return "회원이 아닙니다."; // 에러 코드 날려주던지 등등
        }// 그냥 회원체크 테스트
        HttpSession session = request.getSession(false); // 세션 없으면 null을 반환
        if (session != null) {
            session.invalidate(); // 세션 제거
        }
        return "로그아웃 성공";
    }
    // test용 GET (웹에서 쿠키 확인) => "인터셉터" 동작도 확인 => Uid 얻어내나 확인
    @GetMapping("/v1/testUid")
    public String testUidV1(HttpServletRequest request) {
        HttpSession session = request.getSession();
//        Member member = (Member) session.getAttribute("login_member");
        Long memberId = Long.valueOf(session.getAttribute("login_member").toString());
        Member member = memberService.findOne(memberId);
        return "테스트 uid : "+member.getUid();
    }
    // @Login Long 으로 바로 멤버 id 가져와서 멤버 객체 조회 되는지 테스트 => 이제부터 위 방법 말고 이 방법을 사용하면 된다.
    @GetMapping("/v2/testUid")
    public String testUidV2(@Login Long id) {
        Member member = memberService.findOne(id);
        return "테스트 uid : "+member.getUid();
    }

    @Getter
    static class RegisterMemberResponseDto {
        private Long id;
        private String uid;
        private String nickname;
        public RegisterMemberResponseDto(Member member) {
            this.id = member.getId();
            this.uid = member.getUid();
            this.nickname = member.getNickname();
        }
    }
    @Getter
    static class RegisterMemberRequestDto {
        @NotEmpty(message = "회원 정보는 필수입니다.") // 에러코드 날려줘도 됨 (Valid와 세트)
        private String uid;
        private String nickname;
    }
    @Getter
    static class LoginMemberRequestDto {
        @NotEmpty(message = "회원 정보는 필수입니다.")
        private String uid;
    }
}