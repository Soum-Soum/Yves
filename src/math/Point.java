package math;

import com.sun.istack.internal.Nullable;
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

    public void print(){
         System.out.print("(" + x + "," + y + ")");
    }

    public String toString(){
        return "(" + x + "," + y + ")";
    }

    public boolean isUnderObstacle(List<Beam> list, List<Window> list2, Boolean acceptLeft, Boolean acceptRight){
        for (Beam b : list){
            if (this.x>b.getShape().getMinX()- DATACONTAINER.MONTANTWIDTH && this.x<b.getShape().getMaxX()+ DATACONTAINER.MONTANTWIDTH){
                for (Window w :list2){
                    if (!(this.x>=w.getMinX()- 2*DATACONTAINER.MONTANTWIDTH && this.x<=w.getMaxX()+ 2*DATACONTAINER.MONTANTWIDTH && this.y< w.getMinY())){
                        return true;
                    }
                }
            }else  if (!acceptLeft && (this.x>b.getShape().getMinX()- DATACONTAINER.MONTANTWIDTH && this.x==b.getShape().getMaxX()+ DATACONTAINER.MONTANTWIDTH)){
                for (Window w :list2){
                    if (!(this.x>=w.getMinX()- 2*DATACONTAINER.MONTANTWIDTH && this.x<=w.getMaxX()+ 2*DATACONTAINER.MONTANTWIDTH && this.y< w.getMinY())){
                        return true;
                    }
                }
            }else if (!acceptRight && (this.x==b.getShape().getMinX()- DATACONTAINER.MONTANTWIDTH && this.x<b.getShape().getMaxX()+ DATACONTAINER.MONTANTWIDTH)){
                for (Window w :list2){
                    if (!(this.x>=w.getMinX()- 2*DATACONTAINER.MONTANTWIDTH && this.x<=w.getMaxX()+ 2*DATACONTAINER.MONTANTWIDTH && this.y< w.getMinY())){
                        return true;
                    }
                }
            }
        }
        for (Window w : list2){
            if ((this.x == w.getMinX() || this.x==w.getMaxX()) && this.y< w.getMinY()){
                return true;
            }
        }
        return false;
    }


}
