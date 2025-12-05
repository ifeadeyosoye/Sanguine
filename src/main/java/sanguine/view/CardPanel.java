package sanguine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguinePlayer;

/**
 * A JPanel that represents a Sanguine card.
 *
 * <p>This card holds a SanguineCard, player color, and creates the cards using Graphics2D.</p>
 *
 * <p>This class also handles clicking and highlighting a card.</p>
 */
public class CardPanel extends JPanel {
  // Private fields
  private final SanguineCard card;
  private final PlayerColor color;
  private Color red = new Color(0xC41E3A);
  private Color blue = new Color(0x0077b6);
  private boolean highlighted;

  /**
   * A constructor that takes in a card that this panel represents, and the owner.
   *
   * @param card the Sanguine card the panel represents
   * @param color the player color who owns the card
   */
  public CardPanel(SanguineCard card, PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Color is null!");
    }
    if (card == null) {
      throw new IllegalArgumentException("Card is null!");
    }

    this.card = card;
    this.color = color;

    this.setBorder(BorderFactory.createLineBorder(new Color(0xE7DECC), 2));
  }

  /**
   * A method that highlights a card when it is clicked and unhighlights if it is already selected.
   */
  public void highlight() {

    // to stop interaction when an error msg is given to the player.
    if (!this.isEnabled()) {
        return;
    }

    if (highlighted) {
      // unhighlight the card
      setBorder(BorderFactory.createLineBorder(new Color(0xE7DECC), 2));
      highlighted = false;
    } else {
      // highlight the card
      setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
      highlighted = true;
    }
  }

  /**
   * A method that unhighlights a card.
   */
  public void removeHighlight() {
      setBorder(BorderFactory.createLineBorder(new Color(0xE7DECC), 2));
      highlighted = false;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D)g.create();
    if (color == PlayerColor.RED) {
      g2d.setColor(red);
    } else {
      g2d.setColor(blue);
    }
    g2d.fillRect(0, 0, getWidth(), getHeight());

    placeCardText(g2d);
    placeInfluenceGrid(g2d);
  }

  /**
   * A helper method that places a card's basic information like name, value, and cost on the panel.
   *
   * @param g2d Graphics 2D object
   */
  private void placeCardText(Graphics g2d) {
    String name = card.getName();
    String cost = "Cost: " + card.getCost();
    String value = "Value: " + card.getValue();

    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("TimesNewRoman", Font.BOLD, 20));

    FontMetrics valueMetrics = g2d.getFontMetrics();
    int textWidth = valueMetrics.stringWidth(name);

    int x = (getWidth() - textWidth) / 2;
    int y = 70;
    g2d.drawString(name, x, y - 40);
    g2d.drawString(cost, x, y - 20);
    g2d.drawString(value, x, y);
  }

  /**
   * A helper method that places a card's influence grid on the panel.
   *
   * @param g2d Graphics 2D object
   */
  private void placeInfluenceGrid(Graphics g2d) {
    List<String> influenceGrid = card.getStringInfluence();
    String row1 = influenceGrid.get(0);
    String row2 = influenceGrid.get(1);;
    String row3 = influenceGrid.get(2);;
    String row4 = influenceGrid.get(3);;
    String row5 = influenceGrid.get(4);;

    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("TimesNewRoman", Font.BOLD, 25));

    FontMetrics valueMetrics = g2d.getFontMetrics();
    int textWidth = valueMetrics.stringWidth(row1);

    int x = (getWidth() - textWidth) / 2;
    int y = 70;
    g2d.drawString(row1, x, y + 100);
    g2d.drawString(row2, x, y + 125);
    g2d.drawString(row3, x, y + 150);
    g2d.drawString(row4, x, y + 175);
    g2d.drawString(row5, x, y + 200);
  }
}
