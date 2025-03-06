package com.example.donationmanagement.services.UserManagement;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        try {
            String verificationLink = "http://localhost:9090/api/auth/verify?token=" + token;
            String subject = "Vérification de votre compte";
            String content = "<p>Bonjour,</p>"
                    + "<p>Merci de vous être inscrit. Cliquez sur le lien ci-dessous pour vérifier votre compte :</p>"
                    + "<p><a href=\"" + verificationLink + "\">Vérifier mon compte</a></p>"
                    + "<p>Si vous n'avez pas fait cette demande, ignorez cet e-mail.</p>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            System.out.println("✅ Email envoyé avec succès à : " + to);

        } catch (Exception e) {
            System.err.println("❌ Erreur d'envoi d'email : " + e.getMessage());
            e.printStackTrace(); // 🔥 Cette ligne va afficher l'erreur exacte
            throw new RuntimeException("Erreur lors de l'envoi de l'email.", e);
        }
    }
    public void sendPasswordResetEmail(String to, String resetLink) throws MessagingException {
        String subject = "Réinitialisation de votre mot de passe";
        String content = "<p>Bonjour,</p>"
                + "<p>Vous avez demandé à réinitialiser votre mot de passe.</p>"
                + "<p>Cliquez sur le lien ci-dessous pour le réinitialiser :</p>"
                + "<p><a href=\"" + resetLink + "\">Réinitialiser mon mot de passe</a></p>"
                + "<p>Si vous n'avez pas fait cette demande, ignorez cet email.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}