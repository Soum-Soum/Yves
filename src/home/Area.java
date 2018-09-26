package home;

import data.DATACONTAINER;
import math.Polygone;
import math.Segment;
import math.ShapeType;

import java.util.*;

public abstract class Area {

    public ArrayList<Window> windows;
    public ArrayList<Beam> beams;
    public LinkedList<Montant> montants;
    public LinkedList<Montant> verticalMontant;
    public String name;

    public Area(){
        this.windows = new ArrayList<>();
        this.beams = new ArrayList<>();
        this.montants = new LinkedList<>();
        this.verticalMontant = new LinkedList<>();
    }

    public abstract void setOutLines();

    public abstract Polygone getShape();

    public abstract Polygone getInerShape();

    public void setWindowsMontants(){
        for (Window w : windows){
            Montant buttomMontant = new Montant(w.buttom, DATACONTAINER.MONTANTWITH,0,true, ShapeType.RECTANGLE);
            Montant topMontant = new Montant(w.top,DATACONTAINER.MONTANTWITH,false,ShapeType.PARAlLELOGRAM1);
            Montant leftMontant = new Montant(this.getInerShape().getVerticalSegment(w.buttomLeft.x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.buttomLeft.x),
                    false,this.getInerShape().getType(w.buttomLeft.x));
            Montant rightMontant = new Montant(this.getInerShape().getVerticalSegment(w.buttomRight.x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.buttomRight.x),
                    true,this.getInerShape().getType(w.buttomRight.x));
            Montant buttomLeftMontant = new Montant(Segment.getVerticalSegment(w.buttomLeft.x,buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,0,
                    true,ShapeType.RECTANGLE);
            Montant buttomRightMontant = new Montant(Segment.getVerticalSegment(w.buttomRight.x,buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,0,
                    false,ShapeType.RECTANGLE);
            w.montants.addAll(Arrays.asList(buttomMontant,topMontant,leftMontant,rightMontant,buttomLeftMontant,buttomRightMontant));
            this.montants.addAll(Arrays.asList(buttomMontant,topMontant,leftMontant,rightMontant,buttomLeftMontant,buttomRightMontant));
        }
    }

    public void setBeamMontants(){
        for (Beam b : beams){
            Montant leftMontant = new Montant(this.getInerShape().getVerticalSegment(b.buttomLeft.x),DATACONTAINER.MONTANTWITH,this.getShape().getTheta(b.buttomLeft.x),
                    false,this.getShape().getType(b.buttomLeft.x));
            Montant rightMontant = new Montant(this.getInerShape().getVerticalSegment(b.buttomRight.x),DATACONTAINER.MONTANTWITH,this.getShape().getTheta(b.buttomRight.x),
                    true,this.getShape().getType(b.buttomRight.x));
            double x = b.buttomLeft.x;
            ArrayList<Montant> buttomMontants = new ArrayList<>();
            while (b.buttomRight.x-x>=DATACONTAINER.MONTANTWITH){
                buttomMontants.add(new Montant(Segment.getVerticalSegment(x,b.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,this.getShape().getTheta(x),
                        true,ShapeType.RECTANGLE));
                x += DATACONTAINER.MONTANTWITH;
            }
            System.out.println(x);
            if (x!=b.buttomRight.x){
                buttomMontants.add(new Montant(Segment.getVerticalSegment(x,b.buttom,this.getInerShape().buttom),rightMontant.buttomRight.x-x,this.getShape().getTheta(x),
                        true,ShapeType.RECTANGLE));
            }
            b.montants.addAll(Arrays.asList(leftMontant,rightMontant));
            b.montants.addAll(buttomMontants);
            this.montants.addAll(Arrays.asList(leftMontant,rightMontant));
            this.montants.addAll(buttomMontants);
        }
    }

    public void setVerticalMontant(){
        for (Montant montant : montants){
            if (montant.isVertical()){
                this.verticalMontant.add(montant);
            }
        }
        Collections.sort(this.verticalMontant);
    }

    public void generateMidMontant(){
        double lol = verticalMontant.size();
        for (int i = 0 ; i < lol-1 ; i++){
            lol = verticalMontant.size();
            double dist = verticalMontant.get(i+1).buttomLeft.x - verticalMontant.get(i).buttomLeft.x;
            if ( dist > DATACONTAINER.MONTANTDIST && dist < 2 * DATACONTAINER.MONTANTDIST){
                double x = verticalMontant.get(i).buttomLeft.x + dist/2;
                verticalMontant.add(new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(x),
                        true,this.getInerShape().getType(x)));
            }else if (dist > 2*DATACONTAINER.MONTANTDIST ){
                double x = verticalMontant.get(i).buttomLeft.x + DATACONTAINER.MONTANTDIST;
                verticalMontant.add(new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(x),
                        true,this.getInerShape().getType(x)));
            }
        }
    }
}
