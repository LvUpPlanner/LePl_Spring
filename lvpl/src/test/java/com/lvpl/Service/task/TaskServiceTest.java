package com.lvpl.Service.task;

import com.lvpl.Repository.task.TaskRepository;
import com.lvpl.Service.member.MemberService;
import com.lvpl.domain.task.Task;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 서비스 부분은 대부분 트랜잭션 사용
@Rollback(false) // 일정 삭제 테스트 위햠
public class TaskServiceTest {
    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EntityManager em;

    @Test
    public void taskAdd_taskFind() throws Exception {
        // given
        Task task1 = new Task();
        task1.setContent("test1");
        Task task2 = new Task();
        task2.setContent("test2");

        // when
        taskService.join(task1);
        taskService.join(task2);
        List<Task> list = taskService.findTasks();

        // then
        Assertions.assertEquals(task1, taskRepository.findOne(task1.getId())); // 여기서 레퍼지토리 쓰려고 선언한것
        if(!list.isEmpty()) for(Task t : list) System.out.println(t.getContent());
    }

    @Test
    public void 일정삭제() throws Exception {
        // given
        Task getTask = null;
        List<Task> list_ori = null;
        List<Task> list_nxt = null;

        // when
        list_ori = taskService.findTasks(); // test1, test2
        getTask = taskService.findOne(1l); // test1
        taskService.remove(getTask.getId());
        list_nxt = taskService.findTasks(); // test2

        // then
        for(Task t : list_ori) System.out.println(t.getContent()); // test1, test2
        for(Task t : list_nxt) System.out.println(t.getContent()); // test2
    }
}