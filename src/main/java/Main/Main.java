package Main;


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
    private Canvas canv;
    private GraphicsContext gc;
    private VBox controlsBox;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane root = new Pane();
        HBox mainH = new HBox();
        root.getChildren().addAll(mainH);
        //main setup here
        canv = new Canvas(800, 600);
        gc = canv.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeLine(0, 0, canv.getWidth(), canv.getHeight());
        gc.fillRect(0, 0, 100, 100);
        controlsBox = new VBox();
        mainH.getChildren().addAll(controlsBox, canv);
        primaryStage.setTitle("Game");
        //set scene

        canv.widthProperty().bind(root.widthProperty().subtract(controlsBox.widthProperty()));
        canv.heightProperty().bind(root.heightProperty());
        canv.setVisible(true);
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
        System.out.println(canv.getWidth());
        System.out.println(canv.getHeight());
        canv.setOnMouseClicked(k ->
        {
            System.out.println("pressed!");
            gc.strokeLine(0, 0, canv.getWidth(), canv.getHeight());
            gc.fillRect(0, 0, 100, 100);
        });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
