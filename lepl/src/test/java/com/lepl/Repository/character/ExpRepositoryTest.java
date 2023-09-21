package com.lepl.Repository.character;

import com.lepl.domain.character.Exp;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class ExpRepositoryTest {
    @Autowired
    ExpRepository expRepository;
    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void updatePoint() throws Exception {
        // given
        Exp exp = new Exp();
        exp.setPointTodayTask(5L);
        exp.setPointTodayTimer(5L);
        expRepository.save(exp);
        exp = new Exp();
        exp.setPointTodayTask(5L);
        exp.setPointTodayTimer(5L);
        expRepository.save(exp);

        // when
        expRepository.updatePoint();
        exp = expRepository.findOne(exp.getId());

        // then -> DB 확인
//        Assertions.assertEquals(exp.getPointTodayTask(), 0L);
//        Assertions.assertEquals(exp.getPointTodayTimer(), 0L);
    }
}
