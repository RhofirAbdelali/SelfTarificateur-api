package miage.abdelali.app.services.impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import miage.abdelali.app.entities.Subscription;
import miage.abdelali.app.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Value("${app.mail.from:noreply@selfassurance.fr}")
  private String from;

  @Value("${app.mail.admin:}")
  private String admin;

  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendSubscriptionConfirmation(Subscription s, byte[] pdfBytes, String pdfName) {
    if (s == null || s.getEmail() == null || s.getEmail().isBlank()) return;
    try {
      MimeMessage msg = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

      helper.setFrom(from);
      helper.setTo(s.getEmail());
      if (admin != null && !admin.isBlank()) helper.setBcc(admin);

      String subject = "Votre devis Selfassurance — " + safe(s.getProductCode());
      String body = """
        Bonjour %s %s,

        Merci pour votre souscription. Vous trouverez ci-joint votre devis en PDF.
        Produit : %s
        Version : %s
        Prime TTC : %s %s

        Cet email est généré automatiquement, merci de ne pas répondre directement.

        Cordialement,
        Selfassurance
        """.formatted(
          safe(s.getPrenom()), safe(s.getNom()),
          safe(s.getProductCode()), safe(s.getVersionCode()),
          s.getPrimeTTC() == null ? "-" : s.getPrimeTTC().toString(),
          safe(s.getCurrency())
        );

      helper.setSubject(subject);
      helper.setText(body);

      if (pdfBytes != null && pdfBytes.length > 0) {
        helper.addAttachment(pdfName, new ByteArrayResource(pdfBytes));
      }

      mailSender.send(msg);
    } catch (Exception ignore) { 
    }
  }

  private static String safe(Object o) { return o == null ? "-" : String.valueOf(o); }
}
