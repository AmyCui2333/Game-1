package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static java.lang.Math.min;

public class ModelImplement implements Model{
    ArrayList<Node> locNodes;
    int day;
    int death;
    int population;
    int infected;
    double recoveryRate;

    ModelImplement(){
        generateNodes();
        connectNodes();
        day = 0;
        update();
    }

    @Override
    public void dayPass() {
        day += 1;
        spreadWithin();
        recover();
        travel();
        update();
    }

    @Override
    public ArrayList<Node> getNodeinRange(double minx, double maxx, double miny, double maxy) {
        ArrayList<Node> result= new ArrayList<>();
        for (Node node: locNodes){
            if(minx<=node.getxloc()&&node.getxloc()<=maxx&&miny<=node.getyloc()&&node.getyloc()<=maxy){
                result.add(node);
            }
        }
        return result;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    public void update() {
        int pop = 0;
        int death = 0;
        int infect = 0;
        for (Node node: locNodes){
            pop += node.getPopulation();
            death += node.getDeath();
            infect += node.getInfected();
        }
        this.population = pop;
        this.death = death;
        this.infected = infect;

    }


    @Override
    public int getInfected() {
        return infected;
    }

    @Override
    public void setMasked(boolean bool) {
        for(Node node:locNodes){
            node.setMasked(bool);
        }
    }

    @Override
    public boolean getMasked() {
        return locNodes.get(0).getMasked();
    }

    @Override
    public void setShutDown(Node node, boolean bool) {
        node.setShutDown(bool);
    }

    @Override
    public boolean getShutDown(Node node) {
        return node.getShutDown();
    }

    @Override
    public int getShutDownTotal(ArrayList<Node> nodeList) {
        int shutdown = 0;
        for (Node node : locNodes) {
            if (node.getShutDown()) shutdown += 1;
        }
        return shutdown;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public void skipDay() {

    }

    @Override
    public int getGatherSize(Node node) {
        return node.getGatherSize();
    }

    @Override
    public int getNodePopulation(Node node) {
        return node.getPopulation();
    }

    @Override
    public boolean getState() {
        if(population/death<2){
            return false;
        }
        return true;
    }

    @Override
    public int getDeath() {
        return death;
    }

    @Override
    public void setGatherSize(Node node, int i) {
        node.setGatherSize(i);
    }

    public void generateNodes(){
        ArrayList<Node> Nodes = new ArrayList<>();
        for(int i=1;i<10;i++){
            Node node = new NodeImplementation();
            Nodes.add(node);
        }
        locNodes = Nodes;
    }

    public void connectNodes(){
        for(Node node: locNodes){
            Random random = new Random();
            ArrayList<Integer> randList = randomList(5);
            for (int rand: randList) {
                if (node != locNodes.get(rand) && !node.getConnected().contains(locNodes.get(rand))) {
                    node.addConnected(locNodes.get(rand));
                    locNodes.get(rand).addConnected(node);
                    String msg = "connected between "+ rand + " and "+ locNodes.indexOf(node);
                    System.out.println(msg);
                }
            }
        }
    }

    public void travel(){
        ArrayList<Integer> randList = randomList(3);
        for(int rand:randList){
            Node start = locNodes.get(rand);
            int popStart = start.getPopulation();
            ArrayList<Node> connected = start.getConnected();
            Random flow = new Random();
            int nextNode = flow.nextInt(connected.size());
            int flowsize = flow.nextInt(popStart);
            start.setPopulation(popStart-flowsize);
            Node next = locNodes.get(nextNode);
            next.addPopulation(flowsize);
            System.out.println(flowsize + " moved from" + start + " to " + next);
        }
    }
    //TODO: Model the travelling of infected&recovered individuals(mainly infected)

    public void spreadWithin(){
        if(day>0) {
            for (Node n : locNodes) {
                if (n.getInfected() != 0) {
                    int infectStart = n.getInfected();
                    int spread = min(n.getPopulation(),(int) (infectStart * 3.0));
                    n.setInfected(spread);
                }
            }
        }
    }
    //TODO: Set a rate of spread to update each node

    public void recover(){
        if(day>=14) {
            for (Node n : locNodes) {
                int infectStart = n.getInfected();
            }
        }
    }
    //TODO: Set a rate of recovery for each node after the 14th day

    public ArrayList<Integer> randomList(int max){
        Random random = new Random();
        ArrayList<Integer> randList = new ArrayList<>();
        for (int i=1;i<max;i++){
            int randint = random.nextInt(locNodes.size());
            randList.add(randint);
        }
        return randList;
    }
}
