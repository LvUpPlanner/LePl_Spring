package com.lepl.domain.member;

import com.lepl.domain.task.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberTest {

    @Test
    public void 생성_편의메서드() throws Exception {
        // given
        Member member = new Member();

        // when
        member = Member.createMember("test", "테스트1");

        // then
        Assertions.assertInstanceOf(Member.class, member);
    }

    @Test
    public void 연관관계_편의메서드() throws Exception {
        // given
        Member member = Member.createMember("test", "테스트2");
        log.info("{}",member.getLists().size());

        // when
        member.addLists(new Lists());
        log.info("{}",member.getLists().size()); // 2개가 기록되는 문제 발견
        for(Lists lists : member.getLists()) {
            log.info("{}", lists);
            log.info("{}", lists.getMember());
        }

        // then
        Assertions.assertEquals(member.getLists().size(), 1);
    }

}