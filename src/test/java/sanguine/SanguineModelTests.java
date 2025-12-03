package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguineBoardCell;
import sanguine.model.BasicSanguineModel;
import sanguine.model.DeckParser;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineBoardCell;
import sanguine.model.SanguineCard;
import sanguine.model.SanguinePlayer;
import sanguine.model.TieException;
import sanguine.view.BasicSanguineTextualView;

/**
 * A class for testing the Basic SanguineModel. All tests
 * in this class cannot create Card type objects. Instead,
 * the tests use the createDeck method to help create example games.
 */
public class SanguineModelTests {
  /**
   * A helper method that creates a new sanguine player.
   * This will be used in tests that need a valid player object.
   * This specifically creates a red player.
   */
  public SanguinePlayer makeRedPlayer() throws IOException {
    return new SanguinePlayer(
        DeckParser.makeDeck("docs" + File.separator + "example.deck"),
        PlayerColor.RED,
        3
    );
  }

  /**
   * A helper method that creates a new sanguine player.
   * This will be used in tests that need a valid player object.
   * This specifically creates a blue player.
   */
  public SanguinePlayer makeBluePlayer() throws IOException {
    return new SanguinePlayer(
        DeckParser.makeDeck("docs" + File.separator + "example.deck"),
        PlayerColor.BLUE,
        3
    );
  }

