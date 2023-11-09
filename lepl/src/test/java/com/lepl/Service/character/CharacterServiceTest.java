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
        Exp exp = Exp.createExp(0L,0L,1L);
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // when
        characterService.join(character);
        expService.join(exp);

        // then
        Assertions.assertEquals(character.getId(), findCharacter.getId());
        log.info("character Id : {}", character.getId());
        characterId = character.getId();
    }


    @Test
    @Order(2)
    public void 회원의_캐릭터조회() throws Exception {
        // given
        Exp exp = Exp.createExp(0L,0L,1L);
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Member member = Member.createMember("캐릭터서비스 테스트", "TEST");
        expService.join(exp);
        characterService.join(character);
        member.setCharacter(character);
        memberService.join(member);

        // when
        Character findCharacter = characterService.findCharacterWithMember(member.getId());

        // then
        Assertions.assertEquals(character.getId(), findCharacter.getId());
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