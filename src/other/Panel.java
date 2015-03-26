package other;

import game.RenderLayer;
import game.entities.Enemy;
import game.entities.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author David
 */
public class Panel {
    private Font font;
    private Font scoreFont;
    private Player player;
    private boolean playerFoodCollision;
    private boolean playerEnemyCollision;
    private boolean enemyEaten;
    private RenderLayer layer;
    
    public Panel(Player player, RenderLayer l){
        this.font = new Font("Arial", Font.BOLD, 12);
        this.scoreFont = new Font("Arial", Font.BOLD,15);
        this.player = player;
        this.playerFoodCollision = false;
        this.playerEnemyCollision = false;
        this.layer = l;
        this.enemyEaten = false;
    }
    
    public void paint(Graphics g){
        g.setFont(this.font);
    // pozadí
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,layer.getxResolution()+20 , 50);
    // LEVEL
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("----LEVEL----",50,15);
        g.drawString("LVL:", 65, 35);
        g.drawString(layer.getLEVEL() + "", 95, 35);
    // score
        // score
        if(this.playerFoodCollision)
            g.setColor(Color.ORANGE);
        else
            g.setColor(Color.LIGHT_GRAY);
        g.drawString("----SCORE----",425,15);
        // score number
        g.drawString(layer.getScore() +"", 470, 30);
        g.drawString(layer.getFoodNumber() - layer.getScore() + "", 470, 45);
        // score text
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Your:", 435, 30);
        g.drawString("Left:", 438, 45);
    // životy
        if(this.playerEnemyCollision)
            g.setColor(Color.RED);
        else
            g.setColor(Color.LIGHT_GRAY);
        g.drawString("----HP----",250,15);
        g.drawString(player.getHP() + "", 290, 30);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Your:", 255, 30);
        
    // FPS
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("FPS: " + layer.getFPS(), 820,15);
    // Infused
            // infused
        if(player.getInfused())
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.LIGHT_GRAY);
        g.drawString("----INFUZE----", 325, 15);
            // duration number
        g.drawString(player.getInfusedDuration() + "", 390, 30);
            // duration text
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Duration:", 325, 30);        
            // cooldown text
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Cooldown:", 325, 45);
            // cooldown number
        g.drawString(player.getInfusedCooldown() - player.getInfusedCounter() + "", 390, 45);
    // Shield
            // shield text
        if(player.getShield() == 0)
            g.setColor(Color.LIGHT_GRAY);
        else
            g.setColor(Color.CYAN);
        g.drawString("----SHIELD----", 525, 15);
            // shield duration number
        g.drawString(player.getShield() + "", 590, 30);
            // shield duration text
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Duration:", 525, 30);
    // Speed
        g.drawString("----SPEED----", 625, 15);
            // player
        g.drawString("Your:", 635, 30);
        g.drawString(player.getSpeedSave() + "", 670, 30);
            // enemy
        g.drawString("Enemy:", 620, 45);
        g.drawString(layer.getEnemySpeed() + "", 670, 45);
    // Enemy
        g.drawString("----ENEMY----",725 , 15);
        g.drawString("Left:", 740, 35);
        if(this.enemyEaten)
            g.setColor(Color.RED);
        else
            g.setColor(Color.LIGHT_GRAY);
        g.drawString(layer.getEnemyNumber() + "", 770, 35);
    }
    public void setPlayerFoodCollision(boolean playerFoodCollision){
        this.playerFoodCollision = playerFoodCollision;
    }
    public void setPlayerEnemyCollision(boolean playerEnemyCollision){
        this.playerEnemyCollision = playerEnemyCollision;
    }

    public boolean getEnemyEaten() {
        return enemyEaten;
    }

    public void setEnemyEaten(boolean enemyEaten) {
        this.enemyEaten = enemyEaten;
    }
    
}
