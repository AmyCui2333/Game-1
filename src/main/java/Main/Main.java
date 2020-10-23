package Main;


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //main setup here
        primaryStage.setTitle("An Interesting Title");
        //set scene
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
