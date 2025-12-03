package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;
import sanguine.model.BasicSanguineBoardCell;
import sanguine.model.DeckParser;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguinePlayer;

/**
 * A class for testing BasicSanguineBoardCell and SanguineBoardCell.
 *
 * <p>Currently there is only one board implementation, BasicSanguineBoardCell.</p>
 * Therefore this class will focus on testing that implementation.
 */
public class CellTests {

  /**
   * A helper method that creates a new sanguine player.
   * This will be used in tests that need a valid player object.
   * This specifically creates a red player.
   */
  public SanguinePlayer makeRedPlayer() throws IOException {
    return new SanguinePlayer(
        DeckParser.makeDeck("docs" + File.separator + "example.deck"),
        PlayerColor.RED,
        3
    );
  }

  /**
   * A helper method that creates a new sanguine player.
   * This will be used in tests that need a valid player object.
   * This specifically creates a blue player.
   */
  public SanguinePlayer makeBluePlayer() throws IOException {
    return new SanguinePlayer(
        DeckParser.makeDeck("docs" + File.separator + "example.deck"),
        PlayerColor.BLUE,
        3
    );
  }

  /**
   * A helper method that makes a card for tests that need a card.
   */
  public SanguineCard makeCard() throws IOException {
    Path tempFilePath = null;

    try {
      tempFilePath = Files.createTempFile("myTempFile", ".tmp");
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }


    String content = """
        Security 1 1
        XXXXX
        XXXXX
        XXCIX
        XIXIX
        XIXIX
        """;

    Files.write(Path.of(tempFilePath.toUri()), content.getBytes(StandardCharsets.UTF_8));
    return DeckParser.makeDeck(String.valueOf(tempFilePath)).get(0);
  }


  // TODO FINISH

  /**
   * makes sure that a card can be placed in a cell that has the correct number of pawns.
   * also makes sure that pawns can be placed.
   *
   * @throws IOException if file cannot be read
   */
  @Test
  public void testPlaceCardInCellPasses() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer player = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    System.out.println(cell.getColor());
    cell.placePawn(player);
    player.deckToHand(3);
    cell.placeCard(player.drawHandToBoard(), player);
  }

  /**
   * makes sure we can place 3 pawns in a cell.
   *
   * @throws IOException if file cannot be found/read
   */
  @Test
  public void testPlaceThreePawns() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();

    SanguinePlayer player = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    cell.placePawn(player);
    cell.placePawn(player);
    cell.placePawn(player);

  }

  /**
   * makes sure we cannot place more that 3 pawns in a cell.
   *
   * @throws IOException if file cannot be found/read
   */
  @Test
  public void testPlaceMoreThanThreePawns() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();

    SanguinePlayer player = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    cell.placePawn(player);
    cell.placePawn(player);
    cell.placePawn(player);

    assertThrows(IllegalStateException.class, () -> cell.placePawn(player));
  }

  /**
   * this test makes sure that we cannot place a card in a cell with not enough pawns to cover
   * cost.
   *
   * @throws IOException if file cannot be found/read
   */
  @Test
  public void testPlaceCardInCellFails() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer player = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    player.deckToHand(3);
    assertThrows(IllegalStateException.class,
        () -> cell.placeCard(player.drawHandToBoard(), player));
  }

  /**
   * makes sure place card throws when given a null card.
   *
   * @throws IOException if file cannot be rad
   */
  @Test
  public void placeCardWithNullCard() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer player = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    cell.placePawn(player);
    player.deckToHand(3);
    assertThrows(IllegalArgumentException.class,
        () -> cell.placeCard(null, player));
  }

  /**
   * makes sure place card throws when given a null player.
   *
   * @throws IOException if file cannot be rad
   */
  @Test
  public void placeCardWithNullPlayer() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer player = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    cell.placePawn(player);
    player.deckToHand(3);
    assertThrows(IllegalArgumentException.class,
        () -> cell.placeCard(player.drawHandToBoard(), null));
  }

  /**
   * makes sure a card cannot be placed in a cell that has another card.
   */
  @Test
  public void testPlaceCardInCellFailBecauseAlreadyCard() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer player = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    cell.placePawn(player);
    player.deckToHand(3);
    cell.placeCard(player.drawHandToBoard(), player);

    assertThrows(IllegalStateException.class,
        () -> cell.placeCard(player.drawHandToBoard(), player));
  }

  /**
   * makes sure a card cannot be placed in a cell that has a card of a different color.
   */
  @Test
  public void testPlaceCardInCellFailBecauseWrongColorCard() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer redPlayer = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    cell.placePawn(redPlayer);
    redPlayer.deckToHand(3);
    cell.placeCard(redPlayer.drawHandToBoard(), redPlayer);

    SanguinePlayer bluePlayer = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.BLUE, 3);

    assertThrows(IllegalArgumentException.class,
        () -> cell.placeCard(bluePlayer.drawHandToBoard(), bluePlayer));
  }

  /**
   * makes sure a card cannot be placed in a cell that has a pawn of a different color.
   */
  @Test
  public void testPlaceCardInCellFailBecauseWrongColorPawn() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer redPlayer = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);

    cell.placePawn(redPlayer);
    redPlayer.deckToHand(3);
    cell.placeCard(redPlayer.drawHandToBoard(), redPlayer);

    SanguinePlayer bluePlayer = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.BLUE, 3);

    System.out.println(cell.getColor());
    assertThrows(IllegalStateException.class,
        () -> cell.placePawn(bluePlayer));
  }

  /**
   * makes sure the color of pawns is switched.
   */
  @Test
  public void changeColorOfPawns() throws IOException {
    BasicSanguineBoardCell cell = new BasicSanguineBoardCell();
    SanguinePlayer redPlayer = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.RED, 3);
    SanguinePlayer bluePlayer = new SanguinePlayer(DeckParser.makeDeck(
        "docs" + File.separator + "example.deck"), PlayerColor.BLUE, 3);

    cell.placePawn(redPlayer);
    assertEquals(redPlayer.getColor(), cell.getColor());

    cell.changeColorsOfPawns(bluePlayer);
    assertEquals(bluePlayer.getColor(), cell.getColor());
  }
}