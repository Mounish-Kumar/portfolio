package dev.mounish.portfolio.common;

public enum PortfolioMessage {

	SUCCESS("Success"),
	ID_NOT_FOUND("ID not found"),
	INVALID_COLUMN("Invalid Column"),
	NO_DATA_AVAILABLE("No data available"),
	EMAIL_PARSING_FAILED("Unable to parse Email message"),
	EMAIL_AUTH_FAILED("Email authentication failed"),
	FAILED_TO_SEND_EMAIL("Failed to send Email");
	
	String message;
	
	private PortfolioMessage(final String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
