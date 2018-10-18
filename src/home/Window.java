package home;

import math.Quadrilateral;
import math.ShapeType;
import math.Segment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Window extends Quadrilateral {

    public ArrayList<Montant> montants;
    public Montant buttomMontant,topMontant,leftMontant,rightMontant,buttomLeftMontant,buttomRightMontant, midLeftMontant, midRightMontant;
    public Quadrilateral outLines;
    public String name, ref;

    public Window(Segment segment, double width, boolean isOnRightSide, ShapeType type, String name,double thetaTop, double thetaButtom) {
        super(segment, width, isOnRightSide, type, thetaTop, thetaButtom);
        montants = new ArrayList<>();
        this.name=name;
    }

    public void setRefMontants(){
        this.leftMontant.ref = this.ref + " M-01";
        this.midLeftMontant.ref = this.ref + " M-02";
        this.buttomLeftMontant.ref = this.ref + " M-03";
        this.buttomMontant.ref = this.ref + " M-04";
        this.topMontant.ref = this.ref + " M-05";
        this.midRightMontant.ref = this.ref + " M-06";
        this.rightMontant.ref = this.ref + " M-07";
    }
}
