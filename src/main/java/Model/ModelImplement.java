package Model;

import java.util.ArrayList;

public class ModelImplement implements Model{
    ArrayList<Node> locNodes;
    int day = 0;

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
    public int getPopulation(ArrayList nodeList) {
        int sum = 0;
        for (Node node: locNodes){
            sum += node.getPopulation();
        }
        return sum;
    }

    @Override
    public int getInfectedTotal(ArrayList nodeList) {
        int sum = 0;
        for (Node node: locNodes){
            sum += node.getInfected();
        }
        return sum;
    }

    @Override
    public void setMasked(Node node) {
        node.setMasked();
    }

    @Override
    public boolean getMasked(Node node) {
        return node.getMasked();
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
    public int getShutDownTotal(ArrayList nodeList) {
        int shutdown = 0;
        for (Node node : locNodes) {
            if (node.getShutDown() == true) shutdown += 1;
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
}
