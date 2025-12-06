package sanguine;

import sanguine.model.ModelListener;
import sanguine.model.PlayerColor;
import sanguine.model.SanguineCard;
import sanguine.view.Listener;

import java.io.IOException;

public class SanguineTrackInputMockController implements Listener, ModelListener {
    private Appendable log;

    /**
     * Constructor for this mock class that takes in an Appendable object.
     *
     * @param log the Appendable object
     */
    public SanguineTrackInputMockController(Appendable log) {
        if (log == null) {
            throw new IllegalArgumentException("log is null!");
        }

        this.log = log;
    }

    @Override
    public void turnChanged(PlayerColor color) throws IOException {

    }

    @Override
    public void notifyGameEnded() {

    }

    @Override
    public void clickCard(SanguineCard card) {
        try {
            log.append("clickCard");
        } catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }
    }

    @Override
    public void clickCell(int row, int col) {
        try {
            log.append("clickCell");
        } catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }
    }

    @Override
    public void pressP() {
        try {
            log.append("pressP");
        }  catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }

    }

    @Override
    public void pressM() {
        try {
            log.append("pressM");
        }  catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }
    }
}
