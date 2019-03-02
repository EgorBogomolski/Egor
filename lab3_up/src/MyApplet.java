import java.applet.Applet;
import java.awt.*;



public class MyApplet extends Applet  {



    public void paint(Graphics g) {
        setSize(560,540);
        g.setColor(new Color(12,16,16));
        g.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(106, 116, 131));
        g2d.setStroke(new MyStroke(2));
        g2d.draw(new MyPascal(0.025, 300, 150, 550, 125));

    }
}
