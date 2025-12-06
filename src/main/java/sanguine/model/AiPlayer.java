package sanguine.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sanguine.strategies.BasicStrategy;
import sanguine.strategies.Coordinates;
import sanguine.strategies.MaxOwnership;
import sanguine.view.Listener;

/**
 * A representation of an AI player that has a strategy and listener which should be the controller.
 *
 * <p>The controller needs to be a listener because when the AI player uses a strategy, it</p>
 * needs to notify the controller what its decision was.
 *
 * <p>Additionally, the strategy is set during the instantiation of the AI player and</p>
 * can not be changed. This design choice is intentional because the AI player's strategy should
 * not change in the middle of a game. We envision the AI player's "difficulty" being set before
 * a game and the AI player will stick to that "difficulty" or strategy.
 *
 * <p>A color is also assigned to the player and a read only game model.</p>
 */
public class AiPlayer implements UserPlayer {
  private final BasicStrategy strat;
  private final PlayerColor color;
  private final ModelReadOnlyInterface model;
  private List<Listener> listeners;

  /**
   * A controller for an AI player that takes in a set strategy, color, and read only model.
   *
   * @param strat the final strategy for the AI player
   * @param color the AI player's color
   * @param model a read only model
   */
  public AiPlayer(BasicStrategy strat, PlayerColor color, ModelReadOnlyInterface model) {
    if (strat == null) {
      throw new IllegalArgumentException("Strategy is Null!");
    }

    if (color == null) {
      throw new IllegalArgumentException("Player color is null!");
    }

    if (model == null) {
        throw new IllegalArgumentException("Model is null!");
    }

    this.strat = strat;
    this.color = color;
    this.model = model;
    listeners = new ArrayList<>();
  }

  @Override
  public void notifyTurn() throws IOException {
    Coordinates coor = strat.choose(model, color);

    if (coor == null) {
      throw new IllegalStateException("Strategy choice is null!");
    }

    if (coor.col() == -1 || coor.row() == -1 || coor.card() == null) {
      for (Listener listener : listeners) {
        // pass the AI player's turn
        listener.pressP();
        return;
      }
    }

    for (Listener listener : listeners) {
      listener.clickCard(coor.card());
      listener.clickCell(coor.row(), coor.col());
      listener.pressM();
    }
  }

  @Override
  public void subscribe(Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener is null!");
    }
    listeners.add(listener);
  }

    @Override
    public List<Listener> seeSubscribers() {
        return List.copyOf(listeners);
    }
}
