package sanguine.controller;

import java.io.IOException;

import sanguine.model.*;
import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

/**
 * This is a controller that is dedicated to a specific Sanguine player.
 *
 * <p>It constructs with a model, UserPlayer and player color, but sets the view using a public method.</p>
 * This controller works with AI and human players.
 *
 * <p>This controller subscribes to the user player so that it knows what actions to communicate to the model.</p>
 * Therefore it responds to events at any time from human and AI players.
 * It is also a listener for the model to see when a player's turn changes.
 *
 * <p>When it is NOT the players turn who is connected to this controller, the view is DISABLED.</p>
 * This is so that the opposing player can not interact with the opponents view.
 * In order to be clear though, we create a pop-up on the view when it is a player's turn again.
 */
public class SanguinePlayerController implements Listener, ModelListener, PlayerControllerInterface {
    // Private Fields:
    private SanguineGuiView view;
    private final SanguineModel model;
    private final UserPlayer player;
    private final PlayerColor color;
    private SanguineCard selectedCard;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean myTurn;
    private final boolean human;

    /**
     * A constructor that creates a controller for a specific player using the model, view, player and their color.
     *
     * @param player the UserPlayer for this controller
     * @param model the game model
     * @param color the player's color
     */
    public SanguinePlayerController(UserPlayer player, SanguineModel model, PlayerColor color) {
        if (player == null) {
            throw new IllegalArgumentException("Player is null!");
        }

        if (model == null) {
            throw new IllegalArgumentException("Model is null!");
        }

        if (color == null) {
            throw new IllegalArgumentException("Color is null!");
        }


        this.player = player;
        this.model = model;
        this.color = color;

        model.addControllerSubscriber(this);
        player.subscribe(this);

        if (this.player instanceof HumanPlayer) {
            human = true;
        } else {
            human = false;
        }
    }

    @Override
    public void setView(SanguineGuiView view) {
        if (view == null) {
            throw new IllegalArgumentException("view cannot be null");
        }
        this.view = view;
        this.view.subscribe(this);

        if (color == PlayerColor.RED) {
            myTurn = true;
            view.changeInteraction(myTurn);
            view.showError("Red player's turn to start the game!");
        } else {
            myTurn = false;
            view.changeInteraction(myTurn);
        }

        if (!human) {
            // because we shouldnt be able to do anything with the AI player
            view.changeInteraction(false);
        }
    }

    @Override
    public void clickCard(SanguineCard card) {
        if (!myTurn) {
            view.showError("Not your turn!");
            return;
        }
        selectedCard = card;
    }

    @Override
    public void clickCell(int row, int col) {
        if (!myTurn) {
            view.showError("Not your turn!");
            return;
        }
        selectedRow = row;
        selectedCol = col;
    }

    @Override
    public void pressP() {
        if (!myTurn) {
            view.showError("Not your turn!");
            return;
        }
        try {
            model.passTurn();
        } catch (IllegalStateException exo) {
            System.out.println(exo);
            view.showError("ERROR!! Game has not been started. Try booting again.");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

        view.removeHighlight();
        view.refresh();
        resetAfterEveryTurn();
        if (!model.isGameOver()) {
            view.showError("You passed your turn!");
        }
    }

    @Override
    public void pressM() {
        if (!myTurn) {
            view.showError("Not your turn!");
            return;
        }

        if (selectedCard == null || selectedRow == -1 || selectedCol == -1) {
            // AI already passes by submitting -1 -1 null
            if (human) {
                view.showError("You have not selected a card or cell!");
            }
            return;
        }

        try {
            model.playTurn(selectedRow, selectedCol, selectedCard);
            resetAfterEveryTurn();
            view.removeHighlight();
            view.refresh();
        } catch (IllegalArgumentException | IllegalStateException exo) {
            view.showError("Move is not possible! Select a new move!");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void turnChanged(PlayerColor color) throws IOException {
        if (this.color == color) {
            myTurn = true;
            player.notifyTurn();
            view.changeInteraction(myTurn);
            if (!model.isGameOver()) {
                view.showError("Your turn! Select a cell and card to play!");
            }
        } else {
            myTurn = false;
            view.changeInteraction(myTurn);
        }

        view.removeHighlight();
        view.refresh();
    }

    @Override
    public void notifyGameEnded() {
        if (!model.isGameOver()) {
            return;
        }

        try {
            Player winningplayer = model.getWinner();
            int winningScore = model.getScore(winningplayer.getColor());

            if (winningplayer.getColor() == PlayerColor.RED) {
                view.showError("Game over, Red Player won! Red's Score: " + winningScore);
            } else {
                view.showError("Game over, Blue Player won! Blue's Score: " + winningScore);
            }
        } catch (TieException exo) {
            int winningScore = model.getScore(PlayerColor.RED);
            view.showError("Game over, Game is tied! Your Scores: " + winningScore);
        }

        view.changeInteraction(false);
    }

    private void resetAfterEveryTurn() {
        selectedCard = null;
        selectedRow = -1;
        selectedCol = -1;
    }
}