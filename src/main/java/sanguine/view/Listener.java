package sanguine.view;

import sanguine.model.SanguineCard;

/**
 * This interface defines the events the view needs a subscriber to respond to.
 *
 * <p>For now, our subscriber is our stub controller which lives in the controller package.</p>
 */
public interface Listener {
  /**
   * A method that defines what a subscriber should do when a card is clicked, given that card.
   *
   * @param card the clicked SanguineGame card
   */
  public void clickCard(SanguineCard card);

  /**
   * Defines what a subscriber should do when a cell is clicked, given cell coordinates.
   *
   * @param row the cell row
   * @param col the cell column
   */
  public void clickCell(int row, int col);

  /**
   * A method that defines what a subscriber should do when 'p' is pressed on the keyboard.
   */
  public void pressP();

  /**
   * A method that defines what a subscriber should do when 'm' is pressed on the keyboard.
   */
  public void pressM();

}
