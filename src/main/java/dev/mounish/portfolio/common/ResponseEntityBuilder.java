package dev.mounish.portfolio.common;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
	
	private static final Logger LOG = Logger.getLogger(ResponseEntityBuilder.class);
	
	public static ResponseEntity<Object> buildSuccessResponse() {
		LOG.debug(" ::: Success Response");
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public static ResponseEntity<Object> buildSuccessResponse(Object object) {
		LOG.debug(" ::: Success Response >> " + object);
		return new ResponseEntity(object, HttpStatus.OK);
	}
	
	public static ResponseEntity<Object> buildErrorResponse(Object object, HttpStatus status) {
		LOG.debug(" ::: Error Response >> " + status.getReasonPhrase() + " >> " + object);
		return new ResponseEntity(object, status);
	}

}
