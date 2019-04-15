import java.awt.*;

public class CircleFill extends Canvas {
    protected double diameter = 1.0;
    protected Color color = new Color(255, 255, 255);
    protected Color backColor = new Color(255, 255, 255);


    protected int x = 0;
    protected int y = 0;

    public CircleFill() {
    }

    public CircleFill(double diameter) {
        this.diameter = diameter;
    }

    public CircleFill(Color color) {
        this.color = color;
    }
   

    public CircleFill(double diameter, Color color, Color backColor) {
        this.diameter = diameter;
        this.color = color;
        this.backColor=backColor;
    }

    public void setCenter(int x, int y) {
        this.x = x;
        this.y = y;
        repaint();
    }

    public Point getCenter() {
        return new Point(x, y);
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
        repaint();
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }
    public void setBackColor(Color backColor) {
        this.backColor = backColor;
        repaint();
    }

    public double getDiameter() {
        return diameter;
    }

    public Color getColor() {
        return color;
    }
    public Color getBackColor() {
        return backColor;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(backColor);
        g.fillOval(x, y, (int) (diameter / 2), (int) (diameter / 2));

        g.setColor(color);
        double x1= (2*(Math.sqrt(2)*diameter/4))-diameter/8;
        double y1= (2*(Math.sqrt(2)*diameter/4))-diameter/8;
        g.drawOval(x, y, (int) (diameter / 2), (int) (diameter / 2));
        g.fillRect((int)((-Math.sqrt(2)/4*diameter)+(x+(x1*27/32))) ,(int)((Math.sqrt(2)/4*diameter)+(y-y1+125)),(int) x1-100,(int)y1/6);
    //(int)(-(squrt(2))/2*(int) (diameter / 2))+x)

    }
}
