package dev.mounish.portfolio.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mounish.portfolio.common.PageRequestBuilder;
import dev.mounish.portfolio.common.PortfolioErrorMessage;
import dev.mounish.portfolio.common.PortfolioException;
import dev.mounish.portfolio.common.ResponseEntityBuilder;
import dev.mounish.portfolio.common.SearchRequest;
import dev.mounish.portfolio.common.SpecificationBuilder;
import dev.mounish.portfolio.models.Document;
import dev.mounish.portfolio.repositories.DocumentRepository;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {
	
	private static final Logger LOG = Logger.getLogger(DocumentController.class);
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	SpecificationBuilder specificationBuilder;
	
	@PostMapping
	@ResponseBody ResponseEntity<Object> saveDocument(@Valid @RequestBody final Document document) {
		LOG.debug(" ::: DocumentController >> saveDocument >> document: " + document);
		ResponseEntity<Object> responseEntity = null;
		if(document != null) {
			Document savedDocument = documentRepository.save(document);
			responseEntity = ResponseEntityBuilder.buildSuccessResponse(savedDocument);
		} else {
			throw new PortfolioException(PortfolioErrorMessage.NO_REQUEST_BODY.getMessage());
		}
		return responseEntity;
	}
	
	@PostMapping("/search")
	@ResponseBody ResponseEntity<Object> getDocuments(@RequestBody final SearchRequest searchRequest) {
		LOG.debug(" ::: DocumentController >> getDocuments >> searchRequest: " + searchRequest);
		ResponseEntity<Object> responseEntity = null;
		if(searchRequest != null) {
			PageRequest pageRequest = PageRequestBuilder.createPageRequest(searchRequest);
			Page<Document> document = null;
			if(searchRequest.getQueries() == null || searchRequest.getQueries().isEmpty()) {
				document = documentRepository.findAll(pageRequest);
			} else {
				Specification<Document> specification = specificationBuilder.getSpecification(searchRequest);
				document = documentRepository.findAll(specification, pageRequest);
			}
			if(document != null && document.hasContent()) {
				responseEntity = ResponseEntityBuilder.buildSuccessResponse(document);
			} else {
				throw new PortfolioException(PortfolioErrorMessage.NO_DATA_AVAILABLE.getMessage());
			}
		} else {
			throw new PortfolioException(PortfolioErrorMessage.NO_REQUEST_BODY.getMessage());
		}
		return responseEntity;
	}
	
	@GetMapping("/description/{description}")
	@ResponseBody ResponseEntity<Object> getDocumentByDescription(@PathVariable @NotBlank(message = "Mandatory") final String description) {
		LOG.debug(" ::: DocumentController >> getDocumentByDescription >> description: " + description);
		ResponseEntity<Object> responseEntity = null;
		List<Document> documents = documentRepository.findByDescriptionOrderByCreationDateDesc(description);
		if(documents != null && !documents.isEmpty()) {
			responseEntity = ResponseEntityBuilder.buildSuccessResponse(documents.get(0));
		} else {
			throw new PortfolioException(PortfolioErrorMessage.NO_DATA_AVAILABLE.getMessage());
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
			throw new PortfolioException(PortfolioErrorMessage.ID_NOT_FOUND.getMessage() + " : " + id);
		}
		return responseEntity;
	}

}
