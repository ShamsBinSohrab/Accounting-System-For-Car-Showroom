package com.apiservice.authentication;

import com.google.common.base.Preconditions;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.UUID;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetMailSender {

  private final JavaMailSender javaMailSender;
  private final String mailText =
      "To reset your password please click the link below: <br>"
          + "http://localhost:8080/confirmResetPassword?token={0} <br><br>"
          + "Please ignore this email if you have not made this request.";

  public void send(String email) {
    Preconditions.checkArgument(StringUtils.isNotBlank(email), "Email address can not be null");
    try {
      final MimeMessage message = javaMailSender.createMimeMessage();
      message.setRecipient(RecipientType.TO, new InternetAddress(email));
      message.setFrom(new InternetAddress("team15.common@gmail.com", "Team 15"));
      message.setSubject("Password reset confirmation");
      message.setContent(MessageFormat.format(mailText, UUID.randomUUID()), "text/html; charset=utf-8");
      javaMailSender.send(message);
    } catch (UnsupportedEncodingException | MessagingException e) {
      e.printStackTrace();
    }

  }
}
