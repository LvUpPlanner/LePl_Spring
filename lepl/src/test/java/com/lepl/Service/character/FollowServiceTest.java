package com.lepl.Service.character;

import com.lepl.domain.character.*;
import com.lepl.domain.character.Character;
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
        Exp exp = Exp.createExp(0L, 0L, 1L);
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        expService.join(exp);
        characterService.join(character); // id 기록위함
        characterId = character.getId();
        Follow follow = Follow.createFollow(character, CHARACTER_EX_ID); // followerId 는 자동으로 자기자신 등록, followingId 는 임의로 10L인 캐릭터로 설정

        // when
        Long id = characterService.join(character);
        log.debug("id : {}", id);
        for(long i=0; i<2; i++) {
            Follow follow = new Follow();
//            follow.setFollowingId(id);
            follow.setFollowerId(i+2);

//            character.addFriend(follow);
            followService.join(follow);
        }
        followId = follow.getId();
    }

    @Order(2)
    @Test
    @Rollback(value = false)
    public void 팔로잉_팔로워_조회() throws Exception {
        // given
        Exp exp = Exp.createExp(0L, 0L, 1L);
        Character c = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); // characterId 인 캐릭터를 팔로우 하는 캐릭터를 임의로 생성 목적
        expService.join(exp);
        characterService.join(c);
        Follow follow = Follow.createFollow(c, characterId);
        followService.join(follow);

        List<Follow> followers;
        List<Follow> followings;

        // when
        followers = followService.findAllWithFollower(characterId);
        followings = followService.findAllWithFollowing(characterId);

        // then
        log.debug("character.getId() : {}",character.getId());
        log.debug("character.getId() : {}",character.getFollows().get(0).getFollowingId());
        log.debug("character.getId() : {}",character.getFollows().get(0).getFollowerId());
        log.debug("character.getId() : {}",character.getFollows().get(1).getFollowingId());
        log.debug("character.getId() : {}",character.getFollows().get(1).getFollowerId());
    }
}