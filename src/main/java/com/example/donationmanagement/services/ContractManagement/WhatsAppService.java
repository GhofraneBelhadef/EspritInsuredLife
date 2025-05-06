package com.example.donationmanagement.services.ContractManagement;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    // Remplace avec tes credentials Twilio
    public static final String ACCOUNT_SID = "AC3549cec1f474aacd7df003f03cfa2119";
    public static final String AUTH_TOKEN = "9072784e01d0df23fc30a1dff38cac1e";
    public static final String FROM_WHATSAPP_NUMBER = "whatsapp:+14155238886"; // Numéro Twilio WhatsApp
    public static final String TO_WHATSAPP_NUMBER = "whatsapp:+21654250795"; // Numéro admin WhatsApp

    public WhatsAppService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendWhatsAppMessage(String messageContent) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(TO_WHATSAPP_NUMBER),
                new com.twilio.type.PhoneNumber(FROM_WHATSAPP_NUMBER),
                messageContent
        ).create();

        System.out.println("Message sent: " + message.getSid());
    }
}
