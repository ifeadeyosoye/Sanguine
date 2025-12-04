package sanguine.controller;

import java.util.List;

import sanguine.model.*;
import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

/**
 * ....
 */
public class SanguinePlayerController implements Listener, ModelListener {

    private final SanguineGuiView view;
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
     * @param view the game view
     * @param color the player's color
     */
    public SanguinePlayerController(UserPlayer player, SanguineModel model, SanguineGuiView view,
                                    PlayerColor color) {
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
        this.view = view;
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
     * IFE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * these methods need to be implemented. im not leaving them to you, WE will be working on them
     * together. I think we will need more methods, but i forgot what they were.
     * im soooo tired to think.
     */

    /**
     * A method that defines what a subscriber should do when a card is clicked, given that card.
     *  the SanguineCard they recieve will only be from their hand. wont be possible to get an ops
     *  card.
     *
     * @param card the clicked SanguineGame card
     */
    @Override
    public void clickCard(SanguineCard card) {
        if (!myTurn) {
            view.showError("not your turn");
            return;
        }
        selectedCard = card;
    }

    /**
     * Defines what a subscriber should do when a cell is clicked, given cell coordinates.
     *
     * @param row the cell row
     * @param col the cell column
     */
    @Override
    public void clickCell(int row, int col) {
        if (!myTurn) {
            view.showError("not your turn");
            return;
        }

        selectedRow = row;
        selectedCol = col;
    }


    /**
     * A method that defines what a subscriber should do when 'p' is pressed on the keyboard.
     */
    @Override
    public void pressP() {
        if (!myTurn) {
            view.showError("not your turn");
            return;
        }

        try {
            model.passTurn();
        } catch (IllegalStateException exo) {
            // forgot what goes here... but throws ISE if game has not been started
        }
        resetAfterEveryTurn();
    }

    /**
     * A method that defines what a subscriber should do when 'm' is pressed on the keyboard.
     */
    @Override
    public void pressM() {
        if (!myTurn) {
            view.showError("not your turn");
            return;
        }

        if (selectedCard == null || selectedRow == -1 || selectedCol == -1) {
            //ai passes by submitting -1 -1 null
            if (!human) {
                model.passTurn();
                return;
            }
            view.showError("card or position invalid");
            return;
        }

        try {
            model.playTurn(selectedRow, selectedCol, selectedCard);
        } catch (IllegalArgumentException | IllegalStateException exo) {
            view.showError(exo.getMessage());
            return;
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
        view.showError("game is over");

    }

    private void resetAfterEveryTurn() {
        selectedCard = null;
        selectedRow = -1;
        selectedCol = -1;
    }



}