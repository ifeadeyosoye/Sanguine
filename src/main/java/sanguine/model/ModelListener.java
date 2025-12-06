package sanguine.model;

import java.io.IOException;
import sanguine.view.Listener;

/**
 * This is an interface that represents a subscriber to a model.
 *
 * <p>A subscriber to a model will want to know when a turn has changed and when the game has ended.</p>
 * This will be implemented by a controller.
 */
public interface ModelListener {
  /**
   * A method that notifies listeners when the player turn has changed.
   *
   * @param color the player's color whose turn it is
   */
  void turnChanged(PlayerColor color) throws IOException;


  /**
   * This tells listeners that the game has ended.
   */
  void notifyGameEnded();
}
