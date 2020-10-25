package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

import static Model.LocationType.GROCERYSTORE;
import static Model.LocationType.RECREATION;

public class GroceriesImpl extends Node{




    public GroceriesImpl(){
        super();
        loctype = GROCERYSTORE;
        recovered = 0;
        susceptible = population;
        death = 0;
        transProb = 0.155;
        contactRate = 4;
    }

}
