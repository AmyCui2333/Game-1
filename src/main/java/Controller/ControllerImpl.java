package Controller;

import Model.Model;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class ControllerImpl extends Controller
{
    private Image play, pause;
    private Button playPause;
    public ControllerImpl(Model m)
    {
        VBox mainV = new VBox();
        Label title = new Label("Controls");
        title.setFont(Font.font(30));
        playing = false;
        play = new Image(getClass().getResourceAsStream("play.png"), 30, 30, false, false);
        pause = new Image(getClass().getResourceAsStream("pause.png"), 30, 30, false, false);
        playPause = new Button();
        playPause.setGraphic(new ImageView(play));
        playPause.setOnAction(e ->
        {
            playing = !playing;
            if(playing)
                playPause.setGraphic(new ImageView(pause));
            else playPause.setGraphic(new ImageView(play));
        });
        mainV.setPadding(new Insets(20));
        mainV.setSpacing(20);
        mainV.getChildren().addAll(title, playPause, new Separator());
        this.getChildren().add(mainV);
    }
}
