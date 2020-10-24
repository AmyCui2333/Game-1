package Main;


import Controller.ControllerFac;
import Controller.Controller;
import Model.Model;
import Model.ModelFac;
import View.View;
import View.ViewFac;
import javafx.application.Application;
import javafx.geometry.Insets;
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
    private Controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane root = new Pane();
        HBox mainH = new HBox();
        root.getChildren().addAll(mainH);
        //main setup here
        //TODO CONSTRUCT MODEL
        model = ModelFac.getModel();
        view = ViewFac.getView(model);
        controller = ControllerFac.getCont(model, view);
        view.setController(controller);
        mainH.getChildren().addAll(controller, view);
        primaryStage.setTitle("Game");
        //set scene

        mainH.setPadding(new Insets(20));
        mainH.setSpacing(10);
        view.prefWidthProperty().bind(root.widthProperty().subtract(controller.widthProperty()).subtract(mainH.getPadding().getLeft() + mainH.getPadding().getRight() + 1 * mainH.getSpacing()));
        view.prefHeightProperty().bind(root.heightProperty().subtract(mainH.getPadding().getBottom() + mainH.getPadding().getTop() + 0 * mainH.getSpacing()));
        view.setVisible(true);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
