package home;

import data.DATACONTAINER;
import math.Quadrilateral;
import math.Segment;
import math.SegmentProfile;
import math.ShapeType;

public class Montant extends Quadrilateral implements Comparable<Montant>{

    public Montant(Segment segment, double width, double theta, boolean isOnRightSide, ShapeType type) {
        super(segment, width, theta, isOnRightSide, type);
    }

    public Montant(Segment segment, double width, boolean isOnRightSide, ShapeType type) {
        super(segment, width, isOnRightSide, type);
    }

    public static Montant getNormalMontant(Segment segment, double width, double theta, boolean isOnRightSide, ShapeType type){
        return new Montant(segment, width, theta,  isOnRightSide, type);
    }
    public static Montant getParalelMontant(Segment segment, double width, boolean isOnRightSide, ShapeType type){
        return new Montant(segment, width, isOnRightSide, type);
    }

    public boolean isVertical(){
        if (this.buttom.getLenght()<this.left.getLenght()){
            return  true;
        }
        return false;
    }
    public boolean isHorizontal(){
        if (this.buttom.getLenght()>this.left.getLenght()){
            return  true;
        }
        return false;
    }

    public Montant[] divide(Quadrilateral intersection){
        Montant[] temp = new Montant[2];
        System.out.println(setMontantType(this.top.goesUp(), intersection.top.goesUp()));
        ShapeType topType =  setMontantType(this.top.goesUp(), intersection.top.goesUp());
        ShapeType buttomTYpe = setMontantType(intersection.buttom.goesUp(),this.buttom.goesUp());
        if (topType != ShapeType.PARAlLELOGRAM1 && topType!=ShapeType.PARALlELOGRAM2){
            temp[0] = new Montant(new Segment(intersection.topLeft,this.topLeft), DATACONTAINER.MONTANTWITH,this.theta,true,topType); //TOP MONTANT
        }else {
            temp[0] = new Montant(new Segment(intersection.topLeft,this.topLeft), DATACONTAINER.MONTANTWITH,this.theta,true,topType);
        }

        temp[1] = new Montant(new Segment(this.buttomLeft, intersection.buttomLeft),DATACONTAINER.MONTANTWITH,0,true, buttomTYpe); //BUTTOM MONTANT
        return temp;
    }

    public ShapeType setMontantType(SegmentProfile top, SegmentProfile buttom){
        if (top == SegmentProfile.HORIZONTAL){
            if (buttom == SegmentProfile.HORIZONTAL){
                return ShapeType.RECTANGLE;
            }else  if (buttom == SegmentProfile.GOES_UP){
                return ShapeType.TRAPEZIUM4;
            }else if (buttom == SegmentProfile.GOES_DOWN){
                return ShapeType.TRAPEZIUM3;
            }
        }else  if (top == SegmentProfile.GOES_UP){
            if (buttom == SegmentProfile.HORIZONTAL){
                return ShapeType.TRAPEZIUM1;
            }else  if (buttom == SegmentProfile.GOES_UP){
                return ShapeType.PARAlLELOGRAM1;
            }else if (buttom == SegmentProfile.GOES_DOWN){
                return ShapeType.REGULARTRAPEZIUMRIGHT;
            }
        }else if (top == SegmentProfile.GOES_DOWN){
            if (buttom == SegmentProfile.HORIZONTAL){
                return ShapeType.TRAPEZIUM2;
            }else  if (buttom == SegmentProfile.GOES_UP){
                return ShapeType.REGULARTRAPEZIUMLEFT;
            }else if (buttom == SegmentProfile.GOES_DOWN){
                return ShapeType.PARAlLELOGRAM1;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Montant o) {
        if (this.buttomLeft.x<o.buttomLeft.x){return -1;}
        else if(this.buttomLeft.x==o.buttomLeft.x){return 0;}
        else {return 1;}
    }
}
