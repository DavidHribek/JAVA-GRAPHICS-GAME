package game.entities;

import game.RenderLayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author David
 */
public class Mouse extends Entity implements MouseMotionListener {

    public Mouse(RenderLayer l) {
        super(l);
        this.color = Color.GREEN;
        this.width = this.height = 25;
        this.xPosition = 800 / 2 - this.width / 2;
        this.yPosition = 600 / 2 - this.height /2; 
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.xPosition, this.yPosition, this.width, this.height);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.xPosition = e.getX() - this.width / 2;
        this.yPosition = e.getY() - this.height / 2; 
    }
}
