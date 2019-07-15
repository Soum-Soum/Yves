package home;

import math.*;
import view.obj.ViewBeam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class QuadrialteralBeam extends Beam implements Serializable {
    public Quadrilateral shape;

    public QuadrialteralBeam(Area area, ViewBeam viewBeam){
        leftMontant = new LinkedList<>();
        rightMontant = new LinkedList<>();
        midsMontants = new LinkedList<>();
        double x = area.getShape().getMinX() + Double.parseDouble(viewBeam.x.getValue());
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
        this.ref = "A-" + area.name + " P-" + String.valueOf(area.beams.size()+1);
    }

    @Override
    public Polygone getShape() {
        return shape;
    }
}
