package sanguine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic version of a Sanguine board cell.
 *
 * <p>A cell either holds pawns or a card, and depending on the color of either object,</p>
 * there is an owner of that cell.
 * For example if there are red pawns in a cell, the red player is a temporary owner.
 * However, if a card is placed in the cell, the final owner is whichever player placed that card.
 */
public class BasicSanguineBoardCell implements SanguineBoardCell {
  // Private fields:
  private final List<BasicSanguinePawn> pawns;
  private SanguineCard card;
  private PlayerColor ownerColor;

  /**
   * A constructor that initializes pawns to an empty list.
   */
  public BasicSanguineBoardCell() {
    pawns = new ArrayList<>();
  }

  @Override
  public List<BasicSanguinePawn> getPawns() {
    return List.copyOf(pawns);
  }

  @Override
  public SanguineCard getCard() {
    if (card == null) {
      throw new IllegalStateException("There is no card in cell yet");
    }

    return new BasicSanguineCard(card.getName(), card.getCost(), card.getValue(),
        card.getStringInfluence());
  }

  @Override
  public int getValue() {
    if (!(card == null)) {
      return card.getValue();
    } else {
      return 0;
    }
  }

  @Override
  public PlayerColor getColor() {
    if (!pawns.isEmpty()) {
      return pawns.getFirst().getColor();
    } else if (card != null) {
      return ownerColor;
    } else {
      return null;
    }
  }

  @Override
  public void placePawn(SanguinePlayer currentPlayer) {

    if (getColor() == null) {
      pawns.add(new BasicSanguinePawn(currentPlayer.getColor()));
      ownerColor = currentPlayer.getColor();
      return;
    }

    if (this.getColor() != currentPlayer.getColor()) {
      throw new IllegalStateException("Can not place a pawn in a cell you dont own.");
    }

    if (pawns.size() >= 3) {
      throw new IllegalStateException("cell has too many pawns. cannot add more");
    }
    if (currentPlayer.getColor() == getColor()) {
      pawns.add(new BasicSanguinePawn(pawns.getFirst().getColor()));
    }


  }

  @Override
  public void placeCard(SanguineCard card, Player player) {
    if (this.card != null) {
      throw new IllegalStateException("Can not place another card in this cell");
    }

    if (card == null) {
      throw new IllegalArgumentException("Given a null card");
    }

    if (player == null) {
      throw new IllegalArgumentException("player cannot be null");
    }

    if (!(pawns.size() >= card.getCost())) {
      throw new IllegalStateException("Cell doesnt have enough pawns to place a card");
    }

    if (player.getColor() != pawns.getFirst().getColor()) {
      throw new IllegalArgumentException("Player color and pawn colors do not match!");
    }

    pawns.clear();
    this.card = card;
    this.ownerColor = player.getColor();

  }

  @Override
  public void changeColorsOfPawns(SanguinePlayer player) {
    int size = pawns.size();
    pawns.clear();

    for (int num = 0; num < size; num++) {
      pawns.add(new BasicSanguinePawn(player.getColor()));
    }
    ownerColor = player.getColor();
  }

  @Override
  public boolean containsCard() {
    return card != null && pawns.isEmpty();
  }
}