package sanguine.model;

/**
 * This interface marks the concept of cards.
 *
 * <p>The only behavior guaranteed by this class is it the toString() method which renders</p>
 * the card in the format as specified in the readMe
 */
public interface Card {
  /**
   * Renders a card's name, cost, value, and influence board as a String.
   * In the influence board:
   * - X represents no influence
   * - I represents influence on the board
   * - C represents where the actual card is placed
   * Here is an example card:
   * Security 1 2
   * XXXXX
   * XXIXX
   * XICIX
   * XXIXX
   * XXXXX
   * Here, the name of the card is Security, its cost is 1, its value is 2, and the influence
   * board is present.
   *
   * @return the formatted card
   */
  String toString();
}
