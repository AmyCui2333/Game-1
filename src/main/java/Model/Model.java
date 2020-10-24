package Model;

import java.util.ArrayList;

public interface Model {
    int day = 0;

    ArrayList<NodeImplementation> getNodeinRange(double minx, double maxx, double miny, double maxy);

    int getPopulation(ArrayList nodeList);

    int getInfectedTotal(ArrayList nodeList);

    int getInfectedLoc(NodeImplementation node);

    boolean getMasked(NodeImplementation node);

    void setMasked(NodeImplementation node);

    boolean getShutDown(NodeImplementation node);

    void setShutDown(NodeImplementation node);

    int getShutDownTotal(ArrayList nodeList);

    int getDay();

    void skipDay();

}
