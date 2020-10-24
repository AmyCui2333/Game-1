package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

import static Model.LocationType.NEIGHBOURHOOD;

public class NodeImplementation implements Node{
    private double xloc;
    private double yloc;
    private int population;
    private int infected;
    private ArrayList<Node> connected;
    private boolean masked;
    private int gathersize;
    private boolean shutdown;
    private LocationType loctype;

    public NodeImplementation(){
        int min = -1;
        int max = 1;
        xloc = Math.random() * (max - min) + min;
        yloc = Math.random() * (max - min) + min;
        Random rand = new Random();
        population = rand.nextInt(100);
        infected = rand.nextInt(2);
        connected = new ArrayList<>();
        masked = false;
        shutdown = false;
        loctype = NEIGHBOURHOOD;
    }

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
        shutdown = !shutdown;
    }

    @Override
    public void setMasked() {
        masked = !masked;
    }

    @Override
    public boolean getMasked() {
        return masked;
    }

    @Override
    public void setGatherSize(int i) {
        gathersize = i;
    }

    @Override
    public int getGatherSize() {
        return gathersize;
    }

    @Override
    public Point2D getloc() {
        return new Point2D(xloc, yloc);
    }

    @Override
    public void addConnected(Node node) {
        connected.add(node);
    }

    @Override
    public void removeConnected(Node node) {
        connected.remove(node);
    }

    @Override
    public ArrayList<Node> getConnected() {
        return connected;
    }

    @Override
    public void setPopulation(int pop) {
        population = pop;
    }

    @Override
    public void addPopulation(int pop) {
        population += pop;
    }

}
