package com.lepl.Service.member;

import com.lepl.api.member.dto.FindMemberResponseDto;
import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import com.lepl.domain.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional // 서비스 부분은 대부분 트랜잭션 사용
//@Rollback(false)
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = Member.createMember(UID, "테스트 닉네임");
        Exp exp = Exp.createExp(0L,0L,1L);
        em.persist(exp);
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        em.persist(character);
        member.setCharacter(character);

        // when
        Long saveId = memberService.join(member);

        // then
        // DB에 저장된 member를 찾으려고 레퍼지토리의 함수 사용
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    @Order(2)
    public void 중복검증_예외() throws Exception {
        // given
        Member member = memberService.findOne(memberId);

        // when
        // then
        Throwable exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member); // 예외발생 로직
        });
        Assertions.assertEquals(MESSAGE, exception.getMessage());
        log.info("exception.getMessage() : {}", exception.getMessage());
    }

    @Test
    @Order(3)
    public void 회원_페이징_캐시_조회() throws Exception {
        // given
        // when
        List<FindMemberResponseDto> dto = memberService.findAllWithPage(1);
        log.info("캐시되었으면 쿼리 안날라감1");
        memberService.findAllWithPage(1);
        log.info("캐시되었으면 쿼리 안날라감2");
        memberService.findAllWithPage(1);
        log.info("캐시되었으면 쿼리 안날라감3");
        memberService.initCacheMembers(); // 캐시 초기화
        log.info("캐시 초기화 했으므로 쿼리 날라가야 함");
        memberService.findAllWithPage(1);

        // then
        for (FindMemberResponseDto m : dto) {
            log.info("member.id : {}", m.getId());
            log.info("member.nickName : {}", m.getNickname());
        }
    }
}