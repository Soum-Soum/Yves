package home;

import math.*;
import view.obj.ViewBeam;

import java.util.ArrayList;

public abstract class Beam{

    public ArrayList<Montant> montants;
    public String ref;

    public static Beam BuildBeam(Area area, ViewBeam viewBeam){
        double x = Double.parseDouble(viewBeam.x.getValue());
        double width = Double.parseDouble(viewBeam.wight.getValue());
        if (x < area.getInerShape().getTopPoint().x && x+width > area.getInerShape().getTopPoint().x){
            return new PentagonalBeam(area,viewBeam);
        }else {
            return new QuadrialteralBeam(area,viewBeam);
        }
    }

    public abstract Polygone getShape();

}
