package math;

public class Vector {

    double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Segment segment) {
        x = segment.tail.x-segment.head.x;
        y =segment.tail.y-segment.head.y;
    }

    public double getLenght(){
        return Math.sqrt((x*x)+(y*y));
    }
}
