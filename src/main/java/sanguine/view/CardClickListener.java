package sanguine.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import sanguine.model.SanguineCard;

/**
 * A class that listens for when a card on the GUI is clicked and reports to secondary listeners.
 */
public class CardClickListener extends MouseAdapter {
  private List<Listener> listeners;
  private SanguineCard card;
  private CardPanel panel;
  private static CardPanel currentlyHighlighted; // needs to be shared with all card listeners.

  /**
   * Takes in a list of starting listeners for when a card is clicked and the card itself.
   *
   * @param listeners a list of staring listeners
   * @param card the card clicked
   */
  public CardClickListener(List<Listener> listeners, SanguineCard card, CardPanel panel) {
    if (panel == null) {
      throw new IllegalArgumentException("Panel is null");
    }

    if (listeners == null) {
      throw new IllegalArgumentException("The given listener list is null!");
    }

    for (Listener listener : listeners) {
      if (listener == null) {
        throw new IllegalArgumentException("A given listener is null!");
      }
    }

    if (card == null) {
      throw new IllegalArgumentException("Card is null!");
    }

    this.card = card;
    this.listeners = listeners;
    this.panel = panel;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    for (Listener listener : listeners) {
      if (currentlyHighlighted != null && currentlyHighlighted != panel) {
        currentlyHighlighted.highlight(); // unhighlights if it is highlighted
      }

      listener.clickCard(this.card);
      panel.highlight();

      currentlyHighlighted = panel;
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
