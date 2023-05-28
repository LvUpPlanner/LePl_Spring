package com.lvpl.Repository.task;

import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor // 생성자 주입 + 엔티티 매니저 주입
public class ListsRepository {
    private final EntityManager em;

    /**
     * save, findOne, findByDate, findAll, remove
     */
    public void save(Lists lists) {
        em.persist(lists);
    }
    public Lists findOne(Long id) {
        return em.find(Lists.class, id);
    }
    // 날짜 범위로 리스트(하루단위 일정들) 조회
    public List<Lists> findByDate(LocalDateTime start, LocalDateTime end) {
        return em.createQuery(
                "select l from Lists l where l.listsDate >= :start and " +
                        "l.listsDate <= :end", Lists.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
    // 모든날의 리스트(하루단위 일정들) 조회
    public List<Lists> findAll() {
        return em.createQuery("select l from Lists l", Lists.class)
                .getResultList();
    }
    public void remove(Lists lists) {
        em.remove(lists);
    }
}
