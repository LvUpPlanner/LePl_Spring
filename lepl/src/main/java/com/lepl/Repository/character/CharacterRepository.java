package com.lepl.Repository.character;

import com.lepl.domain.character.Character;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor // 생성자 주입(엔티티매니저)
public class CharacterRepository {
    private final EntityManager em;

    /**
     * save, findOne, remove
     */
    public void save(Character character) {
        if(character.getId() == null) {
            em.persist(character);
        }
    }

    public Character findOne(Long id) {
        return em.find(Character.class, id);
    }

    public void remove(Character character) { em.remove(character);}
}
