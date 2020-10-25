package Model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public abstract class Node {

    protected double xloc;
    protected double yloc;
    protected double population;
    protected double infected;
    protected double susceptible;
    protected double recovered;
    protected double death;
    protected ArrayList<Node> connected;
    protected boolean masked;
    protected int gathersize;
    protected boolean shutdown;
    protected LocationType loctype;
    protected double transProb;
    protected double contactRate;
    protected double revenue;

    public Node()
    {
        double min = -1.0;
        double max = 1.0;
        xloc = Math.random() * (max - min) + min;
        yloc = Math.random() * (max - min) + min;
        Random rand = new Random();
        population = rand.nextInt(10000);
        infected = rand.nextInt(30);
        connected = new ArrayList<>();
        masked = false;
        shutdown = false;
        recovered = 0;
        susceptible = population-infected;
        death = 0;
        transProb = 0.155;
        revenue = 76*susceptible;
    }

    public LocationType getType()
    {
        return this.loctype;
    }

    public double getxloc()
    {
        return this.xloc;
    }

    public double getyloc()
    {
        return this.yloc;
    }

    public double getPopulation()
    {
        return this.population;
    }

    public double getInfected()
    {
        return this.infected;
    }

    public boolean getShutDown()
    {
        return this.shutdown;
    }

    public void setShutDown(boolean bool)
    {
        this.shutdown = bool;
    }

    public void setMasked(boolean bool)
    {
        this.masked = bool;
    }

    public boolean getMasked()
    {
        return this.masked;
    }

    public void setGatherSize(int i)
    {
        this.gathersize = i;
    }

    public int getGatherSize()
    {
        return this.gathersize;
    }

    public Point2D getloc()
    {
        return new Point2D(xloc, yloc);
    }

    public void addConnected(Node node)
    {
        this.connected.add(node);
    }

    public void removeConnected(Node node)
    {
        this.connected.remove(node);
    }

    public ArrayList<Node> getConnected()
    {
        return this.connected;
    }


    public double getSusceptible()
    {
        return this.susceptible;
    }

    public void addSusceptible(double i)
    {
        this.susceptible += i;
        this.population += i;
    }

    public void addRecovered(double i)
    {
        this.recovered += i;
        this.population += i;
    }

    public double getRecovered()
    {
        return this.recovered;
    }

    public void addInfected(double i)
    {
        this.infected += i;
        this.population += i;
    }

    public double getDeath()
    {
        return this.death;
    }

    public void addDeath(double i)
    {
        this.death += i;
        this.population -= i;
    }

    public double getbeta()
    {
        return gettransProb() * contactRate;
    }

    private double gettransProb(){
        double tmp = transProb;
        if (masked) tmp *= .8;
        if (shutdown) tmp *= .27;
        return tmp;
    }

    public double getRevenue(){
        double tmp = 76*(this.susceptible+this.recovered);
        if (shutdown) tmp *= .6;
        return tmp;
    }

}
