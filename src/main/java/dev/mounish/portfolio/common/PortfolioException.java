package dev.mounish.portfolio.common;

import org.springframework.http.HttpStatus;

public class PortfolioException extends RuntimeException {
	
	private HttpStatus status;
	
	private String message;

	public PortfolioException(String message) {
		super();
		this.message = message;
	}
	
	public PortfolioException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}

}
