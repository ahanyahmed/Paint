/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frontend;

import shapes.Shape;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import shapes.AbstractShapeClass;
/**
 *
 * @author ahmed
 */
public class MyPanel extends javax.swing.JPanel implements DrawingEngine{

    /**
     * Creates new form MyPanel
     */
    private ArrayList<AbstractShapeClass> shapes;
    protected AbstractShapeClass dragged, resized;
    MouseAdapter mouseListener = new MouseAdapter() {

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                //refresh(null);
                
                for (AbstractShapeClass p : shapes) {
                    if (p.contains(e.getPoint())) {
                        p.setResizable(true);
                        dragged = p;
                        dragged.setDraggingPoint(e.getPoint());
                        //break;
                    }
                    else if(p.containsVertix(e.getPoint())){
                        resized = p;
                    }
                    else{
                        p.setResizable(false);
                    }
                }
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (dragged != null) {
                    dragged.moveTo(e.getPoint());
                    dragged.setDraggingPoint(e.getPoint());
                    repaint();
                }
                else if(resized != null){
                    resized.resizeTo(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                dragged = null;
                resized = null;
            }

        };
    
    /**
     * Creates new form MyPanell
     */
    public MyPanel() {
        initComponents();
        shapes = new ArrayList<AbstractShapeClass>();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void clearPanel(){
//        for(AbstractShapeClass shape : shapes)
//            removeShape(shape);
        shapes = new ArrayList<AbstractShapeClass>();
        refresh(null);
    }
    @Override
    public void paintComponent(Graphics canvas){
        super.paintComponents(canvas);
        canvas.clearRect(0, 0, 10000, 10000);
        for (Shape shape: shapes){
            shape.draw(canvas);
        }
        initComponents();
    }
    
    @Override
    public void addShape(AbstractShapeClass shape) {
        shapes.add(shape);
    }

    @Override
    public void removeShape(AbstractShapeClass shape) {
        shapes.remove(shape);
    }

    @Override
    public AbstractShapeClass[] getShapes() {
        return shapes.toArray(AbstractShapeClass[]::new);
    }

    @Override
    public void refresh(Graphics canvas) {
        this.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
