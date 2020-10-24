package Controller;

import Model.Model;
import Model.Node;
import View.View;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private View view;
    private Node selected;
    private GridPane selectedG;
    private final Label population, infected, dead, recovered, popuM, infeM, deadM, recoM;
    private final CheckBox mask, shutdown, maskAll;
    public ControllerImpl(Model m, View v)
    {
        model = m;
        view = v;
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
            update();
        });
        gogogo = new Timeline(
                new KeyFrame(Duration.seconds(secondsPerDay), a ->
                        {
                            if(playing)
                            {
                                synchronized (model)
                                {
                                    model.dayPass();
                                }
                                update();
                            }
                        }));
        gogogo.setCycleCount(Timeline.INDEFINITE);
        gogogo.play();
        Slider spd = new Slider(1, 1300, 1);
        spd.valueProperty().addListener((arg, oldVal, newVal) -> gogogo.setRate(newVal.doubleValue()));
        maskAll = new CheckBox("Mask All");
        maskAll.selectedProperty().addListener((arg, oldVal, newVal) ->
        {
            model.setMasked(newVal);
            update();
        });
        var dispInf = new GridPane();
        dispInf.setVgap(10);
        dispInf.setHgap(20);
        selectedG = new GridPane();
        selectedG.setHgap(20);
        selectedG.setVgap(10);
        selectedG.setVisible(false);
        Label pop = new Label("Population:");
        Label inf = new Label("Infected");
        Label ded = new Label("Dead:");
        Label rec = new Label("Recovered:");
        population = new Label();
        population.setTextFill(Color.GREY);
        infected = new Label();
        infected.setTextFill(Color.RED);
        dead = new Label();
        dead.setTextFill(Color.BLACK);
        recovered = new Label();
        recovered.setTextFill(Color.GREEN);
        GridPane.setHalignment(pop, HPos.LEFT);
        GridPane.setHalignment(inf, HPos.LEFT);
        GridPane.setHalignment(ded, HPos.LEFT);
        GridPane.setHalignment(rec, HPos.LEFT);
        GridPane.setHalignment(population, HPos.RIGHT);
        GridPane.setHalignment(infected, HPos.RIGHT);
        GridPane.setHalignment(dead, HPos.RIGHT);
        GridPane.setHalignment(recovered, HPos.RIGHT);
        selectedG.add(pop, 0, 0);
        selectedG.add(inf, 0, 1);
        selectedG.add(ded, 0, 2);
        selectedG.add(rec, 0, 3);
        selectedG.add(population, 1, 0);
        selectedG.add(infected, 1, 1);
        selectedG.add(dead, 1, 2);
        selectedG.add(recovered, 1, 3);
        mask = new CheckBox("Masks");
        var popM = new Label("Population:");
        var infM = new Label("Infected");
        var dedM = new Label("Dead:");
        var recM = new Label("Recovered:");
        popuM = new Label();
        infeM = new Label();
        deadM = new Label();
        recoM = new Label();
        popuM.setTextFill(Color.GREY);
        infeM.setTextFill(Color.RED);
        deadM.setTextFill(Color.BLACK);
        recoM.setTextFill(Color.GREEN);
        GridPane.setHalignment(popM, HPos.LEFT);
        GridPane.setHalignment(infM, HPos.LEFT);
        GridPane.setHalignment(dedM, HPos.LEFT);
        GridPane.setHalignment(recM, HPos.LEFT);
        GridPane.setHalignment(popuM, HPos.RIGHT);
        GridPane.setHalignment(infeM, HPos.RIGHT);
        GridPane.setHalignment(deadM, HPos.RIGHT);
        GridPane.setHalignment(recoM, HPos.RIGHT);

        dispInf.add(popM, 0, 0);
        dispInf.add(infM, 0, 1);
        dispInf.add(dedM, 0, 2);
        dispInf.add(recM, 0, 3);

        dispInf.add(popuM, 1, 0);
        dispInf.add(infeM, 1, 1);
        dispInf.add(deadM, 1, 2);
        dispInf.add(recoM, 1, 3);
        mask.selectedProperty().addListener((arg, oldVal, newVal) ->
        {
            selected.setMasked(newVal);
            update();
        });
        shutdown = new CheckBox("Shutdown");
        shutdown.selectedProperty().addListener((arg, oldVal, newVal) -> selected.setShutDown(newVal));
        selectedG.add(mask, 0, 4);
        selectedG.add(shutdown, 1, 4);

        mainV.setPadding(new Insets(20));
        mainV.setSpacing(20);
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(stepBtn, playPause);
        mainV.getChildren().addAll(title, buttonBox, spd, new Separator(), dispInf, new Separator(), maskAll, new Separator(), selectedG);
        this.getChildren().add(mainV);
        update();
    }
    private void update()
    {
        if(selected != null)
        {
            selectedG.setVisible(true);
            population.setText(String.valueOf(selected.getPopulation()));
            infected.setText(String.valueOf(selected.getInfected()));
            dead.setText(String.valueOf(selected.getDeath()));
            recovered.setText(String.valueOf(selected.getRecovered()));
            mask.setSelected(selected.getMasked());
            shutdown.setSelected(selected.getShutDown());
        } else selectedG.setVisible(false);
        popuM.setText(String.valueOf(model.getPopulation()));
        infeM.setText(String.valueOf(model.getInfected()));
        deadM.setText(String.valueOf(model.getDeath()));
        recoM.setText(String.valueOf(model.getRecovered()));
        maskAll.setSelected(model.getMasked());
        view.draw();
    }

    @Override
    public void updateSelected(Node n)
    {
        this.selected = n;
        update();
    }
}
