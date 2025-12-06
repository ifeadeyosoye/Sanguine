package sanguine.view;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import sanguine.model.GameBoard;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.SanguineGameBoard;

/**
 * A JPanel that represents a gameboard in our SanguineGame GUI.
 *
 * <p>A game board panel is a collection of CellPanel placed in the correct grid size.</p>
 * These cells are placed during the construction of the game board.
 */
public class GameBoardPanel extends JPanel {
  private final ModelReadOnlyInterface model;
  private List<List<CellPanel>> cells;
  private final Listener controller;

  /**
   * A constructor that takes in a read only version of the model and the controller.
   *
   * @param model SanguineGame read only game model
   * @param controller the game controller which is a listener
   * @throws IllegalArgumentException if model is null
   */
  public GameBoardPanel(ModelReadOnlyInterface model, Listener controller)
      throws IllegalArgumentException, IOException {
    if (model == null) {
      throw new IllegalArgumentException("model is null!");
    }
    if (controller == null) {
      throw new IllegalArgumentException("controller is null");
    }
    this.controller = controller;
    this.model = model;

    SanguineGameBoard board = model.getBoard();

    if (board == null) {
      throw new IllegalStateException("Board is null! Game has probably not been started!");
    }

    this.setLayout(new GridLayout(board.getRows(), board.getCols()));

    this.cells = new ArrayList<>();

    // place the cells in the correct order in a grid style layout
    for (int row = 0; row < board.getRows(); row++) {
      List<CellPanel> currentRow = new ArrayList<>();
      for (int col = 0; col < board.getCols(); col++) {
        CellPanel newPanel = new CellPanel(row, col, model);
        newPanel.addMouseListener(new CellClickListener(List.of(controller), row, col, newPanel));
        currentRow.add(newPanel);
        this.add(currentRow.get(col));
      }
      cells.add(currentRow);
    }
  }

  /**
   * A method that displays the game board again for refreshing purposes.
   */
  public void displayBoard() {
      for (List<CellPanel> row: cells) {
          for (CellPanel cell : row) {
              cell.repaint();
              cell.removeHighlight();
          }
      }
  }
}
