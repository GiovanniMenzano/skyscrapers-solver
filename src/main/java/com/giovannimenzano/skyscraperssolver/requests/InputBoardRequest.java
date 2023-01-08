package com.giovannimenzano.skyscraperssolver.requests;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputBoardRequest implements Serializable {

	private static final long serialVersionUID = 2292464492670144368L;

	int[][] inputBoard;

	public int[][] getInputBoard() {
		return inputBoard;
	}

	public void setInputBoard(int[][] inputBoard) {
		this.inputBoard = inputBoard;
	}
	
}
