package sanguine;

import java.io.IOException;
import java.util.List;
import sanguine.controller.SanguinePlayerController;
import sanguine.model.AiPlayer;
import sanguine.model.BasicSanguineModel;
import sanguine.model.DeckParser;
import sanguine.model.HumanPlayer;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.model.UserPlayer;
import sanguine.strategies.FirstSpot;
import sanguine.strategies.MaxOwnership;
import sanguine.strategies.MaximizeRowScore;
import sanguine.strategies.MiniMax;
import sanguine.view.SanguineGuiView;
import sanguine.view.SanguineViewFrame;

/**
 * this is where the game will run. it will be started from here.
 */
public final class SanguineGame {

  /**
   * this is the main method.
   *
   * @param args from the command line.
   *
   * @throws IOException if the deck file is unreachable
   */
  public static void main(String[] args) throws IOException {

    if (args.length < 6) {
      System.exit(1);
    }

    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    String redDeckPath = args[2];
    String blueDeckPath = args[3];
    String redPlayerType = args[4];
    String bluePlayerType = args[5];


    SanguineModel model = new BasicSanguineModel();
    List<SanguineCard> redDeck = setDecks(redDeckPath, model);
    List<SanguineCard> blueDeck = setDecks(blueDeckPath, model);

    model.startGame(rows, cols, redDeck, blueDeck, 7);
    UserPlayer redPlayer = makeUserPlayer(model, PlayerColor.RED, redPlayerType);
    UserPlayer bluePlayer = makeUserPlayer(model, PlayerColor.BLUE, bluePlayerType);

    SanguinePlayerController redController = new SanguinePlayerController(redPlayer, model,
        PlayerColor.BLUE);
    SanguinePlayerController blueController = new SanguinePlayerController(bluePlayer, model,
        PlayerColor.RED);

    SanguineGuiView viewPlayer1 = new SanguineViewFrame(model, redController, PlayerColor.BLUE);
    SanguineGuiView viewPlayer2 = new SanguineViewFrame(model, blueController, PlayerColor.RED);

    redController.setView(viewPlayer1);
    blueController.setView(viewPlayer2);
  }

  /**
   * sets deck to the given parameters or makes the default ones from the model.
   *
   * @param model model
   * @param deckPath path for the bluedeck
   *
   * @throws IOException if the deck file (from the model) is unreadable
   */
  private static List<SanguineCard> setDecks(String deckPath, SanguineModel model)
      throws IOException {
    List<SanguineCard> deck = List.of();
    try {
      deck = DeckParser.makeDeck(deckPath);
    } catch (IllegalArgumentException | IOException e) {
      //we will handle this in the next lines
    }
    if (deck.isEmpty()) {
      deck = model.createDeck();
    }
    return deck;
  }

  /**
   * returns a new player type based off the arguments.
   *
   * @param model model
   * @param color color of player
   * @param type type of player
   *
   * @return a new user player
   */
  private static UserPlayer makeUserPlayer(SanguineModel model, PlayerColor color, String type) {
    String lowerType = type.toLowerCase();
    return switch (lowerType) {
      case "human" -> new HumanPlayer(color);
      case "strategy1" -> new AiPlayer(new FirstSpot(), color, model);
      case "strategy2" -> new AiPlayer(new MaxOwnership(), color, model);
      case "strategy3" -> new AiPlayer(new MaximizeRowScore(), color, model);
      case "strategy4" -> new AiPlayer(new MiniMax(), color, model);
      default -> throw new IllegalArgumentException("invalid player type: " + type);
    };
  }
}
