package Model;

import java.util.ArrayList;

public interface Model {

    ArrayList<Node> getNodeinRange(double minx, double maxx, double miny, double maxy);

    int getPopulation(ArrayList<Node> nodeList);

    int getInfectedTotal(ArrayList<Node> nodeList);

    boolean getMasked();

    void setMasked();

    boolean getShutDown(Node node);

    void setShutDown(Node node);

    int getShutDownTotal(ArrayList<Node> nodeList);

    int getDay();

    void dayPass();

    void skipDay();

    void setGatherSize(Node node, int i);

    int getGatherSize(Node node);

}
