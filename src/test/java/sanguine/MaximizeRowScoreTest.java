package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sanguine.controller.SanguineStubController;
import sanguine.controller.StubController;
import sanguine.model.BasicSanguineCard;
import sanguine.model.BasicSanguineModel;
import sanguine.model.Player;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.strategies.Coordinates;
import sanguine.strategies.FirstSpot;
import sanguine.strategies.MaximizeRowScore;
import sanguine.view.BasicSanguineTextualView;
import sanguine.view.SanguineTextualView;
import sanguine.view.SanguineViewFrame;

/**
 * comprehensive tests for MaximizeRowScore class.
 */
public class MaximizeRowScoreTest {

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
   * test to make sure this selects the expected move given a preconstructed board with moves
   * already on it.
   *
   * @throws IOException if deck file cannot be read.
   */
  @Test
  public void makeValidMove() throws IOException {
    SanguineModel model = makeModel();

    //assertEquals(PlayerColor.BLUE, model.getTurn().getColor());
    model.passTurn();
    MaximizeRowScore max = new MaximizeRowScore();
    Coordinates coords = max.choose(model, model.getTurn().getColor());

    assertEquals(PlayerColor.BLUE, model.getTurn().getColor());
    assertEquals(0, model.getRowScore(PlayerColor.BLUE, 0));

    assertEquals(0, coords.row());
    assertEquals(4, coords.col());

    model.playTurn(coords.row(), coords.col(), coords.card());

    //plays until the game is determined to be over. if this fails then this strategy doesnt work.
    while (!model.isGameOver()) {
        Coordinates coords1 = max.choose(model, model.getTurn().getColor());
      if (coords.row() == -1) {
        model.passTurn();
        continue;
      }
      model.playTurn(coords.row(), coords.col(), coords.card());
    }
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
        Coordinates coords1 = max.choose(model, model.getTurn().getColor());
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
