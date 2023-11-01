package com.lepl.Repository.character;

import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import com.lepl.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest // Bean 사용 위해
@Slf4j
public class ExpRepositoryTest {
    @Autowired
    ExpRepository expRepository;
    @Autowired
    EntityManager em;
    static Long expId; // 전역

    /**
     * save, findOne, remove, findOneWithMember, initPointToday
     */
    @Test
    @Order(1)
    @Transactional
    @Rollback(value = false) // "삭제" 메소드에 활용하기 위해 롤백 취소
    public void 경험치_저장과조회() throws Exception {
        // given
        Exp exp = new Exp();

        // when
        expRepository.save(exp); // persist
        expId = exp.getId();
        Exp findExp = expRepository.findOne(expId);

        // then
        Assertions.assertEquals(exp, findExp);
        Assertions.assertEquals(exp.getId(), findExp.getId());
        log.info("exp id : {}",exp.getId());
    }

    @Test
    @Order(2)
    @Transactional // 롤백
    public void 멤버의_경험치_조회() throws Exception {
        // given
        Member member = Member.createMember("test1", "test1");
        Member member2 = Member.createMember("test2", "test2");
        Exp exp = new Exp();
        em.persist(exp); // id 위해
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        em.persist(character); // id 위해 -> FK 오류 피하기 위함
        member2.setCharacter(character);
        em.persist(member);
        em.persist(member2);
//        em.flush(); // findOneWithMember 는 DB 에서 검색하므로 강제 flush()

        // when
        Member findMember = expRepository.findOneWithMember(member.getId());
        Member findMember2 = expRepository.findOneWithMember(member2.getId());

        // then
        Assertions.assertEquals(findMember, null); // findMember 는 null 이여야 정상
        Assertions.assertEquals(findMember2, member2);
        Assertions.assertEquals(findMember2.getId(), member2.getId());
        log.info("findMember2.getId() : {} , member2.getId() : {}", findMember2.getId(), member2.getId());
    }

    @Test
    @Order(3)
    @Transactional
    public void 전체_경험치_일일제한_초기화() throws Exception {
        // given
        Exp findExp = expRepository.findOne(expId);
        findExp.updateExp(5L, 5L);
//        em.flush(); // dirty checking -> 변경내용 DB로 insert 쿼리
        log.info("findExp.getPointTodayTask() : {}, findExp.getPointTodayTimer() : {}", findExp.getPointTodayTask(), findExp.getPointTodayTimer());

        // when
        expRepository.initPointToday();
        em.clear(); // 캐시 제거 (flush 로는 안지워짐)
        findExp = expRepository.findOne(expId); // 다시 조회 select 쿼리

        // then
        Assertions.assertEquals(findExp.getPointTodayTask(), 0L);
        Assertions.assertEquals(findExp.getPointTodayTimer(), 0L);
        log.info("findExp.getPointTodayTask() : {}, findExp.getPointTodayTimer() : {}", findExp.getPointTodayTask(), findExp.getPointTodayTimer());
    }

    @Test
    @Order(4)
    @Transactional
    public void 경험치_제거() throws Exception {
        // given
        Exp findExp = expRepository.findOne(expId);

        // when
        expRepository.remove(findExp); // 사실 경험치 제거는 사용할 일이 없긴함
        findExp = expRepository.findOne(expId);

        // then
        Assertions.assertEquals(findExp, null);
    }
}
