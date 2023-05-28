package com.lvpl.Repository.task;

import com.lvpl.domain.member.Member;
import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.ListsTask;
import com.lvpl.domain.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ListsRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    ListsRepository listsRepository;
    // save, findOne, findByDate, findAll, remove

    @Test
    @Transactional
//    @Rollback(false)
    public void save_find_test() throws Exception {
        // given
        LocalDateTime testDate = LocalDateTime.of(2022, Month.APRIL, 23, 12, 30); // test 날짜
        LocalDateTime testDate2 = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0); // test 날짜 1월.1일.0시.0분
        LocalDateTime testDate3 = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0); // test 날짜 1월.1일.0시.0분

        Member member = new Member();
        member.setUid("123"); // uid not null 상태라서 테스트에서도 꼭 추가
        ListsTask listsTask = new ListsTask();

        Lists list1 = Lists.createLists(member, testDate, listsTask);
        Lists list2 = Lists.createLists(member, null, listsTask); // null 이면 현재 날짜 자동 등록

        // when
        em.persist(member);
        em.persist(listsTask); // 먼저 적용해줘야 함
        listsRepository.save(list1); // em.persist
        listsRepository.save(list2); // em.persist
        Lists findListsOne = listsRepository.findOne(list1.getId());
        List<Lists> findListsAll = listsRepository.findAll();
        List<Lists> findListsAll_date = listsRepository.findByDate(testDate2, testDate3);

        // then
        System.out.println(findListsOne.getListsDate()); // 2022-04-23T12:30
        System.out.println(findListsAll.get(findListsAll.size()-1).getListsDate()); // 2023-05-28T18:00:28.672150900

        System.out.println("findListsAll_date 개수 : "+findListsAll.size()); // findListsAll_date 개수 : 2
        for(int i = 0 ;i <findListsAll_date.size();i++){
            System.out.println(findListsAll_date.get(i).getListsDate()); // 2022-04-23T12:30, 2023-05-28T18:00:28.672150900
        }

    }

    @Test
    @Transactional
    @Rollback(false) // h2에서 확인하려면 자동 롤백 제거 필요
    public void deleteTest() throws Exception {
        // given
        Member member = new Member();
        member.setUid("1234");
        ListsTask listsTask = new ListsTask();
        Lists lists = Lists.createLists(member, null, listsTask);

        // when
        em.persist(member);
        em.persist(listsTask);
        listsRepository.save(lists); // em.persist
        Lists findLists = listsRepository.findOne(lists.getId());
        listsRepository.remove(findLists);

        // then
        System.out.println(listsRepository.findOne(findLists.getId())); // null
    }
}