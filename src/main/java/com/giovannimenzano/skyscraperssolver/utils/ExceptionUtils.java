package com.giovannimenzano.skyscraperssolver.utils;

public class ExceptionUtils {

	public static final int MISSING_INPUTS = 1;

	public static final String MESSAGE_MISSING_INPUTS = "input data missing, please fill mandatory fields";

	public static String getMessage(int errorCode) {

		switch (errorCode) {
			case MISSING_INPUTS:
				return MESSAGE_MISSING_INPUTS;
			default:
				return "Error... You say what error? Well, I DON'T KNOW OK?";
		}

	}

}