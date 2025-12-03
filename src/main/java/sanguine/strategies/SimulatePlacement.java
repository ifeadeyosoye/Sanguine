package sanguine.strategies;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.BasicSanguineBoardCell;
import sanguine.model.BasicSanguineModel;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineGameBoard;

/**
 * this code simulates placing a card in a cell on a model. uses similar code to that in the model.
 */
public class SimulatePlacement {

  /**
   * helper method that simulates the influence when placing a card down. refactored code from
   * the model that i wrote. very similar logic by slighty different, yeieldig slightly diff impls.
   *
   * @param board board
   * @param card card
   * @param row row
   * @param col col
   * @param color color of player
   * @param model so that we can use model.getTurn()
   */
  public static SanguineGameBoard pretendPlaceCard(SanguineGameBoard board, SanguineCard card, int row,
                                       int col, PlayerColor color, ModelReadOnlyInterface model) {

    //calculates the offset of coords. which cells will contain pawns relative to the cell
    //where the card was placed
    List<InfluenceCoords> coords = getOffSetCoords(card);

    //determines which cells in the board will actually be changed. accounts for edges and OofBs.
    List<InfluenceCoords> finalCoords = validate(board, row, col, coords);

    //goes through finalcoords and adds pawns to each cell if it permits
    for (InfluenceCoords coord : finalCoords) {
      try {
        BasicSanguineBoardCell cell = board.getCellAt(coord.row, coord.col);
        try {
          cell.getCard();
          continue;
        } catch (Exception ignored) {}
        if (cell.getPawns().isEmpty() || cell.getPawns().size() == 1
            || cell.getPawns().size() == 2) {
          cell.placePawn(model.getTurn());
        }
        cell.changeColorsOfPawns(model.getTurn());
      } catch (Exception ignored) {}
    }
    return board;
  }

  /**
   * A method that calculates new coordinates and validates that they are in the board.
   * If they are not, it skips that influence.
   *
   * @param coords a list of differences between the coordinates of a card's influence
   *               cells and its relative center
   * @param board  the game board
   * @param row    the row of where the placed card is
   * @param col    the column of where the placed card is
   * @return a list of (row,column) pairs that will influence the board.
   */
  public static List<InfluenceCoords> validate(SanguineGameBoard board, int row, int col,
                                                List<InfluenceCoords> coords) {

    List<InfluenceCoords> newCoords = new ArrayList<>();

    for (InfluenceCoords coord : coords) {

      int newRow = row + coord.row;
      int newCol = col + coord.col;

      if (newRow > board.getRows() || row < 0) {
        continue;
      }
      if (newCol >= board.getCols() || col < 0) {
        continue;
      }
      newCoords.add(new InfluenceCoords(newRow, newCol));
    }
    return newCoords;
  }

  /**
   * A method used to get coordinates of the cells that a card can possibly influence.
   *
   * @param card the card that will be checked for its influence coordinates
   * @return a list of influence coordinates
   */
  public static List<InfluenceCoords> getOffSetCoords(SanguineCard card) {
    List<InfluenceCoords> coords = new ArrayList<>();

    for (int row = 0; row < card.getIntInfluence().size(); row++) {

      List<Integer> r = card.getIntInfluence().get(row);

      for (int col = 0; col < r.size(); col++) {

        if (r.get(col) == 1) {
          coords.add(
              new InfluenceCoords(row - r.size() / 2, col - card.getIntInfluence().size() / 2));
        }
      }
    }

    return coords;
  }

  /**
   * the record used in model. represnets row and column of the board
   *
   * @param row row
   * @param col col
   */
  private record InfluenceCoords(int row, int col) {}

}
