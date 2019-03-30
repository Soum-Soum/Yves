package home;

import com.sun.istack.internal.Nullable;
import data.DATACONTAINER;
import math.*;
import util.Utilies;

import java.util.LinkedList;
import java.util.List;

public class Montant extends Quadrilateral implements Comparable<Montant>{

    public int number;
    public double topSum=-1, buttomSum=-1;
    public boolean numberWritable = true;

    public Montant(Segment segment, double width, boolean isOnRightSide, ShapeType type, double thetaTop, double thetaButtom) {
        super(segment, width, isOnRightSide, type, thetaTop, thetaButtom);
        this.number = DATACONTAINER.MONTANTCOUNT;
        DATACONTAINER.MONTANTCOUNT+=1;
    }

    public Montant(Segment segment, double width, boolean isOnRightSide, ShapeType type) {
        super(segment, width, isOnRightSide, type);
        this.number = DATACONTAINER.MONTANTCOUNT;
        DATACONTAINER.MONTANTCOUNT+=1;
    }

    public static Montant getNormalMontant(Segment segment, double width, boolean isOnRightSide, ShapeType type, double thetaTop, double thetaButtom){
        return new Montant(segment, width, isOnRightSide, type , thetaTop, thetaButtom);
    }
    public static Montant getParalelMontant(Segment segment, double width, boolean isOnRightSide, ShapeType type){
        return new Montant(segment, width, isOnRightSide, type);
    }

    public Montant[] substract(Intersection intersection){
        Montant[] temp = new Montant[2];
        ShapeType topType = setMontantType(this.top.goesUp(), intersection.intersection.top.goesUp());
        ShapeType buttomTYpe = setMontantType(intersection.intersection.buttom.goesUp(),this.buttom.goesUp());
        temp[0] = new Montant(new Segment(intersection.intersection.topLeft,this.topLeft), DATACONTAINER.MONTANTWITH,true,topType, this.thetaTop, intersection.window.thetaTop); //TOP MONTANT
        temp[0].topSum =this.topSum;
        temp[0].buttomSum = -1;
        temp[0].numberWritable=this.numberWritable;
        temp[1] = new Montant(new Segment(this.buttomLeft, intersection.intersection.buttomLeft),DATACONTAINER.MONTANTWITH,true, buttomTYpe, intersection.window.thetaButtom, this.thetaTop); //BUTTOM MONTANT
        temp[1].topSum = -1;
        temp[1].buttomSum = buttomSum;
        temp[1].numberWritable=this.numberWritable;
        return temp;
    }

    public static ShapeType setMontantType(Profile top, Profile buttom){
        if (top == Profile.HORIZONTAL){
            if (buttom == Profile.HORIZONTAL){
                return ShapeType.RECTANGLE;
            }else  if (buttom == Profile.GOES_UP){
                return ShapeType.TRAPEZIUM4;
            }else if (buttom == Profile.GOES_DOWN){
                return ShapeType.TRAPEZIUM3;
            }
        }else  if (top == Profile.GOES_UP){
            if (buttom == Profile.HORIZONTAL){
                return ShapeType.TRAPEZIUM1;
            }else  if (buttom == Profile.GOES_UP){
                return ShapeType.PARALLELOGRAM1;
            }else if (buttom == Profile.GOES_DOWN){
                return ShapeType.REGULARTRAPEZIUMRIGHT;
            }
        }else if (top == Profile.GOES_DOWN){
            if (buttom == Profile.HORIZONTAL){
                return ShapeType.TRAPEZIUM2;
            }else  if (buttom == Profile.GOES_UP){
                return ShapeType.REGULARTRAPEZIUMLEFT;
            }else if (buttom == Profile.GOES_DOWN){
                return ShapeType.PARALLELOGRAM2;
            }
        }
        return null;
    }

    public boolean isUnderBeam(List<Beam> list , List<Window> list2){
        return  this.buttomLeft.isUnderObstacle(list,list2) && this.buttomRight.isUnderObstacle(list,list2);
    }

    public LinkedList<Montant> montant2List(){
        LinkedList tempList = new LinkedList();
        tempList.add(this);
        return tempList;
    }

    public String printMontant(){
        String str ="";
        double temp[] = this.getWidthHMinHMax();
        str += "Numero : " + this.number;
        if (temp[0]==temp[1]){
            str+= "\tLargeur : " + temp[0];
        }else{
            str += "\tLmin : " + temp[0] + "\tLmax : " + temp[1];
        }
        if (temp[2]==temp[3]){
            str+= "\tHauteur : " + temp[2];
        }else{
            str += "\tHmin : " + temp[2] + "\tHmax : " + temp[3];
        }
        if (this.topSum!=-1){
            str += "\tCumul_Haut : " + this.topSum;
        }
        if (this.buttomSum!=-1){
            str += "\tCumul_Bas : " + this.buttomSum;
        }
        if (this.thetaButtom != 0){
            str += "\tAngle_Bas/Droite : " + Utilies.round3((this.thetaButtom/(Math.PI*2))*360);
        }
        if (this.thetaTop != 0){
            str += "\tAngle_Haut/Gauche : " + Utilies.round3((this.thetaTop/(Math.PI*2))*360);
        }
        return str;
    }

    @Override
    public int compareTo(Montant o) {
        if (this.buttomLeft.x<o.buttomLeft.x){return -1;}
        else if(this.buttomLeft.x==o.buttomLeft.x){return 0;}
        else {return 1;}
    }
}
