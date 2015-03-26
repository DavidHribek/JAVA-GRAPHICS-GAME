/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities;

import game.RenderLayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public class Enemy extends Entity {

    private RenderLayer layer;
    // font
    private Font myFont;
    private int HP;
    private Image img;
    private ImageIcon icon; 
    
    public Enemy(RenderLayer l, int speed) {
        super(l);
        this.layer = l;
        // font
        this.myFont = new Font("Tahoma", Font.BOLD, 14);
        // životy
        this.HP = 1;
        // barva
        Random rnd = new Random();
        this.color = new Color(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
        this.color = Color.red;
        //this.color = Color.RED;
        // rychlost
        this.speed = speed;
        // pozice
        this.xPosition = rnd.nextInt(750)+20;
        this.yPosition = rnd.nextInt(400)+50;
        // rozměry
        this.width = 20;
        this.height = 20;
        // směr
        this.way = rnd.nextInt(2);
        // image
        this.icon = new ImageIcon(this.getClass().getResource("enemy.png"));
        this.img = icon.getImage();
    }

    @Override
    public void update() {
        switch(this.way){
            case 0: this.xPosition += this.getSpeed();
                    break;
            case 1: this.yPosition += this.getSpeed();
                    break;
        }
    }
    
    @Override
    public void render(Graphics g) {
        // nepřitel
        //g.setColor(this.color);
        //g.fillRect(this.xPosition, this.yPosition, this.width, this.height);
        g.drawImage(this.img, this.xPosition, this.yPosition, null);
        // HP
        g.setFont(this.myFont);
        g.setColor(Color.BLACK);
        //g.drawString("" + this.getHP(), this.xPosition+this.width/10, this.yPosition+this.height/5*4);
    }
    
    // výpis rychlosti nepřítele
    private void drawSpeed(Graphics g,int i){
        g.drawString("" + this.getSpeed()*i, this.xPosition+this.width/4, this.yPosition+this.height/5*4);
    }
    
    public int getHP() {
        return this.HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
    
}
