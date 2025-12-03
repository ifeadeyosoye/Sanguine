package sanguine.model;

/**
 * An extension of a pawn specifically for a game of SanguineGame.
 *
 * <p>In this game, a pawn is owned by a player, hence why the pawn color is of type PlayerColor</p>
 * Interface also sets new getter methods to get the color and value of a pawn.
 */
public interface SanguinePawn extends Pawn {
  /**
   * A method that returns the color/owner of the pawn.
   *
   * @return the pawn color as type PlayerColor
   */
  PlayerColor getColor();

  /**
   * A method that returns the value of the pawn.
   *
   * @return the value of the pawn as an int
   */
  int getValue();
}
