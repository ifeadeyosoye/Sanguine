package sanguine.model;


import java.io.IOException;
import java.util.List;


/**
 * this interface will contain the methods that only access contents of the model. nothing they do
 * will change the model's contents. this will be used by view.
 */
public interface ModelReadOnlyInterface {
  // hey


  /**
   * Returns a copy of the board to be printed with Textual View.
   *
   * @return the sanguine game board
   */
  SanguineGameBoard getBoard();


  /**
   * When both players pass their turn in a row.
   *
   * @return whether the game is over
   * @throws IllegalStateException when the game has not started
   */
  boolean isGameOver() throws IllegalStateException;


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
  int getScore(PlayerColor color) throws IllegalStateException, IllegalArgumentException;


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
  SanguinePlayer getWinner() throws IllegalStateException, TieException;


  /**
   * A method that returns the current player turn.
   *
   * @return the player whose turn it is
   * @throws IllegalStateException when the game has not started
   */
  SanguinePlayer getTurn() throws IllegalStateException;


  /**
   * A method that returns a copy of the cell at row,col assuming they are valid interfaces.
   *
   * @param row the row that the cell is in
   * @param col the column that the cell is in
   * @return the cell itself
   */
  SanguineBoardCell getCellAt(int row, int col);


  /**
   * this method returns a copy of the designated player's hand. if color is red, then it returns
   * red player's hand, blue otherwise.
   *
   * @param color color of player
   *
   * @return player's deck
   */
  List<SanguineCard> getPlayerHand(PlayerColor color);




  /**
   * checks which player owns the pawns or cards in a cell.
   *
   * @param row of target cell
   * @param col of target cell
   *
   * @return the color of the player that owns the cell.
   */
  PlayerColor getOwnershipOfCell(int row, int col);


  /**
   * A method to get the color row score of a certain player in the game.
   *
   * @param color the player color
   * @param row the desired row to check
   * @return the score in the row for that player
   * @throws IllegalStateException if the game as not started
   * @throws IllegalArgumentException if the color or row is invalid
   */
  public int getRowScore(PlayerColor color, int row)
      throws IllegalStateException, IllegalArgumentException;




  /**
   * A method that determines if it is legal to place a card in a specific cell.
   *
   * @param row the cell row
   * @param col the cell column
   * @param card the card to be placed
   * @param player the player placing the card
   * @return whether it is legal to place the card
   * @throws IllegalStateException if the game has not been started
   * @throws IllegalArgumentException if the player or card is null; if the coordinates are invalid
   */
  public boolean placeCardLegal(int row, int col, SanguineCard card,
                                SanguinePlayer player)
      throws IllegalStateException, IllegalArgumentException;
}




