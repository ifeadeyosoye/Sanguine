package sanguine.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class takes in a file path and converts it to a list of Basic SanguineGame Cards or a deck.
 *
 * <p>This list is then used as a deck in a game of SanguineGame later on.</p>
 * As a result, these files that contain a deck are checked for a valid deck
 * For example, we can not have more than 2 of the same card and a valid card must have
 * a name, value, cost, and a 5x5 influence grid.
 */
public class DeckParser {

  // Public methods:

  /**
   * A method that will be called to make a deck of Basic SanguineGame Cards.
   * It will construct this deck from a given text file of the correct format to be parsed through.
   *
   * @param path the text file path containing a text representation of the deck.
   * @return a list of Basic SanguineGame Cards that will be used as a deck.
   * @throws IOException              if the file path is invalid.
   * @throws IllegalArgumentException if deck is invalid.
   */
  public static List<SanguineCard> makeDeck(String path)
      throws IOException, IllegalArgumentException {

    List<String> allStrings = parseToList(path);
    List<SanguineCard> deck = new ArrayList<SanguineCard>();

    if (allStrings.size() % 6 != 0) {
      throw new IllegalArgumentException("Invalid deck size");
    }

    for (int i = 0; i < allStrings.size(); i += 6) {

      List<String> influence = new ArrayList<>();

      String title = allStrings.get(i);
      String[] listedTitle = title.split(" ");
      if (listedTitle.length != 3) {
        throw new IllegalArgumentException("deck is invalid for title: " + title);
      }


      for (int j = i + 1; j < i + 6; j++) {
        influence.add(allStrings.get(j));
      }

      // BasicSanguineCard constructor can throw an error, but if it does, the deck is invalid.
      // Integer.parseInt can also throw an error.
      int cost = 0;
      int value = 0;
      try {
        cost = Integer.parseInt(listedTitle[1]);
        value = Integer.parseInt(listedTitle[2]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("deck is invalid for title: " + title);
      }

      BasicSanguineCard card = new BasicSanguineCard(
          listedTitle[0],
          cost,
          value,
          influence
      );

      deck.add(card);
    }
    return deck;
  }

  /**
   * A method that ensures a given deck is valid.
   * A valid deck must not contain more than two duplicates of a card
   *
   * @param deck the deck to be checked for validity
   * @throws IllegalArgumentException if deck is invalid.
   */
  public static void checkValidDeck(List<SanguineCard> deck) {
    Map<String, Integer> checked = new HashMap<>();

    for (SanguineCard card : deck) {
      if (checked.containsKey(card.toString())) {
        if (checked.get(card.toString()) >= 2) {
          throw new IllegalArgumentException("cannot have more than two iterations of a card: "
              + card.toString().substring(0, 5));
        }
        checked.replace(card.toString(), checked.get(card.toString()) + 1);
      } else {
        checked.put(card.toString(), 1);
      }
    }
  }

  /**
   * A method that parses the deck text file and produces a string of each line in the list.
   *
   * @param path the text file path containing a text representation of the deck.
   * @return a list of Strings containing a line of the text file
   * @throws IOException              if file path is invalid
   * @throws IllegalArgumentException if deck is invalid
   */
  private static List<String> parseToList(String path)
      throws IOException, IllegalArgumentException {

    List<String> finalList = new ArrayList<>();
    BufferedReader realReader = new BufferedReader(new FileReader(path));
    BufferedReader countingReader = new BufferedReader(new FileReader(path));

    try {
      validSizeDeckFile(countingReader);
    } catch (IllegalArgumentException | IOException ignored) {
      System.out.print("");
    }

    String next = realReader.readLine();

    while (next != null) {
      finalList.add(next);
      next = realReader.readLine();
    }

    return finalList;
  }

  /**
   * A method that checks if the file given has cards in the correct format with 6 lines per card.
   *
   * @param countingReader the buffered reader
   * @throws IllegalArgumentException if countingReader is null or if deck is an incorrect size
   * @throws IllegalStateException    if deck is invalid
   * @throws IOException              if file path is invalid
   */
  private static void validSizeDeckFile(BufferedReader countingReader)
      throws IllegalStateException, IOException {
    if (countingReader == null) {
      throw new IllegalArgumentException("Buffered Reader is null!");
    }

    int totalLines = 0;

    while (countingReader.readLine() != null) {
      totalLines++;
    }
    if (totalLines % 6 != 0) {
      throw new IllegalArgumentException("Incorrect size deck given!");
    }
  }
}
