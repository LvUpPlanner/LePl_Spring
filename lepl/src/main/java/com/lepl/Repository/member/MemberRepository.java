package com.lepl.Repository.member;

import com.lepl.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // 생성자 주입 + 엔티티매니저 주입 제공
public class MemberRepository {
    private final EntityManager em;

    /**
     * save, findOne, findByUid, findAllWithPage
     */

    public void save(Member member) {
        if(member.getId() == null) {
            em.persist(member); // db 저장
        }
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByUid(String uid) {
        List<Member> findMembers = em.createQuery("select m from Member m where m.uid = :uid", Member.class)
                .setParameter("uid", uid)
                .getResultList(); // List로 반환 받아야 null처리가 쉬움
        return findMembers.isEmpty() ? null : findMembers.get(0);
    }

    public List<Member> findAllWithPage(int pageId) {
        return em.createQuery("select m from Member m " +
                        "join fetch m.character c " +
                        "join fetch c.exp e " +
                        "order by m.id desc", Member.class)
                .setFirstResult((pageId - 1) * 10) // 페이징
                .setMaxResults(10) // 개수임!!
                .getResultList();
    }
}
