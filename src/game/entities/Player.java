package game.entities;

import game.RenderLayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author David
 */
public class Player extends Entity implements KeyListener{
    private final Random rnd;
    private int speedSave;
    public boolean walking;
    private int nextKey;
    private boolean upWay;
    private boolean downWay;
    private boolean rightWay;
    private boolean leftWay;
    private int shield;
    private RenderLayer layer;
    private Color shieldColor;
    private int HP;
    private Font HPFont;
    private Image imgNormal;
    private Image imgInfused;
    private Image imgRight;
    private Image imgLeft;
    private Image imgTop;
    private Image imgBot;
    private Image imgRightInfused;
    private Image imgLeftInfused;
    private Image imgTopInfused;
    private Image imgBotInfused;
    private Image imgShield;

    private ImageIcon icon; 
    private boolean infused;
    private int infusedCounter;
    private int infusedDuration;
    private int infusedCooldownFinal;
    private int infusedDurationFinal;
    
    public Player(RenderLayer l,int HP,int infusedCooldownFinal,int infusedDurationFinal, int playerSaveSpeed) {
        super(l);
        this.layer = l;
        this.rnd = new Random();
        
        // pozice
        this.xPosition = rnd.nextInt(750)+20;
        this.yPosition = rnd.nextInt(400)+50;
        // rozměry
        this.width = 20;
        this.height = 20;
        //rychlost
        this.speed = 0;
        this.speedSave = playerSaveSpeed;
        // barva
        this.color = Color.BLUE;
        // kontrola
        this.walking = false;
        this.nextKey = 0;
        this.upWay = false;
        this.downWay = false;
        this.rightWay = false;
        this.leftWay = false;
        // shield
        this.shield = 5;
        this.shieldColor = Color.RED;
        // HP
        this.HP = HP;
        // font
        this.HPFont = new Font("Tahoma", Font.BOLD, 16);
        // image
        this.icon = new ImageIcon(this.getClass().getResource("player-right.png"));
        this.imgRight= icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("player-left.png"));
        this.imgLeft = icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("player-top.png"));
        this.imgTop = icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("player-bot.png"));
        this.imgBot = icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("player-infused-right.png"));
        this.imgRightInfused= icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("player-infused-left.png"));
        this.imgLeftInfused = icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("player-infused-top.png"));
        this.imgTopInfused = icon.getImage();
        this.icon = new ImageIcon(this.getClass().getResource("player-infused-bot.png"));
        this.imgBotInfused = icon.getImage();
        
        this.icon = new ImageIcon(this.getClass().getResource("shield.png"));
        this.imgShield = icon.getImage();
        // zabíjení nepřátel - povoleno/zakázáno
        this.infused = false;
        this.infusedCounter = 0;
        this.infusedDuration = 0;
        this.infusedCooldownFinal = infusedCooldownFinal;
        this.infusedDurationFinal = infusedDurationFinal;
        

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
        // vykreslení hráče
        //g.setColor(this.color);
        //g.fillRect(this.xPosition, this.yPosition, this.width, this.height);
        //g.drawImage(this.imgNormal, this.xPosition, this.yPosition, null);
        if(infused){
            if(this.way == 0){
                if(this.xPosition + this.speed > this.xPosition)
                    g.drawImage(this.imgRightInfused, this.xPosition, this.yPosition, null);
                else
                    g.drawImage(this.imgLeftInfused, this.xPosition, this.yPosition, null);
            }
            else{
                if(this.yPosition + this.speed > this.yPosition)
                    g.drawImage(this.imgBotInfused, this.xPosition, this.yPosition, null);
                else
                    g.drawImage(this.imgTopInfused, this.xPosition, this.yPosition, null);
            }
        }else{
            if(this.way == 0){
                if(this.xPosition + this.speed > this.xPosition)
                    g.drawImage(this.imgRight, this.xPosition, this.yPosition, null);
                else
                    g.drawImage(this.imgLeft, this.xPosition, this.yPosition, null);
            }
            else{
                if(this.yPosition + this.speed > this.yPosition)
                    g.drawImage(this.imgBot, this.xPosition, this.yPosition, null);
                else
                    g.drawImage(this.imgTop, this.xPosition, this.yPosition, null);
            }
        }
        // vykreslení štítu
        if(this.shield > 0){
            g.setColor(this.shieldColor);
            //g.drawRect(this.xPosition-1, this.yPosition-1, this.width+1, this.height+1);
            g.drawImage(imgShield, this.xPosition-5, this.yPosition-5, null);
            g.drawString(this.shield + "", this.xPosition, this.yPosition);
        }
        // vykreslení HP
        g.setColor(Color.RED);
        g.setFont(this.HPFont);
       // g.drawString("" + this.getHP(), this.xPosition+this.width/10, this.yPosition+this.height/5*4);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.walking == false){
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                this.goLeft();
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                this.goRight();
            }
            if(e.getKeyCode() == KeyEvent.VK_UP){
                this.goUp();
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                this.goDown();
            }
        }
        else{
            this.setNextKey(e.getKeyCode());
            this.run();
        }    
        
    }
    
    public void run(){
        if(this.getNextKey() == KeyEvent.VK_UP && this.getUpWay() == true)
                this.goUp();
        else if(this.getNextKey() == KeyEvent.VK_DOWN && this.getDownWay() == true)
                this.goDown();
        else if(this.getNextKey() == KeyEvent.VK_LEFT && this.getLeftWay() == true)
                this.goLeft();
        else if(this.getNextKey() == KeyEvent.VK_RIGHT && this.getRightWay() == true)
                this.goRight();
    }
    
    
    @Override
    public void keyReleased(KeyEvent e) {
        //if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
        //   this.speed = 0;
        //}
    }
    
    public void playerShieldDischarging(){
        if(this.shield > 0)
            shield--;            
    }
    
    public void setWalking(boolean walking){
        this.walking = walking;
    }
    
    private void goRight(){
        this.way = 0;
        if(this.getSpeedSave() < 0)
            this.speed = this.getSpeedSave()*-1;
        else
            this.speed = this.getSpeedSave();
        this.walking = true;
    }
    private void goLeft(){
        this.way = 0;
        if(this.getSpeedSave() > 0)
            this.speed = this.getSpeedSave()*-1;
        else
            this.speed = this.getSpeedSave();
        this.walking = true;
    }
    private void goUp(){
        this.way = 1;
        if(this.getSpeedSave() > 0)
            this.speed = this.getSpeedSave()*-1;
        else
            this.speed = this.getSpeedSave();
        this.walking = true;
    }
    private void goDown(){
        this.way = 1;
        if(this.getSpeedSave() < 0)
            this.speed = this.getSpeedSave()*-1;
        else
            this.speed = this.getSpeedSave();
        this.walking = true;
    }

 
    public boolean getUpWay() {
        return upWay;
    }

    public void setUpWay(boolean upWay) {
        this.upWay = upWay;
    }

    public boolean getDownWay() {
        return downWay;
    }

    public void setDownWay(boolean downWay) {
        this.downWay = downWay;
    }

    public boolean getRightWay() {
        return rightWay;
    }

    public void setRightWay(boolean rightWay) {
        this.rightWay = rightWay;
    }

    public boolean getLeftWay() {
        return leftWay;
    }

    public void setLeftWay(boolean leftWay) {
        this.leftWay = leftWay;
    }
 
    public int getNextKey() {
        return nextKey;
    }

    public void setNextKey(int nextKey) {
        this.nextKey = nextKey;
    }

    public int getSpeedSave() {
        return speedSave;
    }

    public void setSpeedSave(int speedSave) {
        this.speedSave = speedSave;
    }
    
    // get-okraje
    public Rectangle getBounds() {
        return new Rectangle(this.xPosition, this.yPosition, this.width, this.height);
    } 

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getHP() {
        return this.HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public boolean getInfused() {
        return infused;
    }

    public void setInfused(boolean infused) {
        this.infused = infused;
    }

    public int getInfusedCounter() {
        return this.infusedCounter;
    }

    public void setInfusedCounter(int infusedCounter) {
        this.infusedCounter = infusedCounter;
    }

    public int getInfusedDuration() {
        return infusedDuration;
    }

    public void setInfusedDuration(int infusedDuration) {
        this.infusedDuration = infusedDuration;
    }

    public int getInfusedCooldown() {
        return this.getInfusedCooldownFinal();
    }

    public int getInfusedDurationFinal() {
        return this.infusedDurationFinal;
    }

    public int getInfusedCooldownFinal() {
        return infusedCooldownFinal;
    }

    public void setInfusedCooldownFinal(int infusedCooldownFinal) {
        this.infusedCooldownFinal = infusedCooldownFinal;
    }

    public void setInfusedDurationFinal(int infusedDurationFinal) {
        this.infusedDurationFinal = infusedDurationFinal;
    }
}
