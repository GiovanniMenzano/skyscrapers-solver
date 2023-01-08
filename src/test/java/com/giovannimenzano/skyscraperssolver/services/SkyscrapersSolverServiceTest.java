package com.giovannimenzano.skyscraperssolver.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.giovannimenzano.skyscraperssolver.entities.*;
import com.giovannimenzano.skyscraperssolver.exceptions.CustomException;
import com.giovannimenzano.skyscraperssolver.services.*;
import com.giovannimenzano.skyscraperssolver.utils.ResponseUtils;

public class SkyscrapersSolverServiceTest {
	
	static int[][] board;
	static int[][] solvedBoard;
	static Cell[][] cellSolvedBoard;
	static SkyscrapersSolverService solverService;
	List<Cell<Integer>[][]> solutions;
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = new int[][]{ // board with only clues
				{0, 4, 2, 1, 2, 3, 0},
				{3, 0, 0, 0, 0, 0, 3},
				{2, 0, 0, 0, 0, 0, 4},
				{3, 0, 0, 0, 0, 0, 1},
				{2, 0, 0, 0, 0, 0, 2},
				{1, 0, 0, 0, 0, 0, 2},
				{0, 1, 4, 3, 2, 2, 0}};
		
		solvedBoard = new int[][]{ // only solution to the board above
				{2, 3, 5, 4, 1},
				{1, 5, 4, 3, 2},
				{3, 4, 2, 1, 5},
				{4, 2, 1, 5, 3},
				{5, 1, 3, 2, 4}};
				
		cellSolvedBoard = Cell.toCellArray(solvedBoard);
		
		solverService = new SkyscrapersSolverService();
        solverService.setInputBoard(Cell.toCellArray(board));
        solverService.setRequestedSolutions(10);
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void fullTest() {

        // start the solving process
        try {
			solverService.start();
		} catch (CustomException e) {
			e.printStackTrace();
		}
        
        // get the list of all solutions found and make a few tests
        solutions = solverService.getSolutionsList();
		assertNotNull(solverService.getSolutionsList());
		assertNotEquals(0, solutions.size());
		assertNotNull(solutions.get(0));
		assertArrayEquals(cellSolvedBoard, solutions.get(0));
	}
	
	@Test
	public void cellTest() {
		Cell<Integer> cell1 = new Cell<Integer>(1);
		Cell<Integer> cell2 = new Cell<Integer>(1);
		assertTrue(cell1.equals(cell2));
		assertEquals(Cell.State.PREASSIGNED, cell1.getState());
		
		cell1.setValue(2);
		cell2.setValue(0);
		assertEquals(Cell.State.ASSIGNED, cell1.getState());
		assertEquals(Cell.State.NOT_ASSIGNED, cell2.getState());
		
		Cell<Integer> cell3 = new Cell<Integer>();
		assertEquals(Cell.State.NOT_ASSIGNED, cell3.getState());
		
	}
	
	@Test
	public void validityMethodTest() {
		
	}
	
	@Test
	public void privateFieldsTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		/*
		Field f = solverService.getClass().getDeclaredField("optionsList"); // NoSuchFieldException
		f.setAccessible(true);
		@SuppressWarnings({"unchecked"})
		List<Integer> optionsList = (List<Integer>) f.get(solverService);
		for(int x : optionsList) System.out.println(x);
		*/
	}
	
}
