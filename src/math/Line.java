package math;

import Jama.Matrix;

import java.awt.desktop.OpenURIEvent;
import java.security.PublicKey;

public class Line {

    double a,b,c;

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Line(Segment segment) {
        Vector v = segment.getVector();
        b = -v.x;
        a = v.y;
        c = -(a*segment.head.x+b*segment.head.y);
    }

    public void print(){
        System.out.println("a = " + this.a);
        System.out.println("b = " + this.b);
        System.out.println("c = " + this.c);
    }

    public Point intersect(Line line){
        double[][] temp = {{a,b},{line.a,line.b}};
        Matrix m1 = new Matrix(temp);
        temp = new double[][]{{c}, {line.c}};
        Matrix m2 = new Matrix(temp);
        Matrix ans = m1.solve(m2);
        return new Point(ans.get(0,0),ans.get(1,0));
    }

    public static void main(String[] args){
        Segment segment = new Segment( 0,2,4,1);
        Line line = new Line(segment);
        line.print();
    }
}
