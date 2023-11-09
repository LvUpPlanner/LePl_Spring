package com.lepl.Repository.member;


import com.lepl.domain.member.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

// 현재 메모리에서 테스트하기 때문에 h2 DB에 적용을 보려면 main 함수에서!!
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Order(1)
    @Transactional // 롤백
    @Rollback(value = false)
    public void 회원가입_조회() throws Exception {
        // given
        Member member = Member.createMember("test", "test1");
        // 혹시모를 FK 에러 방지
        Exp exp = Exp.createExp(0L,0L,1L);
        em.persist(exp); // FK id 위해
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        em.persist(character); // FK id 위해
        member.setCharacter(character);

        // when
        memberRepository.save(member); // persist
        log.info("{}", member.getId());
//        em.flush(); // 롤백 true 때문에 insert 쿼리 생략시 flush 추가로 볼 수 있음
//        em.clear(); // flush 사용시 이것까지 해줘야 아래 select 문 전송 쿼리도 볼 수 있음
        Member findMember = memberRepository.findOne(member.getId());
        log.info("{}", member.getId());

        // then
        Assertions.assertEquals(member.getId(), findMember.getId());
        // 단, 위에서 em.clear를 한 경우 영속성이(캐시) 비어있으므로 findMember가 새로운 주소!!
        // 따라서 아래 출력으로 틀리다는 결론이 나온다.
        Assertions.assertEquals(member, findMember);
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUid("123");
        member.setNickname("test1");
        // when
        memberRepository.save(member);
//        em.flush(); // 강제 flush

        Member findMember1 = memberRepository.findOne(member.getId());
        Member findMember2 = memberRepository.findByUid("123");
//        Member findMember2 = memberRepository.findByUid("1234");
        System.out.println(findMember2);

        // then
        Assertions.assertEquals(member, findMember1);
        Assertions.assertEquals(member, findMember2);
        System.out.println(member.getUid());
        System.out.println(findMember1.getUid());
        System.out.println(findMember2.getId());
    }
}