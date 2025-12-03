package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguineCard;
import sanguine.model.BasicSanguineModel;
import sanguine.model.DeckParser;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.strategies.Coordinates;
import sanguine.strategies.MaxOwnership;
import sanguine.strategies.MaximizeRowScore;
import sanguine.view.BasicSanguineTextualView;

/**
 * tests for maxownership strategy.
 */
public class MaxOwnershipTest {

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

  @Test
  public void picksCorrectSpot() throws IOException {
    SanguineModel model = makeModel();

    SanguineCard firstCard = model.getPlayerHand(model.getTurn().getColor()).get(1);
    SanguineCard betterCard = new BasicSanguineCard(firstCard.getName(), 1, 3,
        firstCard.getStringInfluence());

    MaxOwnership max = new MaxOwnership();
    Coordinates foundCoords = max(model, model.getTurn().getColor());

    model.playTurn(foundCoords.row(), foundCoords.col(), foundCoords.card());
    printTextView(model);

    assertEquals(1, foundCoords.row());
    assertEquals(0, foundCoords.col());

    //there is a tie between placing the card at the top or bottom, and it chose the top.
    foundCoords = max.choose(model, model.getTurn().getColor());

    model.playTurn(foundCoords.row(), foundCoords.col(), foundCoords.card());
    assertEquals(0, foundCoords.row());
    assertEquals(4, foundCoords.col());

    foundCoords = max.choose(model, model.getTurn().getColor());
    model.playTurn(foundCoords.row(), foundCoords.col(), foundCoords.card());
    printTextView(model);

    //blue
    foundCoords = max.choose(model, model.getTurn().getColor());
    model.playTurn(foundCoords.row(), foundCoords.col(), foundCoords.card());
    printTextView(model);

    model.passTurn();
    //should retunr null becuase there is no square where a card can be placed where blue gains.
    foundCoords = max.choose(model, model.getTurn().getColor());
    assertEquals(null, foundCoords.card());
    assertEquals(-1, foundCoords.row());
    assertEquals(-1, foundCoords.col());
    printTextView(model);
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

    Coordinates coords = MaximizeRowScore.maximizeScore(model, model.getTurn().getColor());
    while (!model.isGameOver()) {
      coords = MaximizeRowScore.maximizeScore(model, model.getTurn().getColor());
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
