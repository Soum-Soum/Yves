package home;

import data.DATACONTAINER;
import math.Polygone;
import math.Quadrilateral;
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
            w.buttomMontant = new Montant(w.buttom, DATACONTAINER.MONTANTWITH,0,true, ShapeType.RECTANGLE);
            w.buttomLeftMontant = new Montant(Segment.getVerticalSegment(w.buttomLeft.x,w.buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                    0,true,ShapeType.RECTANGLE);
            w.buttomRightMontant = new Montant(Segment.getVerticalSegment(w.buttomRight.x,w.buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                    0,false,ShapeType.RECTANGLE);
            w.midLeftMontant =  new Montant(new Segment(w.buttomLeftMontant.buttomLeft,w.topLeft),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.buttomLeft.x),
                    false,w.type);
            w.midRightMontant =  new Montant(new Segment(w.buttomRightMontant.buttomRight,w.topRight),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.buttomRight.x),
                    true,w.type);
            w.topMontant = new Montant(new Segment(w.midLeftMontant.topLeft,w.midRightMontant.topRight),DATACONTAINER.MONTANTWITH,false,ShapeType.PARALLELOGRAM1);
            w.leftMontant = new Montant(this.getInerShape().getVerticalSegment(w.midLeftMontant.buttomLeft.x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.midLeftMontant.buttomLeft.x),
                    false,this.getInerShape().getType(w.midLeftMontant.buttomLeft.x));
            w.rightMontant = new Montant(this.getInerShape().getVerticalSegment(w.midRightMontant.buttomRight.x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(w.midRightMontant.buttomRight.x),
                    true,this.getInerShape().getType(w.midRightMontant.buttomRight.x));
            w.montants.addAll(Arrays.asList(w.buttomMontant,w.topMontant,w.leftMontant,w.rightMontant,w.buttomLeftMontant,w.buttomRightMontant, w.midLeftMontant, w.midRightMontant));
            this.montants.addAll(Arrays.asList(w.buttomMontant,w.topMontant,w.leftMontant,w.rightMontant,w.buttomLeftMontant,w.buttomRightMontant,w.midLeftMontant,w.midRightMontant));
            w.outLines = new Quadrilateral(w.buttomMontant.buttomLeft,w.buttomMontant.buttomRight,w.topMontant.topLeft,w.topMontant.topRight,w.type);
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
        for (int i = 0 ; i < verticalMontant.size()-1 ; i++){
            double dist = verticalMontant.get(i+1).buttomLeft.x - verticalMontant.get(i).buttomLeft.x;
            double x =0;
            Montant m = null;
            if ( dist > DATACONTAINER.MONTANTDIST && dist < 2 * DATACONTAINER.MONTANTDIST){
                x = verticalMontant.get(i).buttomLeft.x + dist/2;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(x),
                        true,this.getInerShape().getType(x));
            }else if (dist > 2*DATACONTAINER.MONTANTDIST ){
                x = verticalMontant.get(i).buttomLeft.x + DATACONTAINER.MONTANTDIST;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,this.getInerShape().getTheta(x),
                        true,this.getInerShape().getType(x));
            }
            if (m!=null){
                LinkedList<Quadrilateral> colider = this.getColider(m);
                if (colider.size()!=0){
                    verticalMontant.addAll(i+1, recursivDivision(m,colider));
                }else{
                    verticalMontant.add(i+1,m);
                }
            }
        }
    }

    public LinkedList recursivDivision(Montant m, LinkedList<Quadrilateral> coliders){
        LinkedList<Montant> montantPart = new LinkedList();
        if (coliders.size()==0){
            montantPart.add(m);
            return montantPart;
        }else {
            Quadrilateral intersection = coliders.get(0).getIntersection(m);
            coliders.remove(0);
            Montant[] temp = m.divide(intersection);
            montantPart.add(temp[0]);
            montantPart.addAll(recursivDivision(temp[1],coliders));
            return montantPart;
        }

    }

    public LinkedList<Quadrilateral> getColider(Quadrilateral quadrilateral){
        LinkedList<Quadrilateral> q = new LinkedList<>();
        for (Window w : windows){
            if (w.outLines.getMinX()<quadrilateral.getMinX() && w.outLines.getMaxX()>quadrilateral.getMaxX()){
                if (w.outLines.haveAnIntersection(quadrilateral)){
                    int i = 0;
                    if (q.size()==0){
                        q.add(w.outLines);
                    }else {
                        while(i<q.size() && q.get(i).buttomLeft.y>w.outLines.buttomLeft.y){
                            i++;
                        }
                        q.add(i,w.outLines);
                    }
                }
            }
        }
        return q;
    }
}
