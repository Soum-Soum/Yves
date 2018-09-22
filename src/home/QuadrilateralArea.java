package home;

import data.DATACONTAINER;
import math.Quadrilateral;
import math.Segment;
import math.ShapeType;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class QuadrilateralArea extends Area {

    Quadrilateral shap, inerShape;

    public QuadrilateralArea(Quadrilateral shap) {
        windows = new ArrayList<>();
        beams = new ArrayList<>();
        montants = new  ArrayList<>();
        this.shap = shap;
    }


    @Override
    public void setOutLines() {
        Montant buttomMontant = Montant.getNormalMontant(shap.buttom,DATACONTAINER.MONTANTWITH, 0,false, ShapeType.RECTANGLE);
        Montant topMontant = Montant.getParalelMontant(shap.top,DATACONTAINER.MONTANTWITH,true,ShapeType.PARAlLELOGRAM1);
        Segment leftSeg = new Segment(shap.buttomLeft.x,shap.buttomLeft.y+DATACONTAINER.MONTANTWITH,shap.buttomLeft.x,topMontant.buttomLeft.y);
        Montant leftMontant = Montant.getNormalMontant(leftSeg,DATACONTAINER.MONTANTWITH,shap.theta,true,shap.type);
        Segment rightSeg = new Segment(shap.buttomRight.x,shap.buttomRight.y+DATACONTAINER.MONTANTWITH,shap.buttomRight.x,topMontant.buttomRight.y);
        Montant rightMontant = Montant.getNormalMontant(rightSeg,DATACONTAINER.MONTANTWITH,shap.theta,false,shap.type);
        inerShape = new Quadrilateral(leftMontant.buttomRight,rightMontant.buttomLeft,leftMontant.topRight,rightMontant.topLeft, shap.type);
        montants.addAll(Arrays.asList(buttomMontant, leftMontant, rightMontant, topMontant));
    }
}
