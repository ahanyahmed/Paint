/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Area;
import javax.swing.text.Position;
import org.json.simple.JSONObject;

/**
 *
 * @author Omar
 */
public class Rectangle extends AbstractShapeClass {
    
    private int width,height;
    private Point resizingPoint;

    public Rectangle(Point point,int width,int height){
        setPosition(point);
        vertices = new Rectangle[4];
        this.width=width;
        this.height=height;
    }

    public int getHeight(){
        return height;
    }
    
    public int getWidth(){
        return width;
    }
    
    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(getFillColor());
        canvas.fillRect(getPosition().x, getPosition().y, width, height);
        canvas.setColor(getColor());
        canvas.drawRect(getPosition().x, getPosition().y, width, height);
        
        if (isResizable()){
            
            Rectangle vertix1 = new Rectangle(new Point(getPosition().x - 5, getPosition().y - 5), 10, 10);
            vertix1.setColor(Color.BLACK);
            vertix1.setFillColor(Color.BLACK);
            vertix1.draw(canvas);
            vertices[0] = vertix1;
           
            Rectangle vertix2 = new Rectangle(new Point(getPosition().x - 5 + width, getPosition().y - 5), 10, 10);
            vertix2.setColor(Color.BLACK);
            vertix2.setFillColor(Color.BLACK);
            vertix2.draw(canvas);
            vertices[1] = vertix2;
            
            Rectangle vertix3 = new Rectangle(new Point(getPosition().x - 5, getPosition().y - 5 + height), 10, 10);
            vertix3.setColor(Color.BLACK);
            vertix3.setFillColor(Color.BLACK);
            vertix3.draw(canvas);
            vertices[2] = vertix3;
            
            Rectangle vertix4 = new Rectangle(new Point(getPosition().x - 5 + width, getPosition().y - 5 + height), 10, 10);
            vertix4.setColor(Color.BLACK);
            vertix4.setFillColor(Color.BLACK);
            vertix4.draw(canvas);
            vertices[3] = vertix4;
        }
    }

    @Override
    public boolean contains(Point point) {
        java.awt.Rectangle rec = new java.awt.Rectangle(getPosition().x, getPosition().y, getWidth(), getHeight());
        Area area = new Area(rec);
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
                width += xDisplacment;
                height += yDisplacment;
                if (height <= 0 && width <= 0){
                    resizingVertix = 3;
                }
                else if (width <= 0){
                    resizingVertix = 1;
                }
                else if (height <= 0){
                    resizingVertix = 2;
                }
                resizingPoint = point;
                break;
                
            case 1:
                Point newPosition = new Point(getPosition().x, getPosition().y - yDisplacment);
                setPosition(newPosition);
                width -= xDisplacment;
                height += yDisplacment;
                if (height <= 0 && width <= 0){
                    resizingVertix = 2;
                }
                else if (width <= 0){
                    resizingVertix = 0;
                }
                else if (height <= 0){
                    resizingVertix = 3;
                }
                resizingPoint = point;
                break;
                
            case 2:
                newPosition = new Point(getPosition().x - xDisplacment, getPosition().y);
                setPosition(newPosition);
                width += xDisplacment;
                height -= yDisplacment;
                if (height <= 0 && width <= 0){
                    resizingVertix = 1;
                }
                else if (width <= 0){
                    resizingVertix = 3;
                }
                else if (height <= 0){
                    resizingVertix = 0;
                }
                resizingPoint = point;
                break;
                
            case 3:
                width -= xDisplacment;
                height -= yDisplacment;
                if (height <= 0 && width <= 0){
                    resizingVertix = 0;
                }
                else if (width <= 0){
                    resizingVertix = 2;
                }
                else if (height <= 0){
                    resizingVertix = 1;
                }
                resizingPoint = point;
                break;
        }
        height = Math.abs(height);
        width = Math.abs(width);
//        System.out.println("vertix:" + resizingVertix);
    }
    
    @Override
    public JSONObject getJSON() {
        JSONObject shape = super.getJSON();
        shape.put("type", this.getClass().getSimpleName());
        shape.put("height", width);
        shape.put("width", height);
        return shape;

    }

    public static Rectangle JSONtoObject(JSONObject obj) {
        JSONObject positionJSON = (JSONObject) obj.get("position");
        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        int height = Integer.parseInt(obj.get("height").toString());
        int width = Integer.parseInt((String) obj.get("width").toString());
        Rectangle rect = new Rectangle(position, height, width);
        int colorRGB = Integer.parseInt(obj.get("color").toString());
        int fillColorRGB = Integer.parseInt(obj.get("fillColor").toString());
        rect.setColor(new Color(colorRGB, true));
        rect.setFillColor(new Color(fillColorRGB, true));
        return rect;
    }
}
