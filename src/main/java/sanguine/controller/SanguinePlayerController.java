package sanguine.controller;

import java.util.List;

import sanguine.model.*;
import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

/**
 * ....
 */
public class SanguinePlayerController implements Listener, ModelListener {

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

        if (view == null) {
            throw new IllegalArgumentException("View is null!");
        }

        if (color == null) {
            throw new IllegalArgumentException("Color is null!");
        }


        this.player = player;
        this.model = model;
        this.color = color;

        model.addControllerSubscriber(this);
        player.subscribe(this);
        view.subscribe(this);

        if (this.player instanceof HumanPlayer) {
            human = true;
        } else {
            human = false;
        }
    }

    /**
     * this is used because the controller needs the view and the view needs the controller. so we
     * opted to have the controller initialize the view in this method, not the constructor.
     *
     * @param view view
     */
    public void setView(SanguineGuiView view) {
        if (view == null) {
            throw new IllegalArgumentException("view cannot be null");
        }
        this.view = view;
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
            view.showError("ERROR!! Game has not been started. Try booting again.");
        }

        resetAfterEveryTurn();
    }

    @Override
    public void pressM() {
        if (!myTurn) {
            view.showError("Not your turn!");
            return;
        }

        if (selectedCard == null || selectedRow == -1 || selectedCol == -1) {
            // AI already passes by submitting -1 -1 null
            if (!human) {
                model.passTurn();
                return;
            }

            view.showError("You have not selected a card/cell! " +
                    "Make sure your desired choice is highlighted yellow!");
            return;
        }

        try {
            model.placeCardLegal(selectedRow, selectedCol, selectedCard,
                    new SanguinePlayer(List.of(), color, 7));
        } catch (IllegalArgumentException exo) {
            view.showError("Move is not possible! select a new move!");
            resetAfterEveryTurn();
            return;
        }

        try {
            model.playTurn(selectedRow, selectedCol, selectedCard);
        } catch (IllegalArgumentException | IllegalStateException exo) {
            // we checked if the move is valid already so the move must be valid
            // the only error this can throw is ISE if the game has not been started
            view.showError("ERROR!! Game has not been started. Try booting again.");
        }
        view.refresh();
        resetAfterEveryTurn();
    }

    @Override
    public void turnChanged(PlayerColor color) {
        if (this.color == color) {
            player.notifyTurn();
            myTurn = true;
        }

        view.refresh();
    }

    @Override
    public void notifyGameEnded() {
        if (!model.isGameOver()) {
            return;
        }

        Player winningplayer = model.getWinner();
        int winningScore = model.getScore(winningplayer.getColor());

        if (winningplayer.getColor() == PlayerColor.RED) {
            view.showError("Game over, Red Player won! Red's Score: " + winningScore);
        } else {
            view.showError("Game over, Blue Player won! Blue's Score: " + winningScore);
        }
    }

    private void resetAfterEveryTurn() {
        selectedCard = null;
        selectedRow = -1;
        selectedCol = -1;
    }
}