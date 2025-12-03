package sanguine.model;

import sanguine.strategies.Coordinates;
import sanguine.strategies.MaxOwnership;

public class AiPlayer implements UserPlayer {

  /**
   *
   */
  public AiPlayer() {

  }
  @Override
  public void notifyTurn() {
    MaxOwnership strat = new MaxOwnership();
    Coordinates coor = max
  }
}
