package com.lepl.domain.character;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class CharacterTest {
    Exp exp;
    List<CharacterItem> characterItemList;
    List<Follow> followList;
    List<Notification> notificationList;

    @BeforeEach // 테스트 실행 전
    public void beforeEach() {
        exp = new Exp();
        characterItemList = new ArrayList<>();
        followList = new ArrayList<>();
        notificationList = new ArrayList<>();
    }

    @Test
    public void 생성_편의메서드() throws Exception {
        // given
        Character character;

        // when
        character = Character.createCharacter(exp, characterItemList, followList, notificationList);

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
        character.addFollow(new Follow());

        // then
        Assertions.assertEquals(character.getNotifications().size(), 2);
        Assertions.assertEquals(character.getCharacterItems().size(), 1);
        Assertions.assertEquals(character.getFollows().size(), 1);
    }

}