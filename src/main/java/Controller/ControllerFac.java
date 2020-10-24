package Controller;

import Model.Model;

public class ControllerFac
{
    public static Controller getCont(Model m)
    {
        return new ControllerImpl(m);
    }
}
