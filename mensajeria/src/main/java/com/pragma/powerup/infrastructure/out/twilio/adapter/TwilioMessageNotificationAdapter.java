package com.pragma.powerup.infrastructure.out.twilio.adapter;

import com.pragma.powerup.domain.spi.IMessageNotificationPort;
import com.pragma.powerup.infrastructure.out.twilio.exceptions.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioMessageNotificationAdapter implements IMessageNotificationPort {
    private static final String SERVICE_SID = System.getenv("TWILIO_SERVICE_SID");

    /**
     * Send message through twilio service
     *
     * @param message message to be sent
     * @param phoneNumber phone number to send the message to
     * @return true if the message was accepted by the twilio service, false otherwise
     * */
    @Override
    public boolean sendNotificationToNumber(String message, String phoneNumber) {
        try {
            Message twilioMessage = Message.creator(
                            new PhoneNumber(phoneNumber),
                            SERVICE_SID,
                            message
                    )
                    .create();
            return twilioMessage.getStatus() == Message.Status.ACCEPTED;
        } catch (com.twilio.exception.ApiException ex) {
            throw new TwilioException(ex.getMessage());
        }
    }
}
