package Controller;

import Model.Node;
import javafx.scene.layout.Pane;

public abstract class Controller extends Pane
{
    boolean playing;
    public Node selected;

    public abstract void updateSelected(Node n);
}
