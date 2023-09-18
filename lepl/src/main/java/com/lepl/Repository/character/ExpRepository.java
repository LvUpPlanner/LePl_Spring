package com.lepl.Repository.character;

import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpRepository {
    private final EntityManager em;

    /**
     * save, findOne, remove
     */
    public void save(Exp exp) {
        if(exp.getId() == null) {
            em.persist(exp);
        }
    }

    public Exp findOne(Long id) {
        return em.find(Exp.class, id);
    }

    public void remove(Exp exp) { em.remove(exp);}
}
