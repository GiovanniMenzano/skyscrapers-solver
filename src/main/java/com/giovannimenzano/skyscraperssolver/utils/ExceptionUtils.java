package com.giovannimenzano.skyscraperssolver.utils;

public class ExceptionUtils {

	public static final int MISSING_INPUTS = 1;
	public static final int ALREADY_SOLVED = 2;

	public static final String MESSAGE_MISSING_INPUTS = "input data missing, please fill mandatory fields";
	public static final String MESSAGE_ALREADY_SOLVED = "game board is already solved";

	public static String getMessage(int errorCode) {

		switch (errorCode) {
			case MISSING_INPUTS:
				return MESSAGE_MISSING_INPUTS;
			case ALREADY_SOLVED:
				return MESSAGE_ALREADY_SOLVED;
			default:
				return "Error... You say what error? Well, I DON'T KNOW OK?";
		}

	}

}