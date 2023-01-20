const boardDimensionElement = document.querySelector("#board-dimension");
const requestedSolutionsElement = document.querySelector("#requested-solutions");
const currentSolutionElement = document.querySelector("#current-solution");
const solutionsFoundElement = document.querySelector("#solutions-found");

const gridElement = document.querySelector(".grid");
const solveButtonElement = document.querySelector("#solve-button");
const resetButtonElement = document.querySelector("#reset-button");
const previousButtonElement = document.querySelector("#previous-solution");
const nextButtonElement = document.querySelector("#next-solution");

const cornerCellHTML = '<div class="cell cell--corner"></div>';
const frameCellHTML = '<div class="cell cell--frame"><input type="number" value="0"></div>';
const boardCellHTML = '<div class="cell cell--input"><input type="number" value="0"></div>';

const host = "localhost:8090";

let solutions = [];
let currentSolutionIndex = 0;

boardDimensionElement.addEventListener("change", () => {
	debugger;
	let newGrid = '';
	const dimension = parseInt(boardDimensionElement.value) + 2;

	// create the new grid HTML
	for (let i = 0; i < dimension; i++) {debugger;
		for (let j = 0; j < dimension; j++) {

			if ((i === 0 && j === 0) || (i === 0 && j === dimension - 1) || (i === dimension - 1 && j === 0) || (i === dimension - 1 && j === dimension - 1)) {
				newGrid += cornerCellHTML;
			} else if (i === 0 || i === dimension - 1 || j === 0 || j === dimension - 1) {
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

}, false);

// event listener for cell inputs
gridElement.addEventListener("input", (e) => {

	// listener is added to the grid, but the target are the input cells
    if (e.target.matches("input")) {

		// the max number the user can type is the grid dimension minus two, corresponding to the game board dimension
		const maxInput = parseInt(boardDimensionElement.value);
        // delete every character not matching a number between 1 and the max number for the selected board
		e.target.value = e.target.value.replace(new RegExp("[^1-" + maxInput + "]", "g"),"");

		// force user to insert only one character
		if (e.target.value.length > 1) {
            e.target.value = e.target.value.slice(1, 2);
        }

    }

}, true);

// event listener for solve button
solveButtonElement.addEventListener("click", async () => {

	/*
	convert grid to array
	if cell has no input (corner cell) or cell input value is null, return 0
	*/
	// const inputBoard = Array.from(gridElement.querySelectorAll(".cell"), cell => {
	// 	if (cell.input && cell.input.value) {
	// 		return cell.input.value;
	// 	} else {
	// 		return 0;
	// 	}
	// });

	// one line version of the code above
	const grid = Array.from(gridElement.querySelectorAll(".cell"), cell => (cell.firstChild && cell.firstChild.valueAsNumber) || 0);

	const inputBoard = [];
	while (grid.length) {
		inputBoard.push(grid.splice(0, parseInt(boardDimensionElement.value) + 2));
	}
	const requestJson = JSON.stringify({ inputBoard: inputBoard }); debugger;
	// get requested solutions number
	const requestedSolutions = requestedSolutionsElement.value;

	// make the REST call
	// const response = await fetch("http://" + host + "/solvers/skyscrapers/solve?requestedSolutions=" + requestedSolutions, {
	// 	method: "POST",
	// 	body: JSON.stringify({ inputBoard: inputBoard }),
	// 	headers: { "Content-Type": "application/json" },
	// });
	// solutions = await response.json();
	debugger;

	// update solution count and current solution
	solutionsFoundElement.innerHTML = solutions.length;
	currentSolutionElement.innerHTML = currentSolutionIndex + 1;

	// fill the grid with the first solution
	fillGrid(solutions[currentSolutionIndex]);

});

resetButtonElement.addEventListener("click", () => {

	// TODO

});

previousButtonElement.addEventListener("click", () => {

	currentSolutionIndex = currentSolutionIndex > 0 ? currentSolutionIndex - 1 : solutions.length - 1;
	currentSolution.innerHTML = currentSolutionIndex + 1;
	fillGrid(solutions[currentSolutionIndex]);

});

nextButtonElement.addEventListener("click", () => {

	currentSolutionIndex = currentSolutionIndex < solutions.length - 1 ? currentSolutionIndex + 1 : 0;
	currentSolution.innerHTML = currentSolutionIndex + 1;
	fillGrid(solutions[currentSolutionIndex]);

});

// helper function to fill the grid
function fillGrid(solution) {
	solutions.forEach((value, index) => {
		grid.querySelectorAll(".cell")[index].value = value;
	});
}

function escapeRegex(regex) {
    return regex.replace(/[-[\]{}()*+!<=:?.\/\\^$|#\s,]/g, '\\$&'); // full version
	// return string.replace(/[/\-\\^$*+?.()|[\]{}]/g, '\\$&'); // short version
}