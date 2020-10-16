package com.apiservice.authentication.password;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.master.operator.Operator;
import com.apiservice.entity.master.password.PasswordResetConfirmationRequest;
import com.apiservice.repository.password.PasswordResetConfirmationRequestRepository;
import com.google.common.base.Preconditions;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordResetMailSender {

  @Value("${webapp.base.url}")
  private String webappBaseUrl;

  private final JavaMailSender javaMailSender;
  private final PasswordResetConfirmationRequestRepository confirmationTokenRepository;
  private final JwtTokenUtil jwtTokenUtil;

  public void sendForOperator(Operator operator) {
    Preconditions.checkArgument(
        StringUtils.isNotBlank(operator.getEmail()), "Email address can not be null");
    try {
      final PasswordResetConfirmationRequest request =
          PasswordResetConfirmationRequest.createRequest(operator);
      confirmationTokenRepository.save(request);
      final MimeMessage message = javaMailSender.createMimeMessage();
      message.setRecipient(RecipientType.TO, new InternetAddress(operator.getEmail()));
      message.setFrom(new InternetAddress("team15.common@gmail.com", "Team 15"));
      message.setSubject("Password reset confirmation");
      message.setContent(
          MessageFormat.format(getMailText(),
              jwtTokenUtil.generatePasswordResetConfirmationToken(request)),
          "text/html; charset=utf-8");
      javaMailSender.send(message);
    } catch (UnsupportedEncodingException | MessagingException ex) {
      log.error(ex.getMessage(), ex);
    }
  }

  private String getMailText() {
    return "To reset your password please click the link below: <br>"
        + webappBaseUrl + "/confirmResetPassword?token={0} <br><br>"
        + "Please ignore this email if you have not made this request.";
  }
}
