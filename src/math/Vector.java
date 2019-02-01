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

    public double getangle(Vector v){
        return Math.acos((this.dot(v))/(this.getLenght()*v.getLenght()));
    }

    public double dot(Vector v){
        return x*v.x + y*v.y;
    }

    public static Vector getVerticalVector(){
        return new Vector(0,1);
    }

    public static Vector getHorizontalVector(){
        return new Vector(1,0);
    }

    public static void main(String[] args){
        Vector v = new Vector(1,3);
        Vector v2 = new Vector(3,-1);
        System.out.println(v.getangle(v2));
    }
}
