package sanguine;

import java.io.IOException;
import sanguine.controller.SanguinePlayerController;
import sanguine.model.BasicSanguineModel;
import sanguine.model.HumanPlayer;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineModel;
import sanguine.model.UserPlayer;
import sanguine.view.SanguineGuiView;
import sanguine.view.SanguineViewFrame;

public class SanguineGameReal {
  public static void main(String[] args) throws IOException {
    SanguineModel model = new BasicSanguineModel();

    UserPlayer player1 = new HumanPlayer(PlayerColor.RED);
    UserPlayer player2 = new HumanPlayer(PlayerColor.BLUE);

    model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);

    SanguinePlayerController controller1 = new SanguinePlayerController(player1, model,
        PlayerColor.RED);
    SanguinePlayerController controller2 = new SanguinePlayerController(player2, model,
        PlayerColor.BLUE);

    SanguineGuiView viewPlayer1 = new SanguineViewFrame(model, controller1, PlayerColor.RED);
    SanguineGuiView viewPlayer2 = new SanguineViewFrame(model, controller2, PlayerColor.BLUE);

    controller1.setView(viewPlayer1);
    controller2.setView(viewPlayer2);

  }
}
