package com.giovannimenzano.skyscraperssolver.services;

import java.util.*;

import org.springframework.stereotype.Component;

import com.giovannimenzano.skyscraperssolver.entities.Cell;

@Component
public class SkyscrapersSolverService extends ProblemSolver<Cell<Integer>, Integer> {

	private Cell<Integer>[][] outputBoard;
	private HashMap<String, int[]> clues;
	
	public SkyscrapersSolverService() {
		
		this.optionsList = new ArrayList<Integer>();
		this.solutionsList = new ArrayList<Cell<Integer>[][]>();
		this.clues = new HashMap<String, int[]>();
		this.requestedSolutions = 1;
		
	}
	
	public void setInputBoard(Cell<Integer>[][] inputBoard) {
		
		this.initialize();
		
		/*
		 * at this point I know you are already wondering "why does he pass a whole board
		 * filled with zeroes when he can just pass 4 array containing the clues?".
		 * Well, first of all, it's none of your business :). And second, because by doing this I could
		 * allow the client to send me half solved boards, if the user wants to.
		 */
		this.outputBoard = new Cell[inputBoard.length - 2][inputBoard.length - 2];
		
		/*
		 * I build a new board with only solvable positions to make algorithm easier to read and write,
		 * leaving out external clues.
		 * With the outer loop I'm also building the list of options to be used by the algorithm (in this case I'm just filling it with numbers from 1 to N dimension of board).
		 * TODO fill with input values
		 */
		for(int i = 0; i < outputBoard.length; i++) {
			
			optionsList.add(i, i + 1);
			for(int j = 0; j < outputBoard.length; j++) {
				this.outputBoard[i][j] = new Cell<Integer>(0, new int[] {i, j});
			}
			
		}
		
		/*
		 * now I build my lists of clues to simplify future use
		 */		
		// NORTH
		int[] northClues = new int[inputBoard.length - 2];
		for(int i = 1; i < inputBoard.length - 1; i++) {
			northClues[i - 1] = inputBoard[0][i].getValue();
		}
		clues.put("north", northClues);
		
		// EAST
		int[] eastClues = new int[inputBoard.length - 2];
		for(int i = 1; i < inputBoard.length - 1; i++) {
			eastClues[i - 1] = inputBoard[i][inputBoard.length - 1].getValue();
		}
		clues.put("east", eastClues);
		
		// SOUTH
		int[] southClues = new int[inputBoard.length - 2];
		for(int i = 1; i < inputBoard.length - 1; i++) {
			southClues[i - 1] = inputBoard[inputBoard.length - 1][i].getValue();
		}
		clues.put("south", southClues);
		
		// WEST
		int[] westClues = new int[inputBoard.length - 2];
		for(int i = 1; i < inputBoard.length - 1; i++) {
			westClues[i - 1] = inputBoard[i][0].getValue();
		}
		clues.put("west", westClues);
		
	}

	@Override
	protected void initialize() {

		this.optionsList.clear();
		this.solutionsList.clear();
		
	}

	/*
	 * support method to check the if the chosen number is valid through all clues in the same column and same row
	 */
	@Override
	protected boolean isValidOption(Integer option, Cell<Integer> point) {
		
		int row = point.getPosition()[0];
		int col = point.getPosition()[1];
		
		// check row and column
		for(int i = 0; i < outputBoard.length; i++)
			if((outputBoard[row][i].getValue().equals(option) && outputBoard[row][i] != point) || (outputBoard[i][col].getValue().equals(option) && outputBoard[i][col] != point))
				return false;
		
		// return isLastPoint(point) ? checkClues() : checkLocalClues(row, col);
		return checkLocalClues(row, col);
		
	}
	
