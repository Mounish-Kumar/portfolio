package dev.mounish.portfolio.common;

import java.net.URLConnection;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

public class ResponseEntityBuilder {
	
	private static final Logger LOG = LoggerFactory.getLogger(ResponseEntityBuilder.class);
	
	public static ResponseEntity<Object> buildSuccessResponse() {
		LOG.debug(" ::: ResponseEntityBuilder >> buildSuccessResponse");
		PortfolioResponse portfolioResponse = new PortfolioResponse(PortfolioMessage.SUCCESS.getMessage());
		return new ResponseEntity(portfolioResponse, HttpStatus.OK);
	}
	
	public static ResponseEntity<Object> buildSuccessResponse(Object object) {
		LOG.debug(" ::: ResponseEntityBuilder >> buildSuccessResponse >> ", object);
		if(object != null && !(object instanceof String)) {
			return new ResponseEntity(object, HttpStatus.OK);
		} else {
			String successMessage = null;
			if(object != null && object instanceof String && StringUtils.hasText((String) object)) {
				successMessage = (String) object;
			} else {
				successMessage = PortfolioMessage.SUCCESS.getMessage();
			}
			PortfolioResponse portfolioResponse = new PortfolioResponse(successMessage);
			return new ResponseEntity(portfolioResponse, HttpStatus.OK);
		}
	}
	
	public static ResponseEntity<Object> buildErrorResponse(Object object, HttpStatus status) {
		LOG.debug(" ::: ResponseEntityBuilder >> buildErrorResponse >> " + status.getReasonPhrase() + " >> ", object);
		return new ResponseEntity(object, status);
	}
	
	public static ResponseEntity<Object> buildDocumentResponse(String fileName, byte[] fileContent) {
		LOG.debug(" ::: ResponseEntityBuilder >> buildDocumentResponse >> fileName : " + fileName);
		String base64FileContent = new String(fileContent).split(",")[1];
		byte[] decodedFileContent = Base64.decodeBase64(base64FileContent);
		String type = URLConnection.guessContentTypeFromName(fileName);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", type);
		httpHeaders.setContentLength(decodedFileContent.length);
		httpHeaders.add("content-disposition", "attachment; filename=" + fileName);
		return new ResponseEntity<Object>(decodedFileContent, httpHeaders, HttpStatus.OK);
	}

}
