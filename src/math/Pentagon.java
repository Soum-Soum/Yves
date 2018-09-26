package math;

import javax.naming.NamingEnumeration;
import java.util.ArrayList;
import java.util.Arrays;

public class Pentagon extends Polygone {
    public Point mediumRight, mediumLeft, top;
    public Segment topLeft, topRight;
    public double faitageValue, thetaLeft, thetaRight;

    public Pentagon(Point buttomRight, Point buttomLeft, Point mediumRight, Point mediumLeft, Point top) {
        this.buttomRight = buttomRight;
        this.buttomLeft = buttomLeft;
        this.mediumRight = mediumRight;
        this.mediumLeft = mediumLeft;
        this.top = top;
        setSegments();
        faitageValue = (top.x-buttomLeft.x)/(buttomRight.x-buttomLeft.x);
        thetaLeft = Segment.getHorizontalSegment().getAngle(topLeft);
        thetaRight = Math.atan((Math.tan(thetaLeft)*faitageValue)/(1-faitageValue));
    }

    public Pentagon(Point buttomLeft, double height, double width, double thetaLeft, double faitageValue){
        this.faitageValue = faitageValue;
        this.thetaLeft = thetaLeft;
        this.buttomLeft=buttomLeft;
        this.buttomRight = new Point(buttomLeft, width,0);
        this.mediumLeft = new Point(buttomLeft,0,height);
        this.mediumRight = new Point(buttomRight,0,height);
        this.top = new Point(width*faitageValue,height + (width*faitageValue)*Math.tan(thetaLeft));
        setSegments();
        thetaRight = Math.atan((Math.tan(thetaLeft)*faitageValue)/(1-faitageValue));
    }

    private void setSegments(){
        buttom = new Segment(buttomLeft,buttomRight);
        left= new Segment(buttomLeft,mediumLeft);
        right = new Segment(buttomRight,mediumRight);
        topLeft=new Segment(mediumLeft,top);
        topRight=new Segment(top,mediumRight);
    }

    @Override
    public ArrayList<Point> getPoints() {
        return new ArrayList<>(Arrays.asList(buttomLeft,buttomRight,mediumLeft,mediumRight,top));
    }

    @Override
    public Segment getTopSegment(double x) {
        if (x>top.x){return topRight;}
        else{return topLeft;}
    }

    @Override
    public void print() {
        System.out.println("butomLeft : " + buttomLeft.print());
        System.out.println("mediumLeft : " + mediumLeft.print());
        System.out.println("buttomRight : " + buttomRight.print());
        System.out.println("mediumRight : " + mediumRight.print());
        System.out.println("top : " + top.print());
    }

    @Override
    public Segment getVerticalSegment(double x) {
        Line topLine;
        if(x> faitageValue*buttom.getLenght()){
            topLine = new Line(topRight);
        }else{
            topLine = new Line(topLeft);
        }
        Line buttomLine = new Line(buttom);
        Line verticalSegment = new Line(new Segment(x,0, x,1));
        return new Segment( verticalSegment.intersect(buttomLine), verticalSegment.intersect(topLine));
    }

    @Override
    public ArrayList<Segment> getSegments() {
        return new ArrayList<>(Arrays.asList(buttom, left, right, topLeft, topRight));
    }

    @Override
    public ArrayList<Triangle> getTriangles() {
        return new ArrayList<>(Arrays.asList(new Triangle(buttomLeft, mediumLeft, top), new Triangle(buttomLeft, buttomRight, top), new Triangle(buttomRight, mediumRight, top)));
    }

    @Override
    public double getTheta(double x) {
        if (x>top.x){return thetaRight;}
        else{return thetaLeft;}
    }

    @Override
    public ShapeType getType(double x) {
        if (x>top.x){return ShapeType.TRAPEZIUM2;}
        else{return ShapeType.TRAPEZIUM1;}
    }

}
