package home;

import data.DATACONTAINER;
import math.*;

import java.util.ArrayList;
import java.util.Arrays;

public class PentagonalArea extends Area{

    Pentagon shap, inerShape;

    public PentagonalArea(Pentagon shap) {
        windows = new ArrayList<>();
        beams = new ArrayList<>();
        montants = new  ArrayList<>();
        this.shap = shap;
    }

    @Override
    public void setOutLines() {
        Segment butomSeg = new Segment(shap.buttomLeft,new Point(shap.buttomLeft.x,shap.buttomLeft.y+DATACONTAINER.MONTANTWITH));
        Montant buttomMontant = Montant.getNormalMontant(butomSeg, shap.buttom.getLenght(),0,true, ShapeType.RECTANGLE);
        System.out.println("buttomMontant");
        buttomMontant.print();
        Montant topLeftMontant = Montant.getParalelMontant(shap.topLeft,DATACONTAINER.MONTANTWITH,true,ShapeType.PARAlLELOGRAM1);
        System.out.println("topleft");
        topLeftMontant.print();
        Montant topRightMontant = Montant.getParalelMontant(shap.topRight,DATACONTAINER.MONTANTWITH,true,ShapeType.PARAlLELOGRAM1);
        System.out.println("topright");
        topRightMontant.print();
        Segment leftSeg = new Segment(buttomMontant.topLeft,topLeftMontant.buttomLeft);
        Segment rightSeg = new Segment(buttomMontant.topRight,topRightMontant.buttomRight);
        Montant leftMontant = Montant.getNormalMontant(leftSeg,DATACONTAINER.MONTANTWITH,shap.thetaLeft,true,ShapeType.TRAPEZIUM1);
        System.out.println("left");
        leftMontant.print();
        Montant rightMontant = Montant.getNormalMontant(rightSeg,DATACONTAINER.MONTANTWITH,shap.thetaRight,false,ShapeType.TRAPEZIUM2);
        System.out.println("right");
        rightMontant.print();
        inerShape = new Pentagon(rightMontant.buttomLeft, leftMontant.buttomRight, rightMontant.topLeft, leftMontant.topRight, topLeftMontant.buttomRight);
        inerShape.print();
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
        PentagonalArea p = new PentagonalArea(pentagon);
        p.setOutLines();
    }
}
