package View;

import Model.Model;

public class ViewFac
{
    public static View getView(Model m)
    {
        return new ViewImpl(m);
    }
}
