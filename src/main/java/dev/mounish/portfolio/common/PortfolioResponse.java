package dev.mounish.portfolio.common;

import java.util.List;

public class PortfolioResponse {
	
	private String message;
	
	private List<FieldErrorMessage> fieldErrorMessages;

	public PortfolioResponse(String message) {
		super();
		this.message = message;
	}
	
	public PortfolioResponse(List<FieldErrorMessage> fieldErrorMessages) {
		super();
		this.fieldErrorMessages = fieldErrorMessages;
	}
	
	public PortfolioResponse(String message, List<FieldErrorMessage> fieldErrorMessages) {
		super();
		this.message = message;
		this.fieldErrorMessages = fieldErrorMessages;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldErrorMessage> getFieldErrorMessages() {
		return fieldErrorMessages;
	}

	public void setFieldErrorMessages(List<FieldErrorMessage> fieldErrorMessages) {
		this.fieldErrorMessages = fieldErrorMessages;
	}

	@Override
	public String toString() {
		return "PortfolioResponse [message=" + message + ", fieldErrorMessages=" + fieldErrorMessages + "]";
	}

}
