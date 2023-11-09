package com.lepl.Service.task;

import com.lepl.domain.member.Member;
import com.lepl.domain.task.Lists;
import com.lepl.domain.task.Task;
import org.junit.jupiter.api.Assertions;
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
@Transactional
//@Rollback(false) // db 확인위함
public class ListsServiceTest {
    @Autowired
    ListsService listsService;
    @Autowired
    EntityManager em;
    static List<Task> tasks; // 전역
    static Long memberId;
    static Long listsId;

    @BeforeEach // @Test 마다 수행 전 호출
    public void beforeEach() {
        tasks = new ArrayList<>(); // init
        // 2023-11-1~3 일정 임시로 추가 -> 각 task 는 2개씩
        for (int i = 1; i <= 3; i++) {
            LocalDateTime date_s = LocalDateTime.of(2023, 11, i, 1, 0); // 1시
            LocalDateTime date_e = LocalDateTime.of(2023, 11, i, 3, 0); // 3시
            LocalDateTime date2_s = LocalDateTime.of(2023, 11, i, 5, 0); // 5시
            LocalDateTime date2_e = LocalDateTime.of(2023, 11, i, 8, 0); // 8시
            TaskStatus taskStatus = TaskStatus.createTaskStatus(false,false);
            Task task = Task.createTask("조회 테스트입니다.", date_s, date_e, taskStatus);
            Task task2 = Task.createTask("조회 테스트입니다.2", date2_s, date2_e, taskStatus);
            tasks.add(task);
            tasks.add(task2);
        }
    }

    /**
     * save, findOne, findByDate, findAll, remove, findByToday
     */

    @Test
    public void save_find() throws Exception {
        // given
        LocalDateTime testDate = LocalDateTime.of(2022, Month.APRIL, 23, 12, 30); // test 날짜
        LocalDateTime testDate2 = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0); // test 날짜 1월.1일.0시.0분
        LocalDateTime testDate3 = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0); // test 날짜 1월.1일.0시.0분
        LocalDateTime testDate4 = LocalDateTime.of(2023, Month.JUNE, 1, 12, 0); // test 날짜 2023년.6월.1일.12시.0분

        Member member = Member.createMember("12345",null);
        List<Task> tasks = new ArrayList<>();
        Task t1 = new Task();
        Task t2 = new Task();
        tasks.add(t1);
        tasks.add(t2);
        Lists lists1 = Lists.createLists(member, testDate, tasks);
        Lists lists2 = Lists.createLists(member, null, tasks); // 오늘 날짜 등록
        Lists list3 = Lists.createLists(member, testDate4, tasks); // findByToday 확인용

        // when
        List<Lists> listsList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            LocalDateTime listsDate = LocalDateTime.of(2023, 11, i, 1, 0);
            Lists lists = Lists.createLists(member, listsDate, tasks);
            listsService.join(lists);
            listsList.add(lists);
        }
        listsId = listsList.get(0).getId();
        for (Task t : tasks) {
//            em.persist(t); // persist
        }

        // then
        Assertions.assertEquals(lists1, findLists);
        for(Lists getLists : findLists_date){
            System.out.println(getLists.getListsDate()); // 2023-05-28T19:12:47.619809700, 2022-04-23T12:30
        }
        System.out.println(findListsToday.size());
        System.out.println(findListsToday.get(0).getMember().getUid());
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
        for (Lists l : findLists) {
            log.info("findLists : {}", l.getId());
        }
        for (Lists l : findLists2) {
            log.info("findLists2 : {}", l.getListsDate());
        }
    }

    @Test
    @Order(3)
    @Rollback(value = false) // db 적용 확인위해
    public void 멤버의_일정_삭제() throws Exception {
        // given
        Lists findLists = listsService.findOneWithMemberTask(memberId, listsId);

        // when
        listsService.remove(findLists);
        Lists lists3 = listsService.findOne(3L);
        log.info("delete 쿼리문 날라가는 시점 체크1");
        findLists = listsService.findOneWithMemberTask(memberId, listsId);
        log.info("delete 쿼리문 날라가는 시점 체크2");

        // then
        Assertions.assertEquals(findLists, null);
    }
}