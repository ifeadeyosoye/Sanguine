package sanguine.controller;

import java.util.List;
import sanguine.model.Player;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.model.SanguineModel;
import sanguine.model.UserPlayer;
import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

public class SanguinePlayerController implements Listener, ModelListener{

    private SanguineGuiView view;
    private SanguineModel model;
    private UserPlayer player;
    private PlayerColor color;

    public SanguinePlayerController(UserPlayer player, SanguineModel model, SanguineGuiView view,
                                    PlayerColor color) {
        if (player == null) {
            throw new IllegalArgumentException("player null");
        }
        player.subscribe(this);
        this.player = player;

        if (model == null) {
            throw new IllegalArgumentException("model null");
        }
        this.model = model;
        subscribe();

        if (view == null) {
            throw new IllegalArgumentException("view null");
        }
        this.view = view;

        if (color == null) {
            throw new IllegalArgumentException("color is null");
        }
        this.color = color;
    }

    /**
     * tells the controller to notify its player when its their turn.
     *
     * @param color of the current player. controller will notify its player if their colors match.
     */
    @Override
    public void turnChanged(PlayerColor color) {
        if (this.color == color) {
            player.notifyTurn();
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
     *
     * @param card the clicked SanguineGame card
     */
    @Override
    public void clickCard(SanguineCard card) {

    }

    /**
     * Defines what a subscriber should do when a cell is clicked, given cell coordinates.
     *
     * @param row the cell row
     * @param col the cell column
     */
    @Override
    public void clickCell(int row, int col) {

    }

    /**
     * A method that defines what a subscriber should do when 'p' is pressed on the keyboard.
     */
    @Override
    public void pressP() {

    }

    /**
     * A method that defines what a subscriber should do when 'm' is pressed on the keyboard.
     */
    @Override
    public void pressM() {

    }

    /**
     * A method that is called when a player confirms that their move decision is final.
     */
    @Override
    public void confirmMove() {

    }


    /**
     * subscribes the controller to the model. dont need to check for null model becuase the
     * constructor does that.
     */
    @Override
    public void subscribe() {
        this.model.addControllerSubscriber(this);
    }
}