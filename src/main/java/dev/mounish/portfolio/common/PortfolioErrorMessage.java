package dev.mounish.portfolio.common;

public enum PortfolioErrorMessage {

	RECORD_NOT_FOUND("Record not found"),
	NO_REQUEST_BODY("No request body"),
	ID_NOT_FOUND("ID not found"),
	INVALID_COLUMN("Invalid Column"),
	NO_DATA_AVAILABLE("No data available");
	
	String message;
	
	private PortfolioErrorMessage(final String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
