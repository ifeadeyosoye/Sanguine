package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static sanguine.model.CardColor.CYAN;
import static sanguine.model.CardColor.GRAY;
import static sanguine.model.CardColor.ORANGE;

import java.util.List;
import org.junit.Test;
import sanguine.model.BasicSanguineCard;
import sanguine.model.CardColor;

/**
 * A class for testing the Card and Advanced Card which extends Card.
 *
 * <p>Currently there is only one card implementation, BasicSanguineCard.</p>
 * Therefore this class will focus on testing that implementation.
 */
public class CardTests {
  // Private fields used for testing
  private final List<String> influenceGrid1 = List.of("XXXXI", "XXXXX", "XXCXX", "XXXXX", "XXXXI");
  private final List<String> influenceGrid2 = List.of("XXXXX", "XXXIX", "XXCXX", "XXIIX", "IXXXX");
  private final List<String> invalidInfluenceTooSmall1 = List.of("XXXXX", "XXXIX", "XXCXX",
      "XXIIX");
  private final List<String> invalidInfluenceTooSmall2 = List.of("XXXXX", "XXXI", "XXCXX", "XXIIX",
      "IXXXX");
  private final List<String> invalidInfluenceCardWrongPlace = List.of("XXXXX", "XXXIX", "XXXXX",
      "XCIIX",
      "IXXXX");
  private final List<String> invalidInfluenceRandomChar = List.of("XXXXX", "XXXIX", "XXXXX",
      "XCIIK",
      "IXXXX");
  private final List<String> invalidInfluenceExtraC = List.of("XXXXX", "XXXIX", "XXXXX", "XCIIX",
      "IXCXX");
  private final List<List<CardColor>> influenceColorGrid1 = List.of(List.of(GRAY, GRAY, GRAY, GRAY,
          CYAN),
      List.of(GRAY, GRAY, GRAY, GRAY, GRAY), List.of(GRAY, GRAY, ORANGE, GRAY, GRAY),
      List.of(GRAY, GRAY, GRAY, GRAY, GRAY), List.of(GRAY, GRAY, GRAY, GRAY, CYAN));
  private final List<List<CardColor>> influenceColorGrid2 = List.of(List.of(GRAY, GRAY, GRAY, GRAY,
          GRAY),
      List.of(GRAY, GRAY, GRAY, CYAN, GRAY), List.of(GRAY, GRAY, ORANGE, GRAY, GRAY),
      List.of(GRAY, GRAY, CYAN, CYAN, GRAY), List.of(CYAN, GRAY, GRAY, GRAY, GRAY));
  private final List<List<Integer>> influenceIntGrid1 = List.of(List.of(0, 0, 0, 0, 1),
      List.of(0, 0, 0, 0, 0), List.of(0, 0, 0, 0, 0),
      List.of(0, 0, 0, 0, 0), List.of(0, 0, 0, 0, 1));
  private final List<List<Integer>> influenceIntGrid2 = List.of(List.of(0, 0, 0, 0, 0),
      List.of(0, 0, 0, 1, 0), List.of(0, 0, 0, 0, 0),
      List.of(0, 0, 1, 1, 0), List.of(1, 0, 0, 0, 0));
  private final String card1ToString = "Cool 2 10\n" + "XXXXI\n" + "XXXXX\n" + "XXCXX\n"
      + "XXXXX\n"
      + "XXXXI\n";
  private final String card2ToString = "Happy 3 5\n" + "XXXXX\n" + "XXXIX\n" + "XXCXX\n"
      + "XXIIX\n"
      + "IXXXX\n";

