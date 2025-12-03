package sanguine.strategies;

import java.io.IOException;
import java.util.List;
import sanguine.model.GameBoard;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.Player;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineGameBoard;
import sanguine.model.SanguineModel;
import sanguine.model.SanguinePlayer;

/**
 * this class holds a strategy for the sanguine model. this is the easiest strategy to implement.
 */
public class FirstSpot implements BasicStrategy {

  /**
   * this strategy chooses the first card and location that can be played on and plays there.
   * takes in the board and hand as parameters. it will return valid coordinates. if there are no
   * valid coordinates, it will return (-1, -1).
   */
  @Override
  public Coordinates choose(ModelReadOnlyInterface model,
                                        PlayerColor color) {

    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    SanguinePlayer player = new SanguinePlayer(List.of(), color, 7);
    List<SanguineCard> hand = model.getPlayerHand(color);
    model.getBoard();
    GameBoard board = model.getBoard();

    for (SanguineCard card : hand) {
      for (int r = 0; r < board.getRows(); r++) {
        for (int c = 0; c < board.getCols(); c++) {
          if (model.placeCardLegal(r, c, card, player)) {
            return new Coordinates(r, c, card);
          }
        }
      }
    }
    return new Coordinates(-1, -1, null);
  }

}
