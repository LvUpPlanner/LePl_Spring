package com.lepl.Service.character;

import com.lepl.domain.character.Character;
import com.lepl.domain.character.CharacterItem;
import com.lepl.domain.character.Exp;
import com.lepl.domain.character.Follow;
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
class CharacterServiceTest {
    @Autowired
    CharacterService characterService;
    @Autowired
    CharacterItemService characterItemService;
    @Autowired
    ExpService expService;
    @Autowired
    FollowService followService;

    /**
     * 캐릭터, 경험치 한번에 테스트
     */
    
    @Test
    @Rollback(value = false)
    public void join() throws Exception {
        // given
        Exp exp = new Exp();
        exp.setExpAll(0d);
        exp.setExpValue(0d);
        List<CharacterItem> characterItems = new ArrayList<>();
        List<Follow> follows = new ArrayList<>();

        exp.updateExp(15d); // 경험치 15
        Character character = Character.createCharacter(exp, characterItems, follows);

        for(int i=0; i<2; i++) {
            CharacterItem characterItem = new CharacterItem();
            characterItem.setItemId(1l);
            characterItem.setWearingStatus(true);
            character.addCharacterItem(characterItem);
            characterItemService.join(characterItem);

            Follow follow = new Follow();
            character.addFriend(follow);
            followService.join(follow);
        }

        // when
        characterService.join(character);
        expService.join(exp);

        // then
        log.info("character.getId() : {}",character.getId());
        log.info("character.getExp().getExpAll() : {}",character.getExp().getExpAll());
        log.info("character.getExp().getExpValue() : {}",character.getExp().getExpValue());
        log.info("character.getExp().getLevel() : {}",character.getExp().getLevel());
        log.info("character.getCharacterItems().get(0).getItemId() : {}",character.getCharacterItems().get(0).getItemId());
    }

    @Test
    public void find() throws Exception {
        // given

        // when

        // then

    }

    @Test
    public void remove() throws Exception {
        // given

        // when

        // then

    }
}