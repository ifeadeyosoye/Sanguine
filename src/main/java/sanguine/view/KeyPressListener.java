package sanguine.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * A class that listens for when a key press listener for when 'p' or 'm' is pressed during a game.
 */
public class KeyPressListener extends KeyAdapter {
  private List<Listener> listeners;

  /**
   * A constructor that takes in a list of starting listeners for when keys are pressed.
   *
   * @param listeners a list of starting listeners
   */
  public KeyPressListener(List<Listener> listeners) {
    if (listeners == null) {
      throw new IllegalArgumentException("The given listener list is null!");
    }

    for (Listener listener : listeners) {
      if (listener == null) {
        throw new IllegalArgumentException("A given listener is null!");
      }
    }

    this.listeners = listeners;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    char key = Character.toLowerCase(e.getKeyChar());

    if (key == 'p') {
      for (Listener listener : listeners) {
        listener.pressP();
      }
    } else if (key == 'm') {
      for (Listener listener : listeners) {
        listener.pressM();
      }
    }
  }

  /**
   * A method that adds a new listener as a subscriber to this event.
   *
   * @param listener the listener to be added
   */
  public void subscribe(Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener is Null!");
    }

    listeners.add(listener);
  }
}
