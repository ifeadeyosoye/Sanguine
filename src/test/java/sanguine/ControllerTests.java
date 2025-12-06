package sanguine;

import org.junit.Test;
import sanguine.controller.SanguinePlayerController;
import sanguine.model.*;
import sanguine.strategies.BasicStrategy;
import sanguine.strategies.MaximizeRowScore;
import sanguine.view.SanguineGuiView;
import sanguine.view.SanguineViewFrame;

import java.io.IOException;

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

    /**
     * This makes sure that cards are properly being selected.
     *
     * @throws IOException when the deck file is not read properly
     */
    @Test
    public void testCardSelection() throws IOException {

        Appendable controllerLog = new StringBuilder();

        SanguineTrackInputMockController mockController = new SanguineTrackInputMockController(
            controllerLog);

        SanguineModel model = new BasicSanguineModel();
        model.createDeck().get(0);

        SanguineCard card = (SanguineCard)model.createDeck().get(0);

        mockController.clickCard(card);
        assertEquals("clickCard " + card, controllerLog.toString());
    }

    /**
     * Makes sure that when you click a card the model receives it correctly.
     *
     * @throws IOException if deck is unreadable
     */
    @Test
    public void testCardSelectionClickOnce() throws IOException {

        Appendable log = new StringBuilder();
        MockModel mockModel = new MockModel(log);

        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller = new SanguinePlayerController(player, mockModel,
            PlayerColor.RED);

        SanguineMockTrackInputView view = new SanguineMockTrackInputView(log);
        controller.setView(view);
        int row = 1;
        int col = 1;
        SanguineModel model = new BasicSanguineModel();
        model.createDeck().get(0);
        SanguineCard card = (SanguineCard)model.createDeck().get(0);

        controller.clickCard(card);
        controller.clickCell(row, col);
        controller.pressM();

        assertTrue(log.toString().contains("" + row));
        assertTrue(log.toString().contains("" + col));
        assertTrue(log.toString().contains("" + card));

    }

    /**
     * Makes sure that when a card is clicked, and then another one is clicked, the new one is clicked.
     *
     * @throws IOException if deck is unreadable.
     */
    @Test
    public void testCardSelectionClickTwice() throws IOException {

        Appendable log = new StringBuilder();
        MockModel mockModel = new MockModel(log);

        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller = new SanguinePlayerController(player, mockModel,
            PlayerColor.RED);

        SanguineMockTrackInputView view = new SanguineMockTrackInputView(log);
        controller.setView(view);
        int row = 1;
        int col = 1;
        SanguineModel model = new BasicSanguineModel();
        SanguineCard card = (SanguineCard)model.createDeck().get(0);

        controller.clickCard(card);
        controller.clickCell(row, col);
        controller.pressM();

        int newRow = 2;
        int newCol = 2;

        SanguineCard newCard = (SanguineCard)model.createDeck().get(1);

        assertTrue(log.toString().contains("" + newRow));
        assertTrue(log.toString().contains("" + newCol));
        assertTrue(log.toString().contains("" + newCard));

    }

    /**
     * This makes sure that refresh and remove highlight are done properly after the model makes a move.
     *
     * @throws IOException when the deck file is not read properly
     */
    @Test
    public void validPlayTurn() throws IOException {
        Appendable log = new StringBuilder();
        MockModel mockModel = new MockModel(log);
        SanguineModel model = new BasicSanguineModel();

        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller = new SanguinePlayerController(player, mockModel,
            PlayerColor.RED);

        SanguineMockTrackInputView view = new SanguineMockTrackInputView(log);
        controller.setView(view);

        SanguineCard card = (SanguineCard) model.createDeck().get(0);
        int row = 1;
        int col = 1;

        controller.clickCard(card);
        controller.clickCell(row, col);
        controller.pressM();

        assertTrue(log.toString().contains("refresh"));
        assertTrue(log.toString().contains("removeHighlight"));
    }

    /**
     * Makes sure pressM does not allow you to play without having clicked a card.
     *
     * @throws IOException if deck file is unreadable.
     */
    @Test
    public void testMAlertsWhenNoCard() throws IOException {
        Appendable log = new StringBuilder();
        MockModel mockModel = new MockModel(log);
        SanguineModel model = new BasicSanguineModel();

        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller = new SanguinePlayerController(player, mockModel,
            PlayerColor.RED);

        SanguineMockTrackInputView view = new SanguineMockTrackInputView(log);
        controller.setView(view);

        SanguineCard card = (SanguineCard) model.createDeck().get(0);
        int row = 1;
        int col = 1;

        controller.clickCell(row, col);
        controller.pressM();

        System.out.println(log);
        assertTrue(log.toString().contains("You have not selected a card or cell!"));
    }

    /**
     * Makes sure pressM does not allow you to play without having clicked a cell.
     *
     * @throws IOException if deck file is unreadable.
     */
    @Test
    public void testMAlertsWhenNoCell() throws IOException {
        Appendable log = new StringBuilder();
        MockModel mockModel = new MockModel(log);
        SanguineModel model = new BasicSanguineModel();

        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller = new SanguinePlayerController(player, mockModel,
            PlayerColor.RED);

        SanguineMockTrackInputView view = new SanguineMockTrackInputView(log);
        controller.setView(view);

        SanguineCard card = (SanguineCard) model.createDeck().get(0);
        int row = 1;
        int col = 1;

        controller.clickCard(card);
        controller.pressM();

        assertTrue(log.toString().contains("You have not selected a card or cell!"));
    }


    /**
     * Makes sure that controller resets the selected card, col, and row.
     *
     * @throws IOException if the deck file is unreachable
     */
    @Test
    public void testMoveResetsAfterPressM() throws IOException {
        Appendable log = new StringBuilder();
        MockModel mockModel = new MockModel(log);
        SanguineModel model = new BasicSanguineModel();

        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller = new SanguinePlayerController(player, mockModel,
            PlayerColor.RED);

        SanguineMockTrackInputView view = new SanguineMockTrackInputView(log);
        controller.setView(view);

        SanguineCard card = (SanguineCard) model.createDeck().get(0);
        int row = 1;
        int col = 1;

        controller.clickCard(card);
        controller.clickCell(row, col);
        controller.pressM();

        controller.pressM();

        assertTrue(log.toString().contains("You have not selected a card or cell!"));
    }


    /**
     * Makes sure that turnChanged is called correctly.
     *
     * @throws IOException when the deck file is not read properly
     */
    @Test
    public void turnEndsAlert() throws IOException {
        Appendable log = new StringBuilder();
        MockModel mockModel = new MockModel(log);
        SanguineModel model = new BasicSanguineModel();

        UserPlayer player = new HumanPlayer(PlayerColor.RED);

        SanguinePlayerController controller = new SanguinePlayerController(player, mockModel,
            PlayerColor.RED);

        SanguineMockTrackInputView view = new SanguineMockTrackInputView(log);
        controller.setView(view);


        controller.turnChanged(PlayerColor.RED);
        assertTrue(log.toString().contains("Your turn! Select a cell and card to play!"));

    }

    /**
     * Tests if the controller passes twice and then the model ends the games.
     *
     * @throws IOException when the deck file is not read properly
     */
    @Test
    public void gameEndsAlert() throws IOException {
        Appendable log = new StringBuilder();
        SanguineModel model = new BasicSanguineModel();
        model.startGame(3, 5, model.createDeck(), model.createDeck(), 7);
        UserPlayer redPlayer = new HumanPlayer(PlayerColor.RED);
        UserPlayer bluePlayer = new HumanPlayer(PlayerColor.BLUE);

        SanguinePlayerController redController = new SanguinePlayerController(redPlayer, model,
            PlayerColor.RED);
        SanguinePlayerController blueController = new SanguinePlayerController(bluePlayer, model,
            PlayerColor.BLUE);

        SanguineMockTrackInputView redView = new SanguineMockTrackInputView(log);
        redController.setView(redView);

        SanguineMockTrackInputView blueView = new SanguineMockTrackInputView(log);
        blueController.setView(blueView);

        SanguineCard card = (SanguineCard) model.createDeck().get(0);
        int row = 0;
        int col = 0;
        redController.clickCard(card);
        redController.clickCell(row, col);
        redController.pressM();

        blueController.pressP();
        redController.pressP();
        assertTrue(log.toString().contains("Game over"));
    }
}
