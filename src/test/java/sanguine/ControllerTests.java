package sanguine;

import org.junit.Test;
import sanguine.controller.SanguinePlayerController;
import sanguine.model.*;
import sanguine.strategies.BasicStrategy;
import sanguine.strategies.MaximizeRowScore;
import sanguine.view.SanguineGuiView;
import sanguine.view.SanguineViewFrame;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A file for tests relating to the controller.
 *
 * Here we test:
 * - The construction of the controller
 * - How turns are managed throughout the game
 * - Selecting cards
 * - Playing a move
 * - Passing a turn
 * - The game being over and displaying the winner
 * - Edge cases like playing the same card twice or attempting to pass 2 times in a row
 * - We will also attempt to test human v.s. AI players
 * - Making sure the cards/cells are unhighlighted after every move
 * - Also making sure that after any play or pass, the cards and cells are reset for the controller
 */
public class ControllerTests {
    // Testing controller construction:

    /**
     * This tests that an exception is thrown when the userPlayer is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullUserPlayer() {
        SanguineModel model = new BasicSanguineModel();
        SanguinePlayerController controller1 = new SanguinePlayerController(null, model,
                PlayerColor.RED);
    }

    /**
     * This tests that an exception is thrown when the model is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullModel() {
        UserPlayer player = new HumanPlayer(PlayerColor.RED);
        SanguinePlayerController controller1 = new SanguinePlayerController(player, null,
                PlayerColor.RED);
    }

    /**
     * This tests that an exception is thrown when the player color is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullColor() {
        UserPlayer player = new HumanPlayer(PlayerColor.RED);
        SanguineModel model = new BasicSanguineModel();
        SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                null);
    }

    /**
     * This tests that a valid construction is possible for an AI player.
     */
    @Test
    public void testValidConstructionAi() {
        SanguineModel model = new BasicSanguineModel();
        BasicStrategy strat = new MaximizeRowScore();
        UserPlayer player = new AiPlayer(strat, PlayerColor.RED, model);

        SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                PlayerColor.RED);

        assertEquals(1, player.seeSubscribers().size());
        assertEquals(controller1, player.seeSubscribers().getFirst());

