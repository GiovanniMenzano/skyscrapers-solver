package com.giovannimenzano.skyscraperssolver.services;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.giovannimenzano.skyscraperssolver.entities.*;
import com.giovannimenzano.skyscraperssolver.exceptions.CustomException;

public class SkyscrapersSolverServiceTestOld {
	
	private static SkyscrapersSolverService solverService;
	
	private int assignCounter;
	
	/*
	not actually following decorator pattern, only using this class to add print lines to existing methods
	 */
	class SkyscrapersSolverServiceDecorator extends SkyscrapersSolverService {
		
		@Override
		protected void assign(Integer option, Cell<Integer> point) {
			super.assign(option, point);
			System.out.println(assignCounter++ + ")\t[" + point.getPosition()[0] + ":" + point.getPosition()[1] + "] = " + point.getValue());
		}
		
	}
	
	public static void printSolutions() {
		
		System.out.println(System.getProperty("line.separator") + "solutions:\t" + solverService.getSolutionsList().size());
		for(Cell[][] solution : solverService.getSolutionsList()) {
			printBoard(solution);
		}
		
	}
	
	public static void printBoard(Cell[][] solution) {
		
		String nl = System.getProperty("line.separator"); // newline
		StringBuilder sb = new StringBuilder(500);
		
		sb.append(nl);
		for(int i = 0; i < solution.length; i++) {
			sb.append("|");
			for(int j = 0; j < solution[0].length; j++)
				sb.append(solution[i][j].getValue() + "|");
			sb.append(nl);
		}
		
		System.out.println(sb.toString());
		
	}
	
	/* public static void main(String[] args) {

		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm");
		String formattedDate = dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth();
		String formattedTime = dateTime.format(formatter);
		
		URL currentProjectURL = SkyscrapersSolverServiceTestOld.class.getClassLoader().getResource("");
		String currentProjectPath = currentProjectURL.getPath();
		currentProjectPath = currentProjectPath.substring(0, currentProjectPath.lastIndexOf("target"));

		String logPath = currentProjectPath + "src/test/logs/" + formattedDate + "_" + formattedTime + ".log";
		System.out.println(logPath);
		
		try {
			PrintStream file = new PrintStream(logPath);
			System.setOut(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//@formatter:off
		int[][] board = new int[][]
				{{0, 4, 2, 1, 2, 3, 0},
				 {3, 0, 0, 0, 0, 0, 3},
				 {2, 0, 0, 0, 0, 0, 4},
				 {3, 0, 0, 0, 0, 0, 1},
				 {2, 0, 0, 0, 0, 0, 2},
				 {1, 0, 0, 0, 0, 0, 2},
				 {0, 1, 4, 3, 2, 2, 0}};
		
		int[][] solvedBoard = new int[][]
				{{2, 3, 5, 4, 1},
				 {1, 5, 4, 3, 2},
				 {3, 4, 2, 1, 5},
				 {4, 2, 1, 5, 3},
				 {5, 1, 3, 2, 4}};
		//@formatter:on
		
		Cell<Integer>[][] inputBoard = Cell.toCellArray(board);
		
        solverService.setInputBoard(inputBoard);
        solverService.setRequestedSolutions(10);
		
        try {
			solverService.start();
		} catch (CustomException e) {
			e.printStackTrace();
		}
        
        printSolutions();
		
	} */
	
}
