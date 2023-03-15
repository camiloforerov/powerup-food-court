package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.spi.IMessageNotificationPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class NotificationUseCaseTest {

    @InjectMocks
    NotificationUseCase notificationUseCase;
    @Mock
    IMessageNotificationPort messageNotificationPort;

    @Test
    void mustNotifyClientWithSms() {
        String notificationCode = "1232321";
        String phoneNumber = "+55456654676";

        when(messageNotificationPort.sendNotificationToNumber(notificationCode, phoneNumber))
                .thenReturn(true);
        boolean result = notificationUseCase.notifyClientSms(notificationCode, phoneNumber);

        Assertions.assertTrue(result);
    }

    @Test
    void mustNotNotifyClientWithSms() {
        String notificationCode = "1232321";
        String phoneNumber = "+55456654676";

        when(messageNotificationPort.sendNotificationToNumber(notificationCode, phoneNumber))
                .thenReturn(false);
        boolean result = notificationUseCase.notifyClientSms(notificationCode, phoneNumber);

        Assertions.assertFalse(result);
    }
}