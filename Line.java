/**
 * This class is used in HeapSortView.java to draw edges for the tree
 */
import javax.swing.*;
import java.awt.*;

public class Line extends JLabel {
    protected int x1, y1, x2, y2;

    public Line (int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    public void paintComponent(Graphics g) {
        g.drawLine(x1, y1, x2, y2);
    }
}
