package com.giovannimenzano.skyscraperssolver.exceptions;

import com.giovannimenzano.skyscraperssolver.utils.ExceptionUtils;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 7592938660789584205L;

	private int errorCode;
	private String errorMessage;

	public CustomException(int errorCode) {
		super(ExceptionUtils.getMessage(errorCode));
		this.errorMessage = ExceptionUtils.getMessage(errorCode);
		this.errorCode = errorCode;
	}

	public CustomException(String errorMessage, int errorCode) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public CustomException(Throwable t) {
		super(t);
		this.errorMessage = t.getMessage();
	}

	public CustomException(int errorCode, Throwable t) {
		super(t);
		this.errorCode = errorCode;
		this.errorMessage = ExceptionUtils.getMessage(errorCode);
	}

	public CustomException(String errorMessage, int errorCode, Throwable t) {
		super(t);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public int getCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

}
