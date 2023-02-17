package com.giovannimenzano.skyscraperssolver.entities;

public class Cell<V> {
	
	private V value;
	private int[] position;
	private State state;
	
	public enum State {
		PREASSIGNED, ASSIGNED, NOT_ASSIGNED;
	}
	
	public Cell(V value, int[] position) { // full constructor
		this.value = value;
		this.position = position;
		this.state = (value == null || value.equals(0)) ? State.NOT_ASSIGNED : State.PREASSIGNED;
	}
	
	public Cell(Cell<V> cell) { // copy constructor
		this(cell.value, cell.position);
	}
	
	public Cell(V value, int position) {
		this(value, new int[] {position});
	}
	
	public Cell(V value) {
		this(value, null);
	}
	
	public Cell() {
		this(null, null);
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public boolean isStatePreassigned() {
		return this.state == State.PREASSIGNED ? true : false;
	}
	
	public boolean isStateAssigned() {
		return this.state == State.ASSIGNED ? true : false;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
		state = (value == null || value.equals(0)) ? State.NOT_ASSIGNED : State.ASSIGNED;
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public void setPosition(int[] position) {
		this.position = position;
	}
	
	public void setPosition(int position) {
		setPosition(new int[] {position});
	}
	
	/**
	Converts a 2D array of integers into a 2D array of Cell objects.
	Each Cell is created using the corresponding element in the integer array
	and the same i and j index coordinates.
	@param integerBoard the 2D integer array to be converted
	@return the 2D array of Cell objects
	*/
	public static Cell<Integer>[][] toCellArray(int[][] integerBoard) {
		
		Cell<Integer>[][] cellBoard = new Cell[integerBoard.length][integerBoard.length];
		
		for(int i = 0; i < integerBoard.length; i++)
			for(int j = 0; j < integerBoard.length; j++)
				cellBoard[i][j] = new Cell<Integer>(integerBoard[i][j], new int[] {i, j});
		
		return cellBoard;
		
	}
	
	/**
	Converts a 2D array of Cell into a 2D array of integers objects.
	Each place in integerBoard is filled with the value of the
	corresponding element (same array position) in the cellBoard array.
	@param board the 2D Cell array to be converted
	@return the 2D array of int values
	*/
	public static int[][] toIntArray(Cell<Integer>[][] cellBoard) {

		int[][] integerBoard = new int[cellBoard.length][cellBoard.length];
		
		for(int i = 0; i < cellBoard.length; i++)
			for(int j = 0; j < cellBoard.length; j++)
				integerBoard[i][j] = cellBoard[i][j].getValue();
		
		return integerBoard;
		
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Cell))
			return false;
		if(o == this)
			return true;
		Cell c = (Cell) o;
		return getValue().equals(c.getValue());
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	public static void printBoard(Cell<?>[][] solution) {
		
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
	
}
