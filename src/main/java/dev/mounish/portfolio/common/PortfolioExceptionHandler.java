package dev.mounish.portfolio.common;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class PortfolioExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(PortfolioExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseBody ResponseEntity<Object> handleException(Exception e) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String message = e.getMessage();
		LOG.error(" ::: Exception >> " + status.getReasonPhrase() + " >> " + message);
		PortfolioResponse portfolioResponse = new PortfolioResponse(message);
		return ResponseEntityBuilder.buildErrorResponse(portfolioResponse, status);
	}
	
	@ExceptionHandler(PortfolioException.class)
	@ResponseBody ResponseEntity<Object> handlePortfolioException(PortfolioException e) {
		HttpStatus status = e.getStatus() != null ? e.getStatus() : HttpStatus.BAD_REQUEST;
		String message = e.getMessage();
		LOG.error(" ::: PortfolioException >> " + status.getReasonPhrase() + " >> " + message);
		PortfolioResponse portfolioResponse = new PortfolioResponse(message);
		return ResponseEntityBuilder.buildErrorResponse(portfolioResponse, status);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		LOG.error(" ::: MethodArgumentNotValidException >> " + status.getReasonPhrase() + " >> ", fieldErrorMessages);
		PortfolioResponse portfolioResponse = new PortfolioResponse(fieldErrorMessages);
		return ResponseEntityBuilder.buildErrorResponse(portfolioResponse, status);
	}

}
