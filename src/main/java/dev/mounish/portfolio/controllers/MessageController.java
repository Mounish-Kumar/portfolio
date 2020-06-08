package dev.mounish.portfolio.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mounish.portfolio.common.PageRequestBuilder;
import dev.mounish.portfolio.common.PortfolioMessage;
import dev.mounish.portfolio.common.PortfolioException;
import dev.mounish.portfolio.common.ResponseEntityBuilder;
import dev.mounish.portfolio.common.SearchRequest;
import dev.mounish.portfolio.common.SpecificationBuilder;
import dev.mounish.portfolio.models.Message;
import dev.mounish.portfolio.repositories.MessageRepository;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	SpecificationBuilder specificationBuilder;
	
	@PostMapping("/send")
	@ResponseBody ResponseEntity<Object> sendMessage(@Valid @RequestBody final Message message) {
		LOG.debug(" ::: MessageController >> sendMessage >> message: " + message);
		ResponseEntity<Object> responseEntity = null;
		Message savedMessage = messageRepository.save(message);
		LOG.debug(" ::: MessageController >> sendMessage >> Message saved : " + savedMessage.getId());
		// Send Email & SMS Implementation
		LOG.debug(" ::: MessageController >> sendMessage >> Message sent");
		savedMessage.setStatus("SENT");
		messageRepository.save(savedMessage);
		responseEntity = ResponseEntityBuilder.buildSuccessResponse(savedMessage);
		return responseEntity;
	}
	
	@PostMapping("/search")
	@ResponseBody ResponseEntity<Object> getMessages(@RequestBody final SearchRequest searchRequest) {
		LOG.debug(" ::: MessageController >> getMessages >> searchRequest: " + searchRequest);
		ResponseEntity<Object> responseEntity = null;
		PageRequest pageRequest = PageRequestBuilder.createPageRequest(searchRequest);
		Page<Message> messages = null;
		if(searchRequest.getQueries() == null || searchRequest.getQueries().isEmpty()) {
			messages = messageRepository.findAll(pageRequest);
		} else {
			Specification<Message> specification = specificationBuilder.getSpecification(searchRequest);
			messages = messageRepository.findAll(specification, pageRequest);
		}
		if(messages != null && messages.hasContent()) {
			responseEntity = ResponseEntityBuilder.buildSuccessResponse(messages);
		} else {
			throw new PortfolioException(PortfolioMessage.NO_DATA_AVAILABLE.getMessage());
		}
		return responseEntity;
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody ResponseEntity<Object> deleteMessage(@PathVariable final Long id) {
		LOG.debug(" ::: MessageController >> deleteMessage >> id: " + id);
		ResponseEntity<Object> responseEntity = null;
		if(messageRepository.existsById(id)) {
			messageRepository.deleteById(id);
			responseEntity = ResponseEntityBuilder.buildSuccessResponse();
		} else {
			throw new PortfolioException(PortfolioMessage.ID_NOT_FOUND.getMessage() + " : " + id);
		}
		return responseEntity;
	}

}
