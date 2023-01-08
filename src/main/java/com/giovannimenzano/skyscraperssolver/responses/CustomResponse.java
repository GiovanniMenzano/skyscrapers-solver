package com.giovannimenzano.skyscraperssolver.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.giovannimenzano.skyscraperssolver.utils.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse<T> {

	@JsonView(Views.BaseView.class)
	private String message;
	@JsonView(Views.BaseView.class)
	private int code;
	@JsonView(Views.BaseView.class)
	private T result;

	public CustomResponse(int code, String message, T result) {
		
		this.message = message;
		this.code = code;
		this.result = result;
		
	}

	public CustomResponse() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
