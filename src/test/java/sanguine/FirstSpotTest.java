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
import sanguine.controller.SanguineStubController;
import sanguine.model.BasicSanguineBoardCell;
import sanguine.model.BasicSanguineCard;
import sanguine.model.BasicSanguineModel;
import sanguine.model.DeckParser;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineBoardCell;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.model.SanguinePlayer;
import sanguine.model.TieException;
import sanguine.strategies.Coordinates;
import sanguine.strategies.FirstSpot;
import sanguine.strategies.MaximizeRowScore;
import sanguine.view.BasicSanguineTextualView;
import sanguine.view.SanguineViewFrame;

/**
 * comprehensive tests for firstspot strategy.
 */
public class FirstSpotTest {

  /**
   * helper method that makes a new model and starts the game.
   *
   * @return a new model that is started.
   *
   * @throws IOException if the deck file cannot be read.
   */
  public SanguineModel makeModel() throws IOException {
    SanguineModel model = new BasicSanguineModel();

    //10
    SanguineCard basicCard = (SanguineCard) model.createDeck().get(10);

    List<SanguineCard> deck1 = new ArrayList<>();
    List<SanguineCard> deck2 = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      SanguineCard newBasicCard = new BasicSanguineCard(basicCard.getName() + i,
          1, basicCard.getValue(), basicCard.getStringInfluence());

      deck1.add(newBasicCard);
      deck2.add(newBasicCard);
    }

    model.startGame(3, 5, deck1, deck2, 7);
    return model;
  }

  /**
   * tests that it makes valid moves.
   *
   * @throws IOException if deck file is not readable
   */
  @Test
  public void testMovesValid() throws IOException {

    BasicSanguineModel model = makeBasicMoves();

    FirstSpot first = new FirstSpot();
    Coordinates coords = first.choose(model, model.getTurn().getColor());

    assertEquals(4, coords.col());
    assertEquals(0, coords.row());
    assertEquals(1, coords.card().getCost());

    model.playTurn(coords.row(), coords.col(), coords.card());

    //red move
    assertEquals(PlayerColor.RED, model.getTurn().getColor());
    coords = first.choose(model, model.getTurn().getColor());
    System.out.println(coords);
    assertEquals(1, coords.col());
    assertEquals(0, coords.row());
    assertEquals(1, coords.card().getCost());

    //it can keep filling the board until the game is deemed to be over.
    while (!model.isGameOver()) {
      MaximizeRowScore max = new MaximizeRowScore();

      coords = max.choose(model, model.getTurn().getColor());
      if (coords.row() == -1) {
        model.passTurn();
        continue;
      }
      model.playTurn(coords.row(), coords.col(), coords.card());
    }
  }

  /**
   * helper method that creates a model and displays it. this is used to make the game start with
   * some moves having been made already.
   *
   * @return the model.
   *
   * @throws IOException if the deck file cannot be read.
   */
  private static BasicSanguineModel makeBasicMoves() throws IOException {
    BasicSanguineModel model = new BasicSanguineModel();

    List<SanguineCard> deck1 = model.createDeck();
    List<SanguineCard> deck2 = model.createDeck();
    model.startGame(3, 5, deck1, deck2, 3);
    model.playTurn(0, 0, deck1.get(3));
    model.passTurn();
    // comment this line out below to see what 2 pawns in a cell look like
    model.playTurn(2, 0, deck1.get(4));

    model.playTurn(2, 4, deck1.get(4));
    model.passTurn();


    SanguineStubController controller = new SanguineStubController(model);
    SanguineViewFrame frame = new SanguineViewFrame(model, controller);
    frame.refresh();
    return model;
  }

  /**
   * this test makes sure that the model returns -1,-1,null as coordinates. this goes until the
   * game is over, because you cannot place a card when the game is over.
   *
   * @throws IOException if the deck file cant be read.
   */
  @Test
  public void noLegalMovesIsCorrect() throws IOException {
    SanguineModel model = makeModel();

    MaximizeRowScore max = new MaximizeRowScore();
    Coordinates coords = max.choose(model, model.getTurn().getColor());
    while (!model.isGameOver()) {
      coords = max.choose(model, model.getTurn().getColor());
      if (coords.card() == null) {
        model.passTurn();
        continue;
      }
      model.playTurn(coords.row(), coords.col(), coords.card());
    }
    System.out.println(model.getPlayerHand(PlayerColor.RED).size());
    printTextView(model);
    assertEquals(-1, coords.row());
    assertEquals(-1, coords.col());
    assertNull(coords.card());
  }

  /**
   * helper method that prints the given model as a text view.
   *
   * @param model model
   */
  private static void printTextView(SanguineModel model) {
    BasicSanguineTextualView view = new BasicSanguineTextualView(model);
    System.out.println(view.toString());
  }
}