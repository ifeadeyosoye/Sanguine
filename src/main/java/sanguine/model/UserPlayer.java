package sanguine.model;

import java.io.IOException;
import sanguine.strategies.Coordinates;
import sanguine.view.Listener;

/**
 * UserPlayer is a representation of a player who decides to make a move.
 *
 * <p>Represents a human player who clicks on our GUI or inputs commands</p>
 * or inputs commands or an AI player that automatically decides what actions to take.
 */
public interface UserPlayer {

  /**
   * to be used when we want a player to make a move. called by controller.
   */
  void notifyTurn() throws IOException;

  /**
   * subscribes a Listener (the controller) to the player to listen for updates..
   *
   * @param listener a controller
   */
  void subscribe(Listener listener);
}
