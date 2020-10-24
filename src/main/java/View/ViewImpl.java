package View;

import Model.Model;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ViewImpl extends  View
{
    private final Canvas canv;
    private final GraphicsContext gc;
    private double minX, maxX, minY, maxY;
    public ViewImpl(Model m)
    {
        canv = new Canvas();
        this.getChildren().add(canv);
        gc = canv.getGraphicsContext2D();
        canv.widthProperty().bind(this.widthProperty());
        canv.heightProperty().bind(this.heightProperty());
        maxX = maxY = 1;
        minX = minY = -1;
        this.setMinWidth(200);
        this.setMinHeight(200);
        this.widthProperty().addListener((arg, oldVal, newVal) ->
        {
            if(!oldVal.equals(newVal))
                draw();
        });
        this.heightProperty().addListener((arg, oldVal, newVal) ->
        {
            if(!oldVal.equals(newVal))
                draw();
        });
        canv.setVisible(true);
    }
    private double scrToViewX(double scrX)
    {
        return (scrX / (getWidth())) * (maxX - minX) + minX;
    }
    private double scrToViewY(double scrY)
    {
        return (scrY / getHeight()) * (minY - maxY) + minY;
    }
    private double viewToScrX(double vwX)
    {
        return (vwX - minX) / (maxX - minX) * getWidth();
    }
    private double viewToScrY(double vwY)
    {
        return (vwY - maxY) / (minY - maxY) * getHeight();
    }
    private Point2D scrToView(Point2D p)
    {
        return new Point2D(scrToViewX(p.getX()), scrToViewY(p.getY()));
    }
    private Point2D viewToScr(Point2D p)
    {
        return new Point2D(viewToScrX(p.getX()), viewToScrY(p.getY()));
    }
    private boolean inBounds(Point2D p)
    {
        return p.getX() >= minX && p.getX() <= maxX && p.getY() >= minY && p.getY() <= maxY;
    }
    private void drawLine(double x1, double y1, double x2, double y2)
    {
        drawLine(new Point2D(x1, y1), new Point2D(x2, y2));
    }
    private void drawLine(Point2D p1, Point2D p2)
    {
        Point2D psc1 = viewToScr(p1), psc2 = viewToScr(p2);
        System.out.println(getWidth());
        System.out.println(getHeight());
        System.out.println(psc1);
        System.out.println(psc2);
        gc.strokeLine(psc1.getX(), psc1.getY(), psc2.getX(), psc2.getY());
    }

    @Override
    public void draw()
    {
        gc.clearRect(0, 0, canv.getWidth(), canv.getHeight());
        System.out.println("drawing");
        gc.setStroke(Color.BLACK);
        Point2D p1 = new Point2D(-.5, -.5), p2 = new Point2D(-.5, .5), p3 = new Point2D(.5, .5), p4 = new Point2D(.5, -.5);
        drawLine(p1, p2);
        drawLine(p2, p3);
        drawLine(p3, p4);
        drawLine(p4, p1);
        p1 = new Point2D(-1, -1);
        p2 = new Point2D(-1, 1);
        p3 = new Point2D(1, 1);
        p4 = new Point2D(1, -1);
        drawLine(p1, p3);
        drawLine(p2, p4);
        //TODO GET MODEL SHIT HERE
    }
}
