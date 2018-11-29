package home;

import data.DATACONTAINER;
import math.*;

import java.util.*;

public abstract class Area {

    public ArrayList<Window> windows;
    public ArrayList<Beam> beams;
    public LinkedList<Montant> outlinesMontants;
    public LinkedList<Montant> verticalMontant;
    public LinkedList<Montant> midMontant;
    public String name;

    public Area(){
        this.windows = new ArrayList<>();
        this.beams = new ArrayList<>();
        this.outlinesMontants = new LinkedList<>();
        this.verticalMontant = new LinkedList<>();
        this.midMontant = new LinkedList<>();
    }

    public abstract void setOutLines();

    public abstract Polygone getShape();

    public abstract Polygone getInerShape();

    public abstract void setSums(Montant m);

    public void setWindowsMontants(){
        for (Window w : windows){
            boolean leftUnderBeam = w.buttomLeft.isUnderObstacle(beams, windows) , rightUnderBeam = w.buttomRight.isUnderObstacle(beams, windows);
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
            w.leftMontant = new Montant(this.getInerShape().getVerticalSegment(w.midLeftMontant.buttomLeft.x),DATACONTAINER.MONTANTWITH,
                    false,this.getInerShape().getType(w.midLeftMontant.buttomLeft.x),this.getInerShape().getTheta(w.midLeftMontant.buttomLeft.x), 0);
            setSums(w.leftMontant);
            w.rightMontant = new Montant(this.getInerShape().getVerticalSegment(w.midRightMontant.buttomRight.x),DATACONTAINER.MONTANTWITH,
                    true,this.getInerShape().getType(w.midRightMontant.buttomRight.x),this.getInerShape().getTheta(w.midRightMontant.buttomRight.x), 0);
            setSums(w.rightMontant);
            Segment topSegment = new Segment(w.midLeftMontant.topLeft,w.midRightMontant.topRight);
            if (leftUnderBeam){
                topSegment = new Segment(w.topLeft,topSegment.tail);
            }
            if (rightUnderBeam){
                topSegment = new Segment(topSegment.head,w.topRight);
            }
            if (w.type == ShapeType.TRAPEZIUM1 || w.type == ShapeType.TRAPEZIUM2){
                w.topMontant = new Montant(topSegment,DATACONTAINER.MONTANTWITH,false,ShapeType.PARALLELOGRAM1);
            }else {
                w.topMontant = new Montant(topSegment,DATACONTAINER.MONTANTWITH,false,ShapeType.RECTANGLE, 0,0);
            }
            if (leftUnderBeam && rightUnderBeam){
                w.outLines = new Quadrilateral(w.buttomMontant.buttomLeft,w.buttomMontant.buttomRight,w.topMontant.topLeft,w.topMontant.topRight,w.type,w.thetaTop, w.thetaButtom);
            }else if (leftUnderBeam){
                w.outLines = new Quadrilateral(w.buttomMontant.buttomLeft,new Point(w.buttomMontant.buttomRight,+DATACONTAINER.MONTANTWITH,0)
                        ,w.topMontant.topLeft,w.topMontant.topRight,w.type,w.thetaTop, w.thetaButtom);
            }else if (rightUnderBeam){
                w.outLines = new Quadrilateral(new Point(w.buttomMontant.buttomLeft,-DATACONTAINER.MONTANTWITH,0),w.buttomMontant.buttomRight
                        ,w.topMontant.topLeft,w.topMontant.topRight,w.type,w.thetaTop, w.thetaButtom);
            }else {
                w.outLines = new Quadrilateral(new Point(w.buttomMontant.buttomLeft,-DATACONTAINER.MONTANTWITH,0),new Point(w.buttomMontant.buttomRight,+DATACONTAINER.MONTANTWITH,0)
                        ,w.topMontant.topLeft,w.topMontant.topRight,w.type,w.thetaTop, w.thetaButtom);
            }
            w.setRefMontants();
        }
    }

