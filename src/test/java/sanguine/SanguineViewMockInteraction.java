package sanguine;

import sanguine.view.Listener;
import sanguine.view.SanguineGuiView;

import javax.swing.*;

/**
 * A mock of the Sanguine view that deals with making sure the view is interactable at certain points.
 */
public class SanguineViewMockInteraction extends JFrame implements SanguineGuiView {
    private boolean interactable;

    @Override
    public void refresh() {

    }

    @Override
    public void makeVisible() {

    }

    @Override
    public void subscribe(Listener listener) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void changeInteraction(boolean choice) {
        interactable = choice;
    }

    @Override
    public void removeHighlight() {

    }

    @Override
    public boolean interactable() {
        return interactable;
    }
}
