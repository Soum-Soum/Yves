package home;

import data.DATACONTAINER;
import math.Point;
import math.Quadrilateral;
import math.ShapeType;
import math.Segment;

import java.lang.reflect.Array;
import java.util.*;

public class Window extends Quadrilateral {

    public Hashtable <String, Montant> montantsBeforCut;
    public Hashtable <String, LinkedList<Montant>> montantsAfterCut;
    public Quadrilateral outLines;
    public String name;
    public boolean haveTraverse = false;
    public Quadrilateral traverse;

    public Window(Segment segment, double width, boolean isOnRightSide, ShapeType type, String name,double thetaTop, double thetaButtom) {
        super(segment, width, isOnRightSide, type, thetaTop, thetaButtom);
        montantsAfterCut = new Hashtable<>();
        montantsBeforCut = new Hashtable<>();
        this.name=name;
    }

    public void generateTraverse(){
        haveTraverse=true;
        if (this.type == ShapeType.TRAPEZIUM1 || this.type == ShapeType.TRAPEZIUM2){
            this.traverse = new Montant(new Segment(montantsBeforCut.get("topMontant").topLeft,montantsBeforCut.get("topMontant").topRight), DATACONTAINER.TRAVERSEWIDTH,false,ShapeType.PARALLELOGRAM1);
        }else {
            this.traverse = new Montant(new Segment(montantsBeforCut.get("topMontant").topLeft,montantsBeforCut.get("topMontant").topRight),DATACONTAINER.TRAVERSEWIDTH,false,ShapeType.RECTANGLE, 0,0);
        }
        this.outLines = new Quadrilateral(montantsBeforCut.get("buttomMontant").buttomLeft,montantsBeforCut.get("buttomMontant").buttomRight,this.traverse.topLeft,this.traverse.topRight,this.type,this.thetaTop, this.thetaButtom);
    }

}