    public void setBeamMontants(){
        for (Beam b : beams){
            Montant leftMontant = new Montant(this.getInerShape().getVerticalSegment(b.getShape().buttomLeft.x),DATACONTAINER.MONTANTWITH,
                    false,this.getShape().getType(b.getShape().buttomLeft.x),this.getShape().getTheta(b.getShape().buttomLeft.x),0);
            setSums(leftMontant);
            addMontantToList(b.montants,leftMontant,true, false,CollisionBehaviour.STOP_FIRTS_TOP);
            Montant rightMontant = new Montant(this.getInerShape().getVerticalSegment(b.getShape().buttomRight.x),DATACONTAINER.MONTANTWITH,
                    true,this.getShape().getType(b.getShape().buttomRight.x),this.getShape().getTheta(b.getShape().buttomRight.x),0);
            double x = b.getShape().buttomLeft.x;
            ArrayList<Montant> tempMontants = new ArrayList<>();
            while (b.getShape().buttomRight.x-x>=DATACONTAINER.MONTANTWITH){
                Montant buttomMontant = new Montant(Segment.getVerticalSegment(x,b.getShape().buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                        true,ShapeType.RECTANGLE, 0,0);
                buttomMontant.numberWritable=false;
                tempMontants.add(buttomMontant);
                x += DATACONTAINER.MONTANTWITH;
            }
            if (x!=b.getShape().buttomRight.x){
                Montant buttomMontant = new Montant(Segment.getVerticalSegment(x,b.getShape().buttom,this.getInerShape().buttom),rightMontant.buttomRight.x-x,
                        true,ShapeType.RECTANGLE, 0,0);
                buttomMontant.numberWritable=false;
                tempMontants.add(buttomMontant);
            }
            tempMontants.get(tempMontants.size()/2).numberWritable=true;
            addMontantToList(b.montants, tempMontants,true, true, CollisionBehaviour.STOP_FIRTS_TOP);
            setSums(rightMontant);
            addMontantToList(b.montants,rightMontant,true, true, CollisionBehaviour.STOP_FIRTS_TOP);
        }
        for (Window w : windows){
            boolean leftUnderBeam = w.buttomLeft.isUnderObstacle(beams, windows) , rightUnderBeam = w.buttomRight.isUnderObstacle(beams, windows);
            if (leftUnderBeam && rightUnderBeam){
                w.montants.addAll(Arrays.asList( w.buttomMontant, w.topMontant ));
                //w.leftMontant, w.midLeftMontant, ... ,w.midRightMontant, w.rightMontant
                addMontantToList(w.montants, Arrays.asList(w.buttomLeftMontant,w.buttomRightMontant),false, true, CollisionBehaviour.NEVER_STOP);
            }else if (leftUnderBeam){
                w.montants.addAll(Arrays.asList( w.buttomMontant, w.topMontant));
                //w.leftMontant, w.midLeftMontant,...
                addMontantToList(w.montants, Arrays.asList(w.buttomLeftMontant,w.buttomRightMontant,w.midRightMontant,w.rightMontant),false, true, CollisionBehaviour.NEVER_STOP);
            }else if (rightUnderBeam){
                w.montants.addAll(Arrays.asList(w.buttomMontant, w.topMontant));
                //...,w.midRightMontant, w.rightMontant
                addMontantToList(w.montants, Arrays.asList(w.leftMontant,w.midLeftMontant,w.buttomLeftMontant,w.buttomRightMontant),false, true, CollisionBehaviour.NEVER_STOP);
            }else {
                w.montants.addAll(Arrays.asList(w.buttomMontant, w.topMontant));
                addMontantToList(w.montants, Arrays.asList(w.leftMontant,w.midLeftMontant,w.buttomLeftMontant,w.buttomRightMontant,w.midRightMontant,w.rightMontant),false, true, CollisionBehaviour.NEVER_STOP);
            }
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
            Montant m = null;
            if ( dist > DATACONTAINER.MONTANTDIST && dist < 2 * DATACONTAINER.MONTANTDIST){
                x = verticalMontant.get(i).buttomLeft.x + dist/2;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,
                        true,this.getInerShape().getType(x), this.getInerShape().getTheta(x),0);
                setSums(m);
            }else if (dist > 2*DATACONTAINER.MONTANTDIST ){
                x = verticalMontant.get(i).buttomLeft.x + DATACONTAINER.MONTANTDIST;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,
                        true,this.getInerShape().getType(x),this.getInerShape().getTheta(x),0);
                setSums(m);
            }
            if (m!=null){
                LinkedList<Window> colider = this.getColider(m,false);
                if (colider.size()!=0){
                    if (m.isUnderBeam(beams, windows)) {
                        LinkedList temp = recursivDivision(m,colider,CollisionBehaviour.STOP_LAST_BUTTOM);
                        midMontant.addAll(temp);
                        verticalMontant.addAll(i+1, temp );
                    }else {
                        LinkedList temp = recursivDivision(m,colider,CollisionBehaviour.NEVER_STOP);
                        midMontant.addAll(temp);
                        verticalMontant.addAll(i+1, temp);
                    }
                }else{
                    verticalMontant.add(i+1,m);
                    midMontant.add(m);
                }
            }
        }
    }

    public LinkedList recursivDivision(Montant m, LinkedList<Window> coliders, CollisionBehaviour behaviour){
        LinkedList<Montant> montantPart = new LinkedList();
        Intersection intersection;
        Montant[] temp = new Montant[2];
        if (coliders.size()==0){
            montantPart.add(m);
            return montantPart;
        }else {
            switch (behaviour){
                case STOP_FIRTS_TOP:
                    intersection = new Intersection(coliders.get(0).outLines.getIntersection(m), coliders.get(0));
                    coliders.remove(0);
                    if (intersection.intersection != null){
                        temp = m.substract(intersection);
                        montantPart.add(temp[0]);
                    }
                    break;
                case STOP_LAST_TOP:
                    intersection = new Intersection(coliders.get(0).outLines.getIntersection(m), coliders.get(0));
                    coliders.remove(0);
                    if (intersection.intersection != null){
                        temp = m.substract(intersection);
                        montantPart.add(temp[0]);
                    }
                    if (coliders.size()!=1){
                        //montantPart.addAll(recursivDivision(m,coliders,behaviour));
                        montantPart.addAll(recursivDivision(temp[1],coliders,behaviour));
                    }
                    break;
                case STOP_FIRST_BUTTOM:
                    intersection = new Intersection(coliders.get(coliders.size()-1).outLines.getIntersection(m), coliders.get(coliders.size()-1));
                    coliders.remove(coliders.size()-1);
                    if (intersection.intersection != null){
                        temp = m.substract(intersection);
                        montantPart.add(temp[1]);
                    }
                    break;
                case STOP_LAST_BUTTOM:
                    intersection = new Intersection(coliders.get(coliders.size()-1).outLines.getIntersection(m), coliders.get(coliders.size()-1));
                    coliders.remove(coliders.size()-1);
                    if (intersection.intersection != null){
                        temp = m.substract(intersection);
                        montantPart.add(temp[1]);
                    }
                    if (coliders.size()!=1){
                        montantPart.addAll(recursivDivision(temp[0],coliders,behaviour));
                        //montantPart.addAll(recursivDivision(m,coliders,behaviour));
                    }
                    break;
                default:
                    intersection = new Intersection(coliders.get(0).outLines.getIntersection(m), coliders.get(0));
                    coliders.remove(0);
                    if (intersection.intersection!=null){
                        temp = m.substract(intersection);
                        montantPart.add(temp[0]);
                        montantPart.addAll(recursivDivision(temp[1],coliders,behaviour));
                    }else {
                        montantPart.addAll(recursivDivision(m,coliders,behaviour));
                    }
                    break;
            }
        }
        return montantPart;
    }

    public LinkedList<Window> getColider(Quadrilateral quadrilateral, boolean needTraverse){
        LinkedList<Window> q = new LinkedList<>();
        for (Window w : windows){
            if (w.outLines.getMinX()<=quadrilateral.getMinX() && w.outLines.getMaxX()>=quadrilateral.getMaxX()){
                if (w.outLines.haveAnIntersection(quadrilateral)){
                    int i = 0;
                    while(i<q.size() && q.get(i).buttomLeft.y>w.outLines.buttomLeft.y){
                        i++;
                    }
                    q.add(i,w);
                }
            }
        }
        if (q.size()!= 0 && !q.get(0).haveTraverse && needTraverse){q.get(0).generateTraverse();}
        return q;
    }

    public void addMontantToList(List<Montant> list, Montant m, boolean needTraverse,  boolean addToVerticalList, CollisionBehaviour behaviour){
        LinkedList<Window> colider = this.getColider(m,needTraverse);
        if (colider.size()!=0){
            LinkedList<Montant> tempList = recursivDivision(m,colider, behaviour);
            if (addToVerticalList){
                verticalMontant.addAll(tempList);
            }
            list.addAll(tempList);
        }else{
            if (addToVerticalList){
                verticalMontant.add(m);
            }
            if (list!=null){
                list.add(m);
            }
        }
    }

    public void addMontantToList(List<Montant> list, List<Montant> listMontant, boolean needTraverse, boolean addToVerticalList, CollisionBehaviour behaviour){
        for(int i=0;i<listMontant.size();i++){
            Montant m =listMontant.get(i);
            LinkedList<Window> colider = this.getColider(m, needTraverse);
            if (colider.size()!=0){
                LinkedList<Montant> tempList = recursivDivision(m,colider, behaviour);
                if (addToVerticalList){
                    verticalMontant.addAll(tempList);
                }
                if (list!=null){
                    list.addAll(tempList);
                }
            }else{
                if (addToVerticalList){
                    verticalMontant.add(m);
                }
                if (list!=null){
                    list.add(m);
                }
            }
        }
    }
}
