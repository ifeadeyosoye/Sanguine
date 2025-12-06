package sanguine;

import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

import javax.swing.*;
import java.io.IOException;

/**
 * A mock of the view for tracking input.
 */
public class SanguineMockTrackInputView extends JFrame implements SanguineGuiView {
    private Appendable log;

    /**
     * Constructor for this mock class that takes in an Appendable object.
     *
     * @param log the Appendable object
     */
    public SanguineMockTrackInputView(Appendable log) {
        if (log == null) {
            throw new IllegalArgumentException("log is null!");
        }

        this.log = log;
    }

    @Override
    public void refresh() {
        try {
            log.append("refresh");
        } catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }
    }

    @Override
    public void makeVisible() {
        try {
            log.append("makeVisible");
        } catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }
    }

    @Override
    public void subscribe(Listener listener) {
        try {
            log.append("subscribe");
        } catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }
    }

    @Override
    public void showError(String msg) {
        try {
            log.append("showError:" + msg);
        } catch (IOException exo) {
            // if we are here, the test will fail anyway so let it fail.
        }
    }

    @Override
    public void changeInteraction(boolean choice) {
      try {
        log.append("changeInteraction");
      } catch (IOException e) {
          // if we are here, the test will fail anyway so let it fail.
      }
    }

    @Override
    public void removeHighlight() {
      try {
        log.append("removeHighlight");
      } catch (IOException e) {
          // if we are here, the test will fail anyway so let it fail.
      }
    }

    @Override
    public boolean interactable() {
        return false;
    }
}
