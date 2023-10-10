package com.lepl.Repository.character;

import com.lepl.domain.character.Exp;
import com.lepl.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpRepository {

    private final EntityManager em;

    /*
        save, findOne, update, remove, findOneWithMember
     */

    //경험치 쌓기
    public void save(Exp exp) {
        if(exp.getId() == null) {
            em.persist(exp);
        }
    }

    //경험치 조회
    public Exp findOne(Long id) {
        return em.find(Exp.class, id);
    }

    //경험치 삭제 -> 캐릭터 삭제의 경우
    public void remove(Exp exp) {
        em.remove(exp);
    }

    //매일 경험치 리셋
    public void updatePoint() {
        em.createQuery("update Exp e set e.pointTodayTask=0, e.pointTodayTimer=0")
                .executeUpdate();
    }

    public Member findOneWithMember(Long memberId) {
        return em.createQuery("select distinct m from Member m" +
                " join fetch m.character c" +
                " join fetch c.exp e" +
                " where m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
