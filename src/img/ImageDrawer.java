package img;

import home.Area;
import home.Montant;
import home.PentagonalArea;
import home.Window;
import math.Pentagon;
import math.Point;
import math.Segment;
import math.ShapeType;

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

    public void saveIMG(String name){
        try {
            ImageIO.write(imageBuffer, "jpg", new File("src/view/resources/generatedimg/"+name+".jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void drawArea(Area area){
        for (Montant m : area.montants){
            drawSegment(m.getSegments());
        }
        for (Window w : area.windows){
            for (Montant m : w.montants ){
                drawSegment(m.getSegments());
            }
        }
    }

    public void drawSegment(ArrayList<Segment> segments){
        for (Segment segment : segments){
            graph.drawLine((int)(Math.round(segment.head.x)),(int)(Math.round(height-segment.head.y)),(int)(Math.round(segment.tail.x)),(int)(Math.round(height-segment.tail.y)));
        }
    }

    public static void main(String[] args) throws IOException {
        ImageDrawer imageDrawer = new ImageDrawer(2000,800);
        Pentagon pentagon = new Pentagon(new Point(200,50),200,1600,Math.PI/10,0.75);
        PentagonalArea area = new PentagonalArea(pentagon);
        area.setOutLines();
        area.windows.add(new Window(new Segment(500,100,500,200),200,area.getInerShape().getTheta(500),true, ShapeType.TRAPEZIUM1));
        area.setWindowsMontants();
        imageDrawer.drawArea(area);
        imageDrawer.saveIMG("lol");
    }
}