package home;

import math.Quadrilateral;
import math.Segment;
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

    @Override
    public int compareTo(Montant o) {
        if (this.buttomLeft.x<o.buttomLeft.x){return -1;}
        else if(this.buttomLeft.x==o.buttomLeft.x){return 0;}
        else {return 1;}
    }
}
