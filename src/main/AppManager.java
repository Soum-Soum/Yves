package main;

import home.*;
import javafx.collections.ObservableList;
import math.*;
import view.obj.ViewArea;
import view.obj.ViewWindow;

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

    public void generateAreas(ObservableList<ViewArea> list) {
        for (ViewArea viewArea : list) {
            Point butomLeft = new Point(Double.parseDouble(viewArea.x.getValue()), Double.parseDouble(viewArea.y.getValue()));
            Segment segment = new Segment(butomLeft, new Point(butomLeft, 0, Double.parseDouble(viewArea.height.getValue())));
            if (!viewArea.type.getValue().equals("PENTAGON")) {
                Quadrilateral q = Quadrilateral.getNormalMontant(segment, Double.parseDouble(viewArea.wight.getValue()), viewArea.theta, true, ShapeType.valueOf(viewArea.type.getValue()));
                areas.add(new QuadrilateralArea(q));
            } else {
                Pentagon p = new Pentagon(butomLeft, Double.parseDouble(viewArea.height.getValue()), Double.parseDouble(viewArea.wight.getValue()), viewArea.theta, Double.parseDouble(viewArea.faitageValue.getValue()));
                areas.add(new PentagonalArea(p));
            }
        }
    }

    public void generateWindows(ObservableList<ViewWindow> list){
        for (ViewWindow viewWindow: list) {

        }
    }
}
