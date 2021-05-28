package dev.mounish.portfolio.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mounish.portfolio.common.PageRequestBuilder;
import dev.mounish.portfolio.common.PortfolioException;
import dev.mounish.portfolio.common.PortfolioMessage;
import dev.mounish.portfolio.common.ResponseEntityBuilder;
import dev.mounish.portfolio.common.SearchRequest;
import dev.mounish.portfolio.common.SpecificationBuilder;
import dev.mounish.portfolio.models.Message;
import dev.mounish.portfolio.repositories.MessageRepository;
import dev.mounish.portfolio.services.EmailService;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	SpecificationBuilder specificationBuilder;
	
	@Autowired
	EmailService emailService;
	
	@PostMapping("/send")
	ResponseEntity<Object> sendMessage(@Valid @RequestBody final Message message) {
		LOG.debug(" ::: MessageController >> sendMessage >> message : ", message);
		ResponseEntity<Object> responseEntity = null;
		Message savedMessage = messageRepository.save(message);
		LOG.debug(" ::: MessageController >> sendMessage >> Message saved : " + savedMessage.getId());
		sendEmail(savedMessage);
		sendSms(savedMessage);
		savedMessage.setStatus("SENT");
		messageRepository.save(savedMessage);
		responseEntity = ResponseEntityBuilder.buildSuccessResponse(savedMessage);
		return responseEntity;
	}
	
	private void sendEmail(final Message message) {
		SimpleMailMessage adminMail = new SimpleMailMessage();
		adminMail.setTo("vmounishkumar@gmail.com");
		adminMail.setSubject("Enquiry: mounish.dev");
		StringBuilder adminMailBody = new StringBuilder();
		adminMailBody.append("Name: ").append(message.getName().trim()).append("\n")
				.append("Email: ").append(message.getEmail().trim()).append("\n")
				.append("Mobile: ").append(message.getMobile().trim()).append("\n\n")
				.append(message.getMessage().trim());
		adminMail.setText(adminMailBody.toString());
		emailService.sendEmail(adminMail);
		LOG.debug(" ::: MessageController >> sendEmail >> Message sent to vmounishkumar@gmail.com");
		
		SimpleMailMessage ackMail = new SimpleMailMessage();
		ackMail.setTo(message.getEmail().trim());
		ackMail.setSubject("Enquiry: mounish.dev");
		StringBuilder ackMailBody = new StringBuilder();
		ackMailBody.append("Hey ").append(message.getName().trim()).append(",\n\n")
				.append("Thanks for reaching out! This auto-reply is just to let you know I've received your message and I'll get back to you in 48 hours.").append("\n\n")
				.append("If you've any additional information that you think will help me to assist you, please feel free to reply to this email.").append("\n\n")
				.append("Mounish Kumar V").append("\n")
				.append("+91 999 4444 988");
		ackMail.setText(ackMailBody.toString());
		emailService.sendEmail(ackMail);
		LOG.debug(" ::: MessageController >> sendEmail >> Message sent to " + message.getEmail().trim());
	}
	
	private void sendSms(final Message message) {
		// TODO
	}
	
	@PostMapping("/search")
	ResponseEntity<Object> getMessages(@RequestBody final SearchRequest searchRequest) {
		LOG.debug(" ::: MessageController >> getMessages >> searchRequest : ", searchRequest);
		ResponseEntity<Object> responseEntity = null;
		PageRequest pageRequest = PageRequestBuilder.createPageRequest(searchRequest);
		Page<Message> messages = null;
		if(searchRequest.getFilters() == null || searchRequest.getFilters().isEmpty()) {
			messages = messageRepository.findAll(pageRequest);
		} else {
			Specification<Message> specification = specificationBuilder.getSpecification(searchRequest);
			messages = messageRepository.findAll(specification, pageRequest);
		}
		if(messages != null && messages.hasContent()) {
			responseEntity = ResponseEntityBuilder.buildSuccessResponse(messages);
		} else {
			LOG.error(" ::: MessageController >> getMessages >> " + PortfolioMessage.NO_DATA_AVAILABLE.getMessage());
			throw new PortfolioException(PortfolioMessage.NO_DATA_AVAILABLE.getMessage());
		}
		return responseEntity;
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Object> deleteMessage(@PathVariable final Long id) {
		LOG.debug(" ::: MessageController >> deleteMessage >> id : " + id);
		ResponseEntity<Object> responseEntity = null;
		if(messageRepository.existsById(id)) {
			messageRepository.deleteById(id);
			responseEntity = ResponseEntityBuilder.buildSuccessResponse();
		} else {
			LOG.error(" ::: MessageController >> deleteMessage >> " + PortfolioMessage.ID_NOT_FOUND.getMessage() + " : " + id);
			throw new PortfolioException(PortfolioMessage.ID_NOT_FOUND.getMessage() + " : " + id);
		}
		return responseEntity;
	}

}
