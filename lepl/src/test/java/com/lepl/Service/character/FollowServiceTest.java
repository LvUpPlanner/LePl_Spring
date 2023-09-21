package com.lepl.Service.character;

import com.lepl.domain.character.*;
import com.lepl.domain.character.Character;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
@Slf4j
class FollowServiceTest {
    @Autowired
    CharacterService characterService;
    @Autowired
    FollowService followService;
    @Autowired
    ExpService expService;

    @Test
    @Rollback(value = false)
    public void join() throws Exception {
        // given
        List<CharacterItem> characterItems = new ArrayList<>();
        List<Follow> follows = new ArrayList<>();
        Exp exp = new Exp();
        List<Notification> notifications = new ArrayList<>();
        Character character = Character.createCharacter(exp, characterItems, follows, notifications);

        // when
        Long id = characterService.join(character);
        log.debug("id : {}", id);
        for(long i=0; i<2; i++) {
            Follow follow = new Follow();
            follow.setFollowingId(id);
            follow.setFollowerId(i+2);

            character.addFriend(follow);
            followService.join(follow);
        }
        expService.join(exp);

        // then
        log.debug("character.getId() : {}",character.getId());
        log.debug("character.getId() : {}",character.getFollows().get(0).getFollowingId());
        log.debug("character.getId() : {}",character.getFollows().get(0).getFollowerId());
        log.debug("character.getId() : {}",character.getFollows().get(1).getFollowingId());
        log.debug("character.getId() : {}",character.getFollows().get(1).getFollowerId());
    }
}