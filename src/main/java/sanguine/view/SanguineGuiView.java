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

    /**
     * A method that creates a pop-up on the view when a player does an action against the game's rules.
     *
     * @param msg the message to show the human player
     */
  void showError(String msg);

    /**
     * A method that changes whether a view frame is clickable or not
     *
     * <p>This is used when we don't want the current player to click on the opposite player's view.</p>
     *
     * @param choice whether to make the view interactable
     */
  void changeInteraction(boolean choice);
}
