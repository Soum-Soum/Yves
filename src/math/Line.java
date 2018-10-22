package math;

import Jama.Matrix;
import util.Utilies;

public class Line {

    double a,b,c;

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Line(Segment segment) {
        Vector v = segment.getVector();
        b = v.x;
        a = -v.y;
        c = (a*segment.head.x+b*segment.head.y);
    }

    public Profile getProfile(){
        if (b==0){
            return Profile.VERTICAL;
        }else if (a==0){
            return Profile.HORIZONTAL;
        }else if (-a/b>0){
            return Profile.GOES_UP;
        }else if (-a/b<0){
            return Profile.GOES_DOWN;
        }
        return null;
    }

    //FAUX
/*    public Line getParallelLine(double step, boolean isOnRightSide){
        if (isOnRightSide){
            return new Line(a,b,c-step);
        }else {
            return new Line(a,b,c+step);
        }
    }*/

    public void print(){
        System.out.println("a = " + this.a);
        System.out.println("b = " + this.b);
        System.out.println("c = " + this.c);
    }

    public Point intersect(Line line){
        try {
            double[][] temp = {{a,b},{line.a,line.b}};
            Matrix m1 = new Matrix(temp);
            temp = new double[][]{{c}, {line.c}};
            Matrix m2 = new Matrix(temp);
            Matrix ans = m1.solve(m2);
            return new Point(Utilies.round3(ans.get(0,0)),Utilies.round3(ans.get(1,0)));
        }catch (RuntimeException e){
            return null;
        }
    }

    public boolean isVecticalLine(){
        return b==0;
    }

    public boolean isHorizontalLine(){
        return a==0;
    }

    public static void main(String[] args){
        Line line = new Line(-1,3,-8);
    }
}
