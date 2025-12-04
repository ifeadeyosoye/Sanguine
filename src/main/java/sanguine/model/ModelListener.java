package sanguine.model;

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
  void turnChanged(PlayerColor color);
}
