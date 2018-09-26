package main;

import home.*;
import javafx.collections.ObservableList;
import math.*;
import view.obj.ViewArea;
import view.obj.ViewBeam;
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
        areas = new ArrayList<>();
        for (ViewArea viewArea : list) {
            Point butomLeft = new Point(Double.parseDouble(viewArea.x.getValue()), Double.parseDouble(viewArea.y.getValue()));
            Segment segment = new Segment(butomLeft, new Point(butomLeft, 0, Double.parseDouble(viewArea.height.getValue())));
            if (!viewArea.type.getValue().equals("PENTAGON")) {
                Quadrilateral q = Quadrilateral.getNormalMontant(segment, Double.parseDouble(viewArea.wight.getValue()), viewArea.theta, viewArea.isOnRightSide, ShapeType.valueOf(viewArea.type.getValue()));
                areas.add(new QuadrilateralArea(q, viewArea.name.getValue()));
            } else {
                Pentagon p = new Pentagon(butomLeft, Double.parseDouble(viewArea.height.getValue()), Double.parseDouble(viewArea.wight.getValue()), viewArea.theta, Double.parseDouble(viewArea.faitageValue.getValue()));
                areas.add(new PentagonalArea(p, viewArea.name.getValue()));
            }
        }
        initArea();
    }

    public void generateBeam(ObservableList<ViewBeam> list){
        for (ViewBeam beam : list){
            double x = Double.parseDouble(beam.x.getValue());
            Area temp = getAreaByName(beam.dependance.getValue());
            if (temp!=null && x>temp.getShape().buttomLeft.x && x<temp.getShape().buttomRight.x){
                Beam b = new Beam(temp, beam);
                b.print();
                temp.beams.add(b);
                beams.add(b);
            }else {
                System.out.println("Bad Beam");
            }
        }
    }

    public void generateWindows(ObservableList<ViewWindow> list){
        windows = new ArrayList<>();
        for (ViewWindow viewWindow: list) {
            Point butomLeft = new Point(Double.parseDouble(viewWindow.x.getValue()), Double.parseDouble(viewWindow.y.getValue()));
            Segment segment = new Segment(butomLeft, new Point(butomLeft, 0, Double.parseDouble(viewWindow.height.getValue())));
            Window w = new Window(segment, Double.parseDouble(viewWindow.wight.getValue()), viewWindow.theta, viewWindow.isOnRightSide, ShapeType.valueOf(viewWindow.type.getValue()));
            windows.add(w);
            findAreaForWindow(w);
        }
    }

    private void findAreaForWindow(Window w){
        int i=0;
        while (i<areas.size() && !areas.get(i).getInerShape().shapIsInside(w)){
            i++;
        }
        if (i == areas.size()){
            System.out.println("Bad Windows");
        }else {
            areas.get(i).windows.add(w);
        }
    }

    public void initArea(){
        for (Area area :areas){area.setOutLines();}
    }

    public Area getAreaByName(String name){
        for (Area area : areas){
            if (name.equals(area.name)){return area;}
        }
        return null;
    }
}
