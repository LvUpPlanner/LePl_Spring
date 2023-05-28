package com.lvpl.domain.task;

import com.lvpl.domain.member.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListsTest {
    @Autowired
    EntityManager em;

    /**
     * setMember, addListsTask
     */
    @Test
    @Transactional
    public void 연관관계_편의함수_테스트() throws Exception {
        // given
        LocalDateTime testDate = LocalDateTime.of(2022, Month.APRIL, 23, 12, 30); // test 날짜

        Member member = new Member();
        ListsTask listsTask = new ListsTask();
//        listsTask.setCount(5);
        Lists lists = new Lists();
        lists.setListsDate(testDate);

        // when
        lists.setMember(member);
        lists.addListsTask(listsTask);
        Long dbNotId = lists.getId(); // long이 아닌 Long덕분에 null타입 가질 수 있음
        em.persist(lists); // db기록 테스트
        Lists getLists = em.find(Lists.class, lists.getId());
        Long dbYesId = getLists.getId();

        // then
        System.out.println(member.getLists().get(0).getListsDate()); // 2023
//        System.out.println(lists.getListsTasks().get(0).getCount()); // 5
        Assertions.assertEquals(lists.getId(), getLists.getId()); // exp:1, act:1
        Assertions.assertEquals(dbNotId, dbYesId); // exp:null, act:1
    }

    /**
     * createLists
     */
    @Test
    @Transactional
    public void 생성메서드_테스트() throws Exception {
        // given
        LocalDateTime testDate = LocalDateTime.of(2022, Month.APRIL, 23, 12, 30); // test 날짜

        Lists lists;
        Member member = new Member();
        ListsTask[] listsTasks = new ListsTask[5];
        for(int i = 0 ; i<5;i++) listsTasks[i] = new ListsTask(); // init

        // when
        lists = Lists.createLists(member, testDate, listsTasks);
        em.persist(lists);

        // then
        System.out.println(lists.getListsDate()); // 2023
        System.out.println(lists.getListsTasks().get(0).getId()); // null
        System.out.println(lists.getListsTasks().get(1).getId()); // null
    }

    /**
     * getTaskCount
     */
    @Test
    public void 조회로직_테스트() throws Exception {
        // given
        Lists lists = new Lists();
        List<ListsTask> listsTasks = new ArrayList<>();
        listsTasks.add(new ListsTask());
        listsTasks.add(new ListsTask());
        listsTasks.add(new ListsTask()); // 3개 기록
        lists.setListsTasks(listsTasks);

        // when
        Integer totalTaskCount = lists.getTaskCount();

        // then
        System.out.println(totalTaskCount);
    }
}