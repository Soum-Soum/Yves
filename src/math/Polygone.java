package math;

import java.util.ArrayList;

public abstract class Polygone {

    public Segment buttom, left, right;
    public Point buttomRight, buttomLeft;

    public boolean isInside(Point p){
        for (Triangle triangle : getTriangles()){
            if (triangle.isInside(p)){return true;}
        }
        return false;
    }

    public boolean shapIsInside(Polygone p){
        boolean b = true;
        for (Point point : this.getPoints()){
            b = this.isInside(point);
            if (!b){return b;}
        }
        return b;
    }

    public abstract ArrayList<Point> getPoints();

    public abstract Segment getTopSegment(double x);

    public abstract void print();

    public abstract Segment getVerticalSegment(double x);

    public abstract ArrayList<Segment> getSegments();

    public abstract ArrayList<Triangle> getTriangles();

    public abstract double getTheta(double x);

    public abstract ShapeType getType(double x);

    public double getHeight(double x){
        return this.getVerticalSegment(x).tail.y;
    }
}
