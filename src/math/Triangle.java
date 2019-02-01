package math;

public class Triangle {

    Point A,B,C;
    Segment AB,AC,BC;

    public Triangle(Point a, Point b, Point c) {
        A = a;
        B = b;
        C = c;
        setSegment();
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
        return ((b1 == b2) && (b2 == b3)) || isOnTheBorder(AB,pt) || isOnTheBorder(AC,pt) || isOnTheBorder(BC,pt);
    }


    private boolean isOnTheBorder(Segment s , Point p){
        Segment s1 = new Segment(s.head,p), s2 = new Segment(p,s.tail);
        if (s1.getLenght()+s2.getLenght()<=s.getLenght()){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Triangle t = new Triangle(new Point(15,15),new Point(25,35),new Point(25,20));
        Point p = new Point(15,20);
        System.out.println(t.isInside(p));
    }
}
