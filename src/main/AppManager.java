package main;

import file.FileWriter;
import home.*;
import img.ImageDrawer;
import javafx.collections.ObservableList;
import math.*;
import view.controler.FinalControler;
import view.obj.ViewArea;
import view.obj.ViewBeam;
import view.obj.ViewWindow;

import java.io.IOException;
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
            Quadrilateral q;
            if (!viewArea.type.getValue().equals("PENTAGON")) {
                if(viewArea.type.getValue() == "TRAPEZIUM3" || viewArea.type.getValue() == "TRAPEZIUM4"){
                    q = new Quadrilateral(segment, Double.parseDouble(viewArea.wight.getValue()), viewArea.isOnRightSide, ShapeType.valueOf(viewArea.type.getValue()), 0, viewArea.theta);
                }else {
                    q = new Quadrilateral(segment, Double.parseDouble(viewArea.wight.getValue()), viewArea.isOnRightSide, ShapeType.valueOf(viewArea.type.getValue()), viewArea.theta, 0);
                }
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
                Beam b = Beam.BuildBeam(temp, beam);
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
            Window w = new Window(segment, Double.parseDouble(viewWindow.wight.getValue()), viewWindow.isOnRightSide, ShapeType.valueOf(viewWindow.type.getValue()),viewWindow.name.getValue(), viewWindow.theta,0);
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
            w.ref = "A- " + areas.get(i).name + " O- 0" + String.valueOf(areas.get(i).windows.size()+1) + " N- "+ w.name;
        }
    }

    public void initArea(){
        for (Area area :areas){area.setOutLines();}
    }

    public void buildMontants(){
        for(Area area : areas){
            area.setWindowsMontants();
            area.setBeamMontants();
            area.setVerticalMontant();
            area.generateMidMontant();
            FileWriter fileWriter = new FileWriter();
            try {
                fileWriter.writeFile(areas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateImage(FinalControler controler){
        ImageDrawer imageDrawer = new ImageDrawer((int)(getAreaMaxX()+200),(int)(getAreaMaxY()+200));
        for (Area area : areas){
            imageDrawer.drawArea(area);
        }
        String path = imageDrawer.saveIMG("Generated_Img");
        controler.upgradeImg(path);
    }

    public Area getAreaByName(String name){
        for (Area area : areas){
            if (name.equals(area.name)){return area;}
        }
        return null;
    }

    public double getAreaMaxX(){
        double max=0;
        for (Area area : areas){
            if (area.getShape().buttomRight.x>max){
                max = area.getShape().buttomRight.x;
            }
        }
        return max;
    }

    public double getAreaMaxY(){
        double max=0;
        for (Area area : areas){
            if (area.getShape().getTopPoint().y>max){
                max = area.getShape().getTopPoint().y;
                System.out.println(max);
            }
        }
        return max;
    }
}
