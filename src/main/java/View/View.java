package View;

import Model.Model;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class View extends Pane
{
    public View(Model m){}
    /**
     * Draws the current state on the screen
     */
    public abstract void draw();
}
