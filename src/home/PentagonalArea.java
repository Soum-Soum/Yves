package home;

import data.DATACONTAINER;
import math.*;
import util.Utilies;

import java.util.Arrays;

public class PentagonalArea extends Area{

    Pentagon shap, inerShape;

    public PentagonalArea(Pentagon shap, String name) {
        super();
        this.shap = shap;
        this.name = name;
    }

    @Override
    public void setOutLines() {
        outlinesMontants.put("buttomMontant",Montant.getNormalMontant(shap.buttom, DATACONTAINER.MONTANTWITH,false, ShapeType.RECTANGLE,0,0));
        outlinesMontants.put("topLeftMontant",Montant.getParalelMontant(shap.topLeft,DATACONTAINER.MONTANTWITH,true,ShapeType.PARALLELOGRAM1));
        outlinesMontants.put("topRightMontant",Montant.getParalelMontant(shap.topRight,DATACONTAINER.MONTANTWITH,true,ShapeType.PARALLELOGRAM1));
        Segment leftSeg = new Segment(outlinesMontants.get("buttomMontant").topLeft,outlinesMontants.get("topLeftMontant").buttomLeft);
        Segment rightSeg = new Segment(outlinesMontants.get("buttomMontant").topRight,outlinesMontants.get("topRightMontant").buttomRight);
        outlinesMontants.put("leftMontant",Montant.getNormalMontant(leftSeg,DATACONTAINER.MONTANTWITH,true,ShapeType.TRAPEZIUM1,shap.thetaLeft,0));
        outlinesMontants.put("rightMontant",Montant.getNormalMontant(rightSeg,DATACONTAINER.MONTANTWITH,false,ShapeType.TRAPEZIUM2,shap.thetaRight,0));
        inerShape = new Pentagon(outlinesMontants.get("rightMontant").buttomLeft, outlinesMontants.get("leftMontant").buttomRight, outlinesMontants.get("rightMontant").topLeft,
                outlinesMontants.get("leftMontant").topRight, outlinesMontants.get("topLeftMontant").buttomRight,this.shap.faitageValue,this.shap.thetaLeft,this.shap.thetaRight);
    }

    @Override
    public Polygone getShape() {
        return shap;
    }

    @Override
    public Polygone getInerShape() {
        return inerShape;
    }

    @Override
    public void setSums(Montant m, Boolean justBottom) {
        m.buttomSum = Utilies.round3(m.getMinX());
        if(!justBottom){
            if (m.getMinX()<this.shap.top.x){
                m.topSum = Utilies.round3(new Segment(this.shap.mediumLeft,new Line(new Segment(m.getMinX(),0,m.getMinX(),1)).intersect(new Line(this.shap.topLeft))).getLenght());
            }else {
                m.topSum = Utilies.round3(this.shap.topLeft.getLenght() + new Segment(this.shap.top,new Line(new Segment(m.getMinX(),0,m.getMinX(),1)).intersect(new Line(this.shap.topRight))).getLenght());
            }
        }
    }

    public static void main(String[] args){
        Pentagon pentagon = new Pentagon(new Point(1,1),20,100,Math.PI/4,0.5,true);
        pentagon.print();
        PentagonalArea p = new PentagonalArea(pentagon,"lol");
        p.setOutLines();
    }
}
