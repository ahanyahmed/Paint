/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package shapes;

import java.awt.Point;

/**
 *
 * @author Omar
 */
public interface Resizable {
    
    public void setResizingVertix(int resizingVertix);
    public int getResizingVertix();

    public boolean containsVertix(Point point);

    public void resizeTo(Point point);
}
