package dev.mounish.portfolio.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

public class ResponseEntityBuilder {
	
	private static final Logger LOG = LoggerFactory.getLogger(ResponseEntityBuilder.class);
	
	public static ResponseEntity<Object> buildSuccessResponse() {
		LOG.debug(" ::: Success Response");
		PortfolioResponse portfolioResponse = new PortfolioResponse(PortfolioMessage.SUCCESS.getMessage());
		return new ResponseEntity(portfolioResponse, HttpStatus.OK);
	}
	
	public static ResponseEntity<Object> buildSuccessResponse(Object object) {
		LOG.debug(" ::: Success Response >> " + object);
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
		LOG.debug(" ::: Error Response >> " + status.getReasonPhrase() + " >> " + object);
		return new ResponseEntity(object, status);
	}

}
