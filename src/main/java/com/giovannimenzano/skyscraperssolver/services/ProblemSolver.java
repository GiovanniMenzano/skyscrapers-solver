package com.giovannimenzano.skyscraperssolver.services;

import java.util.Collections;
import java.util.List;

import com.giovannimenzano.skyscraperssolver.exceptions.CustomException;

/*
 * abstract class providing a template method suitable for a generic problem solvable with
 * a bruteforce method.
 * It tries solutions (possible permutation of a single part of the problem) from a pool,
 * one by one, and saves all solved configurations.
 * A problem could be for example Sudoku, Futoshiki, Skyscrapers, etc.
 */
public abstract class ProblemSolver<P, S> {
	
	protected List<S> optionsList;
	protected List<P[][]> solutionsList;
	protected int requestedSolutions;
	protected boolean stop;
	
	/*
	 * entry point, method used to start the computation from the first point or part of the problem
	 */
	public void start() throws CustomException {
		
		checkInputData();
		this.stop = false;
		solve(firstPoint());
		
	}

	/*
	 * method used to stop solve method (e.g. if all wanted solutions are found)
	 * Its state is changed most likely by saveSolutions(), but it's sub class responsibility to do so
	 */
	public void stop() {
		this.stop = true;
	}

	/*
	 * template method
	 */
	public void solve(P point) throws CustomException {
		
		for(S option : optionsList) {
			
			/*
			 * exit point for the recursive function in case of a defined stop condition. 
			 * It could be used to stop recursion if a the number of solutions to be found is reached.
			 * Or by a timeout function (not yet implemented).
			 */
			if(stop) return;
			
			/*
			 * procedure:
			 * 1) assign value;
			 * 2) check validity and continue to next point or save solution;
			 * 3) unassign value, before next iteration.
			 */
			assign(option, point);
			
			if(isValidOption(option, point)) {
				if(isLastPoint(point)) {
					saveSolution();
				} else {
					solve(nextPoint(point));
				}
			}
			
			unassign(point);
			
		}
		
	}

	public boolean isStopped() {
		return stop;
	}
	
	public List<P[][]> getSolutionsList() {
		Collections.shuffle(solutionsList);
		return solutionsList;
	}
	
	public int getRequestedSolutions() {
		return requestedSolutions;
	}

	public void setRequestedSolutions(int requestedSolutions) {
		this.requestedSolutions = requestedSolutions;
	}
	
	protected abstract void assign(S option, P point);
	
	protected abstract void unassign(P point);
	
	protected abstract boolean isValidOption(S option, P point);
	
	protected abstract P nextPoint(P currentPoint);
	
	protected abstract P firstPoint();
	
	protected abstract P lastPoint();
	
	protected abstract boolean isLastPoint(P point);
	
	protected abstract void saveSolution();
	
	protected abstract void checkInputData();
	
	protected abstract void initialize();
}
