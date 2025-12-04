package sanguine.model;

import sanguine.controller.ModelListener;

public interface ModelControllerPublisher {


  /**
   * adds listener to the models list of subscribers.
   *
   * @param listener controller to be added.
   */
  public void addControllerSubscriber(ModelListener listener);
  }
