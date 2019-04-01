package img;

import data.DATACONTAINER;
import file.FileWriter;
import home.*;
import home.Window;
import math.*;
import math.Point;
import util.Utilies;
import view.obj.ViewBeam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ImageDrawer {

    BufferedImage imageBuffer;
    public int width, height;
    public double scalingValue;
    public Graphics2D graph;
    Stroke stroke;

    public ImageDrawer(int width ,int height, double scalingValue) {
        this.width= (int) (width*scalingValue);
        this.height= (int) (height*scalingValue);
        this.scalingValue= scalingValue;
        System.out.println(this.width + "---" + this.height);
        this.imageBuffer = new BufferedImage(this.width,this.height,BufferedImage.TYPE_INT_RGB);
        graph = imageBuffer.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fill(new Rectangle(0,0,this.width,this.height));
        graph.setColor(Color.BLACK);
        graph.setFont(new Font("Arial Black", Font.PLAIN,14));
        stroke = new BasicStroke(3);
        graph.setStroke(stroke);
    }

    public String saveIMG(String path){
        try {
            ImageIO.write(imageBuffer, "jpg", new File(path));
            return path;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void drawArea(Area area){
        for (Montant m : area.outlinesMontants.values()){
            drawSegment(m.getSegments());
            if (m.numberWritable){
                drawID(m,m.number);
            }
        }
        for (Window w : area.windows){
            if (w.haveTraverse){drawSegment(w.traverse.getSegments());}
            for (LinkedList<Montant> l : w.montantsAfterCut.values() ){
                for(Montant m : l){
                    drawSegment(m.getSegments());
                    if (m.numberWritable){
                        drawID(m,m.number);
                    }
                }
            }
        }
        for (Beam b : area.beams){
            for (Montant m : b.leftMontant){
                drawSegment(m.getSegments());
                if (m.numberWritable){
                    drawID(m,m.number);
                }
            }
            for (LinkedList<Montant> l : b.midsMontants){
                for (Montant m : l){
                    drawSegment(m.getSegments());
                    if (m.numberWritable){
                        drawID(m,m.number);
                    }
                }
            }
            for (Montant m : b.rightMontant){
                drawSegment(m.getSegments());
                if (m.numberWritable){
                    drawID(m,m.number);
                }
            }
        }
        for (Montant m : area.verticalMontant){
            drawSegment(m.getSegments());
            if (m.numberWritable){
                drawID(m,m.number);
            }
        }
    }

    public void drawSegment(ArrayList<Segment> segments){
        for (Segment segment : segments){
            graph.drawLine((int)(Math.round(100+scalingValue*segment.head.x)),(int)(Math.round(height-(100+scalingValue*segment.head.y))),(int)(Math.round(100+scalingValue*segment.tail.x)),(int)(Math.round(height-(100+scalingValue*segment.tail.y))));
        }
    }

    public void drawID(Quadrilateral quadrilateral, int i){
        LinkedList<Integer> l = Utilies.intToVerticalString(i);
        Point p =  quadrilateral.getCenter();
        if (p!=null){
            p.x -= DATACONTAINER.MONTANTWITH/2;
            for (Integer j : l ) {
                if (quadrilateral.isVertical()){
                    this.graph.drawString(j.toString(),(int) (100+scalingValue*p.x), (int) (height - (100+scalingValue*p.y)));
                    p.y -=10;
                }else {
                    this.graph.drawString(String.valueOf(i),(int) (100+scalingValue*p.x), (int) (height - (100+scalingValue*p.y)));
                }
            }
        }
    }

    public void updateFontSize(double factor){
        if (factor <= 3) {
            graph.setFont(new Font("Arial Black", Font.PLAIN, 14));
        } else if (factor <= 6){
            graph.setFont(new Font("Arial Black", Font.PLAIN, 32));
        }else if (factor <= 9){
            graph.setFont(new Font("Arial Black", Font.PLAIN, 46));
        }else {
            graph.setFont(new Font("Arial Black", Font.PLAIN, 64));
        }
    }

    public static void main(String[] args) throws IOException {
        ImageDrawer imageDrawer = new ImageDrawer(2400,1200,2);
        Pentagon pentagon = new Pentagon(new Point(200,50),500,2000,Math.PI/12,0.5);
        PentagonalArea area = new PentagonalArea(pentagon,"Area");
        //Quadrilateral rectangle = new Quadrilateral(new Segment(50,50,50,1000),2000,true,ShapeType.RECTANGLE,0,0);
        //QuadrilateralArea area = new QuadrilateralArea(rectangle,"Area");
        area.setOutLines();
        //area.windows.add(new Window(new Segment(1250,200,1250,400),500,true, ShapeType.TRAPEZIUM2,"TRAPEZIUM2",  Math.PI/16, Math.PI/10));
        area.windows.add(new Window(new Segment(1200,200,1200,400),500,false, ShapeType.TRAPEZIUM1,"TRAPEZIUM1",  Math.PI/16, Math.PI/10));
        //area.windows.add(new Window(new Segment(350, 55,350,250),75,true, ShapeType.RECTANGLE,"Rectangle",  0, 0));


        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "0", "Area")));
        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "250", "Area")));
        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "500", "Area")));
        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "750", "Area")));
        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "1250", "Area")));
        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "1500", "Area")));
        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "1750", "Area")));
        //area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "1960", "Area")));
        area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","40", "980", "Area")));
        area.setWindowsMontants();
        area.setBeamMontants();
        area.setVerticalMontant();
        area.generateMidMontant();
        imageDrawer.drawArea(area);
        imageDrawer.saveIMG("lol.jpg");
        FileWriter fileWriter = new FileWriter();
        ArrayList<Area> mdr = new ArrayList<>();
        mdr.add(area);
        fileWriter.writeFile(mdr,"", "file.tsv");
    }
}
