package sanguine.controller;

import sanguine.view.SanguineGuiView;

/**
 * A representation of a player controller for a game of Sanguine.
 *
 * <p>Only has one method to set the view because we can not set it in the constructor.</p>
 */
public interface PlayerControllerInterface {
    /**
     * A method that sets the view for the controller.
     *
     * <p>This is used because the controller needs the view and the view needs the controller. </p>
     * So we opted to have the controller initialize the view in this method, not the constructor.
     *
     * @param view view
     */
    void setView(SanguineGuiView view);
}
