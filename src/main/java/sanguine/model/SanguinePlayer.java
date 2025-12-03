package sanguine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A SanguineGame specific player that implements the Player interface.
 *
 * <p>This player is assigned a player color, hand, deck, and max hand size.</p>
 *
 * <p>Two methods mutate the hand/deck, drawHandToBoard() and deckToHand().</p>
 * deckToHand() should be remembered to be called every time a card is removed from the hand
 * in the model.
 */
public class SanguinePlayer implements Player {
  // Private Fields:

  private final PlayerColor playerColor;
  private final List<SanguineCard> hand;
  private final List<SanguineCard> deck;
  private final int maxHandSize;

  /**
   * A constructor that creates a SanguineGame player that has a deck and player color.
   *
   * <p>The player's hand starts off as empty.</p>
   *
   * @param deck        the player's deck as a basic SanguineGame card
   * @param color       the player's color
   * @param maxHandSize the player's max hand size at any given time
   */
  public SanguinePlayer(List<SanguineCard> deck,
                        PlayerColor color, int maxHandSize) throws IllegalArgumentException {
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("Deck is invalid!");
    }
    if (color == null) {
      throw new IllegalArgumentException("Player Color is invalid!");
    }
    if (maxHandSize <= 0) {
      throw new IllegalArgumentException("invalid hand size");
    }

    this.hand = new ArrayList<>();
    this.deck = deck;
    this.playerColor = color;
    this.maxHandSize = maxHandSize;
  }

  @Override
  public List<SanguineCard> getHand() {
    return List.copyOf(hand);
  }

  @Override
  public List<SanguineCard> getDeck() {
    return List.copyOf(deck);
  }

  @Override
  public PlayerColor getColor() {
    return playerColor;
  }

  @Override
  public SanguineCard drawHandToBoard() throws IllegalArgumentException {
    if (!hand.isEmpty()) {
      SanguineCard card = hand.removeFirst();
      return new BasicSanguineCard(card.getName(), card.getCost(), card.getValue(),
          card.getStringInfluence());
    } else {
      throw new IllegalArgumentException("Hand is empty!");
    }
  }

  @Override
  public void shuffle() {
    Collections.shuffle(deck, new Random(42));
  }

  @Override
  public void deckToHand(int numCards) throws IllegalArgumentException, IllegalStateException {
    if (deck.isEmpty()) {
      throw new IllegalStateException("Deck is empty!");
    }

    if (numCards > deck.size()) {
      throw new IllegalArgumentException("Not enough cards to move!");
    }

    if (numCards < 0) {
      throw new IllegalArgumentException("num cards cannot be negative");
    }

    // if current hand size is too large
    if (hand.size() >= maxHandSize) {
      throw new IllegalStateException("Hand is full!");
    }

    // if after adding that number of cards, the hand size will be too large
    if (hand.size() + numCards > maxHandSize) {
      throw new IllegalArgumentException("Can't add this many cards to hand!");
    }

    for (int count = 0; count < numCards; count++) {
      SanguineCard card = deck.removeFirst();
      hand.add(card);
    }
  }
}