	/*
	 * NOTE: modified to count zeros (see validity_test_log.txt in src/test/logs folder for an overview of the issue)
	 */
	private boolean checkLocalClues(int row, int col) {
		
		int clue, visibleSkyscrapers, maxHeight, zerosLeft;
		
		// NORTH, thus the clue is in position north[col number]
		clue = clues.get("north")[col];
		
		if(clue != 0) { // checking if clue is present
			visibleSkyscrapers = maxHeight = zerosLeft = 0;
			for(int i = 0; i < outputBoard.length; i++) {
				if(outputBoard[i][col].getValue() == 0) {
					zerosLeft++;
				} else if(outputBoard[i][col].getValue() > maxHeight) {
					visibleSkyscrapers++;
					maxHeight = outputBoard[i][col].getValue();
				}
			}
			
			// actual check for north
			if(zerosLeft != 0) {
				if(zerosLeft < Math.abs(visibleSkyscrapers - clue) - 1)
					return false;
			} else if(visibleSkyscrapers != clue)
				return false;
			
		}
		// ------------------------------------------------------------------------
		
		// EAST, thus the clue is in position east[row number]
		clue = clues.get("east")[row];
		
		if(clue != 0) { // checking if clue is present
			visibleSkyscrapers = maxHeight = zerosLeft = 0;
			for(int i = outputBoard.length - 1; i >= 0; i--) {
				if(outputBoard[row][i].getValue() == 0) {
					zerosLeft++;
				} else if(outputBoard[row][i].getValue() > maxHeight) {
					visibleSkyscrapers++;
					maxHeight = outputBoard[row][i].getValue();
				}
			}
			
			// actual check for east
			if(zerosLeft != 0) {
				if(zerosLeft < Math.abs(visibleSkyscrapers - clue) - 1)
					return false;
			} else if(visibleSkyscrapers != clue)
				return false;
			
		}
		// ------------------------------------------------------------------------
		
		// SOUTH, thus the clue is in position south[col number]
		clue = clues.get("south")[col];
		
		if(clue != 0) { // checking if clue is present
			visibleSkyscrapers = maxHeight = zerosLeft = 0;
			for(int i = outputBoard.length - 1; i >= 0; i--) {
				if(outputBoard[i][col].getValue() == 0) {
					zerosLeft++;
				} else if(outputBoard[i][col].getValue() > maxHeight) {
					visibleSkyscrapers++;
					maxHeight = outputBoard[i][col].getValue();
				}
			}
			
			// actual check for south
			if(zerosLeft != 0) {
				if(zerosLeft < Math.abs(visibleSkyscrapers - clue) - 1)
					return false;
			} else if(visibleSkyscrapers != clue)
				return false;
			
			
		}
		// ------------------------------------------------------------------------
		
		// WEST, thus the clue is in position west[row number]
		clue = clues.get("west")[row];
		
		if(clue != 0) { // checking if clue is present
			visibleSkyscrapers = maxHeight = zerosLeft = 0;
			for(int i = 0; i < outputBoard.length; i++) {
				if(outputBoard[row][i].getValue() == 0) {
					zerosLeft++;
				} else if(outputBoard[row][i].getValue() > maxHeight) {
					visibleSkyscrapers++;
					maxHeight = outputBoard[row][i].getValue();
				}
			}
			
			// actual check for west
			if(zerosLeft != 0) {
				if(zerosLeft < Math.abs(visibleSkyscrapers - clue) - 1)
					return false;
			} else if(visibleSkyscrapers != clue)
				return false;
			
		}
		// ------------------------------------------------------------------------
		
		return true;
	}
	
	/*
	 * TODO test, unused
	 * a full check over all clues
	 */
	@SuppressWarnings("unused")
	private boolean checkClues() {
		String[] sides = {"north", "east", "south", "west"};
		for(int i = 0; i < 4; i++) {
			if(!checkSide(sides[i]))
				return false;
		}
		return true;
	}
	
	/*
	 * TODO test, probably useless
	 * check every clue on a side, called by checkClues()
	 */
	private boolean checkSide(String side) {
		
		int increment = side.matches("north|west") ? 1 : -1; // To know if I need to increase or decrease the row/col index
		boolean interateOverRow = side.matches("north|south"); // To know if I need to interate over row or col
		
		int from = increment > 0 ? 0 : outputBoard.length - 1;
		int to = increment > 0 ? outputBoard.length - 1 : 0;
		int clue, visibleSkyscrapers, maxHeight;
		
		for(int cIndex = 0; cIndex < outputBoard.length; cIndex++) {
			
			clue = clues.get(side)[cIndex];
			visibleSkyscrapers = maxHeight = 0;
			for(int i = from; i != to; i += increment) {
				
				if(outputBoard[interateOverRow ? i : cIndex][interateOverRow ? cIndex : i].getValue() > maxHeight) {
					visibleSkyscrapers++;
					maxHeight = outputBoard[interateOverRow ? i : cIndex][interateOverRow ? cIndex : i].getValue();
					if(visibleSkyscrapers > clue)
						return false;
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	@Override
	protected void assign(Integer option, Cell<Integer> point) {
		point.setValue(option);
	}
	
	@Override
	protected void unassign(Cell<Integer> point) {
		point.setValue(0);
	}
	
	@Override
	protected Cell<Integer> nextPoint(Cell<Integer> currentPoint) {
		
		int row = currentPoint.getPosition()[0];
		int col = currentPoint.getPosition()[1];
		if(col == outputBoard.length - 1) { // if col is the last one, go to next row
			row++;
			col = 0;
		} else {
			col++;
		}
		return outputBoard[row][col];
		
	}
	
	@Override
	protected Cell<Integer> firstPoint() {
		return (Cell<Integer>) outputBoard[0][0];
	}
	
	@Override
	protected Cell<Integer> lastPoint() {
		return (Cell<Integer>) outputBoard[outputBoard.length - 1][outputBoard.length - 1];
	}
	
	@Override
	protected boolean isLastPoint(Cell<Integer> point) {
		return (point.getPosition()[0] == outputBoard.length - 1 && point.getPosition()[1] == outputBoard[0].length - 1) ? true : false;
	}
	
	@Override
	protected void saveSolution() {
		
		Cell[][] toSave = new Cell[outputBoard.length][outputBoard.length];
		for(int i = 0; i < outputBoard.length; i++)
			for(int j = 0; j < outputBoard.length; j++)
				toSave[i][j] = new Cell(outputBoard[i][j]);
		
        Cell.printBoard(toSave); // TODO remove
        System.out.println("#########################"); // TODO remove
		solutionsList.add(toSave);
		if(solutionsList.size() == requestedSolutions)
			stop();
		
	}

	@Override
	protected boolean missingInputs() {
		
		if(this.outputBoard == null)
			return true;
		return false;
		
	}
	
}
