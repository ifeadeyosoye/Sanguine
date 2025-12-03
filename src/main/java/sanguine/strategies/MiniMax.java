package sanguine.strategies;

import java.io.IOException;
import java.util.List;
import jdk.dynalink.linker.support.SimpleLinkRequest;
import sanguine.model.BasicSanguineModel;
import sanguine.model.GameBoard;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineBoardCell;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;

/**
 * this class holds methods for the MiniMax strategy. We try to minimize the maximum benefit our
 * opponent can gain. for this, we will use a very rudimentary implementation.
 * if the coordinates are (-1,-1,null) then that means the player should pass. this will be handled
 * by the method that calls this one.
 */
public class MiniMax {


  /**
   * figures out the strategy that the opponent is using and determines the best row, col, and card
   * that will prevent the opponent from making their best move on the next turn.
   *
   * @param model model
   *
   * @return Coordinate object holding row, col, card.
   *
   * @throws IOException if deck file cannot be read
   */
  public static Coordinates minimizeOps(ModelReadOnlyInterface model) throws IOException {

    PlayerColor opponent = model.getTurn().getColor() == PlayerColor.RED ? PlayerColor.BLUE
        : PlayerColor.RED;

    Strategy strat;
    Coordinates first = executeFirst(model, opponent);
    Coordinates second = executeSecond(model, opponent);
    Coordinates third = executeThird(model, opponent);

    if (getFirstStrategy(model, opponent) && getSecondStrategy(model, opponent,
        model.getTurn().getColor())) {
      if (first.row() < second.row()) {
        return first;
      }
      if (first.row() > second.row()) {
        return second;
      }
      if (first.col() < second.col()) {
        return first;
      }
      if (first.col() > second.col()) {
        return second;
      }

      List<SanguineCard> myHand = model.getPlayerHand(model.getTurn().getColor());

      for (int i = 0; i < myHand.size(); i++) {
        if (myHand.get(i) == first.card()) {
          return first;
        }
        if (myHand.get(i) == second.card()) {
          return second;
        }
      }
      return third;
    } else if (getFirstStrategy(model, opponent)) {
      return first;
    } else if (getSecondStrategy(model, opponent, model.getTurn().getColor())) {
      return second;
    } else {
      return third;
    }
  }

  /**
   * goes through every cell and adds the amount of pawns or cards to a counter. calculates if more
   * than half of the cards/pawns are in the top half of the rows.
   *
   * @param model model
   * @param opponent not the current player
   *
   * @return if the strategy is being used, a boolean.
   * @throws IOException if the deck file cannot be read
   */
  public static boolean getFirstStrategy(ModelReadOnlyInterface model, PlayerColor opponent)
      throws IOException {
    int total = 0;
    int firstHalf = 0;

    GameBoard board = model.getBoard();

    for (int r = 0; r < board.getRows(); r++) {

      for (int c = 0; c < board.getCols(); c++) {

        SanguineBoardCell cell = model.getCellAt(r, c);
        if (cell.getColor() != opponent) {
          continue;
        }
        if (r <= board.getRows()/2) {
          if (cell.containsCard()) {
            firstHalf += 1;
          } else {
            firstHalf += cell.getPawns().size();
          }
        }
        if (cell.containsCard()) {
          total += 1;
        } else {
          total += cell.getPawns().size();
        }
      }
    }
    return (double) firstHalf /total >= .5;
  }

  /**
   * calculates how many rows the opponent is winning. returns true if the opponent is winning
   * half or more of the rows.
   *
   * @param model model
   * @param opponent opponents color
   * @param currentPlayer currentPlayer color
   *
   * @return a boolean if this strategy is being used.
   *
   * @throws IOException if the deck file cannot be read
   */
  public static boolean getSecondStrategy(ModelReadOnlyInterface model, PlayerColor opponent,
                                   PlayerColor currentPlayer)
      throws IOException {
    int winning = 0;

    for (int r = 0; r < model.getBoard().getRows(); r++) {
      if (model.getRowScore(opponent, r) > model.getRowScore(currentPlayer, r)) {
        winning++;
      }
    }
    return (double) winning/model.getBoard().getRows() >= .5;
  }

  /**
   * determines if and where a card should be placed to counteract the opponent using the first
   * strategy on their next turn.
   *
   * @param model model
   * @param opponent ops color
   *
   * @return coordinates, either (-1,-1,null)
   *
   * @throws IOException if the deck file cannot be read
   */
  public static Coordinates executeFirst(ModelReadOnlyInterface model, PlayerColor opponent)
      throws IOException {
    FirstSpot first = new FirstSpot();
    Coordinates coords = first.choose(model, opponent);
    Coordinates coords1 = blockedDaOps(model, coords);
    return coords1;
  }

  /**
   * checks to see if there is a possible move that we can do to block the opponent from using
   * strategy 1.
   *
   * @param model model
   * @param coords contains the row and col where the opponent will go next if they went to use S1
   *
   * @return row, col, and card to place at row,col to stop opponent.
   *
   * @throws IOException if deck file cannot be read.
   */
  private static Coordinates blockedDaOps(ModelReadOnlyInterface model, Coordinates coords)
      throws IOException {
    for (SanguineCard card : model.getPlayerHand(model.getTurn().getColor())) {
      for (int r = 0; r < model.getBoard().getRows(); r++) {
        for (int c = 0; c < model.getBoard().getCols(); c++) {
          GameBoard board = SimulatePlacement.pretendPlaceCard(model.getBoard(), card, r, c,
              model.getTurn().getColor(), model);
          if (board.getCellAt(coords.row(),
              coords.col()).getColor() == model.getTurn().getColor()) {
            return new Coordinates(r, c, card);
          }
        }
      }
    }
    return new Coordinates(-1, -1, null);
  }

  /**
   * determines if and where a card should be placed to counteract the opponent using the second
   * strategy on their next turn.
   *
   * @param model model
   * @param opponent ops color
   *
   * @return coordinates, either (-1,-1,null)
   *
   * @throws IOException if the deck file cannot be read
   */
  public static Coordinates executeSecond(ModelReadOnlyInterface model, PlayerColor opponent)
      throws IOException {
    MaximizeRowScore max = new MaximizeRowScore();
    Coordinates coords = max.choose(model, opponent);
    return blockedDaOps(model, coords);
  }

  /**
   * determines if and where a card should be placed to counteract the opponent using the second
   * strategy on their next turn.
   *
   * @param model model
   * @param opponent ops color
   *
   * @return coordinates, either (-1,-1,null)
   *
   * @throws IOException if the deck file cannot be read
   */
  public static Coordinates executeThird(ModelReadOnlyInterface model, PlayerColor opponent)
      throws IOException {
    MaxOwnership max = new MaxOwnership();
    Coordinates coords = max.choose(model, opponent);
    return blockedDaOps(model, coords);
  }
}
