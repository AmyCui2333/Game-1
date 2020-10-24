package Model;

import javafx.geometry.Point2D;

public interface Node {

    LocationType getType();

    double getxloc();

    double getyloc();

    int getPopulation();

    int getInfected();

    boolean getShutDown();

    void setShutDown();

    void setMasked();

    boolean getMasked();

    void setGatherSize(int i);

    int getGatherSize();

    Point2D getloc();

}
