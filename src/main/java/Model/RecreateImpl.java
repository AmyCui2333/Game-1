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
        contactRate = 2;
    }

}
