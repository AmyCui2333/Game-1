package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

import static Model.LocationType.HOSPITAL;
import static Model.LocationType.NEIGHBOURHOOD;

public class HospitalImpl extends Node{




    public HospitalImpl(){
        super();
        loctype = HOSPITAL;
        recovered = 0;
        susceptible = population;
        death = 0;
        transProb = 0.155;
        contactRate = 6;
    }

}
