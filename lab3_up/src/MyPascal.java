import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class MyPascal implements Shape {

    double flatnes;
    double aParam;
    double lParam;
    int width = 320;
    int height = 640;

    public MyPascal(double fl,double ap,double lp,int wh,int he){
        this.flatnes=fl;
        this.aParam=ap;
        this.lParam=lp;
        this.width=wh;
        this.height=he;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new PascalIterator(this.flatnes, this.aParam, this.lParam, this.width, this.height);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new PascalIterator(flatness, this.aParam, this.lParam, this.width, this.height);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    class PascalIterator implements PathIterator {

        double flatnes;
        double aParam;
        double lParam;
        double currX;
        double currY;
        double currP;
        double currFI=-Math.PI;
        int width = 320;
        int height = 640;
        boolean isDone=false;

        public PascalIterator(double fl,double ap,double lp,int wh,int he){
            this.flatnes=fl;
            this.aParam=ap;
            this.lParam=lp;
            this.width=wh;
            this.height=he;
        }

        @Override
        public int currentSegment(float[] coords) {
            if (this.currFI == -Math.PI)
            {
                this.currP=this.lParam-this.aParam*Math.sin(this.currFI);
                this.currY=this.currP*Math.sin(this.currFI);
                this.currX=this.currP*Math.cos(this.currFI);
            }
            double x = this.currX + this.width/2;
            double y = this.height/2 - this.currY;
            coords[0] = (float) x;
            coords[1] = (float) y;

            if (this.currFI == -Math.PI)
                return SEG_MOVETO;

            if (Math.abs(this.currFI - Math.PI) < this.flatnes)
                this.isDone = true;

            return SEG_LINETO;
        }

        @Override
        public int currentSegment(double[] coords) {
            if (this.currFI == -Math.PI)
            {
                this.currP=this.lParam-this.aParam*Math.sin(this.currFI);
                this.currY=this.currP*Math.sin(this.currFI);
                this.currX=this.currP*Math.cos(this.currFI);
            }
            double x = this.currX + this.width/2;
            double y = this.height/2 - this.currY;
            coords[0] = x;
            coords[1] = y;

            if (this.currFI == -Math.PI)
                return SEG_MOVETO;

            if (Math.abs(this.currFI - Math.PI) < this.flatnes)
                this.isDone = true;

            return SEG_LINETO;
        }

        @Override
        public int getWindingRule() {return WIND_NON_ZERO;}

        @Override
        public boolean isDone() {
            return this.isDone;
        }

        @Override
        public void next() {
            this.currFI+=this.flatnes;
            this.currP=this.lParam-this.aParam*Math.sin(this.currFI);
            this.currY=this.currP*Math.sin(this.currFI);
            this.currX=this.currP*Math.cos(this.currFI);
        }

    }

}
