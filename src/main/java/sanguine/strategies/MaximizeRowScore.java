package sanguine.strategies;

import java.io.IOException;
import java.util.List;
import sanguine.model.GameBoard;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.Player;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.model.SanguinePlayer;

/**
 * this class holds a method that maximizes score per row. the method will be used by an AI bot
 * to determine the best next move.
 */
public class MaximizeRowScore implements BasicStrategy {


  /**
   * Rows are visited from top-down. If the current player has a lower or equal row-score than
   * their opponent on that row, this strategy chooses the first card and location option that
   * increases their row-score to be greater than the opponent's row-score. If there is no play
   * that would make the current player's row-score greater than the opponent's, move on to the
   * next row. If after visiting all rows, there is still no move to make, report there is no move
   * to make (meaning the player should pass).
   *
   * @param model model that this method is finding the next possible move for.
   *
   * @return coordinates
   */
  @Override
  public Coordinates choose(ModelReadOnlyInterface model,
                                          PlayerColor color) {

    SanguinePlayer player = model.getTurn();
    List<SanguineCard> hand = model.getPlayerHand(color);
    GameBoard board = model.getBoard();

    for (int r = 0; r < board.getRows(); r++) {
      Player oppositePlayer = getOppositePlayer(new SanguinePlayer(List.of(), color, 7));

      int oppositeScore = model.getRowScore(oppositePlayer.getColor(), r);
      int score = model.getRowScore(player.getColor(), r);

      //skip winning rows
      if (oppositeScore < score) {
        continue;
      }

      for (int c = 0; c < board.getCols(); c++) {

        for (SanguineCard card : hand) {

          int calculatedScore = score + card.getValue();

          if (model.placeCardLegal(r, c, card, player) && calculatedScore > oppositeScore) {
            return new Coordinates(r, c, card);
          }
        }
      }

    }
    return new Coordinates(-1, -1, null);
  }


  /**
   * helper method that takes in a player and makes a duplicate of that player object, with only
   * the color differing.
   *
   * @param player to be duplicated
   *
   * @return the duplicated player
   */
  private static Player getOppositePlayer(Player player) {
    if (player.getColor() == PlayerColor.BLUE) {
      return new SanguinePlayer(player.getDeck(), PlayerColor.RED, 7);
    }
    return new SanguinePlayer(player.getDeck(), PlayerColor.BLUE, 7);
  }
}
