package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

import static Model.LocationType.RECREATION;

public class GroceriesImpl implements Node{
    private double xloc;
    private double yloc;
    private double population;
    private double infected;
    private double susceptible;
    private double recovered;
    private double death;
    private ArrayList<Node> connected;
    private boolean masked;
    private int gathersize;
    private boolean shutdown;
    private LocationType loctype;
    private double transProb;
    private double contactRate;
    private double revenue;



    public GroceriesImpl(){
        double min = -1.0;
        double max = 1.0;
        xloc = Math.random() * (max - min) + min;
        yloc = Math.random() * (max - min) + min;
        Random rand = new Random();
        population = rand.nextInt(100000);
        infected = rand.nextInt(10);
        connected = new ArrayList<>();
        masked = false;
        shutdown = false;
        loctype = RECREATION;
        recovered = 0;
        susceptible = population;
        death = 0;
        transProb = 0.155;
        contactRate = 4;
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
    public double getPopulation() {
        return population;
    }

    @Override
    public double getInfected() {
        return infected;
    }

    @Override
    public boolean getShutDown() {
        return shutdown;
    }

    @Override
    public void setShutDown(boolean bool) {
        shutdown = bool;
    }

    @Override
    public void setMasked(boolean bool) {
        masked = bool;
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
    public void setPopulation(double pop) {
        population = pop;
    }

    @Override
    public void addPopulation(double pop) {
        population += pop;
    }

    @Override
    public double getSusceptible() {
        return susceptible;
    }

    @Override
    public void setSusceptible(double i) {
        susceptible = i;
    }

    @Override
    public void setRecovered(double i) {
        recovered = i;
    }

    @Override
    public double getRecovered() {
        return recovered;
    }

    @Override
    public void setInfected(double i) {
        infected = i;
    }

    @Override
    public double getDeath() {
        return death;
    }

    @Override
    public void setDeath(double i) {
        death = i;
    }

    @Override
    public double getbeta() {
        return transProb*contactRate;
    }
}
