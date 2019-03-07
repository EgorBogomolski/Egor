import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.PrintWriter;


public class MyApplet extends Applet implements ActionListener {

    protected Button print;

    boolean dbutton=true;

    public void paint(Graphics g)
    {
        setSize(560,540);
        g.setColor(new Color(123,160,160));
        g.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d                         = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.setStroke(new MyStroke(2));
        g2d.draw(new MyPascal(0.025,300,150,550,155));
        if(dbutton)
        {
            Panel p = new Panel();
            this.add(p,"Center");
            p.setFont(new Font("SansSerif", Font.BOLD, 18));
            print = new Button("Print Page");
            p.add(print,BorderLayout.EAST);
            print.addActionListener(this);
            p.add(print);
            dbutton=false;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == print) printDemoPage();
    }

    public void printDemoPage() {
        // Create a HardcopyWriter, using a 14 point font and 3/4" margins.
        HardcopyWriter hw;
        try { hw=new HardcopyWriter("Pascal page",14,.75,.75,.75,.75);}
        catch (HardcopyWriter.PrintCanceledException e) { return; }
        //hw.draw(new MyPascal(0.03,300,150,200,50));
        // Send output to it through a PrintWriter stream
        PrintWriter out = new PrintWriter(hw);

        // Figure out the size of the page
        int rows = hw.getLinesPerPage(), cols = hw.getCharactersPerLine();

        // Mark upper left and upper-right corners
        out.print("+");                            // upper-left corner
        for(int i=0;i<cols-2;i++) out.print(" ");  // space over
        out.print("+");                            // upper-right corner

        // Display a title
        hw.setFontStyle(Font.BOLD + Font.ITALIC);
        out.println("public int currentSegment(double[] coords) {");
        out.println("if (this.currFI == -Math.PI)");
        out.println("{");
        out.println("this.currP=this.lParam-this.aParam*Math.sin(this.currFI);");
        out.println("this.currY=this.currP*Math.sin(this.currFI);");
        out.println("this.currX=this.currP*Math.cos(this.currFI);");
        out.println("}");
        out.println("double x = this.currX + this.width/2;");
        out.println(" double y = this.height/2 - this.currY;");
        out.println(" coords[0] = x;");
        out.println("  coords[1] = y;");

        out.println("if (this.currFI == -Math.PI)");
        out.println(" return SEG_MOVETO;");

        out.println(" if (Math.abs(this.currFI - Math.PI) < this.flatnes)");
        out.println("     this.isDone = true;");
        out.println("  return SEG_LINETO;");
        out.println("	}");

        // Skip down to the bottom of the page
        for(int i = 0; i < rows-30; i++) out.println();

        // And mark the lower left and lower right
        out.print("+");                            // lower-left
        for(int i=0;i<cols-2;i++) out.print(" ");  // space-over
        out.print("+");                            // lower-right

        // Close the output stream, forcing the page to be printed
        out.close();
    }

}
