package sanguine.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * A class that listens for when a cell on the GUI is clicked and reports to secondary listeners.
 */
public class CellClickListener extends MouseAdapter {
  private List<Listener> listeners;
  private int row;
  private int col;
  private CellPanel panel;

  /**
   * Takes in a list of starting listeners for when a cell is clicked and cell coordinates.
   *
   * @param listeners a list of staring listeners
   * @param row the row of the cell clicked
   * @param col the column of the cell clicked
   */
  public CellClickListener(List<Listener> listeners, int row, int col, CellPanel panel) {
    if (panel == null) {
      throw new IllegalArgumentException("Panel is null!");
    }

    if (listeners == null) {
      throw new IllegalArgumentException("The given listener list is null!");
    }

    for (Listener listener : listeners) {
      if (listener == null) {
        throw new IllegalArgumentException("A given listener is null!");
      }
    }

    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Cell coordinates are invalid!");
    }

    this.listeners = listeners;
    this.row = row;
    this.col = col;
    this.panel = panel;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    for (Listener listener : listeners) {
      listener.clickCell(this.row, this.col);
      panel.highlight();
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
