/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import org.json.simple.JSONObject;

/**
 *
 * @author Omar
 */
public abstract class AbstractShapeClass implements Shape, Movable, Cloneable, Resizable {
    
    private boolean resizable;
    private Point position, draggingPoint;
    private Color color,fillColor;
    protected int resizingVertix;
    protected Rectangle[] vertices;

    
    
    public int getResizingVertix() {
        return resizingVertix;
    }

    public void setResizingVertix(int resizingVertix) {
        this.resizingVertix = resizingVertix;
    }
    
    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }
    
    @Override
    public void setDraggingPoint(Point point) {
        draggingPoint = point;
    }

    @Override
    public Point getDraggingPoint() {
        return draggingPoint;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
    @Override
    public boolean containsVertix(Point point) {
        if (isResizable()){
            for(int i = 0; i < vertices.length; i++){
                if (vertices[i].contains(point)){
                    setResizingVertix(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    public JSONObject getJSON() {
        JSONObject shape = new JSONObject();
        JSONObject JSONposition = new JSONObject();
        JSONposition.put("x", getPosition().x);
        JSONposition.put("y", getPosition().y);

        shape.put("position", JSONposition);
        shape.put("color", getColor().getRGB());
        if (fillColor != null){
            shape.put("fillColor", getFillColor().getRGB());
        }
        return shape;
    }
    
}
