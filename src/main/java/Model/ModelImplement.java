package Model;

import java.util.ArrayList;
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
        ArrayList<Node> result=new ArrayList<Node>();
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
            Random rand = new Random();
            ArrayList<Integer> randList = new ArrayList<>();
            for (int i=1;i<4;i++){
                int rand1 = rand.nextInt(locNodes.size());
                if (node != locNodes.get(rand1) && !node.getConnected().contains(locNodes.get(rand1))) {
                    node.addConnected(locNodes.get(rand1));
                    locNodes.get(rand1).addConnected(node);
                }
            }
        }
    }
}
