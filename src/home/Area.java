package home;

import data.DATACONTAINER;
import math.Polygone;
import math.Segment;
import math.ShapeType;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Area {

    public ArrayList<Window> windows;
    public ArrayList<Beam> beams;
    public ArrayList<Montant> montants;

    public abstract void setOutLines();

    public abstract Polygone getShape();

    public abstract Polygone getInerShape();

    public void setWindowsMontants(){
        for (Window w : windows){
            Montant buttomMontant = new Montant(w.buttom, DATACONTAINER.MONTANTWITH,0,true, ShapeType.RECTANGLE);
            Montant topMontant = new Montant(w.top,DATACONTAINER.MONTANTWITH,false,ShapeType.PARAlLELOGRAM1);
            Segment lol = this.getInerShape().getVerticalSegment(w.buttomLeft.x);
            Montant leftMontant = new Montant(this.getInerShape().getVerticalSegment(w.buttomLeft.x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.buttomLeft.x),
                    false,this.getInerShape().getType(w.buttomLeft.x));
            Montant rightMontant = new Montant(this.getInerShape().getVerticalSegment(w.buttomRight.x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.buttomRight.x),
                    true,this.getInerShape().getType(w.buttomRight.x));
            Montant buttomLeftMontant = new Montant(Segment.getVerticalSegment(w.buttomLeft.x,buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,0,
                    true,ShapeType.RECTANGLE);
            Montant buttomRightMontant = new Montant(Segment.getVerticalSegment(w.buttomRight.x,buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,0,
                    false,ShapeType.RECTANGLE);
            w.montants.addAll(Arrays.asList(buttomMontant,topMontant,leftMontant,rightMontant,buttomLeftMontant,buttomRightMontant));
        }
    }
}
