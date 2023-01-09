
# Skyscrapers solver

Bruteforce solver for the skyscrapers puzzle, a game similar to sudoku. It was originally developed as a project for a university exam and later transformed into a REST service using Spring.
The service exposes a POST endpoint that accepts as input a game grid, that could also be partially solved, and the requested number of solutions. The input grid also includes the game clues.
At the bottom an example of how to call the service.
The heart of the project is a recursive backtracking algorithm, written using the template method pattern. Backtracking is a problem-solving method that consists of trying to find a solution by proceeding step by step, going back when a dead end is encountered. In this case, the algorithm tries to insert the numbers into the game grid one by one, verifying at each step that the solution is still valid. If a point is reached where it is no longer possible to proceed, it goes back and changes the previously inserted value to continue looking for valid solutions.
About the template method pattern, it's developer to define the general structure of the puzzle solving process, in a way that can be easily extended and reused, by implementing only the steps that are specific to the current problem.
The service returns a list of found solutions to the client, which can be used to display the results of the puzzle solving.
A GUI implementation is planned through a web page using HTML, CSS, and JavaScript for user interaction.

Risolutore bruteforce per il gioco dei grattacieli, un puzzle simile al sudoku. È stato originariamente sviluppato come progetto per un esame universitario e successivamente trasformato in un servizio REST utilizzando Spring.
Il servizio espone un endpoint POST che accetta come input una griglia di gioco, anche parzialmente risolta, e il numero richiesto di soluzioni. La griglia in input include anche i vincoli del gioco.
In basso un esempio di come effettuare la chiamata REST.
Il cuore del progetto è un algoritmo di backtracking ricorsivo, scritto tramite pattern template method.
Il backtracking è un metodo di risoluzione di problemi che consiste nel tentativo di trovare una soluzione procedendo passo dopo passo, tornando indietro quando si incontra un vicolo cieco. In questo caso, l'algoritmo prova a inserire i numeri nella griglia di gioco uno per uno, verificando in ogni passo che la soluzione sia ancora valida. Se si arriva a un punto in cui non è più possibile proseguire, si torna indietro e si cambia il valore inserito in precedenza per continuare a cercare soluzioni valide.
Riguardo il pattern template method, questo è stato sviluppato per definire la struttura generale del processo di risoluzione del puzzle, in modo tale da poterlo riutilizzare implementando solo gli step che sono specifici del problema attuale.
Il servizio restituisce una lista di soluzioni trovate al client, che può essere utilizzata per visualizzare i risultati della risoluzione del puzzle.
È prevista un'implementazione della GUI tramite pagina web utilizzando HTML, CSS e JavaScript per l'interazione con l'utente.

```bash
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
```
## API Reference

### Get all items

```http
POST /solvers/skyscrapers/solve
```

| Parameter   | Type      | Description                       |
| :---------- | :-------- | :-------------------------------- |
| `solutions` | `integer` | the number of requested solutions |
