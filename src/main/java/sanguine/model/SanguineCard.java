package sanguine.model;

import java.util.List;

/**
 * A Sanguine version of the Card interface.
 *
 * <p>Adds new methods like getCost(), getValue(), getColorInfluence(), getIntInfluence(), and </p>
 * getName().
 *
 * <p>Any new methods to be added to the card interface should be added here.</p>
 */
public interface SanguineCard extends Card {
  /**
   * A method that returns the cost of a Sanguine Card.
   *
   * @return the cost of a card as an Int
   */
  int getCost();

  /**
   * A method that returns the value of a Sanguine Card.
   *
   * @return the value of a card as an Int
   */
  int getValue();

  /**
   * A method that returns the color influence grid of a Sanguine Card.
   *
   * <p>The colors are:</p>
   * Cyan: Influence on the grid
   * Orange: The card itself
   * Gray: No influence on the grid
   *
   * @return the color influence grid as a list of CardColor
   */
  List<List<CardColor>> getColorInfluence();

  /**
   * A method that returns the String influence grid of a Sanguine Card.
   *
   * <p>The characters are:</p>
   * 'C': The card itself
   * 'I': The influence on the grid
   * 'X': No influence on the grid
   *
   * @return the color influence grid as a list of CardColor
   */
  List<String> getStringInfluence();

  /**
   * A method that returns the Integer influence grid of a Sanguine Card.
   *
   * <p>The integers are as:</p>
   * 1: Influence on the grid
   * 0: No influence on the grid
   *
   * @return the color influence grid as a list of Integers
   */
  List<List<Integer>> getIntInfluence();

  /**
   * A method that returns the name of a Sanguine Card.
   *
   * @return the name as a String
   */
  String getName();
}
