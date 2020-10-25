package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

import static Model.LocationType.NEIGHBOURHOOD;

public class NeighbourhoodImpl extends Node{




    public NeighbourhoodImpl(){
        super();
        loctype = NEIGHBOURHOOD;
        contactRate = 3;
    }


}
