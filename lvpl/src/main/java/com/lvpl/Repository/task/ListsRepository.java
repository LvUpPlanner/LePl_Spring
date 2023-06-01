package com.lvpl.Repository.task;

import com.lvpl.domain.task.Lists;
import com.lvpl.domain.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor // 생성자 주입 + 엔티티 매니저 주입
public class ListsRepository {
    private final EntityManager em;

    /**
     * save, findOne, findByDate, findAll, remove, findByToday
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
    // 현재 일정 조회
    public List<Lists> findByCurrent(LocalDateTime curDate) {
        return em.createQuery(
                "select l from Lists l where FORMATDATETIME(l.listsDate, 'yyyy-MM-dd') = :curDate", Lists.class)
                .setParameter("curDate", curDate.toLocalDate().toString())
                .getResultList();
    }
    // 모든날의 리스트(하루단위 일정들) 조회
    public List<Lists> findAll() {
        return em.createQuery("select l from Lists l", Lists.class)
                .getResultList();
    }

    // fetch join 사용 => 중복 제거하고 싶으면 distinct 활용(근데 여기선 중복이 없어서 미사용)
    // fetch join 은 lazy 로 인한 쿼리문 여러번 나가는 비효율적인 방법을 쿼리문 1번으로 종결시켜주는 중요한 방법
    public List<Lists> findAllWithTask() {
        return em.createQuery(
                "select l from Lists l" +
                        " join fetch l.tasks t" +
                        " join fetch t.taskStatus ts", Lists.class)
                .getResultList();
    }
    public void remove(Lists lists) {
        em.remove(lists);
    }
}
