package math;


import java.util.ArrayList;
import java.util.Arrays;


public class Quadrilateral extends Polygone {

    public ShapeType type;
    public Point topRight, topLeft;
    public  Segment top;
    public double theta;

    public Quadrilateral(Point buttomLeft, Point buttomRight, Point topLeft, Point topRight, ShapeType type) {
        this.buttomRight = buttomRight;
        this.buttomLeft = buttomLeft;
        this.topRight = topRight;
        this.topLeft = topLeft;
        this.type = type;
        setSegments();
    }

    public Quadrilateral(Segment segment, double width, double theta ,boolean isOnRightSide, ShapeType type ){
        this.type=type;
        this.theta = theta;
        if (isOnRightSide){
            if (segment.isHorizontal()){
                Segment tempSeg = new Segment(segment,0,-width,0,-width);
                buttomLeft = tempSeg.head;
                topLeft = segment.head;
                buttomRight = tempSeg.tail;
                topRight = segment.tail;
            }
            if (segment.isVertical()){
                Segment tempSeg = new Segment(segment,width,0,width,0);
                buttomLeft = segment.head;
                topLeft = segment.tail;
                buttomRight = tempSeg.head;
                topRight = tempSeg.tail;
            }

        }else {
            if (segment.isHorizontal()){
                Segment tempSeg = new Segment(segment,0,width,0,width);
                buttomLeft = segment.head;
                topLeft = tempSeg.head;
                buttomRight = segment.tail;
                topRight = tempSeg.tail;
            }
            if (segment.isVertical()){
                Segment tempSeg = new Segment(segment,-width,0,-width,0);
                buttomLeft = tempSeg.head;
                topLeft = tempSeg.tail;
                buttomRight = segment.head;
                topRight = segment.tail;
            }
        }
        switch (type){
            case TRAPEZIUM1:
                if(isOnRightSide){
                    topRight = new Point(topRight,0,width*Math.tan(theta));
                }else {
                    topLeft = new Point(topLeft,0,-width*Math.tan(theta));
                }
                break;
            case TRAPEZIUM2:
                if(isOnRightSide){
                    topRight = new Point(topRight,0,-width*Math.tan(theta));
                }else {
                    topLeft = new Point(topLeft,0,width*Math.tan(theta));
                }
                break;
            case TRAPEZIUM3:
                if(isOnRightSide){
                    buttomRight = new Point(topRight,0,-width*Math.tan(theta));
                }else {
                    buttomLeft = new Point(topRight,0,width*Math.tan(theta));
                }
                break;
            case TRAPEZIUM4:
                if(isOnRightSide){
                    buttomRight = new Point(topRight,0,width*Math.tan(theta));
                }else {
                    buttomLeft = new Point(topRight,0,-width*Math.tan(theta));
                }
                break;
            default:
                break;
        }
        setSegments();
    }

    public Quadrilateral(Segment segment, double width ,boolean isOnRightSide, ShapeType type ) {
        this.type=type;
        double theta;
        Segment parallelSegment;
        switch (type){
            case PARAlLELOGRAM1:
                theta = segment.getAngle(Segment.getVecticalSegment());
                width = width/Math.sin(theta);
                if (isOnRightSide) {
                    parallelSegment = new Segment(segment, 0,-width,0,-width);
                    buttomLeft = parallelSegment.head;
                    buttomRight=parallelSegment.tail;
                    topLeft = segment.head;
                    topRight = segment.tail;
                }else {
                    parallelSegment = new Segment(segment, 0,width,0,width);
                    buttomLeft = segment.head;
                    buttomRight=segment.tail;
                    topLeft = parallelSegment.head;
                    topRight = parallelSegment.tail;
                }
            break;
            case PARALlELOGRAM2:
                theta = segment.getAngle(Segment.getHorizontalSegment());
                width = width/Math.sin(theta);
                if (isOnRightSide){
                    parallelSegment = new Segment(segment, width,0,width,0);
                    buttomLeft = segment.head;
                    buttomRight=parallelSegment.head;
                    topLeft = segment.tail;
                    topRight = parallelSegment.tail;
                }else {
                    parallelSegment = new Segment(segment, -width,0,-width,0);
                    buttomLeft = parallelSegment.head;
                    buttomRight=segment.head;
                    topLeft = parallelSegment.tail;
                    topRight = segment.tail;
                }
                break;
        }
        setSegments();
    }

    public Quadrilateral() {
    }

    @Override
    public ArrayList<Point> getPoints() {
        return new ArrayList<>(Arrays.asList(buttomLeft,buttomRight,topLeft,topRight));
    }

    @Override
    public Segment getTopSegment(double x) {
        return top;
    }

    @Override
    public void print(){
        System.out.println("butomLeft : " + buttomLeft.print());
        System.out.println("topLeft : " + topLeft.print());
        System.out.println("buttomRight : " + buttomRight.print());
        System.out.println("topRight : " + topRight.print());
    }

    @Override
    public Segment getVerticalSegment(double x) {
        Line topLine = new Line(top);
        Line buttomLine = new Line(buttom);
        Line verticalSegment = new Line(new Segment(x,0, x,1));
        return new Segment( verticalSegment.intersect(buttomLine), verticalSegment.intersect(topLine));
    }

    @Override
    public ArrayList<Segment> getSegments() {
        return new ArrayList<>(Arrays.asList(buttom, left, right, top));
    }

    public void setSegments(){
        buttom = new Segment(buttomLeft,buttomRight);
        left= new Segment(buttomLeft,topLeft);
        right = new Segment(buttomRight,topRight);
        top=new Segment(topLeft,topRight);
    }

    public static Quadrilateral getNormalMontant(Segment segment, double width, double theta, boolean isOnRightSide, ShapeType type){
        return new Quadrilateral(segment, width, theta,  isOnRightSide, type);
    }
    public static Quadrilateral getParalelMontant(Segment segment, double width, boolean isOnRightSide, ShapeType type){
        return new Quadrilateral(segment, width, isOnRightSide, type);
    }

    public ArrayList<Triangle> getTriangles(){
        return new ArrayList<>(Arrays.asList(new Triangle(buttomLeft,topLeft,buttomRight),new Triangle(topLeft,topRight,buttomRight)));
    }

    @Override
    public double getTheta(double x) {
        return theta;
    }

    @Override
    public ShapeType getType(double x) {
        return type;
    }

    public static void main(String[] argd){
        Quadrilateral test = Quadrilateral.getParalelMontant(new Segment(1,1,5,5),4.5,true,ShapeType.PARALlELOGRAM2);
        test.print();
    }
}
