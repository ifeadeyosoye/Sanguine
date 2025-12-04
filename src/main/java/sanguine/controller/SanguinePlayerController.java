package sanguine.controller;

import java.util.List;

import sanguine.model.*;
import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

/**
 * ....
 */
public class SanguinePlayerController implements Listener, StubController, ModelListener {

    private final SanguineGuiView view;
    private final SanguineModel model;
    private final UserPlayer player;
    private final PlayerColor color;
    private SanguineCard selectedCard;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean myTurn;


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
    }

    /**
     * IFE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * these methods need to be implemented. im not leaving them to you, WE will be working on them
     * together. I think we will need more methods, but i forgot what they were.
     * im soooo tired to think.
     */
    /**
     * A method that defines what a subscriber should do when a card is clicked, given that card.
     *
     * @param card the clicked SanguineGame card
     */
    @Override
    public void clickCard(SanguineCard card) {
        if (!myTurn) {
            //TODO: show error pop up on view
        }

        // if they try to click on opponents card, do warning pop up

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
            //TODO: show error pop up on view
        }

        // TODO: if cell not empty, show error pop up on view

        selectedRow = row;
        selectedCol = col;
    }

    /**
     * A method that defines what a subscriber should do when 'p' is pressed on the keyboard.
     */
    @Override
    public void pressP() {
        if (!myTurn) {
            return;
        }

        try {
            model.passTurn();
        } catch (IllegalStateException exo) {
            // forgot what goes here... but throws ISE if game has not been started
        }
    }

    /**
     * A method that defines what a subscriber should do when 'm' is pressed on the keyboard.
     */
    @Override
    public void pressM() {
        if (!myTurn) {
            return;
        }

        if (selectedCard == null || selectedRow == -1 || selectedCol == -1) {
            // throw iae??
        }

        try {
            model.playTurn(selectedRow, selectedCol, selectedCard);
        } catch (IllegalArgumentException exo) {
            // MOVE IS INVALID
        } catch (IllegalStateException exo) {
            // GAME HAS NOT BEEN STARTED
        }
    }

    /**
     * A method that is called when a player confirms that their move decision is final.
     */
    @Override
    public void confirmMove() {

    }

    @Override
    public void playGame(int rows, int cols, List<SanguineCard> deck1, List<SanguineCard> deck2, int handSize) {

    }

    @Override
    public void turnChanged(PlayerColor color) {
        if (this.color == color) {
            player.notifyTurn();
            myTurn = true;
        }
    }
}