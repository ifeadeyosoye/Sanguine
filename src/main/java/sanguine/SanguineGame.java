package sanguine;

import java.io.File;
import java.io.IOException;
import java.util.List;
import sanguine.controller.SanguineStubController;
import sanguine.controller.StubController;
import sanguine.model.*;
import sanguine.view.BasicSanguineTextualView;
import sanguine.view.Listener;
import sanguine.view.SanguineViewFrame;

/**
 * This contains the main method to our program. this is the doorway.
 */
public class SanguineGame {

  /**
   * This is the method that will start our program.
   *
   * @param args are the input from the terminal. we will require this to be in the following
   *             format.
   */
  public static void main(String[] args) throws IOException {

    BasicSanguineModel model = new BasicSanguineModel();
    List<SanguineCard> deck;

    if (args.length == 0) {
      System.out.println("using default deck");
      deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    } else {

      File file = new File(args[0]);
      if (file.exists()) {
        try {
          DeckParser.checkValidDeck(DeckParser.makeDeck(args[0]));
          deck = DeckParser.makeDeck(args[0]);
        } catch (IOException e) {
          throw new RuntimeException(e);
        } catch (IllegalStateException e) {
          System.out.println("deck path invalid. using default deck");
          deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
        }
      } else {
        deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
      }
    }
    model.startGame(3, 5, deck, deck, 5);

    Listener controller = new SanguineStubController(model);
    SanguineViewFrame view = new SanguineViewFrame(model, controller, PlayerColor.RED);

    while (!model.isGameOver()) {
      
      System.out.println(view.toString());

      canPlayTurn(model);
    }
  }

  /**
   * This loops through the board and finds the first valid place a player can go.
   * When it finds that place, it
   */
  private static void canPlayTurn(BasicSanguineModel model) {
    SanguinePlayer player = model.getTurn();

    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 5; c++) {
        try {
          model.playTurn(r, c, player.drawHandToBoard());

          try {
            player.deckToHand(1);
          } catch (IllegalArgumentException | IllegalStateException e) {
            return;
          }
        } catch (IllegalStateException | IllegalArgumentException e) {
          continue;
        }
      }
    }
    model.passTurn();
  }
}
