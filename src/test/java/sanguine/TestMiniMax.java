package sanguine;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguineCard;
import sanguine.model.BasicSanguineModel;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.strategies.Coordinates;
import sanguine.strategies.MiniMax;
import sanguine.view.BasicSanguineTextualView;

/**
 * tests for minimax.
 */
public class TestMiniMax {

  @Test
  public void testValidReturn() throws IOException {
    SanguineModel model = makeModel();

    MiniMax miniMax = new MiniMax();
    Coordinates coords = miniMax.choose(model, model.getTurn().getColor());

    System.out.println(coords);
  }

  /**
   * helper method that makes a new model and starts the game.
   *
   * @return a new model that is started.
   *
   * @throws IOException if the deck file cannot be read.
   */
  public SanguineModel makeModel() throws IOException {
    SanguineModel model = new BasicSanguineModel();

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
   * helper method that prints the given model as a text view.
   *
   * @param model model
   */
  private static void printTextView(SanguineModel model) {
    BasicSanguineTextualView view = new BasicSanguineTextualView(model);
    System.out.println(view.toString());
  }
}
