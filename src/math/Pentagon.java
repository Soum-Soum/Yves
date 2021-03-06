package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;

public class Pentagon extends Polygone {
    public Point mediumRight, mediumLeft, top;
    public Segment topLeft, topRight;
    public double faitageValue, thetaLeft, thetaRight;

    public Pentagon(Point buttomRight, Point buttomLeft, Point mediumRight, Point mediumLeft, Point top, double faitageValue, double thetaLeft, double thetaRight){
        this.buttomRight = buttomRight;
        this.buttomLeft = buttomLeft;
        this.mediumRight = mediumRight;
        this.mediumLeft = mediumLeft;
        this.top = top;
        setSegments();
        this.faitageValue = faitageValue;
        this.thetaLeft = thetaLeft;
        this.thetaRight = thetaRight;
        faitageValue = (top.x-buttomLeft.x)/(buttomRight.x-buttomLeft.x);
        thetaLeft = Segment.getHorizontalSegment().getAngle(topLeft);
        thetaRight = Math.atan((Math.tan(thetaLeft)*faitageValue)/(1-faitageValue));
    }

    public Pentagon(Point buttomLeft, double height, double width, double thetaLeft, double faitageValue, Boolean forceSameTheta){
        this.faitageValue = faitageValue;
        this.thetaLeft = thetaLeft;
        this.buttomLeft=buttomLeft;
        this.buttomRight = new Point(buttomLeft, width,0);
        this.mediumLeft = new Point(buttomLeft,0,height);
        this.top = new Point(buttomLeft.x + (width*faitageValue), mediumLeft.y + (width*faitageValue)*Math.tan(thetaLeft));

        if(!forceSameTheta){
            this.mediumRight = new Point(buttomRight,0,height);
            this.thetaRight = Math.atan((Math.tan(thetaLeft)*faitageValue)/(1-faitageValue));
        }else{
            this.thetaRight = thetaLeft;
            double delta = (Math.tan(thetaRight)*(buttomRight.x-top.x));
            this.mediumRight = new Point(buttomRight.x,top.y-delta);
        }
        this.setSegments();

    }

    public Pentagon(){}

    public void setSegments(){
        buttom = new Segment(buttomLeft,buttomRight);
        left= new Segment(buttomLeft,mediumLeft);
        right = new Segment(buttomRight,mediumRight);
        topLeft=new Segment(mediumLeft,top);
        topRight=new Segment(top,mediumRight);
    }

    @Override
    public double getMinY() {
        return buttomLeft.y;
    }

    @Override
    public double getMaxY() {
        return top.y;
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
        System.out.println("butomLeft : " + buttomLeft.toString());
        System.out.println("mediumLeft : " + mediumLeft.toString());
        System.out.println("buttomRight : " + buttomRight.toString());
        System.out.println("mediumRight : " + mediumRight.toString());
        System.out.println("top : " + top.toString());
    }

    @Override
    public Segment getVerticalSegment(double x) {
        Line topLine;
        if(x> buttomLeft.x + (faitageValue*buttom.getLenght())){
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

    @Override
    public Point getTopPoint() {
        return top;
    }

    @Override
    public boolean isPentagon() {
        return true;
    }

}
