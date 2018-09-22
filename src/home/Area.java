package home;

import math.Polygone;

import java.util.ArrayList;

public abstract class Area {

    ArrayList<Window> windows;
    ArrayList<Beam> beams;
    ArrayList<Montant> montants;

    public abstract void setOutLines();

    public abstract Polygone getShape();

    public abstract Polygone getInerShape();
}
