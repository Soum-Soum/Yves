package home;

import data.DATACONTAINER;
import math.Quadrilateral;
import math.Segment;
import math.Profile;
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

    public Montant[] substract(Quadrilateral intersection){
        Montant[] temp = new Montant[2];
        ShapeType topType =  setMontantType(this.top.goesUp(), intersection.top.goesUp());
        ShapeType buttomTYpe = setMontantType(intersection.buttom.goesUp(),this.buttom.goesUp());
        temp[0] = new Montant(new Segment(intersection.topLeft,this.topLeft), DATACONTAINER.MONTANTWITH,this.theta,true,topType); //TOP MONTANT
        temp[1] = new Montant(new Segment(this.buttomLeft, intersection.buttomLeft),DATACONTAINER.MONTANTWITH,0,true, buttomTYpe); //BUTTOM MONTANT
        return temp;
    }

    public ShapeType setMontantType(Profile top, Profile buttom){
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

    @Override
    public int compareTo(Montant o) {
        if (this.buttomLeft.x<o.buttomLeft.x){return -1;}
        else if(this.buttomLeft.x==o.buttomLeft.x){return 0;}
        else {return 1;}
    }
}
