package View;

import Controller.Controller;
import Model.Model;
import Model.Node;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ViewImpl extends  View
{
    private final Canvas canv;
    private final GraphicsContext gc;
    private double minX, maxX, minY, maxY;
    private ArrayList<Node> nodesOnScreen;
    private final Model model;
    private final double nodeSize = .05;
    Controller controller;
    boolean started = false;
    Image welcome, ecoloss, deathloss, win;
    public ViewImpl(Model m)
    {
        this.model = m;
        welcome = new Image(getClass().getResourceAsStream("welcome.png"));
        ecoloss = new Image(getClass().getResourceAsStream("ecoloss.png"));
        deathloss = new Image(getClass().getResourceAsStream("deathloss.png"));
        win = new Image(getClass().getResourceAsStream("win.png"));
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
        this.addEventHandler(ScrollEvent.ANY, e ->
        {
            if(e.isControlDown())
            {
                double width = maxX - minX;
                double height = maxY - minY;
                double avg = (getWidth() + getHeight()) / 2;
                double widthDiff = width * (1 - e.getDeltaY() / avg) - width;
                double heightDiff = height * (1 - e.getDeltaY() / avg) - height;
                maxX += widthDiff / 2;
                minX -= widthDiff / 2;
                maxY += heightDiff / 2;
                minY -= heightDiff / 2;
            } else
            {
                double dx;
                double dy;
                if(e.isShiftDown())
                {
                    dy = (e.getDeltaX() / getWidth()) * (maxX - minX);
                    dx = (e.getDeltaY() / getHeight()) * (minY - maxY);
                } else
                {
                    dx = (e.getDeltaX() / getWidth()) * (maxX - minX);
                    dy = (e.getDeltaY() / getHeight()) * (minY - maxY);
                }
                minX += dx;
                maxX += dx;

                minY += dy;
                maxY += dy;
            }
            draw();
        });
        this.addEventHandler(ZoomEvent.ANY, e ->
        {
            if(started)
            {
                double width = maxX - minX;
                double height = maxY - minY;
                double widthDiff = width * (1 + 10 * e.getZoomFactor());
                double heightDiff = height * (1 + 10 * e.getZoomFactor());
                maxX += widthDiff / 2;
                minX -= widthDiff / 2;
                maxY += heightDiff / 2;
                minY -= heightDiff / 2;
                draw();
            }
        });
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
        {
            if(!started)
            {
                started = true;
                draw();
            } else
            {
                var viewPt = scrToView(e.getX(), e.getY()).add(-nodeSize / 2, nodeSize / 2);
                Node selected = null;
                for (Node n : nodesOnScreen)
                {
                    if (viewPt.distance(n.getloc()) < nodeSize)
                    {
                        selected = n;
                        break;
                    }
                }
                controller.updateSelected(selected);
            }
        });
        canv.setVisible(true);
    }
    @Override
    public void setController(Controller c)
    {
        this.controller = c;
    }
    private double scrToViewX(double scrX)
    {
        return (scrX / (getWidth())) * (maxX - minX) + minX;
    }
    private double scrToViewY(double scrY)
    {
        return (scrY / getHeight()) * (minY - maxY) + maxY;
    }
    private double viewToScrX(double vwX)
    {
        return (vwX - minX) / (maxX - minX) * getWidth();
    }
    private double viewToScrY(double vwY)
    {
        return (vwY - maxY) / (minY - maxY) * getHeight();
    }
    private Point2D scrToView(double x, double y)
    {
        return new Point2D(scrToViewX(x), scrToViewY(y));
    }
    private Point2D scrToView(Point2D p)
    {
        return scrToView(p.getX(), p.getY());
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
        gc.strokeLine(psc1.getX(), psc1.getY(), psc2.getX(), psc2.getY());
    }

    @Override
    public synchronized void draw()
    {
        if(started)
        {
            switch (model.getState())
            {
                case WIN:
                    gc.drawImage(win, 0, 0, canv.getWidth(), canv.getHeight());
                    controller.stop();
                    break;
                case ECOLOSS:
                    gc.drawImage(ecoloss, 0, 0, canv.getWidth(), canv.getHeight());
                    controller.stop();
                    break;
                case DEATHLOSS:
                    gc.drawImage(deathloss, 0, 0, canv.getWidth(), canv.getHeight());
                    controller.stop();
                    break;
                case PLAYING:
                default:
                gc.clearRect(0, 0, canv.getWidth(), canv.getHeight());


                gc.setStroke(Color.BLACK);
                gc.strokeLine(0, 0, canv.getWidth(), 0);
                gc.strokeLine(canv.getWidth(), 0, canv.getWidth(), canv.getHeight());
                gc.strokeLine(canv.getWidth(), canv.getHeight(), 0, canv.getHeight());
                gc.strokeLine(0, canv.getHeight(), 0, 0);
                gc.setFont(new Font(12));
                double w = nodeSize * getWidth() / (maxX - minX);
                double h = nodeSize * getHeight() / (maxY - minY);
                nodesOnScreen = model.getNodeinRange(minX - w * 3, maxX + w * 3, minY - h * 3, maxY + h * 3);
                gc.setStroke(Color.BLUEVIOLET);
                gc.setFill(Color.BLUEVIOLET);
                Color c;
                for (Node n : nodesOnScreen)
                {
                    switch (n.getType())
                    {
                        case HOSPITAL:
                        c = Color.ORANGE;
                        break;
                    case RECREATION:
                        c = Color.BLUE;
                        break;
                    case GROCERYSTORE:
                        c = Color.PURPLE;
                        break;
                    case NEIGHBOURHOOD:
                    default:
                        c = Color.BLACK;
                    }
                    var pt = viewToScr(n.getloc());

                    gc.setFont(new Font(h / 3));
                    double sz = gc.getFont().getSize() / 2;
                    if (controller != null && n == controller.selected)
                    {
                        gc.setFill(Color.RED);
                        gc.fillOval(pt.getX() - w / 2 - 3, pt.getY() + h / 2 - 3, w + 6, h + 6);
                    }
                    gc.setFill(c);
                    gc.setStroke(c);
                    gc.fillOval(pt.getX() - w / 2, pt.getY() + h / 2, w, h);
                    gc.setStroke(Color.BLACK);
                    gc.setFill(Color.WHITE);
                    String popStr = String.valueOf((int) n.getPopulation());
                    Text tmp = new Text();
                    tmp.setFont(gc.getFont());
                    tmp.setText(popStr);
                    double popWidth = tmp.getLayoutBounds().getWidth();
                    gc.fillText(popStr, pt.getX() - popWidth / 2, pt.getY() + 3 * h / 4 + sz);
                    gc.setFill(Color.RED);
                    String infStr = String.valueOf((int) n.getInfected());
                    tmp.setText(infStr);
                    double infWidth = tmp.getLayoutBounds().getWidth();
                    gc.fillText(infStr, pt.getX() - infWidth / 2, pt.getY() + h + sz);
                    gc.setFill(Color.GREEN);
                    String recStr = String.valueOf((int) n.getRecovered());
                    tmp.setText(recStr);
                    double recWidth = tmp.getLayoutBounds().getWidth();
                    gc.fillText(recStr, pt.getX() - recWidth / 2, pt.getY() + 5 * h / 4 + sz);

                }
                gc.setFont(new Font(30));
                gc.setStroke(Color.BLACK);
                gc.setFill(Color.WHITE);
                gc.fillText("Day: " + model.getDay(), 5, 30);
                gc.strokeText("Day: " + model.getDay(), 5, 30);
            }
        } else
        {
            gc.drawImage(welcome, 0, 0, canv.getWidth(), canv.getHeight());
        }
    }
}
