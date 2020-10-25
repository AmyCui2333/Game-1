package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

import static Model.LocationType.HOSPITAL;
import static Model.LocationType.RECREATION;

public class RecreateImpl extends Node{



    public RecreateImpl(){
        super();
        loctype = RECREATION;
        recovered = 0;
        susceptible = population;
        death = 0;
        transProb = 0.155;
        contactRate = 2;
    }

}
