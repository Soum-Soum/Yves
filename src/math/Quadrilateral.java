package math;


import home.Montant;

import java.util.ArrayList;
import java.util.Arrays;


public class Quadrilateral extends Polygone {

    public ShapeType type;
    public Point topRight, topLeft;
    public  Segment top;
    public double thetaTop, thetaButtom;

    public Quadrilateral(Point buttomLeft, Point buttomRight, Point topLeft, Point topRight, ShapeType type, double thetaTop, double thetaButtom) {
        this.buttomRight = buttomRight;
        this.buttomLeft = buttomLeft;
        this.topRight = topRight;
        this.topLeft = topLeft;
        this.type = type;
        this.thetaTop = thetaTop;
        this.thetaButtom = thetaButtom;
        setSegments();
    }

    public Quadrilateral(){}

    public Quadrilateral(Segment segment, double width, boolean isOnRightSide, ShapeType type, double thetaTop, double thetaButtom) {
        this.type = type;
        this.thetaTop = thetaTop;
        this.thetaButtom = thetaButtom;
        if (type != ShapeType.PARALLELOGRAM1 && type != ShapeType.PARALLELOGRAM2 && type != ShapeType.PARALLELOGRAM3 && type != ShapeType.PARALLELOGRAM4 ){
            if (isOnRightSide) {
                if (segment.isHorizontal()) {
                    Segment tempSeg = new Segment(segment, 0, -width, 0, -width);
                    buttomLeft = tempSeg.head;
                    topLeft = segment.head;
                    buttomRight = tempSeg.tail;
                    topRight = segment.tail;
                } else if (segment.isVertical()) {
                    Segment tempSeg = new Segment(segment, width, 0, width, 0);
                    buttomLeft = segment.head;
                    topLeft = segment.tail;
                    buttomRight = tempSeg.head;
                    topRight = tempSeg.tail;
                } else {
                    System.out.println("BAD CONSTRUCTOR");
                }
            } else {
                if (segment.isHorizontal()) {
                    Segment tempSeg = new Segment(segment, 0, width, 0, width);
                    buttomLeft = segment.head;
                    topLeft = tempSeg.head;
                    buttomRight = segment.tail;
                    topRight = tempSeg.tail;
                } else if (segment.isVertical()) {
                    Segment tempSeg = new Segment(segment, -width, 0, -width, 0);
                    buttomLeft = tempSeg.head;
                    topLeft = tempSeg.tail;
                    buttomRight = segment.head;
                    topRight = segment.tail;
                } else {
                    System.out.println("BAD CONSTRUCTOR");
                }
            }
            switch (type) {
                case TRAPEZIUM1:
                    this.thetaButtom =0;
                    if (isOnRightSide) {
                        topRight = new Point(topRight, 0, width * Math.tan(thetaTop));
                    } else {
                        topLeft = new Point(topLeft, 0, -width * Math.tan(thetaTop));
                    }
                    break;
                case TRAPEZIUM2:
                    this.thetaButtom =0;
                    if (isOnRightSide) {
                        topRight = new Point(topRight, 0, -width * Math.tan(thetaTop));
                    } else {
                        topLeft = new Point(topLeft, 0, width * Math.tan(thetaTop));
                    }
                    break;
                case TRAPEZIUM3:
                    this.thetaTop =0;
                    if (isOnRightSide) {
                        buttomRight = new Point(buttomRight, 0, -width * Math.tan(thetaButtom));
                    } else {
                        buttomLeft = new Point(buttomLeft, 0, width * Math.tan(thetaButtom));
                    }
                    break;
                case TRAPEZIUM4:
                    this.thetaTop =0;
                    if (isOnRightSide) {
                        buttomRight = new Point(buttomRight, 0, width * Math.tan(thetaButtom));
                    } else {
                        buttomLeft = new Point(buttomLeft, 0, -width * Math.tan(thetaButtom));
                    }
                    break;
                case REGULARTRAPEZIUMLEFT:
                    this.thetaButtom = this.thetaButtom ==0 ? thetaTop : thetaButtom;
                    if (isOnRightSide) {
                        topRight = new Point(topRight, 0, -width * Math.tan(thetaTop));
                        buttomRight = new Point(buttomRight, 0, width * Math.tan(thetaButtom));
                    } else {
                        topLeft = new Point(topLeft, 0, width * Math.tan(thetaTop));
                        buttomLeft = new Point(topRight, 0, -width * Math.tan(thetaButtom));
                    }
                    break;
                case REGULARTRAPEZIUMRIGHT:
                    this.thetaButtom = this.thetaButtom ==0 ? thetaTop : thetaButtom;
                    if (isOnRightSide) {
                        topRight = new Point(topRight, 0, width * Math.tan(thetaTop));
                        buttomRight = new Point(buttomRight, 0, -width * Math.tan(thetaButtom));
                    } else {
                        topLeft = new Point(topLeft, 0, -width * Math.tan(thetaTop));
                        buttomLeft = new Point(buttomLeft, 0, width * Math.tan(thetaButtom));
                    }
                    break;
                case RECTANGLE:
                    this.thetaTop =0;
                    this.thetaButtom =0;
                    break;
                default:
                    break;
            }
        }else {
            this.thetaButtom = this.thetaButtom ==0 ? thetaTop : thetaButtom;
            double stepTop = width * Math.tan(thetaTop);
            double stepButtom = width * Math.tan(thetaButtom);
            switch (type) {
                case PARALLELOGRAM1:
                    if (segment.isVertical()){
                        if (isOnRightSide) {
                            buttomLeft = segment.head;
                            topLeft = segment.tail;
                            buttomRight = new Point(buttomLeft, width, stepButtom);
                            topRight = new Point(topLeft, width, stepTop);
                        } else {
                            buttomRight = segment.head;
                            topRight = segment.tail;
                            buttomLeft = new Point(buttomLeft, -width, -stepButtom);
                            topLeft = new Point(topLeft, -width, -stepTop);
                        }
                    }else {
                        System.out.println("use the Other constructor");
                    }

                    break;
                case PARALLELOGRAM2:
                    if (segment.isVertical()){
                        if (isOnRightSide) {
                            buttomLeft = segment.head;
                            topLeft = segment.tail;
                            buttomRight = new Point(buttomLeft, width, -stepButtom);
                            topRight = new Point(topLeft, width, -stepTop);
                        } else {
                            buttomRight = segment.head;
                            topRight = segment.tail;
                            buttomLeft = new Point(buttomLeft, -width, stepButtom);
                            topLeft = new Point(topLeft, -width, stepTop);
                        }
                    }else {
                        System.out.println("use the Other constructor");
                    }
                    break;
                case PARALLELOGRAM3:
                    if (segment.isHorizontal()){
                        if (isOnRightSide) {
                            buttomLeft = segment.head;
                            buttomRight = segment.tail;
                            topLeft = new Point(buttomLeft, stepTop, width);
                            topRight = new Point(buttomRight, stepTop, width);
                        } else {
                            topLeft = segment.head;
                            topRight = segment.tail;
                            buttomLeft = new Point(buttomLeft, -stepButtom, -width);
                            buttomRight = new Point(buttomRight, -stepButtom, -width);
                        }
                        break;
                    }else {
                        System.out.println("use the Other constructor");
                    }

                case PARALLELOGRAM4:
                    if (segment.isHorizontal()){
                        if (isOnRightSide) {
                            buttomLeft = segment.head;
                            buttomRight = segment.tail;
                            topLeft = new Point(buttomLeft, -stepTop, width);
                            topRight = new Point(buttomRight, -stepTop, width);
                        } else {
                            topLeft = segment.head;
                            topRight = segment.tail;
                            buttomLeft = new Point(buttomLeft, stepButtom, -width);
                            buttomRight = new Point(buttomRight, stepButtom, -width);
                        }
                    }else {
                        System.out.println("use the Other constructor");
                    }
                    break;
            }
        }
        setSegments();
    }

    public Quadrilateral(Segment segment, double width, boolean isOnRightSide, ShapeType type){
        Segment parallelSegment;
        this.type=type;
        switch (type){
            case PARALLELOGRAM1:
                if (!segment.isVertical()){
                    thetaTop = segment.getAngle(Segment.getVecticalSegment());
                    width = width/Math.sin(thetaTop);
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
                }else{
                    System.out.println("Impossible with a Vertical segment");
                    System.out.println("use the Other constructor");
                }
                break;
            case PARALLELOGRAM2:
                if (!segment.isHorizontal()){
                    thetaTop = segment.getAngle(Segment.getHorizontalSegment());
                    width = width/Math.sin(thetaTop);
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
                }else{
                    System.out.println("Impossible with a Horizontal segment");
                    System.out.println("use the Other constructor");
                }
                break;
            default:
                System.out.println("use the Other constructor");
                break;
        }
        setSegments();
    }

    @Override
    public double getMinY() {
        switch (type){
            case TRAPEZIUM3:
                return buttomRight.y;
            case TRAPEZIUM4:
                return buttomLeft.y;
            default:
                return buttomLeft.y;
        }
    }

    @Override
    public double getMaxY() {
        switch (type){
            case TRAPEZIUM1:
                return topRight.y;
            case TRAPEZIUM2:
                return topLeft.y;
            default:
                return topLeft.y;
        }
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

    public ArrayList<Triangle> getTriangles(){
        return new ArrayList<>(Arrays.asList(new Triangle(buttomLeft,topLeft,buttomRight),new Triangle(topLeft,topRight,buttomRight)));
    }

    public Quadrilateral getIntersection(Montant m){
        Point buttomLeft = this.buttom.intersect(m.left);
        Point buttomRight = this.buttom.intersect(m.right);
        Point topLeft = this.top.intersect(m.left);
        Point topRight = this.top.intersect(m.right);
        if (buttomLeft!= null && buttomRight != null && topLeft != null && topRight != null){
            return new Quadrilateral(buttomLeft,buttomRight,topLeft,topRight,null, 0,0);
        }
        return null;
    }

    @Override
    public double getTheta(double x) {
        return thetaTop;
    }

    @Override
    public ShapeType getType(double x) {
        return type;
    }

    @Override
    public Point getTopPoint() {
        if(topRight.y>= topLeft.y){
            return topRight;
        }
        return topLeft;
    }

    public Point getWritingPoint(int i){
        int i2 = i%10;
        return new Point(buttomLeft.x + buttom.getLenght()/i2, buttomLeft.y+left.getLenght()/i2);
    }

    public static void main(String[] argd){
        Quadrilateral test = new Quadrilateral(new Segment(2,2,4,2),5,false,ShapeType.PARALLELOGRAM1);
        test.print();
    }
}