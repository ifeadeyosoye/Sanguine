package sanguine.model;

import java.util.List;

/**
 * This interface marks the concept of a Player in a game of Sanguine.
 *
 * <p>A player in a game of Sanguine is either Red or Blue</p>
 * A player is able to get their color, deck, hand.
 * They are also able to shuffle their deck, draw cards from their deck to their hand, and then
 * from their hand to the game board within the model later on.
 */
public interface Player {

  /**
   * A method that returns the color of a Player.
   *
   * <p>The two colors are blue and red.</p>
   *
   * @return the player's color as a PlayerColor
   */
  PlayerColor getColor();

  /**
   * A method that returns the player's deck.
   *
   * @return the player's deck as a list of BasicSanguineCard
   */
  List<SanguineCard> getDeck();

  /**
   * A method that returns the player's hand.
   *
   * @return the player's deck as a list of BasicSanguineCard
   */
  List<SanguineCard> getHand();

  /**
   * A method that removes a card from a player's hand that will be placed on board.
   *
   * <p>Should return a copy of the card.</p>
   *
   * @return the drawn BasicSanguineCard from the player's hand
   * @throws IllegalArgumentException when the player's hand is empty
   */
  SanguineCard drawHandToBoard() throws IllegalArgumentException;

  /**
   * A method that shuffles a player's deck.
   */
  void shuffle();

  /**
   * A method that draws a card from the deck to the hand if allowable.
   *
   * <p>This method should be called whenever a card is placed from the hand to the board.</p>
   *
   * @param numCards number of cards that will be drawn. (or want to be drawn).
   * @throws IllegalArgumentException when there aren't enough cards from the deck to move or when
   *                                  the number of cards to be moved will make the hand too big.
   * @throws IllegalStateException    when the deck is empty or when the hand is full.
   */
  void deckToHand(int numCards) throws IllegalArgumentException, IllegalStateException;
}
