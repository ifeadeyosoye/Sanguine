package sanguine.model;

/**
 * An interface that represents a model that publishes events to a controller.
 *
 * <p>This is used specifically in out game of Sanguine because our controller needs to know when</p>
 * A player turn has changed.
 */
public interface ModelControllerPublisher {
  /**
   * Adds listener to the models list of subscribers.
   *
   * @param listener controller to be added.
   */
  public void addControllerSubscriber(ModelListener listener);
  }
