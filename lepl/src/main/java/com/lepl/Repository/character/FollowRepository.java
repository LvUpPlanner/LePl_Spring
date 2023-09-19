package com.lepl.Repository.character;

import com.lepl.domain.character.Follow;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {
    private final EntityManager em;

    /**
     * save, findOne, findAll, remove
     */
    public void save(Follow follow) {
        if(follow.getId() == null) {
            em.persist(follow);
        }
    }

    public Follow findOne(Long id) {
        return em.find(Follow.class, id);
    }

    public List<Follow> findAll() {
        return em.createQuery("select f from Follow f", Follow.class)
                .getResultList();
    }

    public void remove(Follow follow) { em.remove(follow);}
}
