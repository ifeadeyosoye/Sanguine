package sanguine.model;

import java.io.IOException;

/**
 * This interface marks the concept of a game board.
 *
 * <p>A game board is a collection of cells and we should be able to add certain objects</p>
 * to a specific place on the board or get objects at a specific place.
 *
 * <p>Additionally, this game board is able to hold scores so we should be able to get the </p>
 * score of a specific row.
 */
public interface GameBoard {

  /**
   * A method that gets a certain cell given an x and y position.
   *
   * <p>This is 0-base indexed</p>
   *
   * @param x represents the row.
   * @param y represents the column
   * @return the specified game board cell
   */
  SanguineBoardCell getCellAt(int x, int y);

  /**
   * A method that adds a card to a cell while specifying the coordinates, card, and player.
   *
   * @param x      the row the card will be added to
   * @param y      the column the card will be added to
   * @param card   the card that will be added
   * @param player the player placing the card
   * @return the card that was added to the cell
   */
  SanguineCard addCardToCell(int x, int y, SanguineCard card, Player player);

  /**
   * A method that adds a given pawn to a specified cell in the game board.
   *
   * @param x      the row the pawn will be added to
   * @param y      the column the pawn will be added to
   * @param player the player placing the pawn
   */
  void addPawnToCell(int x, int y, SanguinePlayer player);

  /**
   * A method that returns the score of a row on the game board.
   *
   * <p>Returns the max number of both players score in that row.</p>
   *
   * @param x the specified row
   * @return the score of the row as an integer
   */
  int getScoreOfRow(int x);

  /**
   * A method that gets color of the player with the max amount of points in a game board row.
   *
   * @param x the row to find the player color with the max points.
   * @return the player color of the player with max points in a row as PlayerColor
   */
  PlayerColor getPlayerColorMaxPoints(int x);

  /**
   * A method that gets the number of rows in a game board.
   *
   * @return the number of rows as an integer
   */
  int getRows();

  /**
   * A method that gets the number of columns in a game board.
   *
   * @return the number of columns as an integer
   */
  int getCols();

  /**
   * A method that gets the size/area of a game board.
   *
   * @return the size/area of the game board as an integer
   */
  int getBoardSize();

  /**
   * A method that returns a copy of the board.
   *
   * <p>Mainly used in model for getting board info in textual view</p>
   *
   * @return a copy of the game board
   */
  GameBoard getBoard() throws IOException;

  /**
   * A method to get the color row score of a certain player in the game.
   *
   * @param color the player color
   * @param row the desired row to check
   * @return the score in the row for that player
   * @throws IllegalArgumentException If the color is null
   */
  int getPlayerColorRowScore(PlayerColor color, int row) throws IllegalArgumentException;

}