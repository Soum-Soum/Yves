package home;

import data.DATACONTAINER;
import math.*;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.*;

public abstract class Area {

    public ArrayList<Window> windows;
    public ArrayList<Beam> beams;
    public HashMap<String,Montant> outlinesMontants;
    public LinkedList<Montant> verticalMontant;
    public LinkedList<Montant> midMontant;
    public String name;

    public Area(){
        this.windows = new ArrayList<>();
        this.beams = new ArrayList<>();
        this.outlinesMontants = new HashMap<>();
        this.verticalMontant = new LinkedList<>();
        this.midMontant = new LinkedList<>();
    }

    public abstract void setOutLines();

    public abstract Polygone getShape();

    public abstract Polygone getInerShape();

    public abstract void setSums(Montant m, Boolean justButtom);

    public void setWindowsMontants(){
        for (Window w : windows){
            boolean leftUnderBeam = w.buttomLeft.isUnderObstacle(beams, windows) , rightUnderBeam = w.buttomRight.isUnderObstacle(beams, windows);
            if (w.type == ShapeType.TRAPEZIUM3 || w.type == ShapeType.TRAPEZIUM4){
                w.montantsBeforCut.put("buttomMontant",new Montant(w.buttom,DATACONTAINER.MONTANTWITH,true, ShapeType.PARALLELOGRAM1));
            }else {
                w.montantsBeforCut.put("buttomMontant",new Montant(w.buttom, DATACONTAINER.MONTANTWITH,true, ShapeType.RECTANGLE,0,0));
            }
            double thetaTop = w.type == ShapeType.TRAPEZIUM3 || w.type == ShapeType.TRAPEZIUM4  ?  w.thetaButtom : w.thetaTop;
            w.montantsBeforCut.put("buttomLeftMontant", new Montant(Segment.getVerticalSegment(w.buttomLeft.x,w.montantsBeforCut.get("buttomMontant").buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                   true,Montant.setMontantType(w.montantsBeforCut.get("buttomMontant").buttom.goesUp(),this.getInerShape().buttom.goesUp()), thetaTop, 0));
            w.montantsBeforCut.put("buttomRightMontant", new Montant(Segment.getVerticalSegment(w.buttomRight.x,w.montantsBeforCut.get("buttomMontant").buttom,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                    false,Montant.setMontantType(w.montantsBeforCut.get("buttomMontant").buttom.goesUp(),this.getInerShape().buttom.goesUp()), thetaTop, 0));
            w.montantsBeforCut.put("midLeftMontant", new Montant(Segment.getVerticalSegment(w.buttomLeft.x,w.top,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                    false,w.type,thetaTop,0));
            setSums(w.montantsBeforCut.get("midLeftMontant"),true);
            w.montantsBeforCut.put("midRightMontant", new Montant(Segment.getVerticalSegment(w.buttomRight.x,w.top,this.getInerShape().buttom),DATACONTAINER.MONTANTWITH,
                    true,w.type, thetaTop,0));
            setSums(w.montantsBeforCut.get("midRightMontant"),true);
            w.montantsBeforCut.put("leftMontant", new Montant(this.getInerShape().getVerticalSegment(w.montantsBeforCut.get("midLeftMontant").buttomLeft.x),DATACONTAINER.MONTANTWITH,
                    false,this.getInerShape().getType(w.montantsBeforCut.get("midLeftMontant").buttomLeft.x),this.getInerShape().getTheta(w.montantsBeforCut.get("midLeftMontant").buttomLeft.x), 0));
            setSums(w.montantsBeforCut.get("leftMontant"),false);
            w.montantsBeforCut.put("rightMontant", new Montant(this.getInerShape().getVerticalSegment(w.montantsBeforCut.get("midRightMontant").buttomRight.x),DATACONTAINER.MONTANTWITH,
                    true,this.getInerShape().getType(w.montantsBeforCut.get("midRightMontant").buttomRight.x),this.getInerShape().getTheta(w.montantsBeforCut.get("midRightMontant").buttomRight.x), 0));
            setSums(w.montantsBeforCut.get("rightMontant"),false);
            Segment topSegment = new Segment(w.montantsBeforCut.get("midLeftMontant").topLeft,w.montantsBeforCut.get("midRightMontant").topRight);
            if (leftUnderBeam){
                topSegment = new Segment(w.topLeft,topSegment.tail);
            }
            if (rightUnderBeam){
                topSegment = new Segment(topSegment.head,w.topRight);
            }
            if (w.type == ShapeType.TRAPEZIUM1 || w.type == ShapeType.TRAPEZIUM2){
                w.montantsBeforCut.put("topMontant", new Montant(topSegment,DATACONTAINER.MONTANTWITH,false,ShapeType.PARALLELOGRAM1));
            }else {
                w.montantsBeforCut.put("topMontant", new Montant(topSegment,DATACONTAINER.MONTANTWITH,false,ShapeType.RECTANGLE, 0,0));
            }
            if (leftUnderBeam && rightUnderBeam){
                w.outLines = new Quadrilateral(w.montantsBeforCut.get("buttomMontant").buttomLeft,w.montantsBeforCut.get("buttomMontant").buttomRight,w.montantsBeforCut.get("topMontant").topLeft,
                        w.montantsBeforCut.get("topMontant").topRight, w.type,w.thetaTop, w.thetaButtom);
            }else if (leftUnderBeam){
                w.outLines = new Quadrilateral(w.montantsBeforCut.get("buttomMontant").buttomLeft,new Point(w.montantsBeforCut.get("buttomMontant").buttomRight,+DATACONTAINER.MONTANTWITH,0)
                        ,w.montantsBeforCut.get("topMontant").topLeft,w.montantsBeforCut.get("topMontant").topRight,w.type,w.thetaTop, w.thetaButtom);
            }else if (rightUnderBeam){
                w.outLines = new Quadrilateral(new Point(w.montantsBeforCut.get("buttomMontant").buttomLeft,-DATACONTAINER.MONTANTWITH,0),w.montantsBeforCut.get("buttomMontant").buttomRight
                        ,w.montantsBeforCut.get("topMontant").topLeft,w.montantsBeforCut.get("topMontant").topRight,w.type,w.thetaTop, w.thetaButtom);
            }else {
                w.outLines = new Quadrilateral(new Point(w.montantsBeforCut.get("buttomMontant").buttomLeft,-DATACONTAINER.MONTANTWITH,0),new Point(w.montantsBeforCut.get("buttomMontant").buttomRight,
                        +DATACONTAINER.MONTANTWITH,0),w.montantsBeforCut.get("topMontant").topLeft,w.montantsBeforCut.get("topMontant").topRight,w.type,w.thetaTop, w.thetaButtom);
            }
        }
    }

    public void setBeamMontants(){
        for (Beam b : beams){
            Montant leftMontant=null, rightMontant=null;
            if(b.getShape().buttomLeft.x!=this.getShape().buttomLeft.x){
                leftMontant= new Montant(this.getInerShape().getVerticalSegment(b.getShape().buttomLeft.x),DATACONTAINER.MONTANTWITH,
                        false,this.getShape().getType(b.getShape().buttomLeft.x),this.getShape().getTheta(b.getShape().buttomLeft.x),0);
                setSums(leftMontant,false);
                b.leftMontant = handleCollision(leftMontant,true, true,CollisionBehaviour.STOP_FIRTS_TOP);
            }else {
                this.outlinesMontants.remove("leftMontant");
            }
            if(b.getShape().buttomRight.x!=this.getShape().buttomRight.x){
                rightMontant = new Montant(this.getInerShape().getVerticalSegment(b.getShape().buttomRight.x),DATACONTAINER.MONTANTWITH,
                        true,this.getShape().getType(b.getShape().buttomRight.x),this.getShape().getTheta(b.getShape().buttomRight.x),0);
                setSums(rightMontant,false);
            }else {
                this.outlinesMontants.remove("rightMontant");
            }
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
                Montant buttomMontant = new Montant(Segment.getVerticalSegment(x,b.getShape().buttom,this.getInerShape().buttom),b.getShape().buttomRight.x-x,
                        true,ShapeType.RECTANGLE, 0,0);
                buttomMontant.numberWritable=false;
                tempMontants.add(buttomMontant);
            }
            tempMontants.get(tempMontants.size()/2).numberWritable=true;
            b.midsMontants = handleCollision(tempMontants,true, true, CollisionBehaviour.STOP_FIRTS_TOP);
            if (rightMontant != null) {
                b.rightMontant= handleCollision(rightMontant, true, true, CollisionBehaviour.STOP_FIRTS_TOP);
            }
        }
        for (Window w : windows){
            handleSideWindows(w);
            Boolean leftUnderBeam = w.buttomLeft.isUnderObstacle(beams, windows) , rightUnderBeam = w.buttomRight.isUnderObstacle(beams, windows), midleftUnderBeam = null, midrighttUnderBeam = null;
            if (w.montantsBeforCut.get("midLeftMontant") != null){
                midleftUnderBeam = w.montantsBeforCut.get("midLeftMontant").buttomLeft.isUnderObstacle(beams,windows);
            }
            if (w.montantsBeforCut.get("midRightMontant") != null){
                midrighttUnderBeam = w.montantsBeforCut.get("midRightMontant").buttomRight.isUnderObstacle(beams,windows);
            }
            if (leftUnderBeam && rightUnderBeam){
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
            }else if (leftUnderBeam){
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("midRightMontant", handleCollision(w.montantsBeforCut.get("midRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                if (midrighttUnderBeam!= null && !midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midrighttUnderBeam!= null && midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
            }else if (rightUnderBeam){
                if (midleftUnderBeam!= null && !midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midleftUnderBeam!= null && midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
                try{w.montantsAfterCut.put("midLeftMontant", handleCollision(w.montantsBeforCut.get("midLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
            }else {

                if (midleftUnderBeam!= null && !midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midleftUnderBeam!= null && midleftUnderBeam){
                    try{w.montantsAfterCut.put("leftMontant", handleCollision(w.montantsBeforCut.get("leftMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
                try{w.montantsAfterCut.put("midLeftMontant", handleCollision(w.montantsBeforCut.get("midLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomLeftMontant", handleCollision(w.montantsBeforCut.get("buttomLeftMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("buttomRightMontant", handleCollision(w.montantsBeforCut.get("buttomRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                try{w.montantsAfterCut.put("midRightMontant", handleCollision(w.montantsBeforCut.get("midRightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                if (midrighttUnderBeam!= null && !midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.NEVER_STOP));}catch (NullPointerException e){}
                }else if (midrighttUnderBeam!= null && midrighttUnderBeam){
                    try{w.montantsAfterCut.put("rightMontant", handleCollision(w.montantsBeforCut.get("rightMontant"),false,true,CollisionBehaviour.STOP_LAST_BUTTOM));}catch (NullPointerException e){}
                }
            }// On ingnore les exeption nule pour ne pas à avoir à vérifuer si le montant existe toujour ou si il a été suprimé car on est sur un bord de la facade
            for (String s : w.montantsBeforCut.keySet()){
                if (w.montantsAfterCut.get(s)==null){
                    w.montantsAfterCut.put(s,w.montantsBeforCut.get(s).montant2List());
                }
            }
        }
    }

    public void handleSideWindows(Window w){
        if (w.buttomLeft.x <= this.getInerShape().buttomLeft.x + 2*DATACONTAINER.MONTANTWITH){
            w.montantsBeforCut.remove("leftMontant");
        }
        if (w.buttomRight.x >= this.getInerShape().buttomRight.x - 2*DATACONTAINER.MONTANTWITH){
            w.montantsBeforCut.remove("rightMontant");
        }
        if(w.buttomLeft.y < this.getInerShape().buttomLeft.y + DATACONTAINER.MONTANTWITH){
            w.montantsBeforCut.remove("buttomRightMontant");
            w.montantsBeforCut.remove("buttomLeftMontant");
            w.montantsBeforCut.remove("buttomMontant");
        }else if(w.buttomLeft.y == this.getInerShape().buttomLeft.y + DATACONTAINER.MONTANTWITH){
            w.montantsBeforCut.remove("buttomRightMontant");
            w.montantsBeforCut.remove("buttomLeftMontant");
        }
    }

    public void setVerticalMontant(){
        for (Montant montant : outlinesMontants.values()){
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
            if ( dist > DATACONTAINER.MONTANTDIST && dist <= 2 * DATACONTAINER.MONTANTDIST){
                x = verticalMontant.get(i).buttomLeft.x + dist/2;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,
                        true,this.getInerShape().getType(x), this.getInerShape().getTheta(x),0);
                setSums(m, false);
            }else if (dist > 2*DATACONTAINER.MONTANTDIST ){
                x = verticalMontant.get(i).buttomLeft.x + DATACONTAINER.MONTANTDIST;
                m = new Montant(this.getInerShape().getVerticalSegment(x),DATACONTAINER.MONTANTWITH,
                        true,this.getInerShape().getType(x),this.getInerShape().getTheta(x),0);
                setSums(m, false);
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
                    if (coliders.size()>1){
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
                    if (coliders.size()>1){
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
        if (q.size()> 0 && !q.get(0).haveTraverse && needTraverse){q.get(0).generateTraverse();}
        return q;
    }

    public LinkedList<Montant> handleCollision(Montant m, boolean needTraverse, boolean addToVerticalMontantList, CollisionBehaviour behaviour){
        try {
            LinkedList<Window> colider = this.getColider(m,needTraverse);
            LinkedList<Montant> tempList;
            if (colider.size()>0){
                tempList = recursivDivision(m,colider, behaviour);
            }else {
                tempList = m.montant2List();
            }
            if (addToVerticalMontantList){
                verticalMontant.addAll(tempList);
            }
            return tempList;
        }catch (NullPointerException e){
            return null;
        }
    }

    public LinkedList<LinkedList<Montant>> handleCollision(ArrayList<Montant> listMontant, boolean needTraverse, boolean addToVerticalMontantList, CollisionBehaviour behaviour){
        LinkedList<LinkedList<Montant>> tempList = new LinkedList<>();
        for(Montant m : listMontant){
            LinkedList<Window> colider = this.getColider(m, needTraverse);
            LinkedList<Montant> temp2List;
            if (colider.size()!=0) {
                temp2List = recursivDivision(m, colider, behaviour);
            }else {
                temp2List = m.montant2List();
            }
            if (addToVerticalMontantList){
                verticalMontant.addAll(temp2List);
            }
            tempList.add(temp2List);
        }
        return tempList;
    }
}
