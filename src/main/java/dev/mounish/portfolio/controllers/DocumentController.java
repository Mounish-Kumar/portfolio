package dev.mounish.portfolio.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mounish.portfolio.common.PageRequestBuilder;
import dev.mounish.portfolio.common.PortfolioException;
import dev.mounish.portfolio.common.PortfolioMessage;
import dev.mounish.portfolio.common.ResponseEntityBuilder;
import dev.mounish.portfolio.common.SearchRequest;
import dev.mounish.portfolio.common.SpecificationBuilder;
import dev.mounish.portfolio.models.Document;
import dev.mounish.portfolio.repositories.DocumentRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/document")
public class DocumentController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	SpecificationBuilder specificationBuilder;
	
	@PostMapping
	@ResponseBody ResponseEntity<Object> saveDocument(@Valid @RequestBody final Document document) {
		LOG.debug(" ::: DocumentController >> saveDocument >> document: " + document);
		ResponseEntity<Object> responseEntity = null;
		Document savedDocument = documentRepository.save(document);
		responseEntity = ResponseEntityBuilder.buildSuccessResponse(savedDocument);
		return responseEntity;
	}
	
	@PostMapping("/search")
	@ResponseBody ResponseEntity<Object> getDocuments(@RequestBody final SearchRequest searchRequest) {
		LOG.debug(" ::: DocumentController >> getDocuments >> searchRequest: " + searchRequest);
		ResponseEntity<Object> responseEntity = null;
		PageRequest pageRequest = PageRequestBuilder.createPageRequest(searchRequest);
		Page<Document> documents = null;
		if(searchRequest.getQueries() == null || searchRequest.getQueries().isEmpty()) {
			documents = documentRepository.findAll(pageRequest);
		} else {
			Specification<Document> specification = specificationBuilder.getSpecification(searchRequest);
			documents = documentRepository.findAll(specification, pageRequest);
		}
		if(documents != null && documents.hasContent()) {
			responseEntity = ResponseEntityBuilder.buildSuccessResponse(documents);
		} else {
			throw new PortfolioException(PortfolioMessage.NO_DATA_AVAILABLE.getMessage());
		}
		return responseEntity;
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody ResponseEntity<Object> deleteDocument(@PathVariable final Long id) {
		LOG.debug(" ::: DocumentController >> deleteDocument >> id: " + id);
		ResponseEntity<Object> responseEntity = null;
		if(documentRepository.existsById(id)) {
			documentRepository.deleteById(id);
			responseEntity = ResponseEntityBuilder.buildSuccessResponse();
		} else {
			throw new PortfolioException(PortfolioMessage.ID_NOT_FOUND.getMessage() + " : " + id);
		}
		return responseEntity;
	}

}
