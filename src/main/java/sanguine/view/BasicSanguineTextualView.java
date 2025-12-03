package sanguine.view;

import java.io.IOException;
import sanguine.model.BasicSanguineBoardCell;
import sanguine.model.BasicSanguineModel;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineGameBoard;

/**
 * A textual view for a Sanguine game. It renders the current state of the game as a string.
 * String is readable for the user.
 *
 * <p>It renders each row on a new line, documenting the cards, pawns, or empty cells</p>
 * Each line starts and ends with the score for each opposing player
 * When a cell is empty we denote it with "_"
 * When a cell has a card, we put the first letter of the player that placed it "R" or "B"
 * When a cell has pawns, we put the number of pawns "1", "2", or "3"
 */
public class BasicSanguineTextualView implements SanguineTextualView {

  // Private fields
  private final BasicSanguineModel model;

  /**
   * A constructor for a textual view that only takes the model.
   *
   * @param model the Sanguine game model
   */
  public BasicSanguineTextualView(BasicSanguineModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder game = new StringBuilder();
    try {
      SanguineGameBoard board = model.getBoard();

      for (int row = 0; row < board.getRows(); row++) {

        StringBuilder line = new StringBuilder();

        for (int col = 0; col < board.getCols(); col++) {
          addCellToString(board, row, col, line);
        }
        game.append(addScore(line, board, row));
        game.append(System.lineSeparator());
      }
    } catch (IOException exo) {
      throw new IllegalArgumentException("Trouble reading file");
    }


    return game.toString();
  }

  /**
   * A private helper method that helps with adding the correct string for an object in a cell.
   *
   * @param board the game board
   * @param row   the specific row to convert to a String
   * @param col   the specific column to convert to a String
   * @param line  the StringBuilder to add to
   */
  private static void addCellToString(
      SanguineGameBoard board, int row, int col, StringBuilder line) {

    BasicSanguineBoardCell cell = board.getCellAt(row, col);

    int cardValue = cell.getValue();

    if (cardValue != 0) {
      if (cell.getColor() == PlayerColor.RED) {
        line.append("R");
        return;
      }
      line.append("B");
      return;
    }

    if (!cell.getPawns().isEmpty()) {
      line.append(cell.getPawns().size());
      return;
    }

    line.append("_");

  }

  /**
   * A private helper method to calculate and add the score to the textual view.
   *
   * @param line  the StringBuilder to add to
   * @param board the game board
   * @param row   the specific row to convert to a String
   * @return the String with an added score
   */
  private String addScore(StringBuilder line, SanguineGameBoard board, int row) {

    int red = 0;
    int blue = 0;

    for (int i = 0; i < board.getCols(); i++) {
      if (board.getCellAt(row, i).getColor() == PlayerColor.RED) {
        red += board.getCellAt(row, i).getValue();
      } else {
        blue += board.getCellAt(row, i).getValue();
      }
    }
    return red + " " + line.toString() + " " + blue;
  }
}
