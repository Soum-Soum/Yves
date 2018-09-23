package view;

import home.Area;
import home.PentagonalArea;
import home.QuadrilateralArea;
import math.Pentagon;
import math.Point;
import math.Quadrilateral;
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
        drawSegment(area.getShape().getSegments());
        drawSegment(area.getInerShape().getSegments());
    }

    public void drawSegment(ArrayList<Segment> segments){
        for (Segment segment : segments){
            graph.drawLine((int)(segment.head.x),(int)(height-segment.head.y),(int)(segment.tail.x),(int)(height-segment.tail.y));
        }
    }

    public static void main(String[] args) throws IOException {
        ImageDrawer imageDrawer = new ImageDrawer(1200,1200);
        Pentagon pentagon = new Pentagon(new Point(1,1),200,1000,Math.PI/10,0.5);
        PentagonalArea area = new PentagonalArea(pentagon);
        area.setOutLines();
        System.out.println("SHAPE \n");
        area.getShape().print();
        System.out.println("INER SHAPE \n");
        area.getInerShape().print();
        imageDrawer.drawArea(area);
        imageDrawer.saveIMG("lol");
    }
}
