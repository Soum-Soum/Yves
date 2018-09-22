package math;

public class Pentagon extends Polygone {
    Point buttomRight, buttomLeft, mediumRight, mediumLeft, top;
    Segment buttom, left, right, topLeft, topRight;
    double faitageValue;

    public Pentagon(Point buttomRight, Point buttomLeft, Point mediumRight, Point mediumLeft, Point top) {
        this.buttomRight = buttomRight;
        this.buttomLeft = buttomLeft;
        this.mediumRight = mediumRight;
        this.mediumLeft = mediumLeft;
        this.top = top;
        setSegments();
    }

    private void setSegments(){
        buttom = new Segment(buttomLeft,buttomRight);
        left= new Segment(buttomLeft,mediumLeft);
        right = new Segment(buttomRight,mediumLeft);
        topLeft=new Segment(mediumLeft,top);
        topRight=new Segment(mediumRight,top);
    }

    @Override
    public void print() {
        System.out.println("butomLeft : " + buttomLeft.print());
        System.out.println("mediumLeft : " + mediumLeft.print());
        System.out.println("buttomRight : " + buttomRight.print());
        System.out.println("mediumRight : " + mediumRight.print());
        System.out.println("top : " + top.print());
    }

    @Override
    public Segment getVerticalSegment(double x) {
        Line topLine;
        if(x> faitageValue*buttom.getLenght()){
            topLine = new Line(topRight);
        }else{
            topLine = new Line(topLeft);
        }
        Line buttomLine = new Line(buttom);
        Line horizontalLine = new Line(new Segment(x,0, x+1,0));
        return new Segment( horizontalLine.intersect(buttomLine), horizontalLine.intersect(topLine));
    }

}