        assertEquals(1, model.seeSubscribers().size());
        assertEquals(controller1, model.seeSubscribers().getFirst());
    }

    /**
     * This tests that a valid construction is possible for a Human player.
     */
    @Test
    public void testValidConstructionHuman() {
        SanguineModel model = new BasicSanguineModel();
        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                PlayerColor.RED);

        assertEquals(0, player.seeSubscribers().size());

        assertEquals(1, model.seeSubscribers().size());
        assertEquals(controller1, model.seeSubscribers().getFirst());
    }

    /**
     * This tests that when setting a null view, an exception is thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSettingNullView() {
        SanguineModel model = new BasicSanguineModel();
        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                PlayerColor.RED);
        controller1.setView(null);
    }

    /**
     * This tests that we can set a valid view for the controller for a human red player.
     *
     * <p>Makes sure turn assignment is correct</p>
     */
    @Test
    public void testSettingValidViewRedHuman() {
        try {
            SanguineModel model = new BasicSanguineModel();
            UserPlayer player = new HumanPlayer(PlayerColor.RED);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);
            SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                PlayerColor.RED);
            SanguineGuiView view = new SanguineViewFrame(model, controller1, PlayerColor.RED);
            controller1.setView(view);

            // because a human red player and the beginning of the game
            // also reflects whose turn it is! so that is tested too
            assertTrue(view.interactable());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }

    /**
     * This tests that we can set a valid view for the controller for a human blue player.
     *
     * <p>Makes sure turn assignment is correct</p>
     */
    @Test
    public void testSettingValidViewBlueHuman() {
        try {
            SanguineModel model = new BasicSanguineModel();
            UserPlayer player = new HumanPlayer(PlayerColor.BLUE);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);
            SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                    PlayerColor.BLUE);
            SanguineGuiView view = new SanguineViewFrame(model, controller1, PlayerColor.BLUE);
            controller1.setView(view);

            // because a human blue player and the beginning of the game
            assertFalse(view.interactable());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }

    /**
     * This tests that we can set a valid view for the controller for an AI blue player.
     *
     * <p>Makes sure turn assignment is correct</p>
     */
    @Test
    public void testSettingValidViewAiBlue() {
        try {
            SanguineModel model = new BasicSanguineModel();
            BasicStrategy strat = new MaximizeRowScore();
            UserPlayer player = new AiPlayer(strat, PlayerColor.BLUE, model);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);
            SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                    PlayerColor.BLUE);
            SanguineGuiView view = new SanguineViewFrame(model, controller1, PlayerColor.BLUE);
            controller1.setView(view);

            // because Ai player, should not be interactable
            assertFalse(view.interactable());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }

    /**
     * This tests that we can set a valid view for the controller for an AI red player.
     *
     * <p>Makes sure turn assignment is correct</p>
     */
    @Test
    public void testSettingValidViewAiRed() {
        try {
            SanguineModel model = new BasicSanguineModel();
            BasicStrategy strat = new MaximizeRowScore();
            UserPlayer player = new AiPlayer(strat, PlayerColor.RED, model);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);
            SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                    PlayerColor.RED);
            SanguineGuiView view = new SanguineViewFrame(model, controller1, PlayerColor.RED);
            controller1.setView(view);

            // because Ai player, should not be interactable
            assertFalse(view.interactable());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }

    /**
     * This tests the controller talking to the view to output a message when a player tries to pass not on their turn.
     *
     * <p>This uses the track input view mock</p>
     */
    @Test
    public void testPassingNotYourTurn() {
        try {
            SanguineModel model = new BasicSanguineModel();
            Appendable log = new StringBuilder();

            UserPlayer player = new HumanPlayer(PlayerColor.BLUE);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);
            SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                    PlayerColor.BLUE);
            SanguineGuiView mockView = new SanguineMockTrackInputView(log);
            controller1.setView(mockView);
            controller1.pressP();

            assertEquals("subscribeshowError:Not your turn!", log.toString());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }


    /**
     * This tests the controller talking to the view to output a message when a player tries to play not on their turn.
     *
     * <p>This uses the track input view mock</p>
     */
    @Test
    public void testPlayingNotYourTurn() {
        try {
            SanguineModel model = new BasicSanguineModel();
            Appendable log = new StringBuilder();

            UserPlayer player = new HumanPlayer(PlayerColor.BLUE);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);
            SanguinePlayerController controller1 = new SanguinePlayerController(player, model,
                    PlayerColor.BLUE);
            SanguineGuiView mockView = new SanguineMockTrackInputView(log);
            controller1.setView(mockView);
            controller1.pressM();

            assertEquals("subscribeshowError:Not your turn!", log.toString());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }

    /**
     * This tests that each player's views are being turned on and off accordingly for human players using a mock.
     */
    @Test
    public void testViewInteractionCorrectnessHuman() {
        try {
            SanguineModel model = new BasicSanguineModel();

            UserPlayer player1 = new HumanPlayer(PlayerColor.BLUE);
            UserPlayer player2 = new HumanPlayer(PlayerColor.RED);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);

            SanguinePlayerController controller1 = new SanguinePlayerController(player1, model,
                    PlayerColor.BLUE);
            SanguinePlayerController controller2 = new SanguinePlayerController(player2, model,
                    PlayerColor.RED);

            SanguineGuiView view1 = new SanguineViewMockInteraction();
            SanguineGuiView view2 = new SanguineViewMockInteraction();

            controller1.setView(view1);
            controller2.setView(view2);

            assertFalse(view1.interactable());
            assertTrue(view2.interactable());

            controller1.turnChanged(PlayerColor.BLUE);
            controller2.turnChanged(PlayerColor.BLUE);

            assertFalse(view2.interactable());
            assertTrue(view1.interactable());

            controller1.turnChanged(PlayerColor.RED);
            controller2.turnChanged(PlayerColor.RED);

            assertFalse(view1.interactable());
            assertTrue(view2.interactable());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }

    /**
     * This tests that each player's views are being turned on and off accordingly for AI players using a mock.
     *
     * <p>This tests how AI players and humans work together with GUI interaction as well.</p>
     */
    @Test
    public void testViewInteractionCorrectnessAiAndHuman() {
        try {
            SanguineModel model = new BasicSanguineModel();
            BasicStrategy strat = new MaximizeRowScore();

            UserPlayer player1 = new AiPlayer(strat, PlayerColor.RED, model);
            UserPlayer player2 = new HumanPlayer(PlayerColor.BLUE);
            model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);

            SanguinePlayerController controller1 = new SanguinePlayerController(player1, model,
                    PlayerColor.RED);
            SanguinePlayerController controller2 = new SanguinePlayerController(player2, model,
                    PlayerColor.BLUE);

            SanguineGuiView view1 = new SanguineViewMockInteraction();
            SanguineGuiView view2 = new SanguineViewMockInteraction();

            controller1.setView(view1);
            controller2.setView(view2);

            assertFalse(view1.interactable());
            assertFalse(view2.interactable());

            controller1.turnChanged(PlayerColor.BLUE);
            controller2.turnChanged(PlayerColor.BLUE);

            assertFalse(view2.interactable());
            assertTrue(view1.interactable());

            controller1.turnChanged(PlayerColor.RED);
            controller2.turnChanged(PlayerColor.RED);

            assertFalse(view1.interactable());
            assertFalse(view2.interactable());
        } catch (Exception exo) {
            throw new RuntimeException(exo);
        }
    }
}
