package Model;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

public class ModelImplement implements Model{
    ArrayList<Node> locNodes;
    int day;
    double death;
    double population;
    double infected;
    double recovered;
    double gamma;
    double deathRate=0.034;

    ModelImplement(){
        generateNodes();
        connectNodes();
        day = 0;
        gamma = 0.08;
        update();
    }

    @Override
    public void dayPass() {
        ++day;
        spreadWithin();
//        travel();
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
    public double getPopulation() {
        return population;
    }

    public void update() {
        double pop = 0;
        double death = 0;
        double infect = 0;
        double recover = 0;
        for (Node node: locNodes){
            pop += node.getPopulation();
            death += node.getDeath();
            infect += node.getInfected();
            recover += node.getRecovered();
        }
        this.population = pop;
        this.infected = infect;
        this.death = death;
        this.recovered = recover;

    }


    @Override
    public double getInfected() {
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
        for (Node n:locNodes){
            if (!n.getMasked()) return false;
        }
        return true;
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
    public double getNodePopulation(Node node) {
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
    public double getRecovered() {
        return recovered;
    }

    @Override
    public double getDeath() {
        return death;
    }

    @Override
    public void setGatherSize(Node node, int i) {
        node.setGatherSize(i);
    }

    public void generateNodes(){
        ArrayList<Node> Nodes = new ArrayList<>();
        for(int i=1;i<100;i++){
            Node node = new NeighbourhoodImpl();
            Nodes.add(node);
        }
        locNodes = Nodes;
    }

    public void connectNodes(){
        for(Node node: locNodes){
            Random random = new Random();
            ArrayList<Integer> randList = randomList(10);
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

//    public void travel(){
//        ArrayList<Integer> randList = randomList(3);
//        for(int rand:randList){
//            Node start = locNodes.get(rand);
//            double popStart = start.getPopulation();
//            ArrayList<Node> connected = start.getConnected();
//            Random flow = new Random();
//            double nextNode = flow.nextDouble(connected.size());
//            double flowsize = flow.nextDouble(popStart);
//            start.setPopulation(popStart-flowsize);
//            Node next = locNodes.get(nextNode);
//            next.addPopulation(flowsize);
//            System.out.println(flowsize + " moved from" + start + " to " + next);
//        }
//    }


    public void spreadWithin(){
        if(day>0) {
            for (Node n : locNodes) {
                if (n.getInfected() != 0) {
                    double infectStart = n.getInfected();
                    double susStart = n.getSusceptible();
                    double pop = n.getPopulation();
                    double death = n.getDeath();
                    double spread = (n.getbeta()*susStart*infectStart)/pop-gamma*infectStart;
                    n.setInfected(infectStart+spread);
                    double newsus = susStart-(n.getbeta()*susStart*infectStart)/pop;
                    n.setSusceptible(newsus);
                    if (day>=14) {
                        double recoverStart = n.getRecovered();
                        double newRecover = gamma * infectStart * (1.0 - deathRate);
                        n.setRecovered(recoverStart + newRecover);
                        double newDeath = gamma * infectStart * deathRate;
                        n.setDeath(death + newDeath);
                        n.setPopulation(pop - newDeath);
                    }
                }
            }
        }
    }


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
