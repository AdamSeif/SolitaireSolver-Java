# DomSolitaire Game

This Java program implements a solitaire game called DomSolitaire. It consists of two classes, `Domino` and `DomSolitaire`, along with a supporting class `Move`.

## Domino Class

### Attributes
- `high`: The higher number on the domino.
- `low`: The lower number on the domino.
- `id`: Unique identifier for each domino.
- `serialID`: Static variable used to generate unique IDs for each domino.

### Methods
- `Domino(int high, int low)`: Constructor for creating a domino with given high and low numbers.
- `getHigh()`: Returns the high number of the domino.
- `getLow()`: Returns the low number of the domino.
- `toString()`: Returns a string representation of the domino.
- `hasCommonNumber(Domino other)`: Checks if the current domino shares a number with another domino.
- `isOneUp(Domino other)`: Checks if the current domino is one up from another domino.
- `isOneDown(Domino other)`: Checks if the current domino is one down from another domino.
- `getName()`: Returns a single character representing the name of the domino.
- `showNamedContent()`: Returns a string combining the name and string representation of the domino.
- `getSet(int highest)`: Static method to generate a complete set of dominoes up to a specified highest number.
- `shuffle(Domino[] set, int seed)`: Static method to shuffle an array of dominoes using a seed.

## DomSolitaire Class

### Attributes
- `foundation`: Array of stacks representing the foundation piles.
- `stack`: Array of stacks representing the regular stacks.
- `name`: Name of the game instance.
- `debug`: Boolean flag indicating whether debug mode is enabled.

### Methods
- `DomSolitaire(int highestNum, int seed, String name)`: Constructor to initialize the game with given parameters.
- `getName()`: Returns the name of the game.
- `setDebug(boolean debug)`: Sets the debug mode.
- `reset(int seed)`: Resets the game with a new seed.
- `winner()`: Checks if the game has been won.
- `findSFMoves()`: Finds all legal moves involving moving a domino to the foundation.
- `findSESMoves()`: Finds all legal moves involving moving a domino to an empty stack.
- `findSSMoves()`: Finds all legal moves involving moving a domino from one regular stack to another.
- `findMoves(StackADT<Move> st)`: Finds all legal moves and adds them to the given stack.
- `createMove(String from, String to)`: Creates a move from one stack to another based on provided indices.
- `toString()`: Returns a string representation of the game state.
- `showNamedContent()`: Returns a string combining the content of all stacks.
- `findSolution(int maxSteps)`: Attempts to find a solution to the game within a maximum number of steps.
  
## Move Class

### Attributes
- `from`: Stack where the domino is moved from.
- `to`: Stack where the domino is moved to.
- `completed`: Boolean flag indicating whether the move is completed.
- `name`: Name of the move.

### Methods
- `Move(StackADT<Domino> from, StackADT<Domino> to)`: Constructor to create a move between stacks.
- `doIt()`: Performs the move.
- `undoIt()`: Undoes the move.
- `isCompleted()`: Checks if the move is completed.
- `equals(Object obj)`: Checks if the move is equal to another move.
- `toString()`: Returns a string representation of the move.
