package home;

import data.DATACONTAINER;
import math.Polygone;
import math.Quadrilateral;
import math.Segment;
import math.ShapeType;


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
        Montant buttomMontant, topMontant;
        if(this.shap.type == ShapeType.TRAPEZIUM1 || this.shap.type == ShapeType.TRAPEZIUM2){
            buttomMontant = Montant.getNormalMontant(shap.buttom,DATACONTAINER.MONTANTWITH, 0,false, ShapeType.RECTANGLE);
            topMontant = Montant.getParalelMontant(shap.top,DATACONTAINER.MONTANTWITH,true,ShapeType.PARALLELOGRAM1);
        }else {
            topMontant = Montant.getNormalMontant(shap.top,DATACONTAINER.MONTANTWITH, 0,true, ShapeType.RECTANGLE);
            buttomMontant = Montant.getParalelMontant(shap.buttom,DATACONTAINER.MONTANTWITH,false,ShapeType.PARALLELOGRAM1);
        }
        Segment leftSeg = new Segment(shap.buttomLeft.x,shap.buttomLeft.y+DATACONTAINER.MONTANTWITH,shap.buttomLeft.x,topMontant.buttomLeft.y);
        Montant leftMontant = Montant.getNormalMontant(leftSeg,DATACONTAINER.MONTANTWITH,shap.theta,true,shap.type);
        Segment rightSeg = new Segment(shap.buttomRight.x,shap.buttomRight.y+DATACONTAINER.MONTANTWITH,shap.buttomRight.x,topMontant.buttomRight.y);
        Montant rightMontant = Montant.getNormalMontant(rightSeg,DATACONTAINER.MONTANTWITH,shap.theta,false,shap.type);
        inerShape = new Quadrilateral(leftMontant.buttomRight,rightMontant.buttomLeft,leftMontant.topRight,rightMontant.topLeft, shap.type, this.shap.theta);
        montants.addAll(Arrays.asList(buttomMontant, leftMontant, rightMontant, topMontant));
    }

    @Override
    public Polygone getShape() {
        return this.shap;
    }

    @Override
    public Polygone getInerShape() {
        return inerShape;
    }

    public static void main(String[] args){
        Quadrilateral shap = Quadrilateral.getNormalMontant(new Segment(1,1,1,10),100,Math.PI/4,true,ShapeType.TRAPEZIUM1);
        QuadrilateralArea test = new QuadrilateralArea(shap,"lol");
        test.shap.print();
        test.setOutLines();
        System.out.println("iner shape");
        test.inerShape.print();
    }
}
