package com.lepl.domain.character;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class CharacterTest {
    Exp exp;
    List<CharacterItem> characterItemList;
    List<Follow> followList;
    List<Notification> notificationList;

    @BeforeEach // 테스트 실행 전
    public void beforeEach() {
        exp = Exp.createExp(0L,0L,1L);
        characterItemList = new ArrayList<>();
        followList = new ArrayList<>();
        notificationList = new ArrayList<>();
    }

    @Test
    @Transactional
    public void 캐릭터_경험치_관련() throws Exception {
        // given
        Exp exp = new Exp();
        exp.setExpAll(0l);
        exp.setExpValue(0l);
        List<CharacterItem> characterItems = new ArrayList<>();
        List<Follow> follows = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();

        for(int i=0; i<2; i++) {
            CharacterItem characterItem = new CharacterItem();
            characterItem.setItemId(1l);
            characterItem.setWearingStatus(true);
            characterItems.add(characterItem);

            Follow follow = new Follow();
            follow.setFollowerId(10L);
            follows.add(follow);
        }

        // when
//        exp.updateExp(15d); // 경험치 15
        Character character = Character.createCharacter(exp, characterItems, follows, notifications);
        em.persist(character); // id 확인

        // then
        Assertions.assertInstanceOf(Character.class, character);
    }

    @Test
    public void 연관관계_편의메서드() throws Exception {
        // given
        Character character = Character.createCharacter(exp, characterItemList, followList, notificationList);

        // when
        character.addNotification(new Notification());
        character.addNotification(new Notification());
        character.addCharacterItem(new CharacterItem());
        character.addFollow(Follow.createFollow(Character.createCharacter(Exp.createExp(0L, 0L, 1L), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),1L));

        // then
        Assertions.assertEquals(character.getNotifications().size(), 2);
        Assertions.assertEquals(character.getCharacterItems().size(), 1);
        Assertions.assertEquals(character.getFollows().size(), 1);

        Assertions.assertEquals(character.getNotifications().get(0).getCharacter(), character);
        Assertions.assertEquals(character.getCharacterItems().get(0).getCharacter(), character);
        Assertions.assertEquals(character.getFollows().get(0).getCharacter(), character);
    }

}