package com.lepl.Repository.character;

import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import com.lepl.domain.member.Member;
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
    public Member findOneWithMember(Long memberId) {
            return em.createQuery(
                "select distinct m from Member m" +
                        " join fetch m.character c" +
                        " join fetch c.exp e" +
                        " where m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    public void remove(Exp exp) { em.remove(exp);}

    public void updatePoint() {
        em.createQuery(
            "update Exp e set e.pointTodayTimer=0, e.pointTodayTask=0")
                .executeUpdate();
    }
}
