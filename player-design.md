Design for User-Players and Game Interaction:

In the future, we need to incorporate active user input from players or AI playing our game so that we will create a
UserPlayer interface to decide what to do based on the model's state.

What is a UserPlayer?
- A UserPlayer represents a human player who clicks on our GUI or inputs commands, or an AI player that automatically
  decides what actions to take.
  - A human player would be called HumanPlayer, and an AI player would be called AIPlayer
- It should be initialized with a PlayerColor (either RED or BLUE).
- As for a UserPlayer's actions, it should only be able to make game moves when prompted.
- In theory, we would have two implementations of this interface that choose actions differently depending on whether
  they are a human or an AI.

What can a UserPlayer do?
- A UserPlayer should be able to view a snapshot of the game state at any moment.
- Decide on whether to place a card or pass their turn.
- When deciding to take their turn, they should also have a game board coordinate in mind
- Somehow tell the model what game move they decided.
- A UserPlayer can not change the model's internal data.

What methods will UserPlayer have?
- chooseMove() method that takes in one of the two moves in the game.
  - This method would return a move
     - With this design, we would need to create a Move object that can either be a play or a pass.
  - chooseMove() would potentially take in a view so that the UserPlayer can proceed depending on the game state.
     - This view must be a copy because it must not be editable by the UserPlayer to combat cheating.

What happens after a UserPlayer chooses a move?
- The Sanguine Game Model will take over and ensure their move is valid, and if so, execute their desired move.

How we envision instantiating UserPlayer and the Model (Human Player Example):

SanguineModel model = new BasicSanguineModel();
List<SanguineCard> deck1 = model.createDeck();
List<SanguineCard> deck2 = model.createDeck();
UserPlayer redPlayer = new HumanPlayer(PlayerColor.RED);
UserPlayer bluePlayer = new HumanPlayer(PlayerColor.BLUE);
model.startGame(3, 5, deck1, deck2, 3);






