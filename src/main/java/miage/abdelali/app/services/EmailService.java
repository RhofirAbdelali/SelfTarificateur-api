package miage.abdelali.app.services;

import miage.abdelali.app.entities.Subscription;

public interface EmailService {
  void sendSubscriptionConfirmation(Subscription s, byte[] pdfBytes, String pdfName);
}
