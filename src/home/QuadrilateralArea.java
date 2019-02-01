package home;

import data.DATACONTAINER;
import math.*;
import util.Utilies;


import java.util.Arrays;

public class QuadrilateralArea extends Area {

    Quadrilateral shap, inerShape;

    public QuadrilateralArea(Quadrilateral shap, String name) {
        super();
        this.shap = shap;
        this.name = name;
    }


    @Override
    public void setOutLines() {
        if(this.shap.type == ShapeType.TRAPEZIUM1 || this.shap.type == ShapeType.TRAPEZIUM2){
            outlinesMontants.put("buttomMontant",Montant.getNormalMontant(shap.buttom,DATACONTAINER.MONTANTWITH, false, ShapeType.RECTANGLE,0,0));
            outlinesMontants.put("topMontant",Montant.getParalelMontant(shap.top,DATACONTAINER.MONTANTWITH,true,ShapeType.PARALLELOGRAM1));
        }else {
            outlinesMontants.put("topMontant",Montant.getNormalMontant(shap.top,DATACONTAINER.MONTANTWITH, true, ShapeType.RECTANGLE,0,0));
            outlinesMontants.put("buttomMontant",Montant.getParalelMontant(shap.buttom,DATACONTAINER.MONTANTWITH,false,ShapeType.PARALLELOGRAM1));
        }
        Segment leftSeg = new Segment(shap.buttomLeft.x,shap.buttomLeft.y+DATACONTAINER.MONTANTWITH,shap.buttomLeft.x,outlinesMontants.get("topMontant").buttomLeft.y);
        outlinesMontants.put("leftMontant",new Montant(leftSeg,DATACONTAINER.MONTANTWITH,true,shap.type,shap.thetaTop,shap.thetaButtom));
        Segment rightSeg = new Segment(shap.buttomRight.x,shap.buttomRight.y+DATACONTAINER.MONTANTWITH,shap.buttomRight.x,outlinesMontants.get("topMontant").buttomRight.y);
        outlinesMontants.put("rightMontant",new Montant(rightSeg,DATACONTAINER.MONTANTWITH,false,shap.type,shap.thetaTop, shap.thetaButtom));
        inerShape = new Quadrilateral(outlinesMontants.get("leftMontant").buttomRight,outlinesMontants.get("rightMontant").buttomLeft,
                outlinesMontants.get("leftMontant").topRight,outlinesMontants.get("rightMontant").topLeft, shap.type, this.shap.thetaTop, shap.thetaButtom);
    }

    @Override
    public Polygone getShape() {
        return this.shap;
    }

    @Override
    public Polygone getInerShape() {
        return inerShape;
    }

    @Override
    public void setSums(Montant m) {
        m.buttomSum = Utilies.round3(m.getMinX());
        m.topSum = Utilies.round3(new  Segment(this.shap.topLeft,new Line(new Segment(m.getMinX(),0,m.getMinX(),1)).intersect(new Line(this.shap.top))).getLenght());
    }

    public static void main(String[] args){
        Quadrilateral shap = new Quadrilateral(new Segment(1,1,1,10),100,true,ShapeType.TRAPEZIUM1 ,Math.PI/4,0);
        QuadrilateralArea test = new QuadrilateralArea(shap,"lol");
        test.shap.print();
        test.setOutLines();
        test.inerShape.print();
    }
}
