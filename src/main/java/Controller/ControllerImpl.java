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
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.text.NumberFormat;


public class ControllerImpl extends Controller
{
    private final Image play, pause, step;
    private final Button playPause;
    private final Button stepBtn;
    private final Model model;
    private final Timeline gogogo;
    private double secondsPerDay;
    private View view;
    private GridPane selectedG;
    private final Label population, infected, dead, recovered, GDP, popuM, infeM, deadM, recoM, GDPM;
    private final CheckBox mask, shutdown, maskAll;
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
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
        Slider spd = new Slider(1, 10, 1);
        spd.valueProperty().addListener((arg, oldVal, newVal) -> gogogo.setRate(newVal.doubleValue()));
        maskAll = new CheckBox("Mask All");
        maskAll.selectedProperty().addListener((arg, oldVal, newVal) ->
        {
            model.setMasked(newVal);
            update();
        });
        var dispInf = new GridPane();
        dispInf.setVgap(4);
        dispInf.setHgap(20);
        selectedG = new GridPane();
        selectedG.setHgap(20);
        selectedG.setVgap(4);
        selectedG.setVisible(false);
        Label pop = new Label("Population:");
        Label inf = new Label("Infected");
        Label ded = new Label("Dead:");
        Label rec = new Label("Recovered:");
        Label gdp = new Label("GDP:");
        GDP = new Label();
        population = new Label();
        population.setTextFill(Color.GREY);
        infected = new Label();
        infected.setTextFill(Color.RED);
        dead = new Label();
        dead.setTextFill(Color.BLACK);
        recovered = new Label();
        recovered.setTextFill(Color.GREEN);
        GridPane.setHalignment(gdp, HPos.LEFT);
        GridPane.setHalignment(pop, HPos.LEFT);
        GridPane.setHalignment(inf, HPos.LEFT);
        GridPane.setHalignment(ded, HPos.LEFT);
        GridPane.setHalignment(rec, HPos.LEFT);
        GridPane.setHalignment(GDP, HPos.RIGHT);
        GridPane.setHalignment(population, HPos.RIGHT);
        GridPane.setHalignment(infected, HPos.RIGHT);
        GridPane.setHalignment(dead, HPos.RIGHT);
        GridPane.setHalignment(recovered, HPos.RIGHT);
        selectedG.add(gdp, 0, 0);
        selectedG.add(pop, 0, 1);
        selectedG.add(inf, 0, 2);
        selectedG.add(ded, 0, 3);
        selectedG.add(rec, 0, 4);
        selectedG.add(GDP, 1, 0);
        selectedG.add(population, 1, 1);
        selectedG.add(infected, 1, 2);
        selectedG.add(dead, 1, 3);
        selectedG.add(recovered, 1, 4);
        mask = new CheckBox("Masks");
        var gdpM = new Label("GDP:");
        var popM = new Label("Population:");
        var infM = new Label("Infected");
        var dedM = new Label("Dead:");
        var recM = new Label("Recovered:");
        popuM = new Label();
        infeM = new Label();
        deadM = new Label();
        recoM = new Label();
        GDPM  = new Label();
        popuM.setTextFill(Color.GREY);
        infeM.setTextFill(Color.RED);
        deadM.setTextFill(Color.BLACK);
        recoM.setTextFill(Color.GREEN);
        GridPane.setHalignment(gdpM, HPos.LEFT);
        GridPane.setHalignment(popM, HPos.LEFT);
        GridPane.setHalignment(infM, HPos.LEFT);
        GridPane.setHalignment(dedM, HPos.LEFT);
        GridPane.setHalignment(recM, HPos.LEFT);
        GridPane.setHalignment(GDPM, HPos.RIGHT);
        GridPane.setHalignment(popuM, HPos.RIGHT);
        GridPane.setHalignment(infeM, HPos.RIGHT);
        GridPane.setHalignment(deadM, HPos.RIGHT);
        GridPane.setHalignment(recoM, HPos.RIGHT);

        dispInf.add(gdpM, 0, 0);
        dispInf.add(popM, 0, 1);
        dispInf.add(infM, 0, 2);
        dispInf.add(dedM, 0, 3);
        dispInf.add(recM, 0, 4);

