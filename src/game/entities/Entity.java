/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities;

import game.RenderLayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author David
 */
abstract public class Entity {
    protected int xPosition;
    protected int yPosition;
    protected int way;
    protected int speed;
    protected Color color;
    protected int width;
    protected int height;
    protected RenderLayer map;
    
    public Entity(RenderLayer l){
        this.map = l;
    }
    
    abstract public void update();
    abstract public void render(Graphics g);

    // Gettery a settery
    public int getxPosition() {
        return xPosition;
    }
    public void setxPosition(int xPositon) {
        this.xPosition = xPositon;
    }    
    public int getyPosition() {
        return yPosition;
    }
    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWay(){
        return this.way;
    }   
    public void setWay(int way){
        this.way = way;
    }
    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public boolean collisionWall(Wall wall,int x,int y) {
        if(wall.getBounds().intersects(this.getBounds(x,y)))
            return true;
        else
            return false;
    }
    
    public boolean collisionFood(Food food,int x,int y) {
        if(food.getBounds().intersects(this.getBounds(x,y)))
            return true;
        else
            return false;
    }
    
    // get-okraje
    public Rectangle getBounds(int shiftX,int shiftY){
        return new Rectangle(this.xPosition+shiftX, this.yPosition+shiftY, this.width, this.height);
    }




    
}
