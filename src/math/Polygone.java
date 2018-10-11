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
        for (Point point : this.getPoints()){
            if (!this.isInside(point)){return false;}
        }
        return true;
    }

    public boolean haveAnIntersection(Polygone p){
        for (Segment s1 : this.getSegments()){
            for (Segment s2 : p.getSegments()){
                if (s1.intersect(s2)!=null){
                    return true;
                }
            }
        }
        return false;
    }

    public double getMinX(){
        return buttomLeft.x;
    }

    public double getMaxX(){
        return buttomRight.x;
    }

    public abstract double getMinY();

    public abstract double getMaxY();

    public abstract ArrayList<Point> getPoints();

    public abstract Segment getTopSegment(double x);

    public abstract void print();

    public abstract Segment getVerticalSegment(double x);

    public abstract ArrayList<Segment> getSegments();

    public abstract ArrayList<Triangle> getTriangles();

    public abstract double getTheta(double x);

    public abstract ShapeType getType(double x);

    public abstract Point getTopPoint();

    public double getHeight(double x){
        return this.getVerticalSegment(x).tail.y;
    }
}
