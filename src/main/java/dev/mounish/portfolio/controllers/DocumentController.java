package dev.mounish.portfolio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import dev.mounish.portfolio.common.PortfolioException;
import dev.mounish.portfolio.common.PortfolioMessage;
import dev.mounish.portfolio.common.ResponseEntityBuilder;
import dev.mounish.portfolio.common.SearchRequest;
import dev.mounish.portfolio.common.SpecificationBuilder;
import dev.mounish.portfolio.models.Document;
import dev.mounish.portfolio.models.DocumentDTO;
import dev.mounish.portfolio.repositories.DocumentRepository;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	SpecificationBuilder specificationBuilder;
	
	@PostMapping
	@ResponseBody ResponseEntity<Object> saveDocument(@Valid @RequestBody final DocumentDTO documentDTO) {
		LOG.debug(" ::: DocumentController >> saveDocument >> documentDTO : ", documentDTO);
		ResponseEntity<Object> responseEntity = null;
		Document document = new Document();
		BeanUtils.copyProperties(documentDTO, document);
		document.setKey(document.getKey().toUpperCase());
		document.setFileContent(documentDTO.getFileContent().getBytes());
		Document savedDocument = documentRepository.save(document);
		responseEntity = ResponseEntityBuilder.buildSuccessResponse(savedDocument);
		return responseEntity;
	}
	
	@PostMapping("/search")
	@ResponseBody ResponseEntity<Object> getDocuments(@RequestBody final SearchRequest searchRequest) {
		LOG.debug(" ::: DocumentController >> getDocuments >> searchRequest : ", searchRequest);
		ResponseEntity<Object> responseEntity = null;
		PageRequest pageRequest = PageRequestBuilder.createPageRequest(searchRequest);
		Page<Document> documents = null;
		if(searchRequest.getFilters() == null || searchRequest.getFilters().isEmpty()) {
			documents = documentRepository.findAll(pageRequest);
		} else {
			Specification<Document> specification = specificationBuilder.getSpecification(searchRequest);
			documents = documentRepository.findAll(specification, pageRequest);
		}
		if(documents != null && documents.hasContent()) {
			responseEntity = ResponseEntityBuilder.buildSuccessResponse(documents);
//			List<DocumentDTO> content = new ArrayList();
//			for(Document document : documents.getContent()) {
//				if(document != null) {
//					DocumentDTO documentDTO = new DocumentDTO();
//					BeanUtils.copyProperties(document, documentDTO);
//					documentDTO.setFileContent(new String(document.getFileContent()));
//					content.add(documentDTO);
//				}
//			}
//			DocumentDTOListResponse documentDTOListResponse = new DocumentDTOListResponse();
//			documentDTOListResponse.setContent(content);
//			documentDTOListResponse.setNumber(documents.getNumber());
//			documentDTOListResponse.setSize(documents.getSize());
//			documentDTOListResponse.setTotalElements(documents.getTotalElements());
//			documentDTOListResponse.setTotalPages(documents.getTotalPages());
//			LOG.debug(" ::: DocumentController >> getDocuments >> documentDTOListResponse : ", documentDTOListResponse);
//			responseEntity = ResponseEntityBuilder.buildSuccessResponse(documentDTOListResponse);
		} else {
			LOG.error(" ::: DocumentController >> getDocuments >> " + PortfolioMessage.NO_DATA_AVAILABLE.getMessage());
			throw new PortfolioException(PortfolioMessage.NO_DATA_AVAILABLE.getMessage());
		}
		return responseEntity;
	}
	
	@GetMapping("/resume")
	@ResponseBody ResponseEntity<Object> downloadLatestResume() {
		LOG.debug(" ::: DocumentController >> downloadLatestResume");
		ResponseEntity<Object> responseEntity = null;
		List<Document> documents = documentRepository.findByKeyOrderByCreationDateDesc("RESUME");
		if(documents != null && !documents.isEmpty() && documents.get(0) != null) {
			Document document = documents.get(0);
			responseEntity = ResponseEntityBuilder.buildDocumentResponse(document.getFileName(), document.getFileContent());
		} else {
			LOG.error(" ::: DocumentController >> downloadLatestResume >> " + PortfolioMessage.DOC_NOT_AVAILABLE.getMessage());
			throw new PortfolioException(PortfolioMessage.DOC_NOT_AVAILABLE.getMessage());
		}
		return responseEntity;
	}
	
	@GetMapping("/download/{id}")
	@ResponseBody ResponseEntity<Object> downloadDocument(@PathVariable final Long id) {
		LOG.debug(" ::: DocumentController >> downloadDocument >> id : " + id);
		ResponseEntity<Object> responseEntity = null;
		Document document = documentRepository.getOne(id);
		if(document != null) {
			responseEntity = ResponseEntityBuilder.buildDocumentResponse(document.getFileName(), document.getFileContent());
		} else {
			LOG.error(" ::: DocumentController >> downloadDocument >> " + PortfolioMessage.DOC_NOT_AVAILABLE.getMessage());
			throw new PortfolioException(PortfolioMessage.DOC_NOT_AVAILABLE.getMessage());
		}
		return responseEntity;
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody ResponseEntity<Object> deleteDocument(@PathVariable final Long id) {
		LOG.debug(" ::: DocumentController >> deleteDocument >> id : " + id);
		ResponseEntity<Object> responseEntity = null;
		if(documentRepository.existsById(id)) {
			documentRepository.deleteById(id);
			responseEntity = ResponseEntityBuilder.buildSuccessResponse();
		} else {
			LOG.error(" ::: DocumentController >> deleteDocument >> " + PortfolioMessage.ID_NOT_FOUND.getMessage() + " : " + id);
			throw new PortfolioException(PortfolioMessage.ID_NOT_FOUND.getMessage() + " : " + id);
		}
		return responseEntity;
	}

}
