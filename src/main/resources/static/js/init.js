const boardDimensionElement = document.querySelector("#board-dimension");
const requestedSolutionsElement = document.querySelector("#requested-solutions");
const currentSolutionElement = document.querySelector("#current-solution");
const solutionsFoundElement = document.querySelector("#solutions-found");

const gridElement = document.querySelector(".grid");
const previousButtonElement = document.querySelector("#previous-solution");
const nextButtonElement = document.querySelector("#next-solution");
const solveButtonElement = document.querySelector("#solve-button");
const resetButtonElement = document.querySelector("#reset-button");
const exampleButtonElement = document.querySelector("#example-button");

const cornerCellHTML = '<div class="cell cell--corner"><input type="number" value="0"></div>';
const frameCellHTML = '<div class="cell cell--frame"><input type="number" value="0"></div>';
const boardCellHTML = '<div class="cell cell--board"><input type="number" value="0"></div>';
const exampleValues =
	[[0, 4, 2, 1, 2, 3, 0],
	[3, 0, 0, 0, 0, 0, 3],
	[2, 0, 0, 0, 0, 0, 4],
	[3, 0, 0, 0, 0, 0, 1],
	[2, 0, 0, 0, 0, 0, 2],
	[1, 0, 0, 0, 0, 0, 2],
	[0, 1, 4, 3, 2, 2, 0]];

const PROTOCOL = "http://";
const HOST = "127.0.0.1:8090";
const ENDPOINT = "/solvers/skyscrapers/solve";
const REQUESTED_SOLUTION_PARAMETER = "requestedSolutions=";

let solutions = [];
let currentSolutionIndex = 0;
const NEXT_SOLUTION = 1;
const PREVIOUS_SOLUTION = -1;

document.addEventListener("keydown", (event) => {

	switch(event.key) {
		case "ArrowLeft":
			event.preventDefault();
			showSolution(PREVIOUS_SOLUTION);
			break;
		case "ArrowRight":
			event.preventDefault();
			showSolution(NEXT_SOLUTION);
			break;
	}

});

boardDimensionElement.addEventListener("change", () => {

	resetSolutions();
	changeGridDimension(parseInt(boardDimensionElement.value) + 2);

});

requestedSolutionsElement.addEventListener("input", () => {

	const limit = 1000;
	let value = parseInt(requestedSolutionsElement.value);

	if(!isNaN(value)) {
		value = Math.min(value, limit);
		requestedSolutionsElement.value = value;
	} else {
		requestedSolutionsElement.value = "";
	}

});

// event listener for cell inputs
gridElement.addEventListener("input", (e) => {

	// listener has been linked to the grid, but the target are the input cells
	if(e.target.matches("input")) {

		// the limit number the user can type is the same as the game board dimension, like sudoku
		const limit = parseInt(boardDimensionElement.value);
		// delete every character not matching a number between 1 and the limit
		e.target.value = e.target.value.replace(new RegExp("[^1-" + limit + "]", "g"), "");

		// force user to insert only one character
		if(e.target.value.length > 1) {
			e.target.value = e.target.value.slice(1, 2);
		}

	}

}, true);

previousButtonElement.addEventListener("click", () => {

	showSolution(PREVIOUS_SOLUTION);

});

nextButtonElement.addEventListener("click", () => {

	showSolution(NEXT_SOLUTION);

});

// event listener for solve button
solveButtonElement.addEventListener("click", async () => {

	/*
	convert grid to array
	if cell has no input (corner cell) or cell input value is null, return 0
	*/
	/*const inputBoard = Array.from(gridElement.querySelectorAll(".cell"), cell => {
		if (cell.input && cell.input.value) {
			return cell.input.value;
		} else {
			return 0;
		}
	});*/

	// one line version of the code above
	const grid = Array.from(gridElement.querySelectorAll(".cell"), cell => (cell.firstChild && cell.firstChild.valueAsNumber) || 0);

	const inputBoard = [];
	while(grid.length) {
		inputBoard.push(grid.splice(0, parseInt(boardDimensionElement.value) + 2));
	}

	// get requested solutions number
	const requestedSolutions = requestedSolutionsElement.value;

	// get a timestamp to add to the request, in order to make different one everytime and prevent caching
	const timestamp = Date.now();

	// make the REST call
	try {

		let response = await fetch(PROTOCOL + HOST + ENDPOINT + "?" + REQUESTED_SOLUTION_PARAMETER + requestedSolutions + "&t=" + timestamp, {
			method: "POST",
			body: JSON.stringify({ inputBoard: inputBoard }),
			cache: 'no-store',
			headers: {
				"content-type": "application/json",
				"cache-control": "no-store", // headers to prevent caching
				"pragma": "no-cache", // deprecated but I was told to add it anyway by some really competent people, A.K.A. StackOverflow
				"expires": "0"
			}
		});

		if(response.status !== 200) { // can also be !response.ok to be more generic
			throw new Error(response.message + " : " + response.statusText);
		} // TODO check for specific error codes

		let jsonResponse = await response.json();
		solutions = jsonResponse.result;

		// update solution count and current solution
		solutionsFoundElement.innerHTML = solutions.length;
		currentSolutionElement.innerHTML = 1;
		currentSolutionIndex = 0;

		// fill the grid with the first solution
		fillGrid(solutions[0], true);

	} catch(error) {

		console.error("Error: " + error);
		alert("Error: " + error);

	}

});

resetButtonElement.addEventListener("click", () => {

	resetSolutions();
	gridElement.querySelectorAll(".cell input").forEach(input => {
		input.value = 0;
	});

});

exampleButtonElement.addEventListener("click", () => {

	boardDimensionElement.value = 5;
	changeGridDimension(7);
	fillGrid(exampleValues, false);

});

function showSolution(direction) {

	if(solutions.length > 1) {

		if(direction < 0 ) {
			currentSolutionIndex = currentSolutionIndex > 0 ? currentSolutionIndex - 1 : solutions.length - 1;
		} else if(direction > 0) {
			currentSolutionIndex = currentSolutionIndex < solutions.length - 1 ? currentSolutionIndex + 1 : 0;
		}

		currentSolutionElement.innerHTML = currentSolutionIndex + 1;
		fillGrid(solutions[currentSolutionIndex], true);

	}

}

function resetSolutions() {

	solutions = [];
	currentSolutionIndex = 0;
	solutionsFoundElement.innerHTML = 0;
	currentSolutionElement.innerHTML = 0;

}

function changeGridDimension(dimension) {

	let newGrid = "";

	// create the new grid HTML
	for(let i = 0; i < dimension; i++) {
		for(let j = 0; j < dimension; j++) {

			if((i === 0 && j === 0) || (i === 0 && j === dimension - 1) || (i === dimension - 1 && j === 0) || (i === dimension - 1 && j === dimension - 1)) {
				newGrid += cornerCellHTML;
			} else if(i === 0 || i === dimension - 1 || j === 0 || j === dimension - 1) {
				newGrid += frameCellHTML;
			} else {
				newGrid += boardCellHTML;
			}

		}
	}

	// update the grid's innerHTML with the new grid
	gridElement.innerHTML = newGrid;
	// change the grid css for columns and rows, this will be inline, so it will have priority over the css file
	gridElement.style["grid-template-columns"] = "repeat(" + dimension + ", 1fr)";
	gridElement.style["grid-template-rows"] = "repeat(" + dimension + ", 1fr)";

}

function fillGrid(values, noFrame) {

	const parentElement = noFrame ? ".cell--board" : ".cell";
	let cells = gridElement.querySelectorAll(parentElement + " input");
	let cellsIndex = 0;

	for(let i = 0; i < values.length; i++) {
		for(let j = 0; j < values[i].length; j++) {
			cells[cellsIndex].value = values[i][j];
			cellsIndex++;
		}
	}

}

function hasDuplicateSolutions(arr) {

	const set = new Set();

	for(let i = 0; i < arr.length; i++) {

		const str = JSON.stringify(arr[i]);
		if(set.has(str)) {
			console.log("duplicate: \n" + str);
			return true;
		}
		set.add(str);

	}

	return false;

}

function escapeRegex(regex) {
	return regex.replace(/[-[\]{}()*+!<=:?.\/\\^$|#\s,]/g, '\\$&'); // full version
	// return string.replace(/[/\-\\^$*+?.()|[\]{}]/g, '\\$&'); // short version
}
