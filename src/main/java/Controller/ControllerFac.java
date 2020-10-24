package Controller;

import Model.Model;
import View.View;

public class ControllerFac
{
    public static Controller getCont(Model m, View v)
    {
        return new ControllerImpl(m, v);
    }
}