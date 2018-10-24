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
        Montant buttomMontant = Montant.getNormalMontant(shap.buttom, DATACONTAINER.MONTANTWITH,false, ShapeType.RECTANGLE,0,0);
        buttomMontant.ref = "A- " + this.name + " C- Montant Bas";
        Montant topLeftMontant = Montant.getParalelMontant(shap.topLeft,DATACONTAINER.MONTANTWITH,true,ShapeType.PARALLELOGRAM1);
        topLeftMontant.ref = "A- " + this.name + " C- Montant Haut Gauche";
        Montant topRightMontant = Montant.getParalelMontant(shap.topRight,DATACONTAINER.MONTANTWITH,true,ShapeType.PARALLELOGRAM1);
        topRightMontant.ref = "A- " + this.name + " C- Montant Haut Droit";
        Segment leftSeg = new Segment(buttomMontant.topLeft,topLeftMontant.buttomLeft);
        Segment rightSeg = new Segment(buttomMontant.topRight,topRightMontant.buttomRight);
        Montant leftMontant = Montant.getNormalMontant(leftSeg,DATACONTAINER.MONTANTWITH,true,ShapeType.TRAPEZIUM1,shap.thetaLeft,0);
        leftMontant.ref="A- " + this.name + " C- Montant Gauche";
        Montant rightMontant = Montant.getNormalMontant(rightSeg,DATACONTAINER.MONTANTWITH,false,ShapeType.TRAPEZIUM2,shap.thetaRight,0);
        rightMontant.ref="A- " + this.name + " C- Montant Droite";
        inerShape = new Pentagon(rightMontant.buttomLeft, leftMontant.buttomRight, rightMontant.topLeft, leftMontant.topRight, topLeftMontant.buttomRight,this.shap.faitageValue,this.shap.thetaLeft,this.shap.thetaRight);
        outlinesMontants.addAll(Arrays.asList(buttomMontant,topLeftMontant,topRightMontant,leftMontant,rightMontant));
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
