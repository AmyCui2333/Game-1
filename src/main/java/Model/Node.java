package Model;

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


}
