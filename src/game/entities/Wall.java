package game.entities;

import game.RenderLayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public class Wall extends Entity {
    
    private RenderLayer layer;
    private Image img;
    private ImageIcon icon; 
    
    public Wall(RenderLayer l, int xPosition, int yPosition, int width, int height) {
        super(l);
        this.layer = l;
        
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        
        this.width = width;
        this.height = height;
        // color
        this.color = new Color(125,125,125);
        this.color = Color.BLACK;
        // image
        //this.icon = new ImageIcon(this.getClass().getResource("brick.jpg"));
        //this.img = icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("enemy.png"));
        this.img = icon.getImage();
    }

    @Override
    public void update() {
        // --
    }

    @Override
    public void render(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.xPosition, this.yPosition, this.width, this.height);
        g.setColor(Color.PINK);
        //g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
    }
    
    // get-okraje
    public Rectangle getBounds() {
        return new Rectangle(this.xPosition, this.yPosition, this.width, this.height);
    }   

}
