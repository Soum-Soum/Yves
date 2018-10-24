package math;

import data.DATACONTAINER;
import home.Beam;
import home.Window;

import java.util.List;

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

    public boolean isUnderBeam(List<Beam> list, List<Window> list2){
        for (Beam b : list){
            if (this.x>=b.getShape().buttomLeft.x- DATACONTAINER.MONTANTWITH && this.x<=b.getShape().buttomRight.x+ DATACONTAINER.MONTANTWITH){
                for (Window w :list2){
                    if (this.x>=w.buttomLeft.x- 2*DATACONTAINER.MONTANTWITH && this.x<=w.buttomRight.x+ 2*DATACONTAINER.MONTANTWITH && this.y< w.getMinY()){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
