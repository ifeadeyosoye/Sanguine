package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import sanguine.model.DeckParser;
import sanguine.model.GameBoard;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineGameBoard;
import sanguine.model.SanguinePlayer;

/**
 * A class for testing GameBoard and SanguineGameBoard
 *
 * <p>Currently, there is only one implementation of Gameboard, SanguineGameBoard, so that will</p>
 * also be tested.
 */
public class GameBoardTests {

  /**
   * This tests that the constructor throws when the height is invalid.
   */
  @Test
  public void invalidHeights() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new SanguineGameBoard(5, -1)
    );

    assertThrows(
        IllegalArgumentException.class,
        () -> new SanguineGameBoard(5, 0)
    );

    assertThrows(
        IllegalArgumentException.class,
        () -> new SanguineGameBoard(5, 2)
    );

    assertThrows(
        IllegalArgumentException.class,
        () -> new SanguineGameBoard(5, 1)
    );
  }

  /**
   * This tests that a game board is properly instantiated with valid heights.
   */
  @Test
  public void validHeights() {
    GameBoard board = new SanguineGameBoard(5, 3);
    assertEquals(15, board.getBoardSize());
    assertEquals(5, board.getRows());
    assertEquals(3, board.getCols());
  }

  /**
   * This tests that the constructor throws when the width is invalid.
   */
  @Test
  public void invalidWidth() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new SanguineGameBoard(0, 3)
    );

    assertThrows(
        IllegalArgumentException.class,
        () -> new SanguineGameBoard(-1, 3)
    );
  }

  /**
   * This tests that a game board is properly instantiated with valid widths.
   */
  @Test
  public void validWidth() {
    GameBoard board1 = new SanguineGameBoard(5, 3);

    assertEquals(15, board1.getBoardSize());
    assertEquals(5, board1.getRows());
    assertEquals(3, board1.getCols());

    GameBoard board2 = new SanguineGameBoard(1, 3);
    assertEquals(3, board2.getBoardSize());
    assertEquals(1, board2.getRows());
    assertEquals(3, board2.getCols());
  }

  /**
   * This tests that the board has the correct rows and columns.
   */
  @Test
  public void testRowColRightIndex() {
    SanguineGameBoard board = new SanguineGameBoard(3, 5);

    assertEquals(3, board.getRows());
    assertEquals(5, board.getCols());
  }

  /**
   * This tests that placing a pawn in a cell works correctly.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void placePawnValid() throws IOException {
    SanguineGameBoard board = new SanguineGameBoard(3, 5);
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    SanguinePlayer player = new SanguinePlayer(deck, PlayerColor.RED, 3);

    board.addPawnToCell(1, 1, player);

    assertEquals(1, board.getCellAt(1, 1).getPawns().size());
  }

  /**
   * This tests that trying to place a pawn with other colors of pawns does not work.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void placePawnInvalidColor() throws IOException {
    SanguineGameBoard board = new SanguineGameBoard(3, 5);
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    SanguinePlayer redPlayer = new SanguinePlayer(deck, PlayerColor.RED, 3);

    board.addPawnToCell(1, 1, redPlayer);
    board.addPawnToCell(1, 1, redPlayer);

    assertEquals(2, board.getCellAt(1, 1).getPawns().size());
  }

  /**
   * This tests that you are not able to add more than 3 pawns to a cell.
   *
   * @throws IOException if text file is invalid
   */
  @Test(expected = IllegalStateException.class)
  public void placePawnNoMoreThanThree() throws IOException {
    SanguineGameBoard board = new SanguineGameBoard(3, 5);
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    SanguinePlayer redPlayer = new SanguinePlayer(deck, PlayerColor.RED, 3);

    board.addPawnToCell(1, 1, redPlayer);
    board.addPawnToCell(1, 1, redPlayer);
    board.addPawnToCell(1, 1, redPlayer);
    board.addPawnToCell(1, 1, redPlayer);

  }

  /**
   * This tests that we can place a secondary pawn of the same color in the same cell.
   *
   * @throws IOException if text file is invalid
   */
  @Test(expected = IllegalStateException.class)
  public void placeSecondPawn() throws IOException {
    SanguineGameBoard board = new SanguineGameBoard(3, 5);
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    SanguinePlayer redPlayer = new SanguinePlayer(deck, PlayerColor.RED, 3);
    SanguinePlayer bluePlayer = new SanguinePlayer(deck, PlayerColor.BLUE, 3);

    board.addPawnToCell(1, 1, redPlayer);
    board.addPawnToCell(1, 1, bluePlayer);

  }


  /**
   * This tests that we can not place a card when there are no pawns in a cell.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testPlaceCardWhenNoPawns() throws IOException {
    SanguineGameBoard board = new SanguineGameBoard(3, 5);
    SanguineCard card = DeckParser.makeDeck("docs" + File.separator + "example.deck").get(0);
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    SanguinePlayer player = new SanguinePlayer(deck, PlayerColor.BLUE, 3);

    assertThrows(IllegalStateException.class, () -> board.addCardToCell(1, 1, card, player));

  }

  /**
   * This tests that we can place a card when there are pawns in a cell.
   *
   * @throws IOException if text file is invalid
   */
  @Test
  public void testPlaceCardWhenPawns() throws IOException {
    SanguineGameBoard board = new SanguineGameBoard(3, 5);
    List<SanguineCard> deck = DeckParser.makeDeck("docs" + File.separator + "example.deck");
    SanguineCard card = deck.getFirst();
    SanguinePlayer player = new SanguinePlayer(deck, PlayerColor.RED, 3);

    board.addPawnToCell(1, 1, player);

    board.addCardToCell(1, 1, card, player);

    assertEquals(board.getCellAt(1, 1).getCard(), card);
    assertEquals(card.getValue(), board.getCellAt(1, 1).getValue());

  }
}