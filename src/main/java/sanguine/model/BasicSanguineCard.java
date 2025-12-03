package sanguine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of SanguineGame card which extends Card.
 *
 * <p>Holds values like the card's name, cost, value, and influence grid.</p>
 * The constructor sets all of these values above and verifies if the influence grid is valid.
 *
 * <p>This class also overrides the equals and hashcode functions in Java 21.</p>
 */
public class BasicSanguineCard implements SanguineCard {
  // Private Fields:
  private final String name;
  private final int cost;
  private final int value;

  private final List<String> influenceStr;
  private List<List<Integer>> influenceInt;
  private List<List<CardColor>> influenceColor;


  /**
   * A constructor for a Basic SanguineGame Card that takes in its name, cost, value, and influence.
   *
   * @param name         the name of card as a String
   * @param cost         the cost of the card as an int
   * @param value        the value of the card as an int
   * @param influenceStr the influence grid as a list of Strings
   */
  public BasicSanguineCard(String name, int cost, int value, List<String> influenceStr)
      throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Card name can not be null!");
    }

    if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("Card cost is invalid!");
    }

    if (value <= 0) {
      throw new IllegalArgumentException("Card value is invalid!");
    }

    if (influenceStr == null || influenceStr.isEmpty()) {
      throw new IllegalArgumentException("Card influence grid is needed!");
    }

    validInfluenceGrid(influenceStr);

    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influenceStr = influenceStr;

    setIntAndColorInfluence(influenceStr);
  }

  @Override
  public int getCost() {
    return cost;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<List<CardColor>> getColorInfluence() {
    return influenceColor;
  }

  @Override
  public List<String> getStringInfluence() {
    return influenceStr;
  }

  @Override
  public List<List<Integer>> getIntInfluence() {
    return influenceInt;
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();

    output.append(name + " " + cost + " " + value + System.lineSeparator());

    for (String str : influenceStr) {
      output.append(str).append(System.lineSeparator());
    }
    return output.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Card)) {
      return false;
    }
    return this.toString().equals(other.toString());
  }


  @Override
  public int hashCode() {
    return Objects.hash(name, cost, value, influenceStr);
  }

  // Private Methods:

  /**
   * A method that checks if an influence grid is valid.
   *
   * <p>An influence grid is valid if it is a 5x5 with only the characters X, I, and C</p>
   * There also must be only one C in the grid.
   *
   * @param influenceStr the influence grid as a list of Strings for each row
   */
  private void validInfluenceGrid(List<String> influenceStr) {
    if (influenceStr.size() != 5) {
      throw new IllegalArgumentException("Card's influence grid is invalid!");
    }

    if (influenceStr.get(2).length() < 5) {
      throw new IllegalArgumentException("Invalid influence grid");
    }
    if (!(influenceStr.get(2).charAt(2) == 'C')) {
      throw new IllegalArgumentException("C needs to be at the center");
    }

    for (int row = 0; row < influenceStr.size(); row++) {
      String str = influenceStr.get(row);

      if (str.length() != 5) {
        throw new IllegalArgumentException("Card's influence grid is invalid!");
      }
      //if a non middle char is C
      if (str.contains("C") && row != 2) {
        throw new IllegalArgumentException("Card's influence grid is invalid!");
      }
    }

    for (int row = 0; row < influenceStr.size(); row++) {
      // if there isn't a C, I, or X
      if (!influenceStr.get(row).contains("C")
          && !influenceStr.get(row).contains("I") && !influenceStr.get(row).contains("X")) {
        throw new IllegalArgumentException("Card's influence grid is invalid!");
      }
    }

    // check c is in the center
    // need to also check there are only c, x, i in grid
    // only one card?
  }

  /**
   * A method that changes the influence grid from a list of Strings to a list of Integers.
   * sets both influenceInt and influenceColor strings/arrays.
   *
   * <p>1 stands for influence</p>
   * 0 stands for no influence
   *
   * <p>Orange stands for the card itself</p>
   * Gray stands for no influence
   * Cyan stands for influence
   *
   * @param influenceStr the influence grid as a list of Strings
   */
  private void setIntAndColorInfluence(List<String> influenceStr) {
    List<List<Integer>> intInfluence = new ArrayList<>();
    List<List<CardColor>> colorInfluence = new ArrayList<>();

    for (String s : influenceStr) {

      List<CardColor> colorList = new ArrayList<>();
      List<Integer> intList = new ArrayList<>();

      for (int i = 0; i < s.length(); i++) {

        char character = s.charAt(i);

        switch (character) {
          case 'X' -> {
            intList.add(0);
            colorList.add(CardColor.GRAY);
          }
          case 'C' -> {
            intList.add(0);
            colorList.add(CardColor.ORANGE);
          }
          case 'I' -> {
            intList.add(1);
            colorList.add(CardColor.CYAN);
          }
          default -> throw new IllegalArgumentException("influenceStr contains non I,C,X");
        }
      }
      intInfluence.add(intList);
      colorInfluence.add(colorList);
    }
    influenceInt = intInfluence;
    influenceColor = colorInfluence;
  }

}

