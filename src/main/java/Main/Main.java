package Main;


import Model.Model;
import View.View;
import View.ViewFac;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application
{
    private View view;
    private Model model;
    private VBox controlsBox;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane root = new Pane();
        HBox mainH = new HBox();
        root.getChildren().addAll(mainH);
        //main setup here
        //TODO CONSTRUCT MODEL
        model = null;
        view = ViewFac.getView(model);
        controlsBox = new VBox();
        mainH.getChildren().addAll(controlsBox, view);
        primaryStage.setTitle("Game");
        //set scene

        view.prefWidthProperty().bind(root.widthProperty().subtract(controlsBox.widthProperty()));
        view.prefHeightProperty().bind(root.heightProperty());
        view.setVisible(true);
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
