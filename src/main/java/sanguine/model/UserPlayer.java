package sanguine.model;

import sanguine.strategies.Coordinates;

/**
 * UserPlayer is a representation of a player who decides to make a move.
 *
 * <p>Represents a human player who clicks on our GUI or inputs commands</p>
 * or inputs commands or an AI player that automatically decides what actions to take.
 */
public interface UserPlayer {
  void notifyTurn();


}
