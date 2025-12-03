package sanguine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sanguine.model.BasicSanguinePawn;
import sanguine.model.PlayerColor;

/**
 * A test class for pawns that are used in a game of Sanguine.
 * This tests BasicSanguinePawn and Pawn class and interface respectively
 */
public class PawnTests {

  /**
   * This tests that an exception is thrown when the pawn color is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction() {
    BasicSanguinePawn pawn = new BasicSanguinePawn(null);
  }

  /**
   * This tests that a pawn is constructed properly.
   * This also tests that getValue and getColor return the correct values.
   */
  @Test
  public void validConstructionWithValueAndColor() {
    BasicSanguinePawn pawn1 = new BasicSanguinePawn(PlayerColor.RED);
    BasicSanguinePawn pawn2 = new BasicSanguinePawn(PlayerColor.BLUE);

    assertEquals(1, pawn1.getValue());
    assertEquals(1, pawn2.getValue());

    assertEquals(PlayerColor.RED, pawn1.getColor());
    assertEquals(PlayerColor.BLUE, pawn2.getColor());
  }
}
