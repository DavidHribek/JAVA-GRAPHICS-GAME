package game.entities;

import game.RenderLayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public class Food extends Entity {
    private Random rnd;
    private RenderLayer layer;
    private Image img;
    private ImageIcon icon; 
    
    public Food(RenderLayer l, int x, int y){
        super(l);
        this.layer = l;
        this.xPosition = x;
        this.yPosition = y;
        this.width = 20;
        this.height = 20;
        this.rnd = new Random();
        this.color = Color.LIGHT_GRAY;
        this.way = rnd.nextInt(2);
        
        this.icon = new ImageIcon(this.getClass().getResource("apple.fw.png"));
        this.img = icon.getImage();
    }

    @Override
    public void update() {
        // -------
    }

    @Override
    public void render(Graphics g) {
    // --- slabší PC - kuličky
        //g.setColor(this.color);
        //g.fillOval(this.xPosition, this.yPosition, this.width, this.height);
        
    // -- silnější PC - jablka
        g.drawImage(this.img,this.xPosition,this.yPosition, null);
    }    
    // get-okraje
    public Rectangle getBounds() {
        return new Rectangle(this.xPosition, this.yPosition, this.width, this.height);
    } 
}
