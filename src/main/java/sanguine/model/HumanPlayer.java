package sanguine.model;

import sanguine.view.Listener;

/**
 * A representation of a human player that only has a player color.
 *
 * <p>A controller as a listener is not needed because the view already deals with input</p>
 * from the human player and the controller is a subscriber of the view so it knows what moves
 * the human player makes.
 *
 * <p>Therefore nothing is needed in the notifyTurn() method.</p>
 */
public class HumanPlayer implements UserPlayer {
  private final PlayerColor color;

  /**
   * A constructor for a Human player that only takes in the player color.
   *
   * @param color the player color
   */
  public HumanPlayer(PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Player color is null!");
    }

    this.color = color;
  }
  @Override
  public void notifyTurn() {
    // Nothing is needed here because the view deals with getting input from a human player.
  }

  @Override
  public void subscribe(Listener listener) {
    //nothing needs to happen because the view listens to clicks from human players.
  }
}
