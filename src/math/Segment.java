package math;

public class Segment {

    public Point head, tail;

    public Segment(Point head, Point tail) {
        this.head = head;
        this.tail = tail;
    }

    public Segment(double x1, double y1, double x2, double y2){
        this.head = new Point(x1,y1);
        this.tail = new Point(x2,y2);
    }

    public Segment(Segment segment, double dx1, double dy1, double dx2, double dy2){
        this.head = new Point(segment.head,dx1,dy1);
        this.tail = new Point(segment.tail,dx2,dy2);
    }

    public Vector getVector(){
        return new Vector(this);
    }

    public double getLenght(){
        return  getVector().getLenght();
    }
    public boolean isVertical(){
        return Math.round(head.x) == Math.round(tail.x);
    }

    public boolean isHorizontal(){
        return Math.round(head.y) == Math.round(tail.y);
    }

    public double getAngle(Segment s){
        return this.getVector().getangle(s.getVector());
    }

    public static Segment getHorizontalSegment(){
        return new Segment(0,0,1,0);
    }

    public static Segment getVecticalSegment(){
        return new Segment(0,0,0,1);
    }

    public static Segment getVerticalSegment(double x, Segment top, Segment buttom){
        Line topLine = new Line(top);
        Line buttomLine = new Line(buttom);
        Line verticalSegment = new Line(new Segment(x,0, x,1));
        return new Segment( verticalSegment.intersect(buttomLine), verticalSegment.intersect(topLine));
    }
}
