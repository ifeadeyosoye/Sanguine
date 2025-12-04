package sanguine.controller;

import sanguine.model.PlayerColor;

/**
 * this will be impleneted by a controller. listens to the model for updates on when the turn is
 * changed.
 */
public interface ModelListener {

  /**
   * subscribes the controller to the model.
   */
  void subscribe();

  /**
   * tells the controller to notify its player when its their turn.
   * @param color
   */
  void turnChanged(PlayerColor color);
}
