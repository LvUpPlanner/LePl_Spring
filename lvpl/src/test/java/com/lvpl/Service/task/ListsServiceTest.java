package com.lvpl.Service.task;

import com.lvpl.domain.member.Member;
import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.ListsTask;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@Rollback(false) // db 확인위함
public class ListsServiceTest {
    @Autowired
    ListsService listsService;
    @Autowired
    EntityManager em;

    /**
     * save, findOne, findByDate, findAll, remove
     */

    @Test
    public void save_find() throws Exception {
        // given
        LocalDateTime testDate = LocalDateTime.of(2022, Month.APRIL, 23, 12, 30); // test 날짜
        LocalDateTime testDate2 = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0); // test 날짜 1월.1일.0시.0분
        LocalDateTime testDate3 = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0); // test 날짜 1월.1일.0시.0분

        Member member = Member.createMember("12345",null);
        ListsTask listsTask = new ListsTask();
        Lists lists1 = Lists.createLists(member, testDate, listsTask);
        Lists lists2 = Lists.createLists(member, null, listsTask); // 오늘 날짜 등록

        // when
        em.persist(member); // 먼저 persist 필요
        em.persist(listsTask); 
        listsService.join(lists1);
        listsService.join(lists2);
        Lists findLists = listsService.findOne(lists1.getId());
        List<Lists> findLists_date = listsService.findByDate(testDate2, testDate3);

        // then
        Assertions.assertEquals(lists1, findLists);
        for(Lists getLists : findLists_date){
            System.out.println(getLists.getListsDate()); // 2023-05-28T19:12:47.619809700, 2022-04-23T12:30
        }
    }

    @Test
    public void delete() throws Exception {
        // given
        Lists lists = new Lists();
        listsService.join(lists);

        // when
        Lists findLists1 = listsService.findOne(lists.getId());
        listsService.remove(findLists1);
        Lists findLists2 = listsService.findOne(lists.getId());

        // then
        Assertions.assertEquals(findLists1, findLists2); // exp:findLists1, act:null
    }
}