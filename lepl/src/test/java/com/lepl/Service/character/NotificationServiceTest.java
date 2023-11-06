package com.lepl.Service.character;

import com.lepl.domain.character.Notification;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationServiceTest {
    @Autowired
    NotificationService notificationService;

    @Test
    public void 테스트() throws Exception {
        // given
        Notification notification = new Notification();
        notification.setContent("test");

        // when
        Long id = notificationService.join(notification);
        Notification findNotification = notificationService.findOne(id);

        // then
        Assertions.assertEquals(findNotification.getContent(), "test");
    }
}
