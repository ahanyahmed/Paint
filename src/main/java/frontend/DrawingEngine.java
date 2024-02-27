/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package frontend;

import shapes.Shape;
import shapes.AbstractShapeClass;

/**
 *
 * @author Omar
 */
public interface DrawingEngine {
    /* manage shapes objects */
    public void addShape(AbstractShapeClass shape);
    public void removeShape(AbstractShapeClass shape);
    /* return the created shapes objects */
    public AbstractShapeClass[] getShapes();
    /* redraw all shapes on the canvas */
    public void refresh(java.awt.Graphics canvas);
}
