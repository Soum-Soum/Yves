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
    public LinkedList<Montant> outlinesMontants;
    public LinkedList<Montant> verticalMontant;
    public String name;

    public Area(){
        this.windows = new ArrayList<>();
        this.beams = new ArrayList<>();
        this.outlinesMontants = new LinkedList<>();
        this.verticalMontant = new LinkedList<>();
    }

    public abstract void setOutLines();

    public abstract Polygone getShape();

    public abstract Polygone getInerShape();


    // à corriger pour les trapèze 3 et 4
    public void setWindowsMontants(){
        for (Window w : windows){
            if (w.type == ShapeType.TRAPEZIUM1 || w.type == ShapeType.TRAPEZIUM2){
                w.buttomMontant = new Montant(w.buttom, DATACONTAINER.MONTANTWITH,true, ShapeType.RECTANGLE,0,0);
            }else {
                w.buttomMontant = new Montant(w.buttom,DATACONTAINER.MONTANTWITH,true, ShapeType.PARALLELOGRAM1);
            }
            double thetaTop = w.type == ShapeType.TRAPEZIUM3 || w.type == ShapeType.TRAPEZIUM4  ?  w.thetaButtom : w.thetaTop;

            w.buttomLeftMontant = new Montant(Segment.getVerticalSegment(w.buttomLeft.x,w.buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                   true,Montant.setMontantType(w.buttomMontant.buttom.goesUp(),this.getInerShape().buttom.goesUp()), thetaTop, 0);
            w.buttomRightMontant = new Montant(Segment.getVerticalSegment(w.buttomRight.x,w.buttomMontant.buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                    false,Montant.setMontantType(w.buttomMontant.buttom.goesUp(),this.getInerShape().buttom.goesUp()), thetaTop, 0);
            w.midLeftMontant =  new Montant(new Segment(w.buttomLeftMontant.buttomLeft,w.topLeft),DATACONTAINER.MONTANTWITH,
                    false,w.type,thetaTop,0);
            w.midRightMontant =  new Montant(new Segment(w.buttomRightMontant.buttomRight,w.topRight),DATACONTAINER.MONTANTWITH,
                    true,w.type, thetaTop,0);
            if (w.type == ShapeType.TRAPEZIUM1 || w.type == ShapeType.TRAPEZIUM2){
                w.topMontant = new Montant(new Segment(w.midLeftMontant.topLeft,w.midRightMontant.topRight),DATACONTAINER.MONTANTWITH,false,ShapeType.PARALLELOGRAM1);
            }else {
                w.topMontant = new Montant(new Segment(w.midLeftMontant.topLeft,w.midRightMontant.topRight),DATACONTAINER.MONTANTWITH,false,ShapeType.RECTANGLE, 0,0);
            }
            w.leftMontant = new Montant(this.getInerShape().getVerticalSegment(w.midLeftMontant.buttomLeft.x),DATACONTAINER.MONTANTWITH,
                    false,this.getInerShape().getType(w.midLeftMontant.buttomLeft.x),this.getInerShape().getTheta(w.midLeftMontant.buttomLeft.x), 0);
            w.rightMontant = new Montant(this.getInerShape().getVerticalSegment(w.midRightMontant.buttomRight.x),DATACONTAINER.MONTANTWITH,
                    true,this.getInerShape().getType(w.midRightMontant.buttomRight.x),this.getInerShape().getTheta(w.midRightMontant.buttomRight.x), 0);
            w.outLines = new Quadrilateral(w.buttomMontant.buttomLeft,w.buttomMontant.buttomRight,w.topMontant.topLeft,w.topMontant.topRight,w.type,w.thetaTop, w.thetaButtom);
            w.setRefMontants();
            w.montants.addAll(Arrays.asList(w.buttomMontant,w.topMontant));
        }
        for (Window w : windows){
            addMontantToList(w.montants, Arrays.asList(w.buttomLeftMontant,w.buttomRightMontant,w.midLeftMontant,w.midRightMontant,w.leftMontant,w.rightMontant));
        }
    }

    public void setBeamMontants(){
        for (Beam b : beams){
            Montant leftMontant = new Montant(this.getInerShape().getVerticalSegment(b.getShape().buttomLeft.x),DATACONTAINER.MONTANTWITH,
                    false,this.getShape().getType(b.getShape().buttomLeft.x),this.getShape().getTheta(b.getShape().buttomLeft.x),0);
            leftMontant.ref = b.ref +  " M-01";
            addMontantToList(b.montants,leftMontant);
            Montant rightMontant = new Montant(this.getInerShape().getVerticalSegment(b.getShape().buttomRight.x),DATACONTAINER.MONTANTWITH,
                    true,this.getShape().getType(b.getShape().buttomRight.x),this.getShape().getTheta(b.getShape().buttomRight.x),0);
            double x = b.getShape().buttomLeft.x;
            int i = 2;
            ArrayList<Montant> tempMontants = new ArrayList<>();
            while (b.getShape().buttomRight.x-x>=DATACONTAINER.MONTANTWITH){
                Montant buttomMontant = new Montant(Segment.getVerticalSegment(x,b.getShape().buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                        true,ShapeType.RECTANGLE, 0,0);
                buttomMontant.ref = b.ref + " M- 0" + String.valueOf(i);
                i++;
                tempMontants.add(buttomMontant);
                //addMontantToList(b.montants,buttomMontant);
                x += DATACONTAINER.MONTANTWITH;
            }
            if (x!=b.getShape().buttomRight.x){
                Montant buttomMontant = new Montant(Segment.getVerticalSegment(x,b.getShape().buttom,this.getInerShape().buttom),rightMontant.buttomRight.x-x,
                        true,ShapeType.RECTANGLE, 0,0);
                buttomMontant.ref = b.ref + " M- 0" + String.valueOf(i);
                i++;
                tempMontants.add(buttomMontant);
                //addMontantToList(b.montants,buttomMontant);
            }
            addMontantToList(b.montants, tempMontants);
            rightMontant.ref = b.ref+ "M- 0" + String.valueOf(i);
            addMontantToList(b.montants,rightMontant);
        }
    }

    public void setVerticalMontant(){
        for (Montant montant : outlinesMontants){
            if (montant.isVertical()){
                this.verticalMontant.add(montant);
            }
        }
        Collections.sort(this.verticalMontant);
    }

    public void generateMidMontant(){
        for (int i = 0 ; i < verticalMontant.size()-1 ; i++){
            double dist = verticalMontant.get(i+1).buttomLeft.x - verticalMontant.get(i).buttomLeft.x;
            double x;
            int montantCount=1;
            Montant m = null;
            if ( dist > DATACONTAINER.MONTANTDIST && dist < 2 * DATACONTAINER.MONTANTDIST){
                x = verticalMontant.get(i).buttomLeft.x + dist/2;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,
                        true,this.getInerShape().getType(x), this.getInerShape().getTheta(x),0);
                m.ref = "A- " + this.name + "MV- 0" + String.valueOf(montantCount);
                montantCount++;
            }else if (dist > 2*DATACONTAINER.MONTANTDIST ){
                x = verticalMontant.get(i).buttomLeft.x + DATACONTAINER.MONTANTDIST;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,
                        true,this.getInerShape().getType(x),this.getInerShape().getTheta(x),0);
                m.ref = "A- " + this.name + "MV- 0" + String.valueOf(montantCount);
                montantCount++;
            }
            if (m!=null){
                LinkedList<Window> colider = this.getColider(m);
                if (colider.size()!=0){
                    verticalMontant.addAll(i+1, recursivDivision(m,colider));
                }else{
                    verticalMontant.add(i+1,m);
                }
            }
        }
    }

    public LinkedList recursivDivision(Montant m, LinkedList<Window> coliders){
        LinkedList<Montant> montantPart = new LinkedList();
        if (coliders.size()==0){
            montantPart.add(m);
            return montantPart;
        }else {
            Intersection intersection = new Intersection(coliders.get(0).outLines.getIntersection(m), coliders.get(0));
            coliders.remove(0);
            Montant[] temp = m.substract(intersection);
            montantPart.add(temp[0]);
            montantPart.addAll(recursivDivision(temp[1],coliders));
            for (int j = montantPart.size()-1; j>0; j--){
                montantPart.get(j).ref += "P- 0" + String.valueOf(montantPart.size() - j);
            }
            return montantPart;
        }
    }

    public LinkedList<Window> getColider(Quadrilateral quadrilateral){
        LinkedList<Window> q = new LinkedList<>();
        for (Window w : windows){
            if (w.outLines.getMinX()<quadrilateral.getMinX() && w.outLines.getMaxX()>quadrilateral.getMaxX()){
                if (w.outLines.haveAnIntersection(quadrilateral)/* && quadrilateral.buttomLeft.x !=w.outLines.buttomLeft.x && quadrilateral.buttomRight.x !=w.outLines.buttomRight.x*/ ){
                    int i = 0;
                    while(i<q.size() && q.get(i).buttomLeft.y>w.outLines.buttomLeft.y){
                        i++;
                    }
                    q.add(i,w);
                }
            }
        }
        return q;
    }

    public void addMontantToList(List<Montant> list, Montant m){
        LinkedList<Window> colider = this.getColider(m);
        if (colider.size()!=0){
            LinkedList<Montant> tempList = recursivDivision(m,colider);
            verticalMontant.addAll(tempList);
            list.addAll(tempList);
        }else{
            verticalMontant.add(m);
            list.add(m);
        }
    }

    public void addMontantToList(List<Montant> list, List<Montant> listMontant){
        for(int i=0;i<listMontant.size();i++){
            Montant m =listMontant.get(i);
            LinkedList<Window> colider = this.getColider(listMontant.get(i));
            if (colider.size()!=0){
                LinkedList<Montant> tempList = recursivDivision(m,colider);
                verticalMontant.addAll(tempList);
                list.addAll(tempList);
            }else{
                verticalMontant.add(m);
                list.add(m);
            }
        }
    }
}
