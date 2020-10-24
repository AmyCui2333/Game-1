package Model;

import java.util.ArrayList;

public interface Model {

    ArrayList<Node> getNodeinRange(double minx, double maxx, double miny, double maxy);

    int getPopulation();

    int getDeath();

    int getInfected();

    boolean getMasked();

    void setMasked(boolean bool);

    boolean getShutDown(Node node);

    void setShutDown(Node node);

    int getShutDownTotal(ArrayList<Node> nodeList);

    int getDay();

    void dayPass();

    void skipDay();

    void setGatherSize(Node node, int i);

    int getGatherSize(Node node);

    int getNodePopulation(Node node);

    boolean getState();

}
