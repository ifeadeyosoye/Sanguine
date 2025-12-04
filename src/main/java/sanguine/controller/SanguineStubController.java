package sanguine.controller;

import java.util.List;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

/**
 * This specific controller handles printing out messages of what card is clicked on in the GUI.
 *
 * <p>This stub controller is used for handling clicking on cards and cells in the GUI</p>
 * in the early stages.
 */
public class SanguineStubController implements StubController, Listener {
  // Private Fields:
  private SanguineCard card;
  private final SanguineModel model;
  private SanguineGuiView view;

  /**
   * A constructor for the controller that takes in a model.
   *
   * @param model model the mutable game model
   */
  public SanguineStubController(SanguineModel model) {
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    this.model = model;
  }

  /**
   * A method that sets the view for the controller.
   * This method is necessary because we need the view to take in a controller in the constructor,
   * but we come across a loop where this is not possible.
   *
   * <p>So this method needs to be called when you want to place a view into a controller.</p>
   *
   * @param view SanguineGame view
   */
  public void setView(SanguineGuiView view) {
    if (view == null) {
      throw new IllegalArgumentException("view cannot be null");
    }
    this.view = view;
  }

  @Override
  public void playGame(int rows, int cols, List<SanguineCard> deck1, List<SanguineCard> deck2,
                       int handSize) {
    if (view == null) {
      throw new IllegalStateException("controller needs a view");
    }

    model.startGame(rows, cols, deck1, deck2, handSize);

    while (!model.isGameOver()) {

    }
  }
  
  @Override
  public void clickCard(SanguineCard card) {
    System.out.println("This card has been pressed: " + card.toString());
  }

  @Override
  public void clickCell(int row, int col) {
    System.out.println("clicked cell at row: " + row + " col: " + col);
  }

  @Override
  public void pressP() {
    System.out.println("Pressed key 'p' for pass.");
  }

  @Override
  public void pressM() {
    System.out.println("Pressed key 'm' for move.");
  }

  @Override
    public void confirmMove() {
      System.out.println("Move confirmed!");
  }
}
