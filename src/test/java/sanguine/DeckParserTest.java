package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;
import sanguine.model.DeckParser;
import sanguine.model.SanguineCard;

/**
 * tests for deckparser class.
 */
public class DeckParserTest {

  /**
   * helper method that write to a given file.
   */
  public void writeToFile(String content, String path) throws IOException {
    Files.write(Path.of(path), content.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * creates a temporary file. each method creates a new file specifically for its tests. file is
   * then destroyed after being used fro one test.
   */
  private Path createTempDeck() {
    Path tempFilePath = null;

    try {
      tempFilePath = Files.createTempFile("myTempFile", ".tmp");
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
    return tempFilePath;
  }

  /**
   * makes sure DeckParser throws an illegalarugment exception when given an invalid text file.
   */
  @Test
  public void testInvalidDeck() {
    String path = "docs" + File.separator + "invalidDeckForTests.txt";

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path));
  }

  /**
   * makes sure DeckParser throws an illegalarugment exception when given an invalid path.
   */
  @Test
  public void testInvalidPath() {
    String path = "docs" + File.separator + "fail.txt";

    assertThrows(IOException.class, () -> DeckParser.makeDeck(path));
  }

  /**
   * makes sure DeckParser excepts a valid deck.
   */
  @Test
  public void testValidDeck() {
    String path = "docs" + File.separator + "example.deck";

    try {
      DeckParser.makeDeck(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * makes sure a deck of one card is okay. im doing this because my code uses hard addition.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void oneCardValid() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            """,
        path.toString());

    List<String> lines = Files.readAllLines(path);
    System.out.println(lines);

    assertEquals("Security 1 1", lines.get(0));
    assertEquals("XXXXX", lines.get(1));
    assertEquals("XXXXX", lines.get(2));
    assertEquals("XXCIX", lines.get(3));
    assertEquals("XIXIX", lines.get(4));
    assertEquals("XIXIX", lines.get(5));
    DeckParser.makeDeck(path.toString());
  }

  /**
   * makes sure a deck of one card is invalid when its cost is a string.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void oneCardInvalidCost() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 L
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when its value is a string.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void oneCardInvalidValue() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security L 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when its has another value in its title.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void oneCardInvalidSeperator() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security-1 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when it is missing a line on influence.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void lineMissing() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 L
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when it is missing a center.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void missingLetterC() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 L
            XXXXX
            XXMIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when its influence grid is too long.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void linesTooLong() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 L
            XXXXXX
            XXMIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when its influence grid is too short.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void influenceTooShort() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 L
            XXXX
            XXMIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when its name is too long.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void nameTooLong() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 L a
            XXXXX
            XXMIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * makes sure a deck of one card is invalid when its name is too short.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void nameTooShort() throws IOException {
    Path path = createTempDeck();

    writeToFile(
        """
            Security 1
            XXXXX
            XXMIX
            XIXIX
            XIXIX
            """,
        path.toString());

    assertThrows(IllegalArgumentException.class, () -> DeckParser.makeDeck(path.toString()));
  }

  /**
   * a valid deck passes.
   *
   * @throws IOException if file cant be read
   */
  @Test
  public void validDeckPasses() throws IOException {

    List<SanguineCard> deck = DeckParser.makeDeck(createTempDeck().toString());

    DeckParser.checkValidDeck(deck);
  }

  /**
   * makes sure that deckparser allows a deck with two duplicates.
   *
   * @throws IOException if path is invalid.
   */
  @Test
  public void invalidDeckPassesTwoCards() throws IOException {

    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            Security 1 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            """,
        path.toString());

    List<SanguineCard> deck = DeckParser.makeDeck(path.toString());

    DeckParser.checkValidDeck(deck);
  }

  /**
   * test deckparser valid deck when you have a deck with 3 duplicate cards.
   *
   * @throws IOException if path is invalid
   */
  @Test
  public void invalidDeckFailsThreeCards() throws IOException {

    Path path = createTempDeck();

    writeToFile(
        """
            Security 1 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            Security 1 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            Security 1 1
            XXXXX
            XXXXX
            XXCIX
            XIXIX
            XIXIX
            """,
        path.toString());

    List<SanguineCard> deck = DeckParser.makeDeck(path.toString());

    assertThrows(
        IllegalArgumentException.class,
        () -> DeckParser.checkValidDeck(deck)
    );
  }

}
