package math;

import data.DATACONTAINER;
import util.Utilies;

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
        for (Point point : p.getPoints()){
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
        //return buttomLeft.x;
        double min = Double.POSITIVE_INFINITY;
        for (Point p : getPoints()){
            if (min > p.x){
                min = p.x;
            }
        }
        return min;
    }

    public double getMaxX(){
        double max = Double.NEGATIVE_INFINITY;
        for (Point p : getPoints()){
            if (max < p.x){
                max = p.x;
            }
        }
        return max;
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

    public abstract boolean isPentagon();

    public double getHeight(double x){
        return this.getVerticalSegment(x).tail.y;
    }
}
