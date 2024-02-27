/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.lang.Math;
import org.json.simple.JSONObject;

/**
 *
 * @author Omar
 */
public class LineSegment extends AbstractShapeClass {
        
    private Point endPosition;    
    
    public LineSegment(Point point1,Point point2) {
        setPosition(point1);
        vertices = new shapes.Rectangle[2];
        endPosition = point2;
    }

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(getColor());
        canvas.drawLine(getPosition().x, getPosition().y, endPosition.x, endPosition.y);
    
        if (isResizable()){
            shapes.Rectangle vertix1 = new shapes.Rectangle(new Point(getPosition().x - 5, getPosition().y - 5), 10, 10);
            vertix1.setColor(Color.BLACK);
            vertix1.setFillColor(Color.BLACK);
            vertix1.draw(canvas);
            vertices[0] = vertix1;
           
            shapes.Rectangle vertix2 = new shapes.Rectangle(new Point(endPosition.x - 5, endPosition.y - 5), 10, 10);
            vertix2.setColor(Color.BLACK);
            vertix2.setFillColor(Color.BLACK);
            vertix2.draw(canvas);
            vertices[1] = vertix2;

        }
    }
    
    private int distance (Point pointOne, Point pointTwo){
        return (int) Math.sqrt( Math.pow(pointOne.x - pointTwo.x ,2 ) + Math.pow(pointOne.y - pointTwo.y ,2 ) );
    }

    @Override
    public boolean contains(Point point) {
        int startDistance = distance(getPosition(), point);
        int endDistance = distance(endPosition, point);
        int length = distance(getPosition(), endPosition);
        return startDistance + endDistance == length;
    }

    @Override
    public void moveTo(Point point) {
        int xDiffrence = point.x - getDraggingPoint().x;
        int yDiffrence =  point.y - getDraggingPoint().y;
        setPosition(new Point(getPosition().x + xDiffrence, getPosition().y + yDiffrence));
        endPosition = new Point(endPosition.x + xDiffrence, endPosition.y + yDiffrence);
    }

    @Override
    public void resizeTo(Point point) {
        switch (resizingVertix){
            case 0:
                setPosition(point);
                break;
            case 1:
                endPosition = point;
                break;
        }                
    }
    
    @Override
    public JSONObject getJSON() {
        JSONObject shape = super.getJSON();
        JSONObject JSONendPoint = new JSONObject();
        JSONendPoint.put("x", endPosition.x);
        JSONendPoint.put("y", endPosition.y);
        shape.put("type", this.getClass().getSimpleName());
        shape.put("endPoint", JSONendPoint);
        return shape;
    }

    public static LineSegment JSONtoObject(JSONObject obj) {
        JSONObject positionJSON = (JSONObject) obj.get("position");
        JSONObject endPointJSON = (JSONObject) obj.get("endPoint");

        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        Point endPoint = new Point(Integer.parseInt(endPointJSON.get("x").toString()), Integer.parseInt(endPointJSON.get("y").toString()));
        LineSegment line = new LineSegment(position, endPoint);
        int colorRGB = Integer.parseInt(obj.get("color").toString());
        line.setColor( new Color(colorRGB, true));
        return line;
    }
    
}
