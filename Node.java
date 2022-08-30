/**
 * This class draws the nodes in the HeapSortView.java
 */

import javax.swing.*;
import java.awt.*;

public class Node extends JLabel {

    /**
     * Colors used to project actions made of the node
     */
    private final Color light_yellow    = Color.decode("#f6e58d");
    private final Color light_brown     = Color.decode("#f0932b");
    private final Color light_green     = Color.decode("#badc58");
    private final Color swan_white      = Color.decode("#f7f1e3");
    private final Color dark_green      = Color.decode("#6ab04c");
    private final Color dark_orange      = Color.decode("#cd6133");
    private final Color light_blue      = Color.decode("#686de0");
    private final Color dark_blue       = Color.decode("#30336b");
    private final Color golden_yellow   = Color.decode("#f9ca24");
    private final Color brown_yellow    = Color.decode("#de7119");
    private final Color violet          = Color.decode("#be2edd");

    /**
     * Node visual properties:
     * text_color - node's text color
     * background_color - node's background color
     * border_color - node's border color
     */
    private Color text_color = dark_orange;
    private Color background_color = light_yellow;
    private Color border_color = light_brown;

    /**
     * Node properties:
     * x_bound - the x bound of the node in the animation Panel
     * y_bound - the y bound of the node in the animation Panel
     * value - the value of element to be sorted
     */
    protected int x_bound;
    protected int y_bound;
    protected int value;



    public Node (int value, int x_bound, int y_bound){
        this.x_bound = x_bound;
        this.y_bound = y_bound;
        this.value = value;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 40, y = 40, radius = 40;

        g.setColor(border_color);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        g.setColor(background_color);
        g.fillOval(x - radius + 2, y - radius + 2, radius * 2 - 4, radius * 2 - 4);

        g.setColor(text_color);
        String text = String.valueOf(value);

        Font font = new Font("Comic Sans MS", 1, 10);

        FontMetrics metrics = g.getFontMetrics(font);
        int sx = 7 + (60 - metrics.stringWidth(text)) / 2;
        int sy = 7 + ((60 - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, sx, sy);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
        g.setFont(newFont);
    }

    public void setSortedColor(){
        updateNode(violet, golden_yellow, brown_yellow);
        repaint();
    }

    public void setCheckedColor(){
        updateNode(swan_white, light_blue, dark_blue);
        repaint();
    }

    public void setDefaultColor(){
        updateNode(dark_orange, light_yellow, light_brown);
        repaint();
    }

    public void setSelectedColor(){
        updateNode(swan_white, light_green, dark_green);
        repaint();
    }

    private void updateNode(Color text_color, Color background_color, Color border_color){
        this.text_color = text_color;
        this.background_color = background_color;
        this.border_color = border_color;
    }



}
