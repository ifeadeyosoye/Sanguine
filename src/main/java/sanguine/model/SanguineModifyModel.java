package sanguine.model;

import java.io.IOException;
import java.util.List;

/**
 * Behaviors necessary to play a game of SanguineGame.
 * The design of the interfaces abstracts the Card type in case
 * a different implementation needs a specific type of card.
 *
 * <p>The interface assumes the constructor of the implementation
 * cannot start the game. Instead, the user of this type must
 * start the game itself. As a result, most methods report
 * an IllegalStateException if the game has not been started.
 *
 * <p>Start game takes in the necessary information to start a game of SanguineGame.
 *
 * @param <C> the type of card for the implementation
 */
public interface SanguineModifyModel<C extends Card> {


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
  void startGame(int rows, int cols, List<C> deck1,
                 List<C> deck2, int handSize)
      throws IllegalArgumentException, IllegalStateException, IOException;

  /**
   * Creates a new deck for a player if their deck was not specified.
   *
   * @return a new deck of SanguineGame cards
   */
  List<C> createDeck() throws IOException;

  /**
   * When a player passes their turn, they change the current player from themselves.
   *
   * @throws IllegalArgumentException If current player is the same as given player
   * @throws IllegalStateException    when the game has not started
   */
  void passTurn() throws IllegalStateException, IOException;

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
      IllegalStateException, IOException;

}
