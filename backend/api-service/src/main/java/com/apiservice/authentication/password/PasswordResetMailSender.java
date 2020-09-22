package com.apiservice.authentication.password;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.entity.master.password.PasswordResetConfirmationToken;
import com.apiservice.repository.password.PasswordResetConfirmationTokenRepository;
import com.google.common.base.Preconditions;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.UUID;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
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
  private final PasswordResetConfirmationTokenRepository confirmationTokenRepository;
  private final String mailText =
      "To reset your password please click the link below: <br>"
          + "http://localhost:8080/confirmResetPassword?token={0} <br><br>"
          + "Please ignore this email if you have not made this request.";

  public void sendForOperator(Operator operator) {
    Preconditions.checkArgument(
        StringUtils.isNotBlank(operator.getEmail()), "Email address can not be null");
    try {
      final PasswordResetConfirmationToken confirmationToken =
          PasswordResetConfirmationToken.generateNewToken(operator);
      confirmationTokenRepository.save(confirmationToken);
      final MimeMessage message = javaMailSender.createMimeMessage();
      message.setRecipient(RecipientType.TO, new InternetAddress(operator.getEmail()));
      message.setFrom(new InternetAddress("team15.common@gmail.com", "Team 15"));
      message.setSubject("Password reset confirmation");
      message.setContent(
          MessageFormat.format(mailText, confirmationToken.getToken()), "text/html; charset=utf-8");
      javaMailSender.send(message);
    } catch (UnsupportedEncodingException | MessagingException e) {
      e.printStackTrace();
    }
  }
}
