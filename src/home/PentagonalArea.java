package home;

import data.DATACONTAINER;
import math.*;

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
        Montant buttomMontant = Montant.getNormalMontant(shap.buttom, DATACONTAINER.MONTANTWITH,0,false, ShapeType.RECTANGLE);
        Montant topLeftMontant = Montant.getParalelMontant(shap.topLeft,DATACONTAINER.MONTANTWITH,true,ShapeType.PARAlLELOGRAM1);
        Montant topRightMontant = Montant.getParalelMontant(shap.topRight,DATACONTAINER.MONTANTWITH,true,ShapeType.PARAlLELOGRAM1);
        Segment leftSeg = new Segment(buttomMontant.topLeft,topLeftMontant.buttomLeft);
        Segment rightSeg = new Segment(buttomMontant.topRight,topRightMontant.buttomRight);
        Montant leftMontant = Montant.getNormalMontant(leftSeg,DATACONTAINER.MONTANTWITH,shap.thetaLeft,true,ShapeType.TRAPEZIUM1);
        Montant rightMontant = Montant.getNormalMontant(rightSeg,DATACONTAINER.MONTANTWITH,shap.thetaRight,false,ShapeType.TRAPEZIUM2);
        inerShape = new Pentagon(rightMontant.buttomLeft, leftMontant.buttomRight, rightMontant.topLeft, leftMontant.topRight, topLeftMontant.buttomRight);
        montants.addAll(Arrays.asList(buttomMontant,topLeftMontant,topRightMontant,leftMontant,rightMontant));
    }

    @Override
    public Polygone getShape() {
        return shap;
    }

    @Override
    public Polygone getInerShape() {
        return inerShape;
    }

    public static void main(String[] args){
        Pentagon pentagon = new Pentagon(new Point(1,1),20,100,Math.PI/4,0.5);
        pentagon.print();
        PentagonalArea p = new PentagonalArea(pentagon,"lol");
        p.setOutLines();
    }
}
