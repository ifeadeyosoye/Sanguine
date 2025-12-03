package sanguine.model;

/**
 * An implementation of a pawn for a game of SanguineGame.
 *
 * <p>In a game of SanguineGame, a pawn is of value one and is owned by a player (red or blue).</p>
 */
public class BasicSanguinePawn implements SanguinePawn {
  // Private fields:
  private final PlayerColor color;
  private final int value = 1;

  // Constructors:

  /**
   * A constructor for a pawn which sets its color.
   *
   * @param color the pawn's color
   * @throws IllegalArgumentException when the color is null
   */
  public BasicSanguinePawn(PlayerColor color) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Color for pawn is invalid!");
    }

    this.color = color;
  }

  @Override
  public PlayerColor getColor() {
    return color;
  }

  @Override
  public int getValue() {
    return value;
  }
}
