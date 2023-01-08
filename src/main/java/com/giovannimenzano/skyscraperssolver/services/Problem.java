package com.giovannimenzano.skyscraperssolver.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.giovannimenzano.skyscraperssolver.exceptions.CustomException;
import com.giovannimenzano.skyscraperssolver.exceptions.MissingInputsExceptions;
import com.giovannimenzano.skyscraperssolver.utils.ExceptionUtils;

/*
 * abstract class providing a template method suitable for a generic problem solvable with
 * a bruteforce method.
 * It tries solutions (possible permutation of a single part of the problem) from a pool,
 * one by one, and saves all solved configurations.
 * A problem could be for example Sudoku, Futoshiki, Skyscrapers, etc.
 */

public abstract class Problem<P, S> {
	
	/*
	 * variable stop is used to stop template method (e.g. if all wanted solutions are found)
	 * Its state is changed most likely by saveSolutions(), but it's sub class responsibility to do so
	 */
	protected boolean stop = false;
	protected List<S> optionsList = new ArrayList<S>();
	/*
	 * TODO only solution I found for not getting instantiation errors in the concrete class
	 * TODO add generic type
	 */
	protected List<P[][]> solutionsList;
	
	/*
	 * entry point, method used to start the computation from the first point or part of the problem
	 */
	public void start() throws CustomException {
		solve(firstPoint());
	}

	/*
	 * template method
	 */
	public void solve(P point) throws CustomException {
		
		if(missingInputs()) throw new CustomException(ExceptionUtils.MISSING_INPUTS);
		
		for(S option : optionsList) {
			
			if(stop) return; // exit point for the recursive function in case of a defined stop condition. It could be used to stop recursion if a the number of solutions to be found is reached.
			
			/*
			 * Procedure:
			 * 1) assign value
			 * 2) check validity and continue to next point or save solution
			 * 3) deassign value in any case, before next iteration
			 */
			assign(option, point);
			
			if(isValidOption(option, point)) {
				if(isLastPoint(point)) {
					saveSolution();
				} else {
					solve(nextPoint(point));
				}
			}
			
			deassign(point);
			
		}
		
	}
	
	public List<P[][]> getSolutionsList() {
		Collections.shuffle(solutionsList);
		return solutionsList;
	}

	public boolean isStopped() {
		return stop;
	}
	
	public void stop() {
		stop = true;
	}
	
	protected abstract void assign(S option, P point);
	
	protected abstract void deassign(P point);
	
	protected abstract boolean isValidOption(S option, P point);
	
	protected abstract P nextPoint(P currentPoint);
	
	protected abstract P firstPoint();
	
	protected abstract P lastPoint();
	
	protected abstract boolean isLastPoint(P point);
	
	protected abstract void saveSolution();
	
	protected abstract boolean missingInputs();
}
