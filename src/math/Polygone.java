package math;

import java.util.ArrayList;

public abstract class Polygone {

    public abstract void print();

    public abstract Segment getVerticalSegment(double x);

    public abstract ArrayList<Segment> getSegments();
}
