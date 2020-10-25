package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public interface Node {

    LocationType getType();

    double getxloc();

    double getyloc();

    double getPopulation();

    double getInfected();

    boolean getShutDown();

    void setShutDown(boolean bool);

    void setMasked(boolean bool);

    boolean getMasked();

    void setGatherSize(int i);

    int getGatherSize();

    Point2D getloc();

    void addConnected(Node node);

    void removeConnected(Node node);

    ArrayList<Node> getConnected();

    void setPopulation(double pop);

    void addPopulation(double pop);

    double getSusceptible();

    void setSusceptible(double i);

    void setRecovered(double i);

    double getRecovered();

    void setInfected(double i);

    double getDeath();

    void setDeath(double i);

    double getbeta();

}
