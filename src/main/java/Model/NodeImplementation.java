package Model;

import java.util.ArrayList;

public class NodeImplementation implements Node{
    private double xloc;
    private double yloc;
    private int population;
    private int infected;
    private ArrayList<NodeImplementation> connected;
    private boolean masked;
    private boolean shutdown;
    private LocationType loctype;



    @Override
    public LocationType getType() {
        return loctype;
    }

    @Override
    public double getxloc() {
        return xloc;
    }

    @Override
    public double getyloc() {
        return yloc;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public int getInfected() {
        return infected;
    }

    @Override
    public boolean getShutDown() {
        return shutdown;
    }

    @Override
    public void setShutDown() {
        if (shutdown == true) shutdown = false;
        else shutdown = true;
    }

    @Override
    public void setMasked() {
        if (masked == true) masked = false;
        else masked = true;
    }

    @Override
    public boolean getMasked() {
        return masked;
    }

}
