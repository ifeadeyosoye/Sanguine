package sanguine.view;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;

/**
 * A representation of a SanguineGame Frame that holds all visual components needed to display
 * SanguineGame.
 *
 * <p>This organized objects like card hand, game board, and score panels.</p>
 *
 * <p>This class also has a list of listeners that it notifies when certain panels are clicked</p>
 */
public class SanguineViewFrame extends JFrame implements SanguineGuiView {
  private final CardHandPanel handPanel;
  private final GameBoardPanel boardPanel;
  private final ModelReadOnlyInterface model;
  private List<Listener> listeners =  new ArrayList<>();
  private final Listener controller;
  private final PlayerColor color;

  /**
   * construcor that takes in a model and controller and assigns them, assuming they are not null.
   *
   * @param model model
   * @param controller controller
   * @param color Player color
   *
   * @throws IOException if the deck file cannot be read when creating a default deck
   */
  public SanguineViewFrame(ModelReadOnlyInterface model, Listener controller, PlayerColor color) throws IOException {
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    if (controller == null) {
      throw new IllegalArgumentException("controller is null");
    }
    this.controller = controller;
    this.model = model;
    this.color = color;

    if (color == PlayerColor.RED) {
        this.setTitle("Player: Red");
    } else {
        this.setTitle("Player: Blue");
    }

    this.setPreferredSize(new Dimension(1000, 750));
    this.setResizable(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setBackground(Color.cyan);
    this.getContentPane().setBackground(Color.WHITE);

    boardPanel = new GameBoardPanel(model, controller);
    handPanel = new CardHandPanel(model, controller, color);

    // using a pane to organize the board and hand panels
    JSplitPane splitPane = new JSplitPane(
        JSplitPane.VERTICAL_SPLIT,
        boardPanel,   // will be at top
        handPanel     // will be at bottom
    );

    // Doing 50/50 split
    splitPane.setResizeWeight(0.5);
    // don't want a border
    splitPane.setBorder(null);
    splitPane.setDividerSize(1);
    this.add(splitPane, BorderLayout.CENTER);

    this.setVisible(true);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setFocusable(true);
    this.requestFocusInWindow();
    handPanel.displayHand();

    this.addKeyListener(new KeyPressListener(List.of(controller)));
  }

  @Override
  public void refresh() {
    handPanel.displayHand();

    this.repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void subscribe(Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener is null");
    }
  }

  @Override
    public void showError(String msg) {
      javax.swing.JOptionPane.showMessageDialog(this, msg, "Message",
              javax.swing.JOptionPane.INFORMATION_MESSAGE
      );
  }

  @Override
  public void changeInteraction(boolean choice) {
      setPanelEnabled(boardPanel, choice);
      setPanelEnabled(handPanel, choice);
  }


  private void setPanelEnabled(Component component, boolean enabled) {
      component.setEnabled(enabled);

      if (component instanceof Container container) {
          for (Component comp : container.getComponents()) {
              setPanelEnabled(comp, enabled);
          }
      }
  }
}