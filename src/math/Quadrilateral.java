package math;

public class Quadrilateral extends Polygone {

    public ShapeType type;
    public Point buttomRight, buttomLeft, topRight, topLeft;
    public  Segment buttom, left, right, top;
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
                buttomLeft = segment.head;
                topLeft = segment.tail;
                buttomRight = tempSeg.head;
                topRight = tempSeg.tail;
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
                buttomLeft = tempSeg.head;
                topLeft = tempSeg.tail;
                buttomRight = segment.head;
                topRight = segment.tail;
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
                topRight = new Point(topRight,0,width*Math.tan(theta));
                break;
            case TRAPEZIUM2:
                topLeft = new Point(topRight,0,width*Math.tan(theta));
                break;
            case TRAPEZIUM3:
                buttomRight = new Point(topRight,0,-width*Math.tan(theta));
                break;
            case TRAPEZIUM4:
                buttomLeft = new Point(topRight,0,-width*Math.tan(theta));
                break;
            default:
                break;
        }
        setSegments();
    }

    public Quadrilateral(Segment segment, double width ,boolean isOnRightSide, ShapeType type ) {
        this.type=type;
        Segment parallelSegment;
        switch (type){
            case PARAlLELOGRAM1:
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
                if (isOnRightSide){
                    parallelSegment = new Segment(segment, width,0,width,0);
                    buttomLeft = segment.tail;
                    buttomRight=parallelSegment.tail;
                    topLeft = segment.head;
                    topRight = parallelSegment.head;
                }else {
                    parallelSegment = new Segment(segment, -width,0,-width,0);
                    buttomLeft = parallelSegment.tail;
                    buttomRight=segment.tail;
                    topLeft = parallelSegment.head;
                    topRight = segment.tail;
                }
                break;
        }
        setSegments();
    }

    public Quadrilateral() {
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
        Line horizontalLine = new Line(new Segment(x,0, x+1,0));
        return new Segment( horizontalLine.intersect(buttomLine), horizontalLine.intersect(topLine));
    }

    private void setSegments(){
        buttom = new Segment(buttomLeft,buttomRight);
        left= new Segment(buttomLeft,topLeft);
        right = new Segment(buttomRight,topRight);
        top=new Segment(topLeft,topRight);
    }
}
