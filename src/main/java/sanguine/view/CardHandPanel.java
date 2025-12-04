package sanguine.view;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;

/**
 * A JPanel that represents a card hand in our SanguineGame GUI.
 *
 * <p>A card hand is a collection of CardPanels.</p>
 *
 * <p>When this panel is displayed, it decides which player hand to display based on the model.</p>
 */
public class CardHandPanel extends JPanel {
  // Private fields
  private final ModelReadOnlyInterface model;
  private final Listener controller;
  private final PlayerColor color;

  /**
   * A constructor that takes in a read only version of the model.
   *
   * @param model the read only game model
   * @param controller the game controller which is a listener
   * @throws IllegalArgumentException if model is null
   */
  public CardHandPanel(ModelReadOnlyInterface model, Listener controller, PlayerColor color)
      throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException("controller is null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model is null!");
    }

    this.color = color;
    this.controller = controller;
    this.model = model;
  }

  /**
   * A method used to display the hand or essentially refresh the panel to show the proper hand.
   */
  public void displayHand() {
    this.setLayout(new GridLayout());

    if (color == PlayerColor.RED) {
      displayRedHand();
    } else {
      displayBlueHand();
    }
  }

  /**
   * A helper method used to display a blue player's hand.
   */
  private void displayBlueHand() {
    List<SanguineCard> blueHand = model.getPlayerHand(PlayerColor.BLUE);

    for (SanguineCard card : blueHand) {
      CardPanel cardPanel = new CardPanel(card, PlayerColor.BLUE);
      cardPanel.addMouseListener(new CardClickListener(List.of(controller), card, cardPanel));
      this.add(cardPanel);
    }
  }

  /**
   * A helper method used to display a red player's hand.
   */
  private void displayRedHand() {
    List<SanguineCard> redHand = model.getPlayerHand(PlayerColor.RED);

    for (SanguineCard card : redHand) {
      CardPanel cardPanel = new CardPanel(card, PlayerColor.RED);
      cardPanel.addMouseListener(new CardClickListener(List.of(controller), card, cardPanel));
      this.add(cardPanel);
    }
  }
}