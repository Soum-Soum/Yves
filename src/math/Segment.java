package math;

public class Segment {

    Point head, tail;

    public Segment(Point head, Point tail) {
        this.head = head;
        this.tail = tail;
    }

    public Segment(double x1, double y1, double x2, double y2){
        this.head = new Point(x1,y1);
        this.tail = new Point(x2,y2);
    }

    public Segment(Segment segment, double dx1, double dy1, double dx2, double dy2){
        this.head = new Point(head,dx1,dy1);
        this.tail = new Point(tail,dx2,dy2);
    }

    public Vector getVector(){
        return new Vector(this);
    }

    public double getLenght(){
        return  getVector().getLenght();
    }
    public boolean isVertical(){
        return head.x == tail.x;
    }

    public boolean isHorizontal(){
        return head.y == tail.y;
    }
}
