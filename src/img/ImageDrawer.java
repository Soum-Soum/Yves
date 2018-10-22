package img;

import home.*;
import home.Window;
import math.*;
import math.Point;
import view.obj.ViewBeam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageDrawer {

    BufferedImage imageBuffer;
    int width, height;
    Graphics2D graph;
    Stroke stroke;

    public ImageDrawer(int width ,int height) {
        this.width=width;
        this.height=height;
        this.imageBuffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        graph = imageBuffer.createGraphics();
        graph.setColor(Color.WHITE);
        graph.fill(new Rectangle(0,0,width,height));
        graph.setColor(Color.BLACK);
        stroke = new BasicStroke(1);
        graph.setStroke(stroke);
    }

    public String saveIMG(String name){
        try {
            ImageIO.write(imageBuffer, "jpg", new File("src/view/resources/generatedimg/"+name+".jpg"));
            return "src/view/resources/generatedimg/"+name+".jpg";
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void drawArea(Area area){
        int i=1;
        for (Montant m : area.outlinesMontants){
            drawSegment(m.getSegments());
            /*drawID(m,i);
            i++;*/
        }
        for (Window w : area.windows){
            if (w.haveTraverse){drawSegment(w.traverse.getSegments());}
            for (Montant m : w.montants ){
                drawSegment(m.getSegments());
                /*drawID(m,i);
                i++;*/
            }
        }
        for (Beam b : area.beams){
            for (Montant m : b.montants){
                drawSegment(m.getSegments());
                /*drawID(m,i);
                i++;*/
            }
        }
        for (Montant m : area.verticalMontant){
            drawSegment(m.getSegments());
            /*drawID(m,i);
            i++;*/
        }
    }

    public void drawSegment(ArrayList<Segment> segments){
        for (Segment segment : segments){
            graph.drawLine((int)(Math.round(segment.head.x)),(int)(Math.round(height-segment.head.y)),(int)(Math.round(segment.tail.x)),(int)(Math.round(height-segment.tail.y)));
        }
    }

    public void drawID(Montant m, int i){
        Point center = m.getWritingPoint(i);
        this.graph.drawString(String.valueOf(i),(int)(center.x),(int)(height - center.y));
    }

    public static void main(String[] args) throws IOException {
        ImageDrawer imageDrawer = new ImageDrawer(2400,1000);
        Pentagon pentagon = new Pentagon(new Point(200,50),200,2000,Math.PI/6,0.5);
        PentagonalArea area = new PentagonalArea(pentagon,"lol");
        //Quadrilateral quadrilateral = new Quadrilateral(new Segment(200,50,200,250),500,Math.PI/10,true,ShapeType.TRAPEZIUM3);
        //QuadrilateralArea area = new QuadrilateralArea(quadrilateral,"lol");
        area.setOutLines();
        area.windows.add(new Window(new Segment(500,100,500,200),200,true, ShapeType.TRAPEZIUM1,"",Math.PI/6,0));
        area.windows.add(new Window(new Segment(1000,400,1000,600),500,true, ShapeType.TRAPEZIUM4,"",  0, Math.PI/10));
        area.windows.add(new Window(new Segment(800,100,800,350),750,true, ShapeType.TRAPEZIUM2,"", Math.PI/12,0));
        area.windows.add(new Window(new Segment(1200,300,1200,400),500,true, ShapeType.RECTANGLE,"", 0,0));
        area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","80", "750", "lol")));
        area.beams.add(Beam.BuildBeam(area,new ViewBeam("80","80", "1150", "lol2")));
        area.setWindowsMontants();
        area.setBeamMontants();
        area.setVerticalMontant();
        area.generateMidMontant();
        imageDrawer.drawArea(area);
        imageDrawer.saveIMG("lol");
    }
}
