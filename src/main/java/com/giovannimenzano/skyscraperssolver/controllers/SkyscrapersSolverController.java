package com.giovannimenzano.skyscraperssolver.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.giovannimenzano.skyscraperssolver.entities.*;
import com.giovannimenzano.skyscraperssolver.exceptions.*;
import com.giovannimenzano.skyscraperssolver.requests.InputBoardRequest;
import com.giovannimenzano.skyscraperssolver.responses.CustomResponse;
import com.giovannimenzano.skyscraperssolver.services.*;
import com.giovannimenzano.skyscraperssolver.utils.*;

/*
example on how to call this controller

POST
localhost:8090/solvers/skyscrapers/solve?solutions=1

BODY
{
    "inputBoard": [
        [0, 4, 2, 1, 2, 3, 0],
        [3, 0, 0, 0, 0, 0, 3],
        [2, 0, 0, 0, 0, 0, 4],
        [3, 0, 0, 0, 0, 0, 1],
        [2, 0, 0, 0, 0, 0, 2],
        [1, 0, 0, 0, 0, 0, 2],
        [0, 1, 4, 3, 2, 2, 0]
    ]
}
*/

@RestController
@RequestMapping("/skyscrapers")
public class SkyscrapersSolverController {
    
    @Autowired
    private SkyscrapersSolverService solverService;
    
    @PostMapping("/solve")
    public ResponseEntity<CustomResponse<?>> solve(@RequestParam("solutions") int requestedSolutions, @RequestBody InputBoardRequest inputBoardRequest) {
    	
        // set the input grid and the requested number of solutions in the service
    	solverService.setInputBoard(Cell.toCellArray(inputBoardRequest.getInputBoard()));
        solverService.setRequestedSolutions(requestedSolutions);
        
        // start the solving process
        try {
			solverService.start();
		} catch (CustomException e) {
			// return a response with an error status and message to the frontend
	        return ResponseUtils.buildFailedOperationResponse(e);
		}
        
        // returns the list of solutions found
        return ResponseUtils.buildSuccessOperationResponse(solverService.getSolutionsList().stream().map(Cell::toIntArray).collect(Collectors.toList()));
        
    }
    
}
