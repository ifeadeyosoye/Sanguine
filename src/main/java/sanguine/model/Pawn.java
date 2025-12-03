package sanguine.model;

/**
 * This interface marks the concept of pawns.
 *
 * <p>The only behavior guaranteed by this class is it the toString() method which renders</p>
 * the pawn in a printable format.
 *
 * <p>To string prints it in a way that could be used for other future game implementations.</p>
 */
public interface Pawn {
  /**
   * Renders a pawn's color and value.
   * Here is an example pawn (red pawn with value of 1):
   * Red1
   *
   * @return the formatted pawn
   */
  String toString();
}
