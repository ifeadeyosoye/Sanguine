package sanguine.strategies;

import static sanguine.strategies.SimulatePlacement.pretendPlaceCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sanguine.model.BasicSanguineBoardCell;
import sanguine.model.BasicSanguineModel;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.Player;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineGameBoard;
import sanguine.model.SanguineModel;
import sanguine.model.SanguinePlayer;

/**
 * this class holds methods that use a strategy that maximizes the amount of squares that a player
 * owns.
 */
public class MaxOwnership implements BasicStrategy {

  /**
   * chooses the row, column, and card combination that maximizes the amount of cells that the
   * player will own. mimes every single possible condition: places every in every cell and
   * calculates the amount of cells it would have ownership of.
   *
   * @param model model
   *
   * @return a coordinate object which holds a row, column, and card where the user should go.
   *
   * @throws IOException if the deck file cannot be read.
   */
  @Override
  public Coordinates choose(ModelReadOnlyInterface model,
                            PlayerColor color) throws IOException {

    int maxControl = -1;
    Coordinates maxCoords = new Coordinates(-1, -1, null);

    int rows = model.getBoard().getRows();
    int cols = model.getBoard().getCols();

    for (SanguineCard currentCard : model.getPlayerHand(color)) {
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {

          int tempMax = getOwnershipAfterPlaceCard(model, currentCard, r, c, color);

          if (tempMax > maxControl
              || (tempMax == maxControl && r < maxCoords.row())
              || (tempMax == maxControl && r == maxCoords.row() && c < maxCoords.col())) {
            maxControl = tempMax;
            maxCoords = new Coordinates(r, c, currentCard);
          }
        }
      }
    }
    return maxCoords;
  }

  /**
   * helper method that creates a copy of the given model's board and places the given card in the
   * cell at the specified row and col, and then calculates how many cells the player would own.
   *
   * @param model model
   * @param card the card that will be placed at row and col
   * @param row the row card is placed at
   * @param col the col the card is placed at
   *
   * @return a counter of how many cells the player owns AFTER card is placed at (row, col)
   *
   * @throws IOException if the deck file cannot be read.
   */
  private static int getOwnershipAfterPlaceCard(ModelReadOnlyInterface model, SanguineCard card,
                                                int row, int col, PlayerColor color)
      throws IOException {

    SanguineGameBoard board = model.getBoard().getBoard();
    SanguinePlayer tempPlayer = new SanguinePlayer(List.of(), color, 7);
    if (!model.placeCardLegal(row, col, card, tempPlayer)) {
      return -1;
    }

    board.getCellAt(row, col).placeCard(card, model.getTurn().getColor());

    pretendPlaceCard(board, card, row, col, model.getTurn().getColor(), model);
    int counter = 0;

    for (int r = 0; r < board.getRows(); r++) {
      for (int c = 0; c < board.getCols(); c++) {
        if (board.getCellAt(r, c).getColor() == model.getTurn().getColor()) {
          counter++;
        }
      }
    }
    return counter;
  }

}
