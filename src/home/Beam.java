package home;

import math.Point;
import math.Quadrilateral;
import math.Segment;
import math.ShapeType;
import view.obj.ViewBeam;

import java.util.ArrayList;

public class Beam extends Quadrilateral {

    public ArrayList<Montant> montants;

    public Beam(Segment segment, double width, double theta, boolean isOnRightSide, ShapeType type) {
        super(segment, width, theta, isOnRightSide, type);
        montants = new ArrayList<>();
    }

    public Beam(Area area, ViewBeam viewBeam){
        super();
        montants = new ArrayList<>();
        double x = Double.parseDouble(viewBeam.x.getValue());
        double width = Double.parseDouble(viewBeam.wight.getValue());
        double height = Double.parseDouble(viewBeam.height.getValue());
        topLeft = new Point(x,area.getShape().getHeight(x));
        topRight = new Point(x+width,area.getShape().getHeight(x+width));
        if (topLeft.y<topRight.y){
            buttomRight = new Point(topRight.x,topRight.y-height);
            buttomLeft = new Point(topLeft.x,buttomRight.y);
        }else {
            buttomLeft = new Point(topLeft.x,topLeft.y-height);
            buttomRight = new Point(topRight.x,buttomLeft.y);
        }
        theta=area.getShape().getTheta(buttomLeft.x);
        this.setSegments();
    }

}
