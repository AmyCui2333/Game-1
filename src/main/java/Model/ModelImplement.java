package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class ModelImplement implements Model{
    ArrayList<Node> locNodes;
    int day;

    ModelImplement(){
        generateNodes();
        connectNodes();
        day = 0;
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
    public int getPopulation(ArrayList<Node> nodeList) {
        int sum = 0;
        for (Node node: locNodes){
            sum += node.getPopulation();
        }
        return sum;
    }

    @Override
    public int getInfectedTotal(ArrayList<Node> nodeList) {
        int sum = 0;
        for (Node node: locNodes){
            sum += node.getInfected();
        }
        return sum;
    }

    @Override
    public void setMasked() {
        for(Node node:locNodes){
            node.setMasked();
        }
    }

    @Override
    public boolean getMasked() {
        return locNodes.get(0).getMasked();
    }

    @Override
    public void setShutDown(Node node) {
        node.setShutDown();
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
    public void dayPass() {
        day += 1;
        travel();
    }

    @Override
    public void skipDay() {

    }

    @Override
    public int getGatherSize(Node node) {
        return node.getGatherSize();
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
            ArrayList<Integer> randList = new ArrayList<>();
            for (int i=1;i<5;i++){
                int randint = random.nextInt(locNodes.size());
                randList.add(randint);
            }
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
        Random random = new Random();
        ArrayList<Integer> randList = new ArrayList<>();
        for (int i=1;i<3;i++){
            int randint = random.nextInt(locNodes.size());
            randList.add(randint);
        }
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
            System.out.print(flowsize + " moved from" + start + " to " + next);
        }
    }
}
