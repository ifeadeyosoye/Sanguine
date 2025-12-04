package sanguine.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Basic implementation of SanguineModel that takes in type SanguineGame Card.
 *
 * <p>Class defines the board, players, max player hand size,</p>
 * whether a game is started and the current player.
 *
 * <p>Client should not be able to start game without calling the startGame method</p>
 * The class constructor aids in setting up class to call startGame
 *
 * <p>Class adds helper methods that remove redundant code like:</p>
 * Seeing if the game has started
 * Getting influence coordinates
 * Influencing one cell
 * etc...
 */
public class BasicSanguineModel implements SanguineModel<SanguineCard>, ModelControllerPublisher {

  private SanguineGameBoard board;
  private boolean gameStarted; // if the game has been started at one point in time
  // INVARIANT: consecutivePasses is always >= to zero.
  private int consecutivePasses;
  private SanguinePlayer currentPlayer; // starts as red player
  private SanguinePlayer redPlayer;
  private SanguinePlayer bluePlayer;
  // INVARIANT: maxHandSize is always > than zero.
  private int maxHandSize;
  private List<ModelListener> listeners;

  /**
   * construcotr for basic sanguine model. initializes variables that are not passed into the
   * start game method.
   */
  public BasicSanguineModel() {
    gameStarted = false;
    consecutivePasses = 0;
    this.listeners = new ArrayList<>();
  }


  @Override
  public SanguineGameBoard getBoard() {
    return board.getBoard();
  }

  @Override
  public void startGame(int rows, int cols, List<SanguineCard> deck1,
                        List<SanguineCard> deck2, int handSize)
      throws IllegalArgumentException, IllegalStateException {

    if (gameStarted) {
      throw new IllegalStateException("Game has already been started!");
    }

    // dimension validity should be checked in SanguineGame board
    board = new SanguineGameBoard(rows, cols);

    // If decks are empty or null, we give the player a default deck
    Result checkedNullDecks = handleNullDecks(deck1, deck2);

    // check deck size validity
    checkDeckSize(checkedNullDecks.deck1(), checkedNullDecks.deck2());

    // CHECKING FOR MORE THAN 2 OF THE SAME CARDS IN DECK PARSER AND THROW IAE
    DeckParser.checkValidDeck(checkedNullDecks.deck1());
    DeckParser.checkValidDeck(checkedNullDecks.deck2());

    // check hand size validity
    if (handSize > checkedNullDecks.deck1().size() / 3
        || handSize > checkedNullDecks.deck2().size() / 3 || handSize <= 0) {
      throw new IllegalArgumentException("Hand size is invalid");
    }

    // Set players
    this.redPlayer = new SanguinePlayer(checkedNullDecks.deck1(), PlayerColor.RED, handSize);
    this.bluePlayer = new SanguinePlayer(checkedNullDecks.deck2(), PlayerColor.BLUE, handSize);

    // shuffle their decks for randomized hand
    redPlayer.shuffle();
    bluePlayer.shuffle();

    // takes handSize amount of cards from each player's deck to hand
    makePlayerHands(handSize);

    // placing pawns for start of game
    placePawns();
    currentPlayer = redPlayer;
    this.maxHandSize = handSize;
    gameStarted = true;
  }

  /**
   * private helper method that checks if two decks are null. if theyre null, it makes default
   * decks.
   *
   * @param deck1 first to be checked
   * @param deck2 second to be checked.
   *
   * @return a Result
   */
  private Result handleNullDecks(List<SanguineCard> deck1, List<SanguineCard> deck2) {
    if (deck1 == null || deck1.isEmpty()) {
      try {
        deck1 = this.createDeck();
      } catch (IOException exo) {
        throw new IllegalStateException("Parsing file for deck 1 failed!");
      }
    }

    if (deck2 == null || deck2.isEmpty()) {
      try {
        deck2 = this.createDeck();
      } catch (IOException exo) {
        throw new IllegalStateException("Parsing file for deck 1 failed!");
      }
    }
    return new Result(deck1, deck2);
  }

  private record Result(List<SanguineCard> deck1, List<SanguineCard> deck2) {
  }

  /**
   * private helper method that draws cards for player. this is to make startgame method shorter.
   *
   * @param handSize the amount of cards to be drawn.
   */
  private void makePlayerHands(int handSize) {
    redPlayer.deckToHand(handSize);
    bluePlayer.deckToHand(handSize);
  }

  /**
   * This is a private helper method that places pawns at the leftmost and rightmost columns of
   * the board.
   */
  private void placePawns() {
    for (int row = 0; row < board.getRows(); row++) {
      board.addPawnToCell(row, 0, redPlayer);
    }

    for (int row = 0; row < board.getRows(); row++) {
      board.addPawnToCell(row, board.getCols() - 1, bluePlayer);
    }
  }

  /**
   * This is a private method that checks the sizes of given decks for validity.
   *
   * @param deck1 first deck to be checked
   * @param deck2 second deck to be checked
   */
  private void checkDeckSize(List<SanguineCard> deck1, List<SanguineCard> deck2) {
    if (deck1.size() < board.getBoardSize()) {
      throw new IllegalArgumentException("Deck 1 is invalid!");
    }

    if (deck2.size() < board.getBoardSize()) {
      throw new IllegalArgumentException("Deck 2 is invalid!");
    }
  }

  @Override
  public List<SanguineCard> createDeck() throws IOException {
    return DeckParser.makeDeck("docs" + File.separator + "example.deck");
  }

  @Override
  public void passTurn()
      throws IllegalStateException {
    hasGameStarted();

    changePlayer();
  }

  /**
   * A method that changes the current player from red to blue, or blue to red.
   */
  private void changePlayer() {
    if (currentPlayer == redPlayer) {
      currentPlayer = bluePlayer;
    } else {
      currentPlayer = redPlayer;
    }

    consecutivePasses++;

    for (ModelListener listener : listeners) {
      listener.turnChanged(currentPlayer.getColor());
    }

  }

  @Override
  public void addControllerSubscriber(ModelListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener is null");
    }
    this.listeners.add(listener);
  }
  private void notify(PlayerColor color) {

  }
  @Override
  public void playTurn(int row, int col, SanguineCard card)
      throws IllegalArgumentException, IllegalStateException {
    hasGameStarted();

    if (card == null) {
      throw new IllegalArgumentException("card is null");
    }

    placeCard(row, col, card);

    if (!currentPlayer.getDeck().isEmpty() && currentPlayer.getHand().size() < maxHandSize) {
      currentPlayer.deckToHand(1);
    }

    changePlayer();

    consecutivePasses = 0;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    hasGameStarted();

    return consecutivePasses >= 2;
  }

  @Override
  public int getScore(PlayerColor color) throws IllegalStateException, IllegalArgumentException {
    hasGameStarted();
    if (color == null) {
      throw new IllegalArgumentException("Player color is null");
    }

    int score = 0;

    for (int row = 0; row < board.getRows(); row++) {
      try {
        if (board.getPlayerColorMaxPoints(row) == color) {
          // add their score
          score += board.getScoreOfRow(row);
        }
      } catch (TieException exo) {
        score += 0;
      }
    }

    return score;
  }

  @Override
  public int getRowScore(PlayerColor color, int row)
      throws IllegalStateException, IllegalArgumentException {
    hasGameStarted();
    if (color == null) {
      throw new IllegalArgumentException("Player color is null!");
    }

    if (row < 0) {
      throw new IllegalArgumentException("Row num is invalid!");
    }

    return board.getPlayerColorRowScore(color, row);
  }

  @Override
  public SanguinePlayer getWinner() throws IllegalStateException, TieException {
    if (!isGameOver()) {
      return null;
    }

    int blue = getScore(PlayerColor.BLUE);
    int red = getScore(PlayerColor.RED);

    if (red > blue) {
      return new SanguinePlayer(redPlayer.getDeck(), PlayerColor.RED, maxHandSize);
    }
    if (red < blue) {
      return new SanguinePlayer(bluePlayer.getDeck(), PlayerColor.BLUE, maxHandSize);
    }

    throw new TieException("Game is tied!");
  }

  @Override
  public SanguinePlayer getTurn() throws IllegalStateException {
    hasGameStarted();

    return currentPlayer;
  }

  @Override
  public SanguineBoardCell getCellAt(int row, int col) {
    SanguineBoardCell original = board.getCellAt(row, col);

    return original.getCopy();
  }

  /**
   * this method returns a copy of the designated player's hand. if color is red, then it returns
   * red player's hand, blue otherwise.
   *
   * @param color color of player
   *
   * @return player's deck
   */
  @Override
  public List<SanguineCard> getPlayerHand(PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("color cant be null");
    }
    if (color == PlayerColor.RED) {
      return List.copyOf(redPlayer.getHand());
    }
    return List.copyOf(bluePlayer.getHand());
  }

  /**
   * checks which player owns the pawns or cards in a cell.
   *
   * @param row of target cell
   * @param col of target cell
   * @return the color of the player that owns the cell
   */
  @Override
  public PlayerColor getOwnershipOfCell(int row, int col) {
    if (row < 0 || row > board.getRows()) {
      throw new IllegalArgumentException("rows is out of bounds for row " + row);
    }
    if (col < 0 || col > board.getRows()) {
      throw new IllegalArgumentException("cols is out of bounds for col " + col);
    }
    return board.getCellAt(row, col).getColor();
  }

  @Override
  public boolean placeCardLegal(int row, int col, SanguineCard card,
                                SanguinePlayer player)
      throws IllegalStateException, IllegalArgumentException {
    hasGameStarted();

    if (card == null) {
      throw new IllegalArgumentException("Card is null!");
    }

    if (player == null) {
      throw new IllegalArgumentException("Player color is null!");
    }

    if (row < 0 || row > board.getRows() - 1) {
      throw new IllegalArgumentException("Row coordinate is invalid!");
    }

    if (col < 0 || col > board.getCols() - 1) {
      throw new IllegalArgumentException("Row coordinate is invalid!");
    }

    BasicSanguineBoardCell cell = board.getCellAt(row, col);

    if (cell.containsCard() || cell.getPawns().isEmpty() || cell.getColor() != player.getColor()) {
      return false;
    }

    if (card.getCost() < cell.getPawns().size()) {
      return false;
    } else {
      return true;
    }
  }


  // Private Helper Methods:

  /**
   * Helper method that throws an exception when the game has not started.
   *
   * @throws IllegalStateException when game has not been started
   */
  private void hasGameStarted() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started!");
    }
  }

  /**
   * A helper method that places a card on the board and influences the board accordingly.
   *
   * @throws IllegalStateException if game is not started
   */
  private void placeCard(int row, int col, SanguineCard card) throws IllegalStateException {
    try {
      board.addCardToCell(row, col, card, currentPlayer);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    List<InfluenceCoords> coords = validateInfluenceCoords(getInfluenceCoords(card), board, row,
        col);

    for (InfluenceCoords coordinate : coords) {

      try {
        influenceOneCell(board.getCellAt(coordinate.row, coordinate.col));
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
  }

  /**
   * A helper method that changes the pawns in one cell to be the color of the current player.
   * if theres a card it does nothing. if theres pawns it does nothing, if there are pawns, it
   * changes the color of the pawns.
   *
   * @param cell the cell whose's pawns will be changed
   */
  private void influenceOneCell(BasicSanguineBoardCell cell) {
    try {
      cell.getCard();
      return;
    } catch (Exception ignored) {
      System.out.print("");
    }

    if (cell.getPawns().isEmpty() || cell.getPawns().size() == 1 || cell.getPawns().size() == 2) {
      cell.placePawn(currentPlayer);
    }

    cell.changeColorsOfPawns(currentPlayer);
  }

  /**
   * A method that calculates new coordinates and validates that they are in the board.
   * If they are not, it skips that influence.
   *
   * @param coords a list of differences between the coordinates of a card's influence
   *               cells and its relative center
   * @param board  the game board
   * @param row    the row of where the placed card is
   * @param col    the column of where the placed card is
   * @return a list of (row,column) pairs that will influence the board.
   */
  private List<InfluenceCoords> validateInfluenceCoords(List<InfluenceCoords> coords,
                                                        SanguineGameBoard board,
                                                        int row,
                                                        int col) {
    List<InfluenceCoords> newCoords = new ArrayList<>();

    for (InfluenceCoords coord : coords) {

      int newRow = row + coord.row;
      int newCol = col + coord.col;

      if (newRow > board.getRows() || row < 0) {
        continue;
      }
      if (newCol >= board.getCols() || col < 0) {
        continue;
      }
      newCoords.add(new InfluenceCoords(newRow, newCol));
    }
    return newCoords;
  }

  /**
   * A method used to get coordinates of the cells that a card can possibly influence.
   *
   * @param card the card that will be checked for its influence coordinates
   * @return a list of influence coordinates
   */
  private List<InfluenceCoords> getInfluenceCoords(SanguineCard card) {
    List<InfluenceCoords> coords = new ArrayList<>();

    for (int row = 0; row < card.getIntInfluence().size(); row++) {

      List<Integer> r = card.getIntInfluence().get(row);

      for (int col = 0; col < r.size(); col++) {

        if (r.get(col) == 1) {
          coords.add(
              new InfluenceCoords(row - r.size() / 2, col - card.getIntInfluence().size() / 2));
        }
      }
    }

    return coords;
  }

  /**
   * A private record that holds the coordinates of influence.
   *
   * <p>Since java does not have tuple objects, this record represents the row and column</p>
   * coordinates of a card's influence.
   *
   * @param row the row where influence is found
   * @param col the column where influence is found
   */
  private record InfluenceCoords(int row, int col) {
  }

}
