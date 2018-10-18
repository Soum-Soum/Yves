package home;

import math.*;
import view.obj.ViewBeam;

import java.util.ArrayList;

public class QuadrialteralBeam extends Beam {
    public Quadrilateral shape;

    public QuadrialteralBeam(Segment segment, double width, boolean isOnRightSide, ShapeType type, String ref, double thetaTop, double thetaButtom) {
        this.shape = new Quadrilateral(segment, width, isOnRightSide, type, thetaTop, thetaButtom);
        montants = new ArrayList<>();
        this.ref=ref;
    }

    public QuadrialteralBeam(Area area, ViewBeam viewBeam){
        montants = new ArrayList<>();
        double x = Double.parseDouble(viewBeam.x.getValue());
        double width = Double.parseDouble(viewBeam.wight.getValue());
        double height = Double.parseDouble(viewBeam.height.getValue());
        shape = new Quadrilateral();
        shape.topLeft = new Point(x,area.getInerShape().getHeight(x));
        shape.topRight = new Point(x+width,area.getInerShape().getHeight(x+width));
        if (shape.topLeft.y<shape.topRight.y){
            shape.buttomRight = new Point(shape.topRight.x,shape.topRight.y-height);
            shape.buttomLeft = new Point(shape.topLeft.x,shape.buttomRight.y);
        }else {
            shape.buttomLeft = new Point(shape.topLeft.x,shape.topLeft.y-height);
            shape.buttomRight = new Point(shape.topRight.x,shape.buttomLeft.y);
        }
        shape.thetaTop =area.getInerShape().getTheta(shape.buttomLeft.x);
        this.shape.setSegments();
        this.ref = "A-" + area.name + " P-n°" + String.valueOf(area.beams.size()+1);
    }

    @Override
    public Polygone getShape() {
        return shape;
    }
}
