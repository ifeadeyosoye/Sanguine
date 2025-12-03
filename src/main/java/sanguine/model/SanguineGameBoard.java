package sanguine.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This is a class representing a SanguineGame game board.
 *
 * <p>The actual game board is represented as a 2D array</p>
 * An array is used because the game board size should be immutable
 *
 * <p>Each row will contain cell objects.</p>
 * There will be various methods that will get the current state of the game,
 * i.e. current objects in a cell, row scores, etc.
 */
public class SanguineGameBoard implements GameBoard {

  private BasicSanguineBoardCell[][] board;

  //the rows and cols and invariant.
  private int rows;
  private int cols;

  /**
   * A constructor that checks the validity of width and height before initializing the board.
   *
   * <p>The amount of columns must be odd and greater than one.</p>
   * The amount of rows must be greater than zero.
   *
   * @param rows the amount of rows
   * @param cols the amount of columns
   */
  public SanguineGameBoard(int rows, int cols) {
    if (cols % 2 != 1 || cols == 1) {
      throw new IllegalArgumentException("invalid cols");
    }
    if (rows < 1) {
      throw new IllegalArgumentException("invalid rows");
    }

    board = new BasicSanguineBoardCell[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        board[r][c] = new BasicSanguineBoardCell();
      }
    }

    this.cols = cols;
    this.rows = rows;
  }

  /**
   * A constructor that takes in a given board and checks the validity before accepting it.
   *
   * <p>The amount of columns must be odd and greater than one.</p>
   * The amount of rows must be greater than zero.
   *
   * @param board the given game board the amount of rows
   * @param rows  the amount of rows
   * @param cols  the amount of columns
   */
  public SanguineGameBoard(int rows, int cols, BasicSanguineBoardCell[][] board) {
    if (cols % 2 != 1 || cols == 1) {
      throw new IllegalArgumentException("invalid cols");
    }
    if (rows < 1) {
      throw new IllegalArgumentException("invalid rows");
    }

    board = new BasicSanguineBoardCell[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        board[r][c] = new BasicSanguineBoardCell();
      }
    }
  }

  @Override
  public BasicSanguineBoardCell getCellAt(int row, int col) {
    areCoordsValid(row, col);

    return board[row][col];
  }

  @Override
  public SanguineCard addCardToCell(int row, int col, SanguineCard card, Player player) {
    areCoordsValid(row, col);

    if (board[row][col] == null) {
      throw new IllegalStateException("cannot add card to cell with no pawns.");
    }
    board[row][col].placeCard(card, player.getColor());
    return card;
  }

  @Override
  public void addPawnToCell(int row, int col, SanguinePlayer player) {
    areCoordsValid(row, col);

    board[row][col].placePawn(player);
  }

  @Override
  public int getScoreOfRow(int row) {
    Result result = getResult(row);

    return Math.max(result.red(), result.blue());
  }

  @Override
  public PlayerColor getPlayerColorMaxPoints(int row) {
    Result result = getResult(row);

    if (result.red > result.blue) {
      return PlayerColor.RED;
    } else if (result.blue > result.red) {
      return PlayerColor.BLUE;
    } else {
      throw new TieException("Tie when calculating row score!");
    }
  }

  @Override
  public int getPlayerColorRowScore(PlayerColor color, int row) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Player Color is null!");
    }

    Result result = getResult(row);

    switch (color) {
      case PlayerColor.BLUE:
        return result.blue;

      case PlayerColor.RED:
        return result.red;

      default:
        throw new IllegalStateException("Couldn't get PlayerColor row score");
    }
  }

  @Override
  public int getRows() {
    return board.length;
  }

  @Override
  public int getCols() {
    return board[0].length;
  }

  @Override
  public int getBoardSize() {
    return getCols() * getRows();
  }

  @Override
  public SanguineGameBoard getBoard() {
    SanguineGameBoard copy = new SanguineGameBoard(getRows(), getCols());

    for (int r = 0; r < getRows(); r++) {
      for (int c = 0; c < getCols(); c++) {
        BasicSanguineBoardCell tempCell = this.getCellAt(r, c);

        if (tempCell.getColor() == null) {
          continue;
        }

        //List<SanguineCard> fakeDeck = DeckParser.makeDeck("docs"
            //+ File.separator + "example.deck");
        SanguinePlayer tempPlayer = new SanguinePlayer(List.of(), tempCell.getColor(), 3);


        //if the cell has a card
        try {
          if (tempCell.getCard() != null) {
            for (int i = 0; i < tempCell.getCard().getCost(); i++) {
              copy.addPawnToCell(r, c, tempPlayer);
            }
            copy.addCardToCell(r, c, tempCell.getCard(), tempPlayer);
            continue;
          }
        } catch (IllegalStateException ignored) {
          System.out.print("");
        }

        for (int i = 0; i < tempCell.getPawns().size(); i++) {
          copy.addPawnToCell(r, c, tempPlayer);
        }
      }
    }
    return copy;
  }

  // Private Methods:

  /**
   * A method that makes sure given coordinates are valid within the board.
   *
   * @param row row that needs to be checked
   * @param col column that needs to be checked
   */
  private void areCoordsValid(int row, int col) {

    if (row >= board.length || row < 0) {
      throw new IllegalArgumentException("row invalid: " + row);
    }
    if (col >= board[0].length || col < 0) {
      throw new IllegalArgumentException("column invalid " + col);
    }
  }

  /**
   * A method that returns a record Result holding red and blue scores in the row.
   * Score is calculated by getting the value of the card at each cell for both players.
   *
   * @param row the row we are calculating the scores from.
   * @return a record of score results for both players
   */
  private Result getResult(int row) {

    int red = 0;
    int blue = 0;

    if (row >= board.length || row < 0) {
      throw new IllegalArgumentException("row invalid: " + row);
    }

    for (BasicSanguineBoardCell cell : board[row]) {
      if (cell.containsCard()) {
        if (cell.getColor() == PlayerColor.BLUE) {
          blue += cell.getCard().getValue();
        } else {
          red += cell.getCard().getValue();
        }
      }
    }
    Result result = new Result(red, blue);
    return result;
  }

  /**
   * This is a record object that is used to hold both red and blue player scores.
   *
   * @param red  red player score
   * @param blue blue player score
   */
  private record Result(int red, int blue) {
  }
}