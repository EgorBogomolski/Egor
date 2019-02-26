
import java.applet.Applet;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class UP_1 extends Applet implements Runnable {
    final int X=580,Y=580;
    Color clrSector, clrBorderRectangle, clrBorderCircle;
    float depth;
    private Thread t = null;
    private AffineTransform circle, rectangle;
    private Image offScreenImage;
    private Graphics offScreenGraphics;
    private int k = 0;
    Area circleArea, rectangleArea;

    public void init() {
        setSize(X,Y);
        setBackground(new Color(5, 182, 193));
        offScreenImage = createImage(X,Y);
        offScreenGraphics = offScreenImage.getGraphics();
        offScreenGraphics.setColor(Color.BLACK);
        int r, g, b;
        r = Integer.parseInt(this.getParameter("red_param_sector"));
        g = Integer.parseInt(this.getParameter("green_param_sector"));
        b = Integer.parseInt(this.getParameter("blue_param_sector"));
        this.clrSector = new Color(r, g, b);
        r = Integer.parseInt(this.getParameter("red_param_border"));
        g = Integer.parseInt(this.getParameter("green_param_border"));
        b = Integer.parseInt(this.getParameter("blue_param_border"));
        this.clrBorderRectangle = new Color(r, g, b);
        this.clrBorderCircle = new Color(r-40, g-140, b+40);
        this.depth = Float.parseFloat(this.getParameter("border_depth"));
        circle = new AffineTransform();
        rectangle=  new AffineTransform();
        if ( t == null )
            t = new Thread( this );
        t.start();
    }

    public void draw(Graphics g) {
        offScreenGraphics.clearRect(0,0,X,Y);
        Graphics2D g2 = ( Graphics2D ) offScreenGraphics;
        BasicStroke stroke = new BasicStroke(this.depth);
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        circleArea = new Area(new Ellipse2D.Float(140, 180, 260, 220));
        rectangleArea = new Area(new Rectangle2D.Float(140 - this.depth, 180 - this.depth, 260 + 2*this.depth, 220 + 2*this.depth));
        circleArea.transform(circle);
        rectangleArea.transform(rectangle);
        g2.setStroke(stroke);
        g2.setColor(this.clrBorderRectangle);
        g2.draw(rectangleArea);
        g2.setColor(this.clrBorderCircle);
        g2.draw(circleArea);
        g2.setColor(this.clrSector);
        g2.fill(circleArea);
        g.drawImage(offScreenImage, 0, 0, this);
    }

    void prepareDraw( Graphics g ) {
        g.translate(0, 0);
    }

    public void paint(Graphics g) {
        prepareDraw(g);
        draw(g);
    }

    private void rotate()
    {
        circle.rotate(Math.toRadians(Math.PI/2), 270, 290);
        rectangle.rotate(Math.toRadians(-Math.PI/2), 270, 290);
    }

    public void run() {
        try
        {
            while (true)
            {
                t.sleep(90);
                rotate();
                repaint();
            }
        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
    }

}
