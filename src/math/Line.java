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
            return new Point(Utilies.rount3(ans.get(0,0)),Utilies.rount3(ans.get(1,0)));
        }catch (RuntimeException e){
            return null;
        }
    }

    public static void main(String[] args){
        Segment segment = new Segment( 0,2,4,1);
        Line line = new Line(segment);
        line.print();
    }
}
