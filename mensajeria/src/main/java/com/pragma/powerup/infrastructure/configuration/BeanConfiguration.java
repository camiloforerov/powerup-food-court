package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.INotificationServicePort;
import com.pragma.powerup.domain.spi.IMessageNotificationPort;
import com.pragma.powerup.domain.usecase.NotificationUseCase;
import com.pragma.powerup.infrastructure.out.twilio.adapter.TwilioMessageNotificationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IMessageNotificationPort messageNotificationPort() {
        return new TwilioMessageNotificationAdapter();
    }

    @Bean
    public INotificationServicePort notificationServicePort() {
        return new NotificationUseCase(messageNotificationPort());
    }
}