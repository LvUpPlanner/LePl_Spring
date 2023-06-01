package com.lvpl.Repository.task;

import com.lvpl.domain.member.Member;
import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListsRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    ListsRepository listsRepository;
    // save, findOne, findByDate, findAll, remove, findByToday

    @Test
    @Transactional
//    @Rollback(false)
    public void save_find_test() throws Exception {
        // given
        LocalDateTime testDate = LocalDateTime.of(2022, Month.APRIL, 23, 12, 30); // test 날짜
        LocalDateTime testDate2 = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0); // test 날짜 2022년.1월.1일.0시.0분
        LocalDateTime testDate3 = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0); // test 날짜 2024년.1월.1일.0시.0분
        LocalDateTime testDate4 = LocalDateTime.of(2023, Month.JUNE, 1, 12, 0); // test 날짜 2023년.6월.1일.12시.0분

        Member member = new Member();
        member.setUid("123"); // uid not null 상태라서 Member를 persist 할거라면, 테스트에서도 꼭 추가
        List<Task> tasks = new ArrayList<>();
        Task t1 = new Task();
        Task t2 = new Task();
        tasks.add(t1);
        tasks.add(t2);
        Lists list1 = Lists.createLists(member, testDate, tasks);
        Lists list2 = Lists.createLists(member, null, tasks); // null 이면 현재 날짜 자동 등록
        Lists list3 = Lists.createLists(member, testDate4, tasks); // findByToday 확인용

        // when
        em.persist(member);
        listsRepository.save(list1); // em.persist
        listsRepository.save(list2); // em.persist
        listsRepository.save(list3); // em.persist
        Lists findListsOne = listsRepository.findOne(list1.getId());
        List<Lists> findListsAll = listsRepository.findAll();
        List<Lists> findListsAll_date = listsRepository.findByDate(testDate2, testDate3);
        List<Lists> findListsToday = listsRepository.findByCurrent(testDate4);

        // then
        System.out.println(findListsOne.getListsDate()); // 2022-04-23T12:30
        System.out.println(findListsAll.get(findListsAll.size()-1).getListsDate()); // 2023-05-28T18:00:28.672150900
        System.out.println(findListsAll.size()); // 2

        System.out.println("findListsAll_date 개수 : "+findListsAll.size()); // findListsAll_date 개수 : 2
        for(int i = 0 ;i <findListsAll_date.size();i++){
            System.out.println(findListsAll_date.get(i).getListsDate()); // 2022-04-23T12:30, 2023-05-28T18:00:28.672150900
        }

        System.out.println(findListsToday.size());
        System.out.println(findListsToday.get(0).getMember().getUid());
    }

    @Test
    @Transactional
    @Rollback(false) // h2에서 확인하려면 자동 롤백 제거 필요
    public void deleteTest() throws Exception {
        // given
        Member member = new Member();
        member.setUid("1234");
        List<Task> tasks = new ArrayList<>();
        Task t1 = new Task();
        Task t2 = new Task();
        tasks.add(t1);
        tasks.add(t2);
        Lists lists = Lists.createLists(member, null, tasks);

        // when
        em.persist(member);
        listsRepository.save(lists); // em.persist
        Lists findLists = listsRepository.findOne(lists.getId());
        listsRepository.remove(findLists);

        // then
        System.out.println(listsRepository.findOne(findLists.getId())); // null

    }
}