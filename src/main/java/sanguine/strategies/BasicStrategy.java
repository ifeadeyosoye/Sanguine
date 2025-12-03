package sanguine.strategies;

import java.io.IOException;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineModel;


/**
 * interface that represents a strategy. to be used by firstspot.java, maximizerowscore.java,
 * and maxownership.java
 */
public interface BasicStrategy {

  /**
   * this is the one public method that all strategies will use.
   *
   * @param model to make an informed decision
   * @param color the player its choosing for
   *
   * @return Coordinate object that has row, col, card. null and -1 values if its a pass.
   * @throws IOException if deck object cannot be read
   */
  public Coordinates choose(ModelReadOnlyInterface model,
                            PlayerColor color);
}
