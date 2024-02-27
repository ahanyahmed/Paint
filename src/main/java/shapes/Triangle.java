/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shapes;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import org.json.simple.JSONObject;

/**
 *
 * @author ahmed
 */
public class Triangle extends AbstractShapeClass{
    private Point point2,point3;

    public Triangle(Point point1,Point point2,Point point3) {
        setPosition(point1);
        vertices = new Rectangle[3];
        this.point2 = point2;
        this.point3 = point3;
    }
    
    @Override
    public void draw(Graphics canvas) {
        int xPoints[] = {getPosition().x,point2.x,point3.x};
        int yPoints[] = {getPosition().y,point2.y,point3.y};
        canvas.setColor(getFillColor());
        canvas.fillPolygon(xPoints, yPoints, 3);
        canvas.setColor(getColor());
        canvas.drawPolygon(xPoints, yPoints, 3);
        
        if (isResizable()){
            Rectangle vertix1 = new Rectangle(new Point(getPosition().x - 5, getPosition().y - 5), 10, 10);
            vertix1.setColor(Color.BLACK);
            vertix1.setFillColor(Color.BLACK);
            vertix1.draw(canvas);
            vertices[0] = vertix1;
           
            Rectangle vertix2 = new Rectangle(new Point(point2.x - 5, point2.y - 5), 10, 10);
            vertix2.setColor(Color.BLACK);
            vertix2.setFillColor(Color.BLACK);
            vertix2.draw(canvas);
            vertices[1] = vertix2;
            
            Rectangle vertix3 = new Rectangle(new Point(point3.x - 5, point3.y - 5), 10, 10);
            vertix3.setColor(Color.BLACK);
            vertix3.setFillColor(Color.BLACK);
            vertix3.draw(canvas);
            vertices[2] = vertix3;
            
        }
    }
    
    @Override
    public boolean contains(Point point) {
        int xPoints[] = {getPosition().x,point2.x,point3.x};
        int yPoints[] = {getPosition().y,point2.y,point3.y};
        java.awt.Polygon tri = new Polygon(xPoints, yPoints, 3);
        Area area = new Area(tri);
        return area.contains(point);
    }

    @Override
    public void moveTo(Point point) {
        int xDiffrence = point.x - getDraggingPoint().x;
        int yDiffrence =  point.y - getDraggingPoint().y;
        setPosition(new Point(getPosition().x + xDiffrence, getPosition().y + yDiffrence));
        point2 = new Point(point2.x + xDiffrence, point2.y + yDiffrence);
        point3 = new Point(point3.x + xDiffrence, point3.y + yDiffrence);
    }

    @Override
    public void resizeTo(Point point) {
        switch (resizingVertix){
            case 0:
                setPosition(point);
                break;
            case 1:
                point2 = point;
                break;
            case 2:
                point3 = point;
                break;
        }                
    }
     @Override
    public JSONObject getJSON() {
        JSONObject shape = super.getJSON();
        JSONObject JSONPoint2 = new JSONObject();
        JSONObject JSONPoint3 = new JSONObject();
        JSONPoint2.put("x", point2.x);
        JSONPoint2.put("y", point2.y);

        JSONPoint3.put("x", point3.x);
        JSONPoint3.put("y", point3.y);

        shape.put("type", this.getClass().getSimpleName());
        shape.put("point2", JSONPoint2);
        shape.put("point3", JSONPoint3);

        return shape;

    }

    public static Triangle JSONtoObject(JSONObject obj) {
        JSONObject positionJSON = (JSONObject) obj.get("position");
        JSONObject point2JSON = (JSONObject) obj.get("point2");
        JSONObject point3JSON = (JSONObject) obj.get("point3");
        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        Point point2 = new Point(Integer.parseInt(point2JSON.get("x").toString()), Integer.parseInt(point2JSON.get("y").toString()));
        Point point3 = new Point(Integer.parseInt(point3JSON.get("x").toString()), Integer.parseInt(point3JSON.get("y").toString()));
        Triangle triangle = new Triangle(position, point2, point3);
        int colorRGB = Integer.parseInt(obj.get("color").toString());
        int fillColorRGB = Integer.parseInt(obj.get("fillColor").toString());
        triangle.setColor(new Color(colorRGB, true));
        triangle.setFillColor(new Color(fillColorRGB, true));
        return triangle;
    }
}
