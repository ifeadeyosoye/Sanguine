package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguineModel;
import sanguine.model.DeckParser;
import sanguine.model.SanguineCard;
import sanguine.view.BasicSanguineTextualView;

/**
 * A class that tests the textual view display for a game of SanguineGame.
 *
 * <p>Currently, the class prints out the board after each move to see the update.</p>
 */
public class TextualViewTests {

  /**
   *  makes sure that the board is correctly displayed after every turn. checks influence and
   *  color. this checks if the influence is put correctly too.
   *  fails when the cards a shuffled beucase we cannot predict the influence.
   */
  @Test
  public void placesCorrectly() throws IOException {
    BasicSanguineModel model = new BasicSanguineModel();

    List<SanguineCard> redDeck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    List<SanguineCard> blueDeck = DeckParser.makeDeck("docs" + File.separator + "example.deck");

    System.out.println(redDeck.getFirst().getValue());
    model.startGame(
        6,
        5,
        redDeck,
        blueDeck,
        3);

    BasicSanguineTextualView view = new BasicSanguineTextualView(model);

    assertEquals("1", view.toString().substring(2, 3));

    defaultMove(model, redDeck, view, blueDeck);

    //displays card where its supposed to
    assertEquals("R", view.toString().substring(2, 3));
    assertEquals("B", view.toString().substring(6, 7));

    //influence works properly
    assertEquals("1", view.toString().substring(3, 4));
    assertEquals("1", view.toString().substring(5, 6));

    System.out.println(view.toString());

    model.playTurn(1, 0, redDeck.removeFirst());
    assertEquals("1", view.toString().substring(3, 4));

    System.out.println(blueDeck.getFirst().toString());
    model.playTurn(0, 3, blueDeck.removeFirst());
    assertEquals("1", view.toString().substring(3, 4));
    assertEquals("B", view.toString().substring(5, 6));

    System.out.println(view.toString());
  }

  /**
   * this makes two default moves on the board. this is so i can start testing from the "middle" of
   * the game. also serves as a test.
   *
   * @param model model
   * @param redDeck deck 1
   * @param view view
   * @param blueDeck deck 2
   */
  private void defaultMove(BasicSanguineModel model, List<SanguineCard> redDeck,
                                BasicSanguineTextualView view, List<SanguineCard> blueDeck)
      throws IOException {
    System.out.println(view.toString());
    model.playTurn(0, 0, redDeck.removeFirst());

    model.playTurn(0, 4, blueDeck.removeFirst());
  }

  /**
   * makes sure that a card is not placed when its not in a valid spot. this proves that neither
   * the card or the influence are displayed.
   */
  @Test
  public void testPlaceNoCard() throws IOException {
    BasicSanguineModel model = new BasicSanguineModel();

    List<SanguineCard> redDeck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    List<SanguineCard> blueDeck = DeckParser.makeDeck("docs" + File.separator + "example.deck");

    System.out.println(redDeck.getFirst().getValue());
    model.startGame(
        6,
        5,
        redDeck,
        blueDeck,
        3);

    BasicSanguineTextualView view = new BasicSanguineTextualView(model);

    String before = view.toString();

    assertThrows(
        IllegalArgumentException.class,
        () -> model.playTurn(2, 2, redDeck.removeFirst()));

    assertEquals(before, view.toString());

  }

}