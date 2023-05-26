package com.lvpl.Repository.task;

import com.lvpl.domain.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // 생성자 주입 + 엔티티매니저 주입
public class TaskRepository {
    private final EntityManager em;

    /**
     * save, findOne, findAll, remove
     */
    public void save(Task task) {
        if(task.getId() == null){ // null 인 경우 db에 없다는 의미(db에 insert 할 때 id 생성)
            em.persist(task); // merge 사용 x => 더티 체킹
        }
    }

    public Task findOne(Long id) {
        return em.find(Task.class, id);
    }

    public List<Task> findAll() {
        return em.createQuery("select t from Task t", Task.class) // 올바르게 매핑해서 조회하기 위해서는 Task.class 가 필요
                .getResultList();
    }

    public void remove(Long id) {
        em.createQuery("delete from Task t where t.id = :id") // delete 의 경우 Task.class 가 필요없음
                .setParameter("id", id)
                .executeUpdate(); // select 를 제외하고는 해당 함수 필요
    }

}
