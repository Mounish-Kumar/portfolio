package dev.mounish.portfolio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import dev.mounish.portfolio.common.PortfolioException;
import dev.mounish.portfolio.common.PortfolioMessage;

@Service
public class EmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(final SimpleMailMessage simpleMailMessage) {
		if(simpleMailMessage != null) {
			LOG.debug(" ::: EmailService >> sendEmail >> simpleMailMessage : ", simpleMailMessage);
			try {
				simpleMailMessage.setFrom("vmounishkumar@gmail.com");
				javaMailSender.send(simpleMailMessage);
				LOG.debug(" ::: EmailService >> sendEmail >> Email sent to " + simpleMailMessage.getTo());
			} catch(final MailParseException e) {
				LOG.error(" ::: EmailService >> sendEmail >> MailParseException: " + e.getMessage());
				throw new PortfolioException(PortfolioMessage.EMAIL_PARSING_FAILED.getMessage());
			} catch(final MailAuthenticationException e) {
				LOG.error(" ::: EmailService >> sendEmail >> MailAuthenticationException: " + e.getMessage());
				throw new PortfolioException(PortfolioMessage.EMAIL_AUTH_FAILED.getMessage());
			} catch(final MailSendException e) {
				LOG.error(" ::: EmailService >> sendEmail >> MailSendException: " + e.getMessage());
				throw new PortfolioException(PortfolioMessage.FAILED_TO_SEND_EMAIL.getMessage());
			}
		} else {
			LOG.error(" ::: EmailService >> sendEmail >> SimpleMailMessage is null");
			throw new PortfolioException(PortfolioMessage.EMAIL_PARSING_FAILED.getMessage());
		}
	}

}
