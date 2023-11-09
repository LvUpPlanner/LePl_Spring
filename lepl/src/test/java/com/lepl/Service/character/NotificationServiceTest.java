package com.lepl.Service.character;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.lepl.domain.character.Character;
import com.lepl.domain.character.Exp;
import com.lepl.domain.character.Notification;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class NotificationServiceTest {
    @Autowired
    NotificationService notificationService;
    @Autowired
    CharacterService characterService;
    @Autowired
    ExpService expService;
    static Long notificationId; // 전역

    /**
     * join, findOne, findAll, remove, findAllWithCharacter
     */
    @Test
    @Order(1)
    @Rollback(value = false)
    public void 알림_저장과조회() throws Exception {
        // given
        Exp exp = Exp.createExp(0L, 0L, 1L);
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Notification notification = Notification.createNotification(character, "테스트 알림입니다.");
        expService.join(exp);
        characterService.join(character);

        // when
        notificationService.join(notification);
        Notification findNotification = notificationService.findOne(notification.getId());
        List<Notification> notifications = notificationService.findAll();
        notificationId = notification.getId();

        // then
        Assertions.assertEquals(notification.getId(), findNotification.getId());
        for (Notification no : notifications) {
            Assertions.assertInstanceOf(Notification.class, no);
        }

    }

    @Test
    public void 테스트() throws Exception {
        // given
        Exp exp = Exp.createExp(0L, 0L, 1L);
        Character character = Character.createCharacter(exp, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Notification notification = Notification.createNotification(character, "테스트 알림입니다.@@@");
        expService.join(exp);
        characterService.join(character);
        notificationService.join(notification);

        // when
        Long id = notificationService.join(notification);
        Notification findNotification = notificationService.findOne(id);

        // then
        Assertions.assertEquals(findNotification.getContent(), "test");
    }
}
