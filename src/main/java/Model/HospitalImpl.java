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
        contactRate = 6;
    }

}
