package Model;

import java.util.ArrayList;

public interface Model {

    ArrayList<Node> getNodeinRange(double minx, double maxx, double miny, double maxy);

    int getPopulation(ArrayList nodeList);

    int getInfectedTotal(ArrayList nodeList);

    boolean getMasked(Node node);

    void setMasked(Node node);

    boolean getShutDown(Node node);

    void setShutDown(Node node);

    int getShutDownTotal(ArrayList nodeList);

    int getDay();

    void dayPass();

    void skipDay();

}
