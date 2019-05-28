import java.awt.*;
import java.io.Serializable;

class Ship_ implements Serializable {
    private Point[] koords;
    private int[] states;
    private int length, hor_vert;

    Ship_(int beginX, int beginY, int hor_vert, int length){
        this.length = length;
        koords = new Point[length];
        states = new int[length];
        this.hor_vert = hor_vert;
        for(int i = 0; i < length; i++)
            states[i] = 1;

        if(hor_vert == 0)
            for(int i = 0; i < length; i++)
            {
                koords[i] = new Point(beginX + i, beginY);
            }
        else
            for(int i = 0; i < length; i++)
            {
                koords[i] = new Point(beginX, beginY + i);
            }
    }

    int getHor_vert(){
        return hor_vert;
    }

    int getLength(){
        return length;
    }

    Point[] getKoords(){
        return koords;
    }

    void setKoords(Point[] koords){
        this.koords = koords;
    }

    boolean isAttacked(int x, int y){
        for(int i = 0; i < length; i++){
            if(koords[i].x == x && koords[i].y == y){
                states[i] = 2;
                return true;
            }
        }
        return false;
    }

    boolean isDead(){
        int countAttacked = 0;
        for(int i = 0; i < length; i++)
            if(states[i] == 2) ++countAttacked;
        return countAttacked == length;
    }
}