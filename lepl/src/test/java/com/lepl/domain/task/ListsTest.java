package com.lepl.domain.task;

import com.lepl.domain.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class ListsTest {
    static Member member; // 전역

    @BeforeEach
    public void 멤버_추가() {
        member = Member.createMember("12", "Lists테스트");
    }

    /**
     * setMember, addTask
     */
    @Test
    @Transactional
    public void 연관관계_편의함수_테스트() throws Exception {
        // given
        Lists lists = Lists.createLists(member, null, new ArrayList<>());

        // when
        lists.setMember(member);
        lists.addTask(t1); // 연관관계 편의 메서드
        lists.addTask(t2); // 연관관계 편의 메서드
        Long dbNotId = lists.getId(); // long이 아닌 Long덕분에 null타입 가질 수 있음
        em.persist(lists); // db insert log 보려고.
        Lists getLists = em.find(Lists.class, lists.getId());
        Long dbYesId = getLists.getId(); // db 적용후 생성된 id값 확인용

        // then
        System.out.println(member.getLists().get(0).getListsDate()); // 2022-04-23T12:30
        Assertions.assertEquals(lists.getId(), getLists.getId()); // exp:1, act:1
        Assertions.assertEquals(lists.getTasks().get(0).getId(),t1.getId()); // exp:2, act:2
        Assertions.assertEquals(lists.getTasks().get(1).getId(),t2.getId()); // exp:3, act:3
        Assertions.assertEquals(dbNotId, dbYesId); // exp:null, act:1 => 에러발생
    }

    /**
     * createLists
     */
    @Test
    @Transactional
    public void 생성메서드_테스트() throws Exception {
        // given
        LocalDateTime testDate = LocalDateTime.of(2022, Month.APRIL, 23, 12, 30); // test 날짜

        // when
        lists = Lists.createLists(member, null, new ArrayList<>());

        // then
        Assertions.assertInstanceOf(Lists.class, lists);
        Assertions.assertEquals(LocalDateTime.now().toLocalDate(), lists.getListsDate().toLocalDate()); // 금일날짜 자동적용
    }

    @Test
    public void 비지니스_편의메서드() throws Exception {
        // given
        Lists lists;
        List<Task> tasks = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime date2 = LocalDateTime.of(2023, 11, 4, 1, 0);
        Task task = Task.createTask("임시 테스트", date, date, new TaskStatus());
        Task task2 = Task.createTask("임시 테스트", date2, date2, new TaskStatus());
        tasks.add(task);
        tasks.add(task2);

        // when
        lists = Lists.createLists(member, date, tasks); // 내부에 compareDate 테스트
        Lists lists2 = Lists.createLists(member, date2, tasks); // 내부에 compareDate 테스트

        // then
        List<Task> findTasks = lists.getTasks(); // compareDate 에 의해 lists<->task 가 동일한 날짜만 기록되어야 정상
        List<Task> findTasks2 = lists2.getTasks();
        for (Task t : findTasks) {
            Assertions.assertEquals(lists.getListsDate(), t.getStartTime());
            Assertions.assertNotEquals(lists.getListsDate(), task2.getStartTime()); // lists<->task2 날짜는 달라야 정상
        }
        for (Task t2 : findTasks2) {
            Assertions.assertEquals(lists2.getListsDate(), t2.getStartTime());
            Assertions.assertNotEquals(lists2.getListsDate(), task.getStartTime()); // lists2<->task 날짜는 달라야 정상
        }
    }
}