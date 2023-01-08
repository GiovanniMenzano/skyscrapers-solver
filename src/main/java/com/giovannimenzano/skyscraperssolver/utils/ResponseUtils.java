package com.giovannimenzano.skyscraperssolver.utils;

import org.springframework.http.ResponseEntity;

import com.giovannimenzano.skyscraperssolver.exceptions.CustomException;
import com.giovannimenzano.skyscraperssolver.responses.CustomResponse;

public class ResponseUtils {

	public static <T> ResponseEntity<CustomResponse<?>> buildSuccessOperationResponse(T data) {
		return buildResponse(200, "SUCCESS", data);
	}

	public static ResponseEntity<CustomResponse<?>> buildFailedOperationResponse(int code, String errorMessage) {
		return buildResponse(code, errorMessage, null);
	}

	public static ResponseEntity<CustomResponse<?>> buildFailedOperationResponse(CustomException e) {
		return buildResponse(e.getCode(), e.getMessage(), null);
	}

	public static <T> ResponseEntity<CustomResponse<?>> buildResponse(int code, String message, T data) {
		return ResponseEntity.ok(new CustomResponse<>(code, message, data));
	}

}
