package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import sanguine.model.DeckParser;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguinePlayer;

/**
 * A class for testing Player and Sanguine Player and Advanced Card which extends Card.
 *
 * <p>Because Sanguine Player relies on receiving a valid deck, we will be using DeckParser</p>
 * to create the deck and operate under the assumption that it works because it was tested
 * beforehand.
 */
public class PlayerTests {

  /**
   * A helper method that creates a valid card. this will be used to test output of methods.
   */
  public SanguinePlayer makeValid() throws IOException {
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    PlayerColor color = PlayerColor.RED;
    int maxHandSize = 3;

    return new SanguinePlayer(deck, color, maxHandSize);
  }

  /**
   * A test that makes sure no exception is thrown when parameters are valid.
   *
   * @throws IOException if deck path is not valid.
   */
  @Test
  public void testValidCreation() throws IOException {
    makeValid();
  }

  /**
   * A test that makes sure an IllegalArgumentException is thrown when deck is null.
   */
  @Test
  public void testNullDeck() {
    List<SanguineCard> deck = null;
    PlayerColor color = PlayerColor.RED;
    int maxHandSize = 3;

    assertThrows(IllegalArgumentException.class,
        () -> new SanguinePlayer(deck, color, maxHandSize));
  }

  /**
   * A test that makes sure an IllegalArgumentException is thrown when deck is empty.
   */
  @Test
  public void testEmptyDeck() {
    List<SanguineCard> deck = Collections.emptyList();
    PlayerColor color = PlayerColor.RED;
    int maxHandSize = 3;

    assertThrows(IllegalArgumentException.class,
        () -> new SanguinePlayer(deck, color, maxHandSize));
  }

  /**
   * A test makes sure an IllegalArgumentException is thrown when color is null.
   */
  @Test
  public void testNullColor() throws IOException {
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    PlayerColor color = null;
    int maxHandSize = 3;

    assertThrows(IllegalArgumentException.class,
        () -> new SanguinePlayer(deck, color, maxHandSize));
  }

  /**
   * A test that makes sure an IllegalArgumentException is thrown when color is null.
   */
  @Test
  public void testInvalidHandSize() throws IOException {
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    PlayerColor color = PlayerColor.RED;
    int maxHandSize = -3;

    assertThrows(IllegalArgumentException.class,
        () -> new SanguinePlayer(deck, color, maxHandSize));
  }

  /**
   * A test that makes sure that we can get the deck from the player.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testGetDeck() throws IOException {
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    PlayerColor color = PlayerColor.RED;
    int maxHandSize = 3;

    SanguinePlayer player = new SanguinePlayer(deck, color, maxHandSize);

    assertEquals(deck, player.getDeck());
  }

  /**
   * A test that makes sure that we can get the color from the player.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testGetColor() throws IOException {
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    PlayerColor color = PlayerColor.RED;
    int maxHandSize = 3;

    SanguinePlayer player = new SanguinePlayer(deck, color, maxHandSize);

    assertEquals(color, player.getColor());
  }

  /**
   * A test that makes sure that we can't draw from the hand when the hand is empty.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void drawHandToBoardFailsBecauseEmptyHand() throws IOException {
    SanguinePlayer player = makeValid();

    assertThrows(IllegalArgumentException.class, () -> player.drawHandToBoard());

  }

  /**
   * A test that makes sure that we can draw from the player's hand.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void drawHandToBoardPasses() throws IOException {
    SanguinePlayer player = makeValid();

    player.deckToHand(3);

    SanguineCard expectedCard = player.getHand().get(0);
    SanguineCard removedCard = player.drawHandToBoard();

    assertEquals(expectedCard, removedCard);
  }

  /**
   * A test that makes sure we can get the player's hand.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testGetHand() throws IOException {
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    PlayerColor color = PlayerColor.RED;
    int maxHandSize = 3;

    SanguinePlayer player = new SanguinePlayer(deck, color, maxHandSize);

    List<SanguineCard> playerDeck = player.getDeck();
    for (int i = 0; i < maxHandSize; i++) {
      assertEquals(deck.get(i), playerDeck.get(i));
    }
  }

  /**
   * This tests that the limit of the hand holds for a player.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void deckToHandFailsBecauseNumCardsTooBig() throws IOException {
    SanguinePlayer player = makeValid();

    assertThrows(IllegalArgumentException.class, () -> player.deckToHand(300));
  }

  /**
   * This tests that we can't add a negative amount of cards to player's hand.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void deckToHandFailsBecauseNumCardsTooSmall() throws IOException {
    SanguinePlayer player = makeValid();

    assertThrows(IllegalArgumentException.class, () -> player.deckToHand(-300));
  }

  /**
   * Test that nothing happens when we try to add 0 cards from the deck to the player's hand.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testNothingHappensWhenNumCardIsZeroInDeckToHand() throws IOException {
    SanguinePlayer player = makeValid();

    SanguineCard card = player.getDeck().getFirst();

    player.deckToHand(0);

    assertTrue(player.getHand().isEmpty());
    assertEquals(card, player.getDeck().getFirst());
  }

  /**
   * Test that we can't add more card to the hand when the hand is full.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testDeckToHandFailsBecauseHandIsFull() throws IOException {
    SanguinePlayer player = makeValid();

    SanguineCard card = player.getDeck().getFirst();

    player.deckToHand(3);

    assertThrows(IllegalStateException.class, () -> player.deckToHand(1));
  }

  /**
   * Test that we can't add more card to the hand when the hand is full.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testDeckToHandFailsBecauseOverflow() throws IOException {
    SanguinePlayer player = makeValid();

    SanguineCard card = player.getDeck().getFirst();

    player.deckToHand(2);

    assertThrows(IllegalArgumentException.class, () -> player.deckToHand(3));
  }

  /**
   * Test that we can properly move cards from the deck to the hand when valid.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testDeckToHandPassesAndDoesEverythingCorrect() throws IOException {
    SanguinePlayer player = makeValid();

    List<SanguineCard> oldDeck = player.getDeck();

    List<SanguineCard> expected = new ArrayList<>();
    int previousLength = oldDeck.size();

    for (int i = 0; i < 3; i++) {
      expected.add(oldDeck.get(i));
    }

    player.deckToHand(3);

    assertEquals(expected, player.getHand());
    assertEquals(oldDeck.size() - 3, player.getDeck().size());
  }

}

