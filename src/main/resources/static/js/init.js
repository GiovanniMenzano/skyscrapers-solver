/* 
get the grid, requested solution and solve button to start the rest call,
previous and next button to cycle through possible solutions,
and the other necessary elements
*/

const gridElement = document.querySelector(".grid");
const requestedSolutionsElement = document.querySelector("#requested-solutions");
const solveButtonElement = document.querySelector("#solve-button");
const resetButtonElement = document.querySelector("#reset-button");
const currentSolutionElement = document.querySelector("#current-solution");
const solutionsFoundElement = document.querySelector("#solutions-found");
const previousButtonElement = document.querySelector("#previous-solution");
const nextButtonElement = document.querySelector("#next-solution");

const host = "localhost:8090";

let gridDimension = Math.sqrt(gridElement.querySelectorAll(".cell").length);
let solutions = [];
let currentSolutionIndex = 0;

document.querySelector("#solver-form").addEventListener("submit", function(event) {

	console.log("form submit event received");

	// document.getElementById("submit-button").setAttribute("disabled", true); // since I'm making the call to the solver service, I disable the submit button
	
	event.preventDefault(); // allow customized form submit via event listener on solve button

});

// event listener for solve button
solveButtonElement.addEventListener("click", async () => {

	/*
	convert grid to array
	if cell has no input (angle cell) or cell input value is null, return 0
	*/
	debugger;
	// const inputBoard = Array.from(gridElement.querySelectorAll(".cell"), cell => {
	// 	if (cell.input && cell.input.value) {
	// 		return cell.input.value;
	// 	} else {
	// 		return 0;
	// 	}
	// });
	
	// one line version of the code above
	const grid = Array.from(gridElement.querySelectorAll(".cell"), cell => (cell.firstChild && cell.firstChild.value) || '0');
	
	const inputBoard = [];
	while (grid.length) {
		inputBoard.push(grid.splice(0, gridDimension));
	}
	const requestJson = JSON.stringify({ inputBoard: inputBoard })
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
