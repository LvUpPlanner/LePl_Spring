package com.lepl.Repository.character;

import com.lepl.domain.character.Exp;
import com.lepl.domain.character.Friend;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendRepository {
    private final EntityManager em;

    /**
     * save, findOne, findAll, remove
     */
    public void save(Friend friend) {
        if(friend.getId() == null) {
            em.persist(friend);
        }
    }

    public Friend findOne(Long id) {
        return em.find(Friend.class, id);
    }

    public List<Friend> findAll() {
        return em.createQuery("select f from Friend f", Friend.class)
                .getResultList();
    }

    public void remove(Friend friend) { em.remove(friend);}
}
