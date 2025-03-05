package com.example.donationmanagement.services.RiskManagement;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsappNumber;

    public void sendWhatsappMessage(String userWhatsapp, String messageBody) {
        // Initialiser Twilio avec les identifiants
        Twilio.init(accountSid, authToken);

        // Envoyer un message WhatsApp
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + userWhatsapp), // Numéro de l'utilisateur
                new com.twilio.type.PhoneNumber(twilioWhatsappNumber), // Numéro Twilio WhatsApp
                messageBody // Contenu du message
        ).create();

        System.out.println("Message WhatsApp envoyé : " + message.getSid());
    }
}
