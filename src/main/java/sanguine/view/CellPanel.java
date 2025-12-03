package sanguine.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sanguine.controller.StubController;
import sanguine.model.BasicSanguineBoardCell;
import sanguine.model.ModelReadOnlyInterface;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineBoardCell;

/**
 * A JPanel that represents a game board cell.
 *
 * <p>This cell will either hold pawns, or a SanguineCard, with visual reference of blue or</p>
 * red for whoever owns the cell using Graphics2D.
 *
 * <p>This class also handles clicking and highlighting a cell.</p>
 */
public class CellPanel extends JPanel {
  // Private fields:
  private final ModelReadOnlyInterface model;
  private int row;
  private int col;
  private JLabel cardNum;
  private static final int PAWN_DIAMETER = 24;
  private static final int PAWN_RADIUS = PAWN_DIAMETER / 2;
  private Color red = new Color(0xC41E3A);
  private Color blue = new Color(0x0077b6);
  private boolean highlighted;

  /**
   * This takes in the location of this cell panel on the board, and the model.
   *
   * <p>Constructor does not check if coordinates are valid because that is handled in</p>
   * GameBoardPanel class.
   *
   * @param row row of cell.
   * @param col column of cell.
   * @param model model we are working with.
   */
  public CellPanel(int row, int col, ModelReadOnlyInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }

    this.row = row;
    this.col = col;
    this.model = model;
    this.setBorder(BorderFactory.createLineBorder(new Color(0xE7DECC), 2));
    this.setLayout(new FlowLayout());
  }

  /**
   * A method that highlights a card when it is clicked and unhighlights if it is already selected.
   */
  public void highlight() {
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

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();

    SanguineBoardCell cell = model.getCellAt(row, col);

    if (cell.containsCard()) {
      if (cell.getColor() == PlayerColor.RED) {
        g2d.setColor(red);
      } else {
        g2d.setColor(blue);
      }
      g2d.fillRect(0, 0, getWidth(), getHeight());

      drawCard(g2d, cell);
    } else if (!cell.getPawns().isEmpty()) {
      drawPawn(g2d, cell);
    } else {
      g2d.setColor(Color.WHITE);
      g2d.fillRect(0, 0, getWidth(), getHeight());
    }
  }

  /**
   * A private helper method that draws the value number for the card on the panel.
   *
   * @param g2d Graphics 2D object
   * @param cell the given cell
   */
  private void drawCard(Graphics2D g2d, SanguineBoardCell cell) {
    String value = String.valueOf(cell.getValue());
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("TimesNewRoman", Font.BOLD, 32));

    FontMetrics valueMetrics = g2d.getFontMetrics();
    int textWidth = valueMetrics.stringWidth(value);
    int textHeight = valueMetrics.getAscent();

    int x = (getWidth() - textWidth) / 2;
    int y = (getHeight() + textHeight) / 2 - 2;
    g2d.drawString(value, x, y);
  }

  /**
   * A private helper method that draws the correct number of pawns on the panel as circles.
   *
   * @param g2d Graphics 2D object
   * @param cell the given cell
   */
  private void drawPawn(Graphics2D g2d, SanguineBoardCell cell) {
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    if (cell.getColor() == PlayerColor.RED) {
      g2d.setColor(red);
    } else {
      g2d.setColor(blue);
    }

    // Margins used for pawn placement
    int pawnHorizontalMargin = getWidth() - PAWN_DIAMETER;
    int pawnVerticalMargin = getHeight() - PAWN_DIAMETER;

    if (cell.getPawns().size() == 1) {
      int x = pawnHorizontalMargin / 2;
      int y = pawnVerticalMargin / 2;

      g2d.fillOval(x, y, PAWN_DIAMETER, PAWN_DIAMETER);
    }

    if (cell.getPawns().size() == 2) {
      int x = pawnHorizontalMargin / 3;
      int y = pawnVerticalMargin / 3;

      g2d.fillOval(x, y, PAWN_DIAMETER, PAWN_DIAMETER);
      g2d.fillOval(2 * x, 2 * y, PAWN_DIAMETER, PAWN_DIAMETER);
    }

    if (cell.getPawns().size() == 3) {
      int x = pawnHorizontalMargin / 4;
      int y = pawnVerticalMargin / 4;

      g2d.fillOval(x, y, PAWN_DIAMETER, PAWN_DIAMETER);
      g2d.fillOval(2 * x, 2 * y, PAWN_DIAMETER, PAWN_DIAMETER);
      g2d.fillOval(3 * x, 3 * y, PAWN_DIAMETER, PAWN_DIAMETER);
    }
  }
}
