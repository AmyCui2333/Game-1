package Model;

public class ModelFac {
    public static Model getModel(){

        return new ModelImplement();
    }
}
