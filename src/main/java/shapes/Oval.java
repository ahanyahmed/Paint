/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import org.json.simple.JSONObject;
    
/**
 *
 * @author Omar
 */
public class Oval extends AbstractShapeClass {

    private int horizontalDiameter,verticalDiameter;
    private Point resizingPoint;

    public Oval(Point point,int verticalRadius,int horizontalRadius){
        setPosition(point);
        vertices = new Rectangle[4];
        horizontalDiameter = horizontalRadius*2;
        verticalDiameter= verticalRadius*2;
    }
    
    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(getColor());
        canvas.drawOval(getPosition().x, getPosition().y, horizontalDiameter,verticalDiameter);
        canvas.setColor(getFillColor());
        canvas.fillOval(getPosition().x, getPosition().y, horizontalDiameter,verticalDiameter);
        
        if (isResizable()){
            Rectangle vertix1 = new Rectangle(new Point(getPosition().x - 5, getPosition().y - 5), 10, 10);
            vertix1.setColor(Color.BLACK);
            vertix1.setFillColor(Color.BLACK);
            vertix1.draw(canvas);
            vertices[0] = vertix1;
           
            Rectangle vertix2 = new Rectangle(new Point(getPosition().x - 5 + horizontalDiameter, getPosition().y - 5), 10, 10);
            vertix2.setColor(Color.BLACK);
            vertix2.setFillColor(Color.BLACK);
            vertix2.draw(canvas);
            vertices[1] = vertix2;
            
            Rectangle vertix3 = new Rectangle(new Point(getPosition().x - 5, getPosition().y - 5 + verticalDiameter), 10, 10);
            vertix3.setColor(Color.BLACK);
            vertix3.setFillColor(Color.BLACK);
            vertix3.draw(canvas);
            vertices[2] = vertix3;
            
            Rectangle vertix4 = new Rectangle(new Point(getPosition().x - 5 + horizontalDiameter, getPosition().y - 5 + verticalDiameter), 10, 10);
            vertix4.setColor(Color.BLACK);
            vertix4.setFillColor(Color.BLACK);
            vertix4.draw(canvas);
            vertices[3] = vertix4;
        }       
    }

    @Override
    public boolean contains(Point point) {
        java.awt.Shape cir = new Ellipse2D.Double(getPosition().x, getPosition().y, horizontalDiameter,verticalDiameter);
        Area area = new Area(cir);
        return area.contains(point);
    }

    @Override
    public void moveTo(Point point) {
        int xDiffrence = point.x - getDraggingPoint().x;
        int yDiffrence =  point.y - getDraggingPoint().y;
        setPosition(new Point(getPosition().x + xDiffrence, getPosition().y + yDiffrence));
    }

    @Override
    public boolean containsVertix(Point point) {
        if (isResizable()){
            for(int i = 0; i < vertices.length; i++){
                if (vertices[i].contains(point)){
                    setResizingVertix(i);
                    resizingPoint = point;
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void resizeTo(Point point) {
        int xDisplacment, yDisplacment;
        xDisplacment = resizingPoint.x - point.x;
        yDisplacment = resizingPoint.y - point.y;
        
        switch (resizingVertix){
            case 0:
                setPosition(point);
                horizontalDiameter += xDisplacment;
                verticalDiameter += yDisplacment;
                if (verticalDiameter <= 0 && horizontalDiameter <= 0){
                    resizingVertix = 3;
                }
                else if (horizontalDiameter <= 0){
                    resizingVertix = 1;
                }
                else if (verticalDiameter <= 0){
                    resizingVertix = 2;
                }
                resizingPoint = point;
                break;
                
            case 1:
                Point newPosition = new Point(getPosition().x, getPosition().y - yDisplacment);
                setPosition(newPosition);
                horizontalDiameter -= xDisplacment;
                verticalDiameter += yDisplacment;
                if (verticalDiameter <= 0 && horizontalDiameter <= 0){
                    resizingVertix = 2;
                }
                else if (horizontalDiameter <= 0){
                    resizingVertix = 0;
                }
                else if (verticalDiameter <= 0){
                    resizingVertix = 3;
                }
                resizingPoint = point;
                break;
                
            case 2:
                newPosition = new Point(getPosition().x - xDisplacment, getPosition().y);
                setPosition(newPosition);
                horizontalDiameter += xDisplacment;
                verticalDiameter -= yDisplacment;
                if (verticalDiameter <= 0 && horizontalDiameter <= 0){
                    resizingVertix = 1;
                }
                else if (horizontalDiameter <= 0){
                    resizingVertix = 3;
                }
                else if (verticalDiameter <= 0){
                    resizingVertix = 0;
                }
                resizingPoint = point;
                break;
                
            case 3:
                horizontalDiameter -= xDisplacment;
                verticalDiameter -= yDisplacment;
                if (verticalDiameter <= 0 && horizontalDiameter <= 0){
                    resizingVertix = 0;
                }
                else if (horizontalDiameter <= 0){
                    resizingVertix = 2;
                }
                else if (verticalDiameter <= 0){
                    resizingVertix = 1;
                }
                resizingPoint = point;
                break;
        }
        
        verticalDiameter = Math.abs(verticalDiameter);
        horizontalDiameter = Math.abs(horizontalDiameter);
        System.out.println("vertix:" + resizingVertix);
    }
        @Override
    public JSONObject getJSON() {
        JSONObject shape = super.getJSON();
        shape.put("type", this.getClass().getSimpleName());
        shape.put("horizontalRadius", verticalDiameter/2);
        shape.put("verticalRadius", horizontalDiameter/2);
        return shape;

    }

    public static Oval JSONtoObject(JSONObject obj) {
        JSONObject positionJSON = (JSONObject) obj.get("position");
        JSONObject draggingPointJSON = (JSONObject) obj.get("draggingPoint");
        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        int horizontalradius = Integer.parseInt( obj.get("horizontalRadius").toString());
        int verticallradius = Integer.parseInt((String) obj.get("verticalRadius").toString());
        Oval oval = new Oval(position, horizontalradius, verticallradius);
        int colorRGB = Integer.parseInt(obj.get("color").toString());
        int fillColorRGB = Integer.parseInt(obj.get("fillColor").toString());
        oval.setColor(new Color(colorRGB, true));
        oval.setFillColor(new Color(fillColorRGB, true));
        return oval;
    }
        
    }

