package sanguine.model;

import java.io.IOException;
import java.util.List;

/**
 * Behaviors necessary to play a game of Sanguine.
 * The design of the interfaces abstracts the Card type in case
 * a different implementation needs a specific type of card.
 *
 * <p>The interface assumes the constructor of the implementation
 * cannot start the game. Instead, the user of this type must
 * start the game itself. As a result, most methods report
 * an IllegalStateException if the game has not been started.
 *
 * <p>Start game takes in the necessary information to start a game of Sanguine.
 *
 * @param <C> the type of card for the implementation
 */
public interface SanguineModel<C extends Card> {

  /**
   * Returns a copy of the board to be printed with Textual View.
   *
   * @return the sanguine game board
   */
  SanguineGameBoard getBoard() throws IOException;

  /**
   * A method that starts a game of Sanguine.
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
  void startGame(int rows, int cols, List<C> deck1,
                 List<C> deck2, int handSize)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Creates a new deck for a player if their deck was not specified.
   *
   * @return a new deck of Sanguine cards
   */
  List<C> createDeck() throws IOException;

  /**
   * When a player passes their turn, they change the current player from themselves.
   *
   * @throws IllegalArgumentException If current player is the same as given player
   * @throws IllegalStateException    when the game has not started
   */
  void passTurn() throws IllegalStateException;

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
  void playTurn(int row, int col, C card) throws IllegalArgumentException,
      IllegalStateException;

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
}