        dispInf.add(GDPM, 1, 0);
        dispInf.add(popuM, 1, 1);
        dispInf.add(infeM, 1, 2);
        dispInf.add(deadM, 1, 3);
        dispInf.add(recoM, 1, 4);
        mask.selectedProperty().addListener((arg, oldVal, newVal) ->
        {
            selected.setMasked(newVal);
            update();
        });
        shutdown = new CheckBox("Shutdown");
        shutdown.selectedProperty().addListener((arg, oldVal, newVal) -> selected.setShutDown(newVal));
        selectedG.add(mask, 0, 5);
        selectedG.add(shutdown, 1, 5);

        mainV.setPadding(new Insets(20));
        mainV.setSpacing(5);
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(stepBtn, playPause);
        Button recreation = new Button("Shut Recreation");
        Button grocery = new Button("Shut Grocery");
        Button shutNei = new Button("Shut 10 Neighbourhoods");
        Button openNei = new Button("Open 10 Neighbourhoods");
        recreation.setOnAction(e ->
        {
            model.setRecreation(!model.getRecreationShutdown());
            if(!model.getRecreationShutdown())
            {
                recreation.setText("Shut Recreation");
            } else
            {
                recreation.setText("Open Recreation");
            }
            update();
        });
        grocery.setOnAction(e ->
        {
            model.setGroceries(!model.getGroceriesShutdown());
            if(!model.getGroceriesShutdown())
            {
                grocery.setText("Shut Grocery");
            } else
            {
                grocery.setText("Open Grocery");
            }
            update();
        });
        shutNei.setOnAction(e ->
        {
            model.setHood();
            update();
        });
        openNei.setOnAction(e ->
        {
            model.openHood();
            update();
        });
        GridPane key = new GridPane();
        var gro = new Label("Grocery");
        var recr = new Label("Recreation");
        var hos = new Label("Hospital");
        var nei = new Label("Neighbour");
        GridPane.setHalignment(gro, HPos.LEFT);
        GridPane.setHalignment(recr, HPos.LEFT);
        GridPane.setHalignment(hos, HPos.LEFT);
        GridPane.setHalignment(nei, HPos.LEFT);
        Rectangle rGro = new Rectangle(20, 20, Color.PURPLE);
        Rectangle rRec = new Rectangle(20, 20, Color.BLUE);
        Rectangle rHos = new Rectangle(20, 20, Color.ORANGE);
        Rectangle rNei = new Rectangle(20, 20, Color.BLACK);
        GridPane.setHalignment(rGro, HPos.RIGHT);
        GridPane.setHalignment(rRec, HPos.RIGHT);
        GridPane.setHalignment(rHos, HPos.RIGHT);
        GridPane.setHalignment(rNei, HPos.RIGHT);
        key.add(gro, 0, 0);
        key.add(rGro, 1, 0);
        key.add(recr, 0, 1);
        key.add(rRec, 1, 1);
        key.add(hos, 0, 2);
        key.add(rHos, 1, 2);
        key.add(nei, 0, 3);
        key.add(rNei, 1, 3);
        key.setVgap(10);
        key.setHgap(10);
        mainV.getChildren().addAll(title, buttonBox, spd, new Separator(), dispInf, new Separator(), maskAll,
                recreation, grocery, shutNei, openNei, new Separator(), selectedG, new Separator(),  key);
        this.getChildren().add(mainV);
        update();
    }
    private void update()
    {
        if(selected != null)
        {
            selectedG.setVisible(true);
            population.setText(String.valueOf((int) selected.getPopulation()));
            infected.setText(String.valueOf((int) selected.getInfected()));
            dead.setText(String.valueOf((int) selected.getDeath()));
            recovered.setText(String.valueOf((int) selected.getRecovered()));
            mask.setSelected(selected.getMasked());
            shutdown.setSelected(selected.getShutDown());
            GDP.setText(String.valueOf((int) selected.getRevenue()));
        } else selectedG.setVisible(false);
        popuM.setText(String.valueOf((int) model.getPopulation()));
        infeM.setText(String.valueOf((int) model.getInfected()));
        deadM.setText(String.valueOf((int) model.getDeath()));
        recoM.setText(String.valueOf((int) model.getRecovered()));
        GDPM.setText(String.valueOf((int) model.getGDP()));
        maskAll.setSelected(model.getMasked());
        view.draw();
    }

    @Override
    public void stop()
    {
        gogogo.stop();

    }

    @Override
    public void updateSelected(Node n)
    {
        this.selected = n;
        update();
    }
}
