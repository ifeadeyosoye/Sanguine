package sanguine.model;

import java.util.List;

/**
 * This interface marks the concept of a board cell.
 *
 * <p>A board cell is a singular unit in a game board.</p>
 * A collection of board cells is a game board.
 * A board cell can hold values like pawns or cards.
 * A board cell has an owner depending on what game objects are held in the cell.
 */
public interface SanguineBoardCell {
  /**
   * A method that gets all pawns in the cell.
   *
   * @return a list of all pawns in the cell
   */
  List<BasicSanguinePawn> getPawns();

  /**
   * A method that gets a card in the cell if possible.
   *
   * @return the card in the cell.
   */
  SanguineCard getCard();

  /**
   * A method that gets the current value of the cell.
   *
   * <p>The cell's value is calculated by returning the value of the Card in the cell.</p>
   *
   * @return the current value of the cell
   */
  int getValue();

  /**
   * A method that gets the color of the cell.
   * This is determined by the color of the owner.
   *
   * @return the color of a cell as a PlayerColor
   */
  PlayerColor getColor();

  /**
   * A method that adds a new pawn to the cell.
   *
   * <p>A new pawn can only be placed if it is the same color as the other pawns currently</p>
   * in the cell.
   */
  void placePawn(SanguinePlayer currentPlayer);

  /**
   * A method that places a card in the cell if the card value, number of pawns and pawn colors
   * match.
   */
  void placeCard(SanguineCard card, Player player);

  /**
   * A method that changes the colors of pawns in the cell to the opposing player's color.
   *
   * <p>This is used specifically for the influence methods in the model.</p>
   *
   * @param player who will now own the pawns.
   */
  void changeColorsOfPawns(SanguinePlayer player);

  /**
   * A method that returns whether a cell contains a card.
   *
   * @return whether the cell contains a card
   */
  boolean containsCard();
}