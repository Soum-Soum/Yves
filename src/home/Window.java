package home;

import math.Quadrilateral;
import math.ShapeType;
import math.Segment;

public class Window extends Quadrilateral {

    public Window(Segment segment, double width, double theta, boolean isOnRightSide, ShapeType type) {
        super(segment, width, theta, isOnRightSide, type);
    }
}
