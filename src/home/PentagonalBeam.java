package home;

import math.*;
import view.obj.ViewBeam;

import java.util.ArrayList;

public class PentagonalBeam extends Beam {
    public Pentagon shape;

    public PentagonalBeam(Point buttomLeft, double width, double height ,double theta, double faitageValue, String ref) {
        this.shape = new Pentagon(buttomLeft, width, height, theta, faitageValue );
        montants = new ArrayList<>();
        this.ref=ref;
    }

    public PentagonalBeam(Area area, ViewBeam viewBeam){
        montants = new ArrayList<>();
        double x = Double.parseDouble(viewBeam.x.getValue());
        double width = Double.parseDouble(viewBeam.wight.getValue());
        double height = Double.parseDouble(viewBeam.height.getValue());
        this.shape=new Pentagon();
        shape.mediumLeft = new Point(x,area.getInerShape().getHeight(x));
        shape.mediumRight = new Point(x+width,area.getInerShape().getHeight(x+width));
        shape.top=area.getInerShape().getTopPoint();
        if (shape.mediumLeft.y<shape.mediumRight.y){
            shape.buttomRight = new Point(shape.mediumRight.x,shape.mediumRight.y-height);
            shape.buttomLeft = new Point(shape.mediumLeft.x,shape.buttomRight.y);
        }else {
            shape.buttomLeft = new Point(shape.mediumLeft.x,shape.mediumLeft.y-height);
            shape.buttomRight = new Point(shape.mediumRight.x,shape.buttomLeft.y);
        }
        shape.thetaLeft=area.getShape().getTheta(shape.buttomLeft.x);
        shape.thetaRight=area.getShape().getTheta(shape.buttomRight.x);
        this.shape.setSegments();
        this.ref = "A- " + area.name + " P- 0" + String.valueOf(area.beams.size()+1);
    }

    @Override
    public Polygone getShape() {
        return shape;
    }
}