  /**
   * This tests that the constructor is properly creating a card.
   */
  @Test
  public void testValidConstruction() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);
    BasicSanguineCard card2 = new BasicSanguineCard("Happy", 3, 5,
        influenceGrid2);

    assertEquals("Cool", card1.getName());
    assertEquals("Happy", card2.getName());

    assertEquals(2, card1.getCost());
    assertEquals(3, card2.getCost());

    assertEquals(10, card1.getValue());
    assertEquals(5, card2.getValue());

    assertEquals(influenceGrid1, card1.getStringInfluence());
    assertEquals(influenceGrid2, card2.getStringInfluence());
  }

  /**
   * This tests an invalid construction of a card where the name is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionName() {
    BasicSanguineCard card1 = new BasicSanguineCard(null, 2, 10, influenceGrid1);
  }

  /**
   * This tests an invalid construction of a card where the cost is less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionCost1() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 0, 10,
        influenceGrid1);
  }

  /**
   * This tests an invalid construction of a card where the cost is greater than 3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionCost2() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 4, 10,
        influenceGrid1);
  }

  /**
   * This tests an invalid construction of a card where the value is 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionValue1() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 0, influenceGrid1);
  }

  /**
   * This tests an invalid construction of a card where the value is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionValue2() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, -3,
        influenceGrid1);
  }

  /**
   * This tests an invalid construction of a card where the influence grid is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionInfluenceNull() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        null);
  }

  /**
   * This tests an invalid construction of a card where the influence grid is empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionInfluenceEmpty() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10, List.of());
  }

  /**
   * This tests an invalid construction of a card where the influence grid doesn't have enough rows.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionInfluenceRows() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        invalidInfluenceTooSmall1);
  }

  /**
   * This tests an invalid construction of a card where the influence strings are the wrong size.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionInfluenceStringsSize() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        invalidInfluenceTooSmall2);
  }

  /**
   * Tests an invalid construction of a card where the card is in the wrong place in influence grid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionInfluenceCenterWrongPlace() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        invalidInfluenceCardWrongPlace);
  }

  /**
   * Tests an invalid construction of a card where there is a random char in the influence grid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionInfluenceRandomChar() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        invalidInfluenceRandomChar);
  }

  /**
   * Tests an invalid construction of a card where there is an extra C in the influence grid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionInfluenceExtraC() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        invalidInfluenceExtraC);
  }

  /**
   * Tests that getCost() returns the cost of the card.
   */
  @Test
  public void testGetCost() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);

    assertEquals(2, card1.getCost());
  }

  /**
   * Tests that getValue() returns the value of the card.
   */
  @Test
  public void testGetValue() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);

    assertEquals(10, card1.getValue());
  }

  /**
   * Tests that getName() returns the name of the card.
   */
  @Test
  public void testGetName() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);

    assertEquals("Cool", card1.getName());
  }

  /**
   * Tests that getColorInfluence() properly returns the color influence grid of the card.
   */
  @Test
  public void testGetColorInfluence() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);
    BasicSanguineCard card2 = new BasicSanguineCard("Happy", 3, 5,
        influenceGrid2);

    assertEquals(influenceColorGrid1, card1.getColorInfluence());
    assertEquals(influenceColorGrid2, card2.getColorInfluence());
  }

  /**
   * Tests that getColorInfluence() properly returns the color influence grid of the card.
   */
  @Test
  public void testGetIntInfluence() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);
    BasicSanguineCard card2 = new BasicSanguineCard("Happy", 3, 5,
        influenceGrid2);

    assertEquals(influenceIntGrid1, card1.getIntInfluence());
    assertEquals(influenceIntGrid2, card2.getIntInfluence());
  }

  /**
   * Tests that toString() properly returns the string of the card.
   */
  @Test
  public void testToString() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);
    BasicSanguineCard card2 = new BasicSanguineCard("Happy", 3, 5,
        influenceGrid2);

    assertEquals(card1ToString, card1.toString());
    assertEquals(card2ToString, card2.toString());
  }

  /**
   * Tests that equals() properly compares whether two cards are the same or not.
   */
  @Test
  public void testEquals() {
    BasicSanguineCard card1 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);
    BasicSanguineCard card2 = new BasicSanguineCard("Cool", 2, 10,
        influenceGrid1);
    BasicSanguineCard card3 = new BasicSanguineCard("Happy", 3, 5,
        influenceGrid2);

    assertEquals(card1, card2);
    assertNotEquals(card1, card3);
  }
}
