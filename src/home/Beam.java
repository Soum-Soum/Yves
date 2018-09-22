package home;

import math.Quadrilateral;
import math.Segment;
import math.ShapeType;

public class Beam extends Quadrilateral {

    public Beam(Segment segment, double width, double theta, boolean isOnRightSide, ShapeType type) {
        super(segment, width, theta, isOnRightSide, type);
    }

}
