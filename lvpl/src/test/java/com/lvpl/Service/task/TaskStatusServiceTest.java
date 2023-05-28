package com.lvpl.Service.task;

import com.lvpl.domain.task.TaskStatus;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaskStatusServiceTest {
    @Autowired
    TaskStatusService taskStatusService;

    /**
     * save, findOne
     */

    @Test
    public void save_find() throws Exception {
        // given
        TaskStatus taskStatus = TaskStatus.createTaskStatus(false, false);

        // when
        taskStatusService.join(taskStatus);
        TaskStatus findTaskStatus = taskStatusService.findOne(taskStatus.getId());

        // then
        Assertions.assertEquals(taskStatus, findTaskStatus);
    }
}