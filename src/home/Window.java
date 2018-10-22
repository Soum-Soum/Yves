package home;

import data.DATACONTAINER;
import math.Point;
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
    public boolean haveTraverse = false;
    public Quadrilateral traverse;

    public Window(Segment segment, double width, boolean isOnRightSide, ShapeType type, String name,double thetaTop, double thetaButtom) {
        super(segment, width, isOnRightSide, type, thetaTop, thetaButtom);
        montants = new ArrayList<>();
        this.name=name;
    }

    public void generateTraverse(){
        haveTraverse=true;
        if (this.type == ShapeType.TRAPEZIUM1 || this.type == ShapeType.TRAPEZIUM2){
            this.traverse = new Montant(new Segment(this.topMontant.topLeft,this.topMontant.topRight), DATACONTAINER.TRAVERSEWIDTH,false,ShapeType.PARALLELOGRAM1);
        }else {
            this.traverse = new Montant(new Segment(this.topMontant.topLeft,this.topMontant.topRight),DATACONTAINER.TRAVERSEWIDTH,false,ShapeType.RECTANGLE, 0,0);
        }
        this.outLines = new Quadrilateral(this.buttomMontant.buttomLeft,this.buttomMontant.buttomRight,this.traverse.topLeft,this.traverse.topRight,this.type,this.thetaTop, this.thetaButtom);
        /*this.outLines = new Quadrilateral(new Point(this.buttomMontant.buttomLeft,-DATACONTAINER.MONTANTWITH,0),new Point(this.buttomMontant.buttomRight,DATACONTAINER.MONTANTWITH,0),
                this.traverse.topLeft,this.traverse.topRight,this.type,this.thetaTop, this.thetaButtom);*/
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
