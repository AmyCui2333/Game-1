package View;

import Controller.Controller;
import Model.Model;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class View extends Pane
{
    /**
     * Draws the current state on the screen
     */
    public abstract void draw();

    /**
     * sets the controller of this view
     * @param c the controller to set
     */
    public abstract void setController(Controller c);
}
