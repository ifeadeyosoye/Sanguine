package sanguine.view;

import java.util.List;

/**
 * this defines the methods needed for a graphical user interface view for sanguine.
 */
public interface SanguineGuiView {

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();


  /**
   * A method that adds a new listener to the GUI.
   *
   * @param listener the listener to add
   */
  void subscribe(Listener listener);
}
