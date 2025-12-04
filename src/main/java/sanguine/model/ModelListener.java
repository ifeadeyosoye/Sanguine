package sanguine.model;

import java.io.IOException;
import sanguine.view.Listener;

/**
 * this will be impleneted by a controller. listens to the model for updates on when the turn is
 * changed.
 */
public interface ModelListener {
  /**
   * A method that notifies listeners when the player turn has changed.
   * @param color the player's color whose turn it is
   */
  void turnChanged(PlayerColor color) throws IOException;


  /**
   * this tells the controller to tell the player that the game is over. puts a message on the
   * screen.
   */
  void notifyGameEnded();
}
