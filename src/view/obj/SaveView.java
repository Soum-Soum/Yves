package view.obj;

import java.util.LinkedList;

public class SaveView {

    public LinkedList<ViewArea> areas;
    public LinkedList<ViewWindow> windows;
    public LinkedList<ViewBeam> beams;
    public Double montantDist;
    public Double montantWidth;
    public Double traverseWidth;

    public SaveView(){}

    public SaveView(LinkedList<ViewArea> areas, LinkedList<ViewWindow> windows, LinkedList<ViewBeam> beams, Double montantDist, Double montantWidth, Double traverseWidth) {
        this.areas = areas;
        this.windows = windows;
        this.beams = beams;
        this.montantDist = montantDist;
        this.montantWidth = montantWidth;
        this.traverseWidth = traverseWidth;
    }
}
