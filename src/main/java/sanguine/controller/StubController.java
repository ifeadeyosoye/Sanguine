package sanguine.controller;

import java.io.IOException;
import java.util.List;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;

/**
 * Represents a stub Controller for SanguineGame: handles user clicking by notifying certain
 * actions.
 */
public interface StubController {

  /**
   * Execute a single game of SanguineGame given a SanguineGame Model.
   * When the game is over, the playGame method ends.
   *
   * @param rows the num of rows on game board
   * @param cols the num of columns on game board
   * @param deck1 the first player's deck
   * @param deck2 the second player's deck
   * @param handSize the max hand size for each player
   */
  void playGame(int rows, int cols, List<SanguineCard> deck1, List<SanguineCard> deck2,
                int handSize) throws IOException;
}
