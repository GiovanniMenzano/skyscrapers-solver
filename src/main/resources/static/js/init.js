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

const protocol = "http://";
const host = "127.0.0.1:8090";
const endpoint = "/solvers/skyscrapers/solve";
const requestedSolutionsParameter = "requestedSolutions=";

let solutions = [];
let currentSolutionIndex = 0;

boardDimensionElement.addEventListener("change", () => {

	resetSolutions();
	const dimension = parseInt(boardDimensionElement.value) + 2;
	changeGridDimension(dimension);

});

// event listener for cell inputs
gridElement.addEventListener("input", (e) => {

	// listener is added to the grid, but the target are the input cells
	if(e.target.matches("input")) {

		// the max number the user can type is the grid dimension minus two, corresponding to the game board dimension
		const maxInput = parseInt(boardDimensionElement.value);
		// delete every character not matching a number between 1 and the max number for the selected board
		e.target.value = e.target.value.replace(new RegExp("[^1-" + maxInput + "]", "g"), "");

		// force user to insert only one character
		if(e.target.value.length > 1) {
			e.target.value = e.target.value.slice(1, 2);
		}

	}

}, true);

previousButtonElement.addEventListener("click", () => {

	if(solutions.length > 0) {
		currentSolutionIndex = currentSolutionIndex > 0 ? currentSolutionIndex - 1 : solutions.length - 1;
		currentSolutionElement.innerHTML = currentSolutionIndex + 1;
		fillGrid(solutions[currentSolutionIndex], true);
	}

});

nextButtonElement.addEventListener("click", () => {

	if(solutions.length > 0) {
		currentSolutionIndex = currentSolutionIndex < solutions.length - 1 ? currentSolutionIndex + 1 : 0;
		currentSolutionElement.innerHTML = currentSolutionIndex + 1;
		fillGrid(solutions[currentSolutionIndex], true);
	}

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
		debugger;
		let response = await fetch(protocol + host + endpoint + "?" + requestedSolutionsParameter + requestedSolutions + "&t=" + timestamp, {
			method: "POST",
			body: JSON.stringify({ inputBoard: inputBoard }),
			cache: 'no-store',
			headers: {
				"content-type": "application/json",
				"cache-control": "no-store", // headers to prevent caching
				"pragma": "no-cache", // deprecated but I was told to add it anyway by some really competent people *stackoverflow AHEM*
				"expires": "0"
			}
		});
		debugger;
		if(response.status !== 200) { // can also be !response.ok to be more generic
			throw new Error(response.message + " : " + response.statusText); // TODO test using response.statusText
		} // TODO check for specific error codes

		let jsonResponse = await response.json();
		solutions = jsonResponse.result;

		// update solution count and current solution
		solutionsFoundElement.innerHTML = solutions.length;
		currentSolutionElement.innerHTML = currentSolutionIndex + 1;

		// fill the grid with the first solution
		fillGrid(solutions[currentSolutionIndex], true);

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

function resetSolutions() {

	solutions = [];
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

function escapeRegex(regex) {
	return regex.replace(/[-[\]{}()*+!<=:?.\/\\^$|#\s,]/g, '\\$&'); // full version
	// return string.replace(/[/\-\\^$*+?.()|[\]{}]/g, '\\$&'); // short version
}
