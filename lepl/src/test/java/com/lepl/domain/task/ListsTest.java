package com.lepl.domain.task;

import com.lepl.domain.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class ListsTest {


    @Test
    public void 연관관계_편의메서드() throws Exception {
        // given
        Lists lists = Lists.createLists(new Member(), null, new ArrayList<>());

        // when
        lists.addTask(new Task());

        // then
        Assertions.assertEquals(lists.getTasks().size(), 1);
    }

    @Test
    public void 생성_편의메서드() throws Exception {
        // given
        Lists lists;

        // when
        lists = Lists.createLists(new Member(), null, new ArrayList<>());

        // then
        Assertions.assertInstanceOf(Lists.class, lists);
        Assertions.assertEquals(LocalDateTime.now().toLocalDate(), lists.getListsDate().toLocalDate()); // 금일날짜 자동적용
    }

}