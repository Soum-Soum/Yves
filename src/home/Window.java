package home;

import math.Quadrilateral;
import math.ShapeType;
import math.Segment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Window extends Quadrilateral {

    public ArrayList<Montant> montants;

    public Window(Segment segment, double width, double theta, boolean isOnRightSide, ShapeType type) {
        super(segment, width, theta, isOnRightSide, type);
        montants = new ArrayList<>();
    }
}
