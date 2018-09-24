package math;

import java.util.ArrayList;

public class Triangle {

    Point A,B,C;
    Segment AB,AC,BC;

    public Triangle(Point a, Point b, Point c) {
        A = a;
        B = b;
        C = c;
    }

    public void setSegment(){
        AB = new Segment(A,B);
        AC = new Segment(A,C);
        BC = new Segment(B,C);
    }

    public double sign(Point p1, Point p2, Point p3){
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    public boolean isInside (Point pt){
        boolean b1, b2, b3;
        b1 = sign(pt, A, B) < 0.0f;
        b2 = sign(pt, B, C) < 0.0f;
        b3 = sign(pt, C, A) < 0.0f;
        return ((b1 == b2) && (b2 == b3));
    }
}
