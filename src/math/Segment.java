package math;

import java.awt.event.MouseAdapter;

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

    public Point intersect(Segment s){
        if (this.getMaxAbs() < s.getMinAbs() || s.getMaxAbs() < this.getMinAbs() ){
            return null;
        }else{
            Line l1 = new Line(this);
            Line l2 = new Line(s);
            Point p = l1.intersect(l2);
            if (p!=null && (p.x >= Math.max( this.getMinAbs(), s.getMinAbs())) && (p.x <= Math.min( this.getMaxAbs(),s.getMaxAbs()) &&
                    (p.y >= Math.max(this.getMinOrd(),s.getMinOrd())) && (p.y <= Math.min(this.getMaxOrd(),s.getMaxOrd())))){
                return p;
            }
        }
        return null;
    }

    public SegmentProfile goesUp(){
        if (isVertical()){
            return SegmentProfile.VERTICAL;
        }else if (isHorizontal()){
            return SegmentProfile.HORIZONTAL;
        }if(head.y<tail.y){
            return SegmentProfile.GOES_UP;
        }else if (tail.y<head.y){
            return SegmentProfile.GOES_DOWN;
        }else {
            System.out.println("Bad segment");
        }
        return null;
    }

    public double getMaxAbs(){
        return Math.max(this.head.x,this.tail.x);
    }

    public double getMinAbs(){
        return Math.min(this.head.x,this.tail.x);
    }

    public double getMaxOrd(){
        return Math.max(this.head.y,this.tail.y);
    }

    public double getMinOrd(){
        return Math.min(this.head.y,this.tail.y);
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

    public static void main(String[] args){
        Segment s1 = new Segment(500,100,700,100);
        Segment s2 = new Segment(560,55,565,55);
        System.out.println(s1.intersect(s2).print());
    }

}
