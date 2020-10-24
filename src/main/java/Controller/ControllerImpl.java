package Controller;

import Model.Model;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;



public class ControllerImpl extends Controller
{
    private final Image play, pause, step;
    private final Button playPause;
    private final Button stepBtn;
    private final Model model;
    private final Timeline gogogo;
    private double secondsPerDay;
    public ControllerImpl(Model m)
    {
        model = m;
        secondsPerDay = 1;
        VBox mainV = new VBox();
        Label title = new Label("Controls");
        title.setFont(Font.font(30));
        playing = false;
        play = new Image(getClass().getResourceAsStream("play.png"), 30, 30, false, true);
        pause = new Image(getClass().getResourceAsStream("pause.png"), 30, 30, false, true);
        step = new Image(getClass().getResourceAsStream("step.png"), 30, 30, false, true);
        playPause = new Button();
        playPause.setGraphic(new ImageView(play));
        playPause.setOnAction(e ->
        {
            playing = !playing;
            if(playing)
                playPause.setGraphic(new ImageView(pause));
            else playPause.setGraphic(new ImageView(play));
        });
        stepBtn = new Button();
        stepBtn.setGraphic(new ImageView(step));
        stepBtn.setOnAction(e ->
        {
            model.dayPass();
        });
        gogogo = new Timeline(
                new KeyFrame(Duration.seconds(secondsPerDay), a ->
                        {
                            if(playing)
                            {
                                //TODO ENABLE synchronized (model)
                                {
                                    System.out.println("moving");
                                    //model.dayPass();
                                }
                            }
                        }));
        gogogo.setCycleCount(Timeline.INDEFINITE);
        gogogo.play();
        gogogo.setRate(10);
        mainV.setPadding(new Insets(20));
        mainV.setSpacing(20);
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(stepBtn, playPause);
        mainV.getChildren().addAll(title, buttonBox, new Separator());
        this.getChildren().add(mainV);
    }
}
