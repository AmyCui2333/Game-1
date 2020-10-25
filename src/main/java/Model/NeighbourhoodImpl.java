package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

import static Model.LocationType.NEIGHBOURHOOD;

public class NeighbourhoodImpl extends Node{




    public NeighbourhoodImpl(){
        super();
        loctype = NEIGHBOURHOOD;
        recovered = 0;
        susceptible = population;
        death = 0;
        transProb = 0.06;
        contactRate = 3;
    }


}
