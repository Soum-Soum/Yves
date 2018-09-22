package math;

import java.util.ArrayList;
import java.util.Arrays;

public class Pentagon extends Polygone {
    Point buttomRight, buttomLeft, mediumRight, mediumLeft, top;
    Segment buttom, left, right, topLeft, topRight;
    double faitageValue, thetaLeft, thetaRight;

    public Pentagon(Point buttomRight, Point buttomLeft, Point mediumRight, Point mediumLeft, Point top) {
        this.buttomRight = buttomRight;
        this.buttomLeft = buttomLeft;
        this.mediumRight = mediumRight;
        this.mediumLeft = mediumLeft;
        this.top = top;
        setSegments();
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
        this.thetaRight = topRight.getAngle(right);
    }

    private void setSegments(){
        buttom = new Segment(buttomLeft,buttomRight);
        left= new Segment(buttomLeft,mediumLeft);
        right = new Segment(buttomRight,mediumLeft);
        topLeft=new Segment(mediumLeft,top);
        topRight=new Segment(mediumRight,top);
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
        Line horizontalLine = new Line(new Segment(x,0, x+1,0));
        return new Segment( horizontalLine.intersect(buttomLine), horizontalLine.intersect(topLine));
    }

    @Override
    public ArrayList<Segment> getSegments() {
        return new ArrayList<>(Arrays.asList(buttom, left, right, topLeft, topRight));
    }

}
