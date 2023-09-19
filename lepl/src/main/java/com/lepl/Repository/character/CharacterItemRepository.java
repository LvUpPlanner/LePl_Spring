package com.lepl.Repository.character;

import com.lepl.domain.character.CharacterItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CharacterItemRepository {
    private final EntityManager em;

    /**
     * save, findOne, findAll, remove
     */
    public void save(CharacterItem characterItem) {
        if(characterItem.getId() == null) {
            em.persist(characterItem);
        }
    }

    public CharacterItem findOne(Long id) {
        return em.find(CharacterItem.class, id);
    }

    public List<CharacterItem> findAll() {
        return em.createQuery("select f from Friend f", CharacterItem.class)
                .getResultList();
    }

    public void remove(CharacterItem characterItem) { em.remove(characterItem);}
}