  /**
   * This tests that the createDeck() in model is properly creating a deck.
   */
  @Test
  public void testCreateDeck() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      // should be the same example deck every time this is called
      List<SanguineCard> deck = model.createDeck();
      assertEquals(30, deck.size());
    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }

  }

  /**
   * This tests that startGame starts the game properly in the model.
   */
  @Test
  public void testValidStartGame() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertEquals(3, model.getBoard().getRows());
      assertEquals(5, model.getBoard().getCols());
      assertFalse(model.isGameOver());
      assertEquals(0, model.getScore(PlayerColor.RED));
      assertEquals(0, model.getScore(PlayerColor.BLUE));
      assertEquals(null, model.getWinner());
      assertEquals(PlayerColor.RED, model.getTurn().getColor());

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that start game does not run when the row size is too small.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartGameRowTooSmall() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(0, 5, deck1, deck2, 3);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that start game does not run when the col size is too small.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartGameColTooSmall() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 0, deck1, deck2, 3);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that start game does not run when the col size is not odd.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartGameColEven() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 6, deck1, deck2, 3);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that start game still runs when a deck is null and the model makes a default deck.
   */
  @Test
  public void testInvalidStartGameNullDeck() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, null, deck2, 3);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that start game still runs when a deck is empty and the model makes a default deck.
   */
  @Test
  public void testInvalidStartGameEmptyDeck() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = new ArrayList<>();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that start throws when hand size is less than zero.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartGameHandTooSmall() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 0);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that start throws when hand size is greater than 1/3 of the deck.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartGameHandInvalid() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 11);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that passTurn() changes the players turn.
   */
  @Test
  public void testPassTurn() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      model.passTurn();

      assertEquals(PlayerColor.BLUE, model.getTurn().getColor());

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that getTurn in the model returns the correct player's turn.
   */
  @Test
  public void testGetTurn() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertEquals(PlayerColor.RED, model.getTurn().getColor());

      model.passTurn();

      assertEquals(PlayerColor.BLUE, model.getTurn().getColor());

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that isGameOver returns false if the game is not over.
   */
  @Test
  public void testIsGameOverFalse() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertFalse(model.isGameOver());

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that isGameOver returns true when both players pass in a row.
   */
  @Test
  public void testIsGameOverConsecutivePasses() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      model.passTurn();
      model.passTurn();

      assertTrue(model.isGameOver());

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that playTurn throws when the card is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayTurnInvalidCard() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      model.playTurn(0, 0, null);

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that playTurn throws when the coordinates for the card to be placed is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayTurnInvalidCoordinates1() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      model.playTurn(0, -1, deck1.get(0));

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that playTurn throws when the coordinates for the card to be placed is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayTurnInvalidCoordinates2() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      model.playTurn(0, 5, deck1.get(0));

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This tests that playTurn properly places a card on the board in the correct spot.
   *
   * <p>Also tests that board is influenced properly</p>
   */
  @Test
  public void testPlayTurn() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      // red players turn
      // card 1 should be of value greater than 1
      model.playTurn(0, 0, deck1.get(1));
      //System.out.println(deck1.get(1));
      assertTrue(model.getBoard().getCellAt(0, 0).containsCard());
      assertEquals(1, model.getBoard().getBoard().getCellAt(0, 1).getPawns().size());
      assertEquals(PlayerColor.RED, model.getBoard().getBoard().getCellAt(0, 1).getColor());

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Tests that getScore throws when the player color is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetScoreNullColor() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertEquals(0, model.getScore(null));

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Tests that getScore is properly returning the score for a specific player.
   */
  @Test
  public void testGetScore() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertEquals(0, model.getScore(PlayerColor.RED));
      assertEquals(0, model.getScore(PlayerColor.BLUE));

      // red players turn
      // card 1 should be of value greater than 1
      model.playTurn(0, 0, deck1.get(1));

      assertEquals(1, model.getScore(PlayerColor.RED));
      assertEquals(0, model.getScore(PlayerColor.BLUE));

      model.playTurn(1, 4, deck2.get(1));

      System.out.println(model.getTurn().getColor());
      System.out.println(model.getScore(PlayerColor.RED));
      assertEquals(1, model.getScore(PlayerColor.RED));
      assertEquals(1, model.getScore(PlayerColor.BLUE));

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Tests that getScore is properly returning the score for a specific player in a row.
   */
  @Test
  public void testGetRowScore() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertEquals(0, model.getRowScore(PlayerColor.RED, 2));
      assertEquals(0, model.getRowScore(PlayerColor.BLUE, 2));

      // red players turn
      // card 1 should be of value greater than 1
      model.playTurn(0, 0, deck1.get(1));

      assertEquals(1, model.getRowScore(PlayerColor.RED, 0));
      assertEquals(0, model.getRowScore(PlayerColor.BLUE, 0));

      model.playTurn(1, 4, deck2.get(1));

      assertEquals(1, model.getRowScore(PlayerColor.RED, 0));
      assertEquals(1, model.getRowScore(PlayerColor.BLUE, 1));

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Tests that getWinner returns null in the model when the game is still ongoing.
   */
  @Test
  public void testGetWinnerNone() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertNull(model.getWinner());

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Tests that getWinner returns tie when both players are tied.
   */
  @Test(expected = TieException.class)
  public void testGetWinnerTie() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);
      model.passTurn();
      model.passTurn();

      model.getWinner();

    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Tests that getWinner returns the correct winner when one player wins.
   */
  @Test
  public void testGetWinner() {
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      assertEquals(0, model.getScore(PlayerColor.RED));
      assertEquals(0, model.getScore(PlayerColor.BLUE));

      // red players turn
      // card 1 should be of value greater than 1
      model.playTurn(0, 0, deck1.get(1));

      assertEquals(1, model.getScore(PlayerColor.RED));
      assertEquals(0, model.getScore(PlayerColor.BLUE));

      model.passTurn();
      model.passTurn();

      assertEquals(PlayerColor.RED, model.getWinner().getColor());
    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Tests that getScore is properly returning the score for a specific player.
   */
  @Test
  public void testPlaceCardLegal() {
    //TODO: need to do!!!!
    BasicSanguineModel model = new BasicSanguineModel();
    try {
      List<SanguineCard> deck1 = model.createDeck();
      List<SanguineCard> deck2 = model.createDeck();
      model.startGame(3, 5, deck1, deck2, 3);

      SanguinePlayer bluePlayer = makeBluePlayer();
      SanguinePlayer redPlayer = makeRedPlayer();

      assertFalse(model.placeCardLegal(0, 0, deck1.getFirst(), bluePlayer));
      assertTrue(model.placeCardLegal(0, 4, deck1.getFirst(), bluePlayer));

      assertTrue(model.placeCardLegal(0, 0, deck2.getFirst(), redPlayer));
      assertFalse(model.placeCardLegal(2, 4, deck2.getFirst(), redPlayer));
    } catch (IOException exo) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * tests the method getOwnershipOfCell.
   *
   * @throws IOException if cannot read file.
   */
  @Test
  public void testGetOwnershipOfCell() throws IOException {
    BasicSanguineModel model = new BasicSanguineModel();
    BasicSanguineTextualView view = new BasicSanguineTextualView(model);

    List<SanguineCard> deck1 = model.createDeck();
    List<SanguineCard> deck2 = model.createDeck();
    model.startGame(3, 5, deck1, deck2, 3);


    assertEquals(PlayerColor.RED, model.getOwnershipOfCell(0, 0));

    model.playTurn(0, 0, deck1.get(1));

    assertEquals(PlayerColor.RED, model.getOwnershipOfCell(0, 0));

    assertThrows(IllegalArgumentException.class, () -> model.getOwnershipOfCell(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> model.getOwnershipOfCell(0, -1));
  }

  /**
   * tests the observer method of gethand.
   *
   * @throws IOException if files is unreadable.
   */
  public void testGetHand() throws IOException {

    BasicSanguineModel model = new BasicSanguineModel();
    List<SanguineCard> deck1 = model.createDeck();
    List<SanguineCard> deck2 = model.createDeck();
    model.startGame(3, 5, deck1, deck2, 3);
    List<SanguineCard> hand = new ArrayList<>();
    hand.add(deck1.removeFirst());
    hand.add(deck1.removeFirst());
    hand.add(deck1.removeFirst());
    assertEquals(hand, model.getPlayerHand(PlayerColor.RED));
  }
}
