package com.lepl.Repository.task;

import com.lepl.domain.task.Task;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// 현재 메모리에서 테스트하기 때문에 h2 DB에 적용을 보려면 main 함수에서!!
@SpringBootTest
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EntityManager em;

    @Test
    @Transactional // 자동 롤백
    @Rollback(false) // deleteTask() 테스트 위해 잠시 롤백 제거
    public void save_find_test() throws Exception {
        // given
        Task task = Task.createTask("테스트입니다.", LocalDateTime.now(), LocalDateTime.now(), TaskStatus.createTaskStatus(false,false));

        // when
        taskRepository.save(task1);
        taskRepository.save(task2);
//        em.flush(); // 강제 flush
        Task getTask = taskRepository.findOne(task1.getId());
        List<Task> list = taskRepository.findAll();

        // then
        Assertions.assertEquals(task.getId(), findTask.getId());
        log.info("taskList size : {}", taskList.size());
        log.info("taskID : {}", taskId);
        for (Task t : taskList) {
            log.info(t.getContent());
        }
    }

    @Test
    @Transactional
    public void 멤버의_일정조회() throws Exception {
        // given
        Task task = Task.createTask("멤버 테스트", LocalDateTime.now(), LocalDateTime.now(), TaskStatus.createTaskStatus(false,false));
        Member member = Member.createMember("UID", "닉네임");
        em.persist(member); // id 위해(FK 오류 방지)
        Lists lists = Lists.createLists(member, LocalDateTime.now(), new ArrayList<>());
        em.persist(task); // id
        lists.addTask(task);
        member.addLists(lists);
        em.persist(lists);
        log.info("전");
        em.flush(); // insert
        log.info("후");

        // when
        Task findTask = taskRepository.findOneWithMember(member.getId(), task.getId()); // flush

        // then
        Assertions.assertEquals(task, findTask); // em.clear() 없으므로 주소 동일
        Assertions.assertEquals(task.getId(), findTask.getId());
        Assertions.assertEquals(findTask.getContent(), "멤버 테스트");
    }

    @Test
    @Order(2)
    @Transactional // 자동 롤백
    @Rollback(false) // h2에서 확인하려면 자동 롤백 제거 필요
    public void deleteTest() throws Exception {
        // given
        Task getTask = null;
        
        // when
        List<Task> list = taskRepository.findAll(); // test1, test2
        if(!list.isEmpty()) getTask = list.get(0); // test1

        if(getTask != null) {
            System.out.println(getTask.getId()); // 1
            taskRepository.remove(getTask);
//            em.flush(); // 강제 flush
        }
        list = taskRepository.findAll(); // test2
        
        // then
        if(!list.isEmpty()) {
            for(Task t : list) System.out.println(t.getId()); // 2
        }
    }
}