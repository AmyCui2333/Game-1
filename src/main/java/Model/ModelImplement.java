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
    double gdp;

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
        if((int) getInfected() > 0)spreadWithin();
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
    public double getPopulation() {
        return population;
    }

    public void update() {
        double pop = 0;
        double death = 0;
        double infect = 0;
        double recover = 0;
        double revenue = 0;
        for (Node node: locNodes){
            pop += node.getPopulation();
            death += node.getDeath();
            infect += node.getInfected();
            recover += node.getRecovered();
            revenue += node.getRevenue();
        }
        this.population = pop;
        this.infected = infect;
        this.death = death;
        this.recovered = recover;
        this.gdp = revenue;
        System.out.println(revenue);
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
    public int getGatherSize(Node node) {
        return node.getGatherSize();
    }

    @Override
    public double getNodePopulation(Node node) {
        return node.getPopulation();
    }

    @Override
    public GameState getState() {
        if(death > .2 * population){
            return GameState.DEATHLOSS;
        } else if (false)
        {
            return GameState.ECOLOSS;
        } else if ((int) getInfected() == 0 || getDay() > 200)
        {
            return GameState.WIN;
        } else
        {
            return GameState.PLAYING;
        }
    }

    @Override
    public double getRecovered() {
        return recovered;
    }

    @Override
    public double getGDP() {
        return gdp;
    }

    @Override
    public void setRecreation(boolean bool) {
        for (Node n : locNodes){
            if (n.loctype==LocationType.RECREATION)
            {
                n.setShutDown(bool);
            }
        }
    }

    @Override
    public boolean getRecreationShutdown() {
        for (Node n : locNodes){
            if (n.loctype==LocationType.RECREATION&&!n.getShutDown())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setGroceries(boolean bool) {
        for (Node n : locNodes){
            if (n.loctype==LocationType.GROCERYSTORE)
            {
                n.setShutDown(bool);
            }
        }
    }

    @Override
    public boolean getGroceriesShutdown(boolean bool) {
        for (Node n : locNodes){
            if (n.loctype==LocationType.GROCERYSTORE&&!n.getShutDown())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setHood() {
        int i = 0;
        for (Node n : locNodes){
            if (n.loctype==LocationType.NEIGHBOURHOOD&&!n.getShutDown()&&i<10)
            {
                n.setShutDown(true);
            }
        }
    }

    @Override
    public void openHood() {
        int i = 0;
        for (Node n : locNodes){
            if (n.loctype==LocationType.NEIGHBOURHOOD&&n.getShutDown()&&i<10)
            {
                n.setShutDown(false);
            }
        }

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
        for(int i=1;i<81;i++){
            Node node = new NeighbourhoodImpl();
            Nodes.add(node);
        }
        for(int i=1;i<11;i++){
            Node node = new GroceriesImpl();
            Nodes.add(node);
        }
        locNodes = Nodes;
        for(int i=1;i<6;i++){
            Node node = new RecreateImpl();
            Nodes.add(node);
        }
        for(int i=1;i<6;i++){
            Node node = new HospitalImpl();
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

    public void travel(){
        var open = new ArrayList<Node>(locNodes.size() / 2);
        for(Node n : locNodes)
            if(!n.shutdown)
                open.add(n);

        ArrayList<Integer> randList = randomList(30, open.size());
        for(int rand:randList){
            Node start = locNodes.get(rand);
            var connected = new ArrayList<Node>();
            for(Node n : start.getConnected())
                if(!n.shutdown)
                    connected.add(n);
            Random flow = new Random();
            int nextNode = flow.nextInt(connected.size());
            double flowSus, flowRec, flowInf;
            try
            {
                flowSus = Math.max(flow.nextInt((int) start.getSusceptible() / 5), 0);
            } catch (IllegalArgumentException i)
            {
                flowSus = 0;
            }
            try
            {
                flowRec = Math.max(flow.nextInt((int) start.getRecovered() / 4), 0);
            } catch (IllegalArgumentException i)
            {
                flowRec = 0;
            }
            try
            {
                flowInf = Math.max(flow.nextInt((int) start.getInfected() / 10), 0);
            } catch (IllegalArgumentException i)
            {
                flowInf = 0;
            }
            start.addInfected(-flowInf);
            start.addRecovered(-flowRec);
            start.addSusceptible(-flowSus);
            Node next = locNodes.get(nextNode);
            next.addInfected(flowInf);
            next.addRecovered(flowRec);
            next.addSusceptible(flowSus);
        }
    }


    public void spreadWithin(){
        if(day>0) {
            for (Node n : locNodes) {
                if (n.getInfected() != 0) {
                    double infectStart = n.getInfected();
                    double susStart = n.getSusceptible();
                    double pop = n.getPopulation();
                    double death = n.getDeath();
                    double spread = (n.getbeta()*susStart*infectStart)/pop - gamma*infectStart;
                    n.infected = (infectStart+spread);
                    n.susceptible = (susStart-(n.getbeta()*susStart*infectStart)/pop);
                    {
                        double recoverStart = n.getRecovered();
                        double newRecover = gamma * infectStart * (1.0 - deathRate);
                        n.recovered = (recoverStart + newRecover);
                        double newDeath = gamma * infectStart * deathRate;
                        n.death = (death + newDeath);
                        n.population = (pop - newDeath);
                    }
                    if(n.infected + n.susceptible + n.recovered  - 1 > n.population)
                        throw new Error();
                }
            }
        }
    }

    public ArrayList<Integer> randomList(int max) {return randomList(max, locNodes.size());}
    public ArrayList<Integer> randomList(int max, int size){
        Random random = new Random();
        ArrayList<Integer> randList = new ArrayList<>();
        for (int i=1;i<max;i++){
            int randint = random.nextInt(locNodes.size());
            randList.add(randint);
        }
        return randList;
    }
}
