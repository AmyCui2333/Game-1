package Model;

import java.util.ArrayList;

public interface Model {

    ArrayList<Node> getNodeinRange(double minx, double maxx, double miny, double maxy);

    double getPopulation();

    double getDeath();

    double getInfected();

    boolean getMasked();

    void setMasked(boolean bool);

    boolean getShutDown(Node node);

    void setShutDown(Node node, boolean bool);

    int getShutDownTotal(ArrayList<Node> nodeList);

    int getDay();

    void dayPass();

    void setGatherSize(Node node, int i);

    int getGatherSize(Node node);

    double getNodePopulation(Node node);

    GameState getState();

    double getRecovered();

    double getGDP();

    void setRecreation(boolean bool);

    boolean getRecreationShutdown();

    void setGroceries(boolean bool);

    boolean getGroceriesShutdown();

    void setHood();

    void openHood();

}
