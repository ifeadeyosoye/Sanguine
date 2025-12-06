package sanguine;

import org.junit.Test;
import sanguine.controller.SanguineStubController;
import sanguine.controller.StubController;
import sanguine.model.*;
import sanguine.strategies.BasicStrategy;
import sanguine.strategies.MaximizeRowScore;
import sanguine.view.Listener;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.model.MultipleFailureException.assertEmpty;

/**
 * This is a class that tests the new UserPlayer object.
 *
 * We will test:
 * - the construction of both AI and human user players
 * - notifyTurn
 * - subscribe()
 */
public class UserPlayerTests {
    // Construction Tests:

    /**
     * This tests that the constructor throws when the strategy is null for an AI player
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullAiStrat() {
        ModelReadOnlyInterface model = new BasicSanguineModel();
        UserPlayer player = new AiPlayer(null, PlayerColor.RED, model);
    }

    /**
     * This tests that the constructor throws when the player color is null for an AI player
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullAiColor() {
        ModelReadOnlyInterface model = new BasicSanguineModel();
        BasicStrategy strat = new MaximizeRowScore();
        UserPlayer player = new AiPlayer(strat, null, model);
    }

    /**
     * This tests that the constructor throws when the model is null for an AI player
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullAiModel() {
        BasicStrategy strat = new MaximizeRowScore();
        UserPlayer player = new AiPlayer(strat, PlayerColor.RED, null);
    }

    /**
     * This tests that the constructor throws when the player color is null for a human player
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullHumanColor() {
        UserPlayer player = new HumanPlayer(null);
    }

    /**
     * This tests that construction is valid and nothing is thrown when all arguments are valid for an AI player
     */
    @Test
    public void testValidConstructionAi() {
        ModelReadOnlyInterface model = new BasicSanguineModel();
        BasicStrategy strat = new MaximizeRowScore();
        UserPlayer player = new AiPlayer(strat, PlayerColor.RED, model);
        assertEquals(0, player.seeSubscribers().size());
    }

    /**
     * This tests that construction is valid and nothing is thrown when all arguments are valid for a Human player
     */
    @Test
    public void testValidConstructionHuman() {
        UserPlayer player1 = new HumanPlayer(PlayerColor.RED);
        UserPlayer player2 = new HumanPlayer(PlayerColor.BLUE);
        assertEquals(0, player1.seeSubscribers().size());
        assertEquals(0, player2.seeSubscribers().size());
    }

    // subscribe() Tests:

    @Test
    public void testSubscribeAi() {
        ModelReadOnlyInterface model = new BasicSanguineModel();
        SanguineModel model2 = new BasicSanguineModel();
        BasicStrategy strat = new MaximizeRowScore();
        UserPlayer player = new AiPlayer(strat, PlayerColor.RED, model);
        Listener controller = new SanguineStubController(model2);

        player.subscribe(controller);
        assertEquals(1, player.seeSubscribers().size());
        assertEquals(controller, player.seeSubscribers().getFirst());
    }

    @Test
    public void testSubscribeHuman() {
        UserPlayer player = new HumanPlayer(PlayerColor.RED);
        SanguineModel model = new BasicSanguineModel();

        Listener controller = new SanguineStubController(model);

        player.subscribe(controller);
        assertEquals(0, player.seeSubscribers().size());
    }

    // notifyTurn() AI Tests:

    /**
     * This tests that the AI is properly communicating to the controller using a mock controller when making a move.
     */
    @Test
    public void testNotifyTurnAi() {
        try {
            Appendable log = new StringBuilder();
            SanguineModel model = new BasicSanguineModel();
            List<SanguineCard> deck = model.createDeck();
            model.startGame(3, 5, deck, deck, 7);
            BasicStrategy strat = new MaximizeRowScore();
            SanguineTrackInputMockController mock = new SanguineTrackInputMockController(log);
            UserPlayer player = new AiPlayer(strat, PlayerColor.RED, model);

            player.subscribe(mock);
            player.notifyTurn();

            assertEquals("clickCardclickCellpressM", log.toString());
        } catch (IOException exo) {
            // ....
        }
    }
}
