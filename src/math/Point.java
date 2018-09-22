package math;

public class Point {
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point, double dx, double dy){
        x = point.x + dx;
        y = point.y + dy;
    }

    public String print(){
        return "(" + x + "," + y + ")";
    }


}
