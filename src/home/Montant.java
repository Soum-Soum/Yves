package home;

import math.Quadrilateral;
import math.Segment;
import math.ShapeType;

public class Montant extends Quadrilateral{

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
}
