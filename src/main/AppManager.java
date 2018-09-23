package main;

import home.Area;
import home.Beam;
import home.QuadrilateralArea;
import home.Window;
import javafx.collections.ObservableList;
import math.Point;
import math.Quadrilateral;
import math.Segment;
import math.ShapeType;
import view.obj.ViewArea;

import java.util.ArrayList;

public class AppManager {

    ArrayList<Area> areas;
    ArrayList<Window> windows;
    ArrayList<Beam> beams;

    public AppManager() {
        areas=new ArrayList<>();
        windows=new ArrayList<>();
        beams=new ArrayList<>();
    }

    public void generateAreas(ObservableList<ViewArea> list){
        //public Quadrilateral(Segment segment, double width, double theta ,boolean isOnRightSide, ShapeType type ){
        for (ViewArea viewArea : list){
            Point butomLeft = new Point(Double.parseDouble(viewArea.x.getValue()),Double.parseDouble(viewArea.y.getValue()));
            Segment segment = new Segment( butomLeft, new Point(butomLeft,0,Double.parseDouble(viewArea.height.getValue())));
            Quadrilateral q;
            q = new Quadrilateral(segment,Double.parseDouble(viewArea.wight.getValue()),viewArea.theta,true, ShapeType.valueOf(viewArea.type.getValue()));
            areas.add(new QuadrilateralArea(q));
        }
    }
}
