package sanguine.model;

/**
 * this is thrown when calculating scores. it will be thrown if the players have the same score in
 * the same row. it will also be thrown if the players have the same score in the overall game.
 */
public class TieException extends RuntimeException {

  /**
   * this is the constructor. we will pass in a message.
   *
   * @param message to be shown if warranted.
   */
  public TieException(String message) {
    super(message);
  }
}
