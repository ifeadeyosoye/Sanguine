package sanguine;

import java.io.IOException;
import java.util.List;
import sanguine.model.BasicSanguineModel;
import sanguine.model.Card;
import sanguine.model.ModelListener;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineBoardCell;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineGameBoard;
import sanguine.model.SanguineModel;
import sanguine.model.SanguinePlayer;
import sanguine.model.TieException;

/**
 * A mock of the Sanguine model that tracks inputs and methods called.
 */
public class MockModel implements SanguineModel {

  Appendable log;

    /**
     * A constructor that sets the Appendable object.
     *
     * @param log the Appendable object
     */
  public MockModel(Appendable log) {
    this.log = log;
  }

  /**
   * adds listener to the models list of subscribers.
   *
   * @param listener controller to be added.
   */
  @Override
  public void addControllerSubscriber(ModelListener listener) {

  }

  /**
   * Returns a copy of the board to be printed with Textual View.
   *
   * @return the sanguine game board
   */
  @Override
  public SanguineGameBoard getBoard() throws IOException {
    return null;
  }

  /**
   * When both players pass their turn in a row.
   *
   * @return whether the game is over
   * @throws IllegalStateException when the game has not started
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    try {
      log.append("game is over");
    } catch (IOException e) {
      //can leave empty becuase test will fail regardless
    }
    return true;
  }

  /**
   * Returns the score of the game at a certain moment for a specific player.
   * To get the score we:
   * 1. Tally row scores for each player
   * 2. The player with the higher row-score adds their row score to the total score
   * 3. If it is the same for each player, no points are rewarded
   *
   * @param color the player color to get the score for
   * @return the score for a specific player
   * @throws IllegalStateException    if game has not started
   * @throws IllegalArgumentException if player is null
   */
  @Override
  public int getScore(PlayerColor color) throws IllegalStateException, IllegalArgumentException {
    return 0;
  }

  /**
   * Gets the winner of the game.
   *
   * <p>If the game is not over, null is returned for the winner</p>
   * Calculate score for each player and who ever has a higher score is the winner
   * If a tie occurs, a tie exception is thrown
   *
   * @return the winner of the game
   * @throws IllegalStateException when the game has not started
   * @throws TieException          then the game is tied
   */
  @Override
  public SanguinePlayer getWinner() throws IllegalStateException, TieException {
    return null;
  }

  /**
   * A method that returns the current player turn.
   *
   * @return the player whose turn it is
   * @throws IllegalStateException when the game has not started
   */
  @Override
  public SanguinePlayer getTurn() throws IllegalStateException {
    return null;
  }

  /**
   * A method that returns a copy of the cell at row,col assuming they are valid interfaces.
   *
   * @param row the row that the cell is in
   * @param col the column that the cell is in
   * @return the cell itself
   */
  @Override
  public SanguineBoardCell getCellAt(int row, int col) {
    return null;
  }

  /**
   * this method returns a copy of the designated player's hand. if color is red, then it returns
   * red player's hand, blue otherwise.
   *
   * @param color color of player
   * @return player's deck
   */
  @Override
  public List<SanguineCard> getPlayerHand(PlayerColor color) {
    return List.of();
  }

  /**
   * checks which player owns the pawns or cards in a cell.
   *
   * @param row of target cell
   * @param col of target cell
   * @return the color of the player that owns the cell.
   */
  @Override
  public PlayerColor getOwnershipOfCell(int row, int col) {
    return null;
  }

  /**
   * A method to get the color row score of a certain player in the game.
   *
   * @param color the player color
   * @param row   the desired row to check
   * @return the score in the row for that player
   * @throws IllegalStateException    if the game as not started
   * @throws IllegalArgumentException if the color or row is invalid
   */
  @Override
  public int getRowScore(PlayerColor color, int row)
      throws IllegalStateException, IllegalArgumentException {
    return 0;
  }

  /**
   * A method that determines if it is legal to place a card in a specific cell.
   *
   * @param row    the cell row
   * @param col    the cell column
   * @param card   the card to be placed
   * @param player the player placing the card
   * @return whether it is legal to place the card
   * @throws IllegalStateException    if the game has not been started
   * @throws IllegalArgumentException if the player or card is null; if the coordinates are invalid
   */
  @Override
  public boolean placeCardLegal(int row, int col, SanguineCard card, SanguinePlayer player)
      throws IllegalStateException, IllegalArgumentException {
    return false;
  }

  /**
   * A method that returns a list of subscribers.
   *
   * @return the subscribers of the class
   */
  @Override
  public List<ModelListener> seeSubscribers() {
    return List.of();
  }

  /**
   * A method that starts a game of SanguineGame.
   *
   * <p>rows and cols specify the dimensions of the game board.</p>
   * board starts with a pawn in each first and last column with the opposing player's colors.
   *
   * <p>Takes in two decks and creates players that hold these two different decks.</p>
   * If either or both decks are null or empty, we will make a basic deck for them to play.
   * If given decks, this checks whether they are valid decks.
   *
   * <p>Additionally, hand size can not be greater than 1/3 of the deck size.</p>
   *
   * @param rows     the number of rows in the game board
   * @param cols     the number of columns in the game board
   * @param deck1    the first player's deck
   * @param deck2    the second player's deck
   * @param handSize the hand size for each player
   * @throws IllegalArgumentException if rows/columns are neg/zero or invalid
   *                                  (dealt with in SanguineGameBoard constructor) or if the
   *                                  hand size is invalid
   * @throws IllegalStateException    if the game has already been started
   */
  @Override
  public void startGame(int rows, int cols, List deck1, List deck2, int handSize)
      throws IllegalArgumentException, IllegalStateException, IOException {

  }

  /**
   * Creates a new deck for a player if their deck was not specified.
   *
   * @return a new deck of SanguineGame cards
   */
  @Override
  public List createDeck() throws IOException {
    return List.of();
  }

  /**
   * When a player passes their turn, they change the current player from themselves.
   *
   * @throws IllegalArgumentException If current player is the same as given player
   * @throws IllegalStateException    when the game has not started
   */
  @Override
  public void passTurn() throws IllegalStateException, IOException {

  }

  /**
   * A method for when the player takes a turn to place a card down.
   *
   * <p>Player must pick a valid cell with its own pawns that match the value of the card.</p>
   * This new card can affect the surrounding cells by adding or converting pawns on the board.
   *
   * @param row  the specified row of cell
   * @param col  the specified column of cell
   * @param card the card to be placed
   * @throws IllegalArgumentException when the row/column is invalid
   * @throws IllegalStateException    when the card can not be placed because of value reasons,
   *                                  or when the game has not started
   */
  @Override
  public void playTurn(int row, int col, Card card)
      throws IllegalArgumentException, IllegalStateException, IOException {
    SanguineModel model = new BasicSanguineModel();
    log.append("" + row).append("" + col).append("" + card);
  }

  /**
   * tests for when the user plays a bad move
   */
  public void playTurnIllegal() {
    try {
      log.append("bad move");
    } catch (IOException e) {
      // if we are here, the test will fail anyway so let it fail.
    }
  }
}
