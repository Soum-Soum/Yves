package home;

import math.*;
import view.obj.ViewBeam;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Beam{

    public LinkedList<Montant> leftMontant, rightMontant;
    public LinkedList<LinkedList<Montant>> midsMontants;
    public String ref;

    public static Beam BuildBeam(Area area, ViewBeam viewBeam){
        double x = area.getShape().getMinX() + Double.parseDouble(viewBeam.x.getValue());
        double width = Double.parseDouble(viewBeam.wight.getValue());
        if (area.getShape().isPentagon() && area.getShape().getMinX() + x < area.getInerShape().getTopPoint().x && area.getShape().getMinX() + x + width > area.getInerShape().getTopPoint().x){
            return new PentagonalBeam(area,viewBeam);
        }else {
            return new QuadrialteralBeam(area,viewBeam);
        }
    }

    public abstract Polygone getShape();

}
