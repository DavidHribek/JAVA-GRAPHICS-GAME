/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.entities.Enemy;
import game.entities.Entity;
import game.entities.Food;
import game.entities.Wall;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import other.Map;
import game.entities.Mouse;
import other.Panel;
import game.entities.Player;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.awt.image.ImageObserver.ABORT;
import java.util.Random;
import java.util.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import other.Level;

/**
 *
 * @author David
 */
public class RenderLayer extends Canvas implements Runnable{
    private boolean isRunning;
    private boolean isGameOver;
    private boolean isWinner;
    private int score;
    private Random rnd;
    private Icon icon;
    private Image backgroundImage;
    private Panel topPanel;
    // version
    private String version;
    // font
    Font myFont;
    // rozlišení
    private int resolutionX;
    private int resolutionY;
    private boolean fullScreen;
    // FPS
    private int FPS;
    // počet nepřátel
    private int enemyNumber;
    // timer
    private Timer timer;
    // LEVEL
    private int LEVEL;

    private Mouse mouse;
    private ArrayList<Enemy> enemies;
    private Game game;
    private ArrayList<Wall> walls;
    private Map map;
    private Player player;
    private ArrayList<Food> food;
    private ArrayList<Food> food_to_destroy;
    private Graphics g;
    private int FoodNumber;
    
    private int enemySpeed;
    private int playerSaveSpeed;
    private int playerInfusedCooldownFinal;
    private int playerInfusedDurationFinal;
    private int playerHP;
    private Level LVL;

    public RenderLayer(Game game){
        super();
        this.game = game;
        this.isRunning = false;
        this.isGameOver = false;
        this.isWinner = false;
        this.rnd = new Random();
        
        this.icon = new ImageIcon("ww.png",
                               "ikonka");
        //this.backgroundImage = icon.getImage();
        // font
        this.myFont = new Font("Tahoma", Font.BOLD, 18);
        // timer
        this.timer = new Timer();
        
        this.score = 0;
        // LEVEL 
        this.LEVEL = 1;
        this.playerSaveSpeed = 5;
        this.enemySpeed = 1;
        this.enemyNumber = 1;
        // version
        this.version = "Version 2.0";
        // rozlišení obrazovky
        this.resolutionX = 890;
        this.resolutionY = 600;
        this.fullScreen = false;
        this.setSize(new Dimension(this.resolutionX,this.resolutionY));
        //fullscreen
        this.fullscreen(false);
        // počet jídla
        this.FoodNumber = 0;
        // objekty
        this.mouse = new Mouse( this );
        this.addMouseMotionListener( this.mouse );
        this.enemies = new ArrayList<Enemy>();
        this.walls = new ArrayList<Wall>();
        this.map = new Map( this );
        this.player = new Player( this , this.playerHP , this.playerInfusedCooldownFinal , this.playerInfusedDurationFinal , this.playerSaveSpeed );
        this.food = new ArrayList<Food>();

        this.LVL = new Level( this.LEVEL , this , this.player );
        // panel
        this.topPanel = new Panel( this.player , this );
        // vytvoření objektů
        this.wallSpawn();
        this.enemySpawn();
        this.foodSpawn();
        // Naslouchání kláves
        addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {     //puštění klávesy
                player.keyReleased(e);
            }
            @Override
            public void keyPressed(KeyEvent e) {    //stisk klavesy
                player.keyPressed(e);
            }
        });
        setFocusable(true); 
    }
    
    // fullscreen
    private void fullscreen(boolean full){
        if(full){
            this.game.setVisible(false);
            this.game.dispose();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.resolutionX = (int) screenSize.getWidth();
            this.resolutionY = (int) screenSize.getHeight();
            this.setSize(new Dimension(this.resolutionX,this.resolutionY));
            this.game.setUndecorated(true);
            this.game.setVisible(true);
            this.fullScreen = true;
        }
    }
    
    @Override
    public void run() {
        long lastTimeCycle = System.nanoTime();
        long lastTimeOutput = System.currentTimeMillis();
        double unprocessedTicks = 0;
        double nsPerTick = Math.pow(10,9)/60;
        int FPS = 0;
        int ticks = 0;
        
        while(this.isRunning){
            long nowTimeCycle = System.nanoTime();
            unprocessedTicks += (nowTimeCycle - lastTimeCycle) / nsPerTick;
            lastTimeCycle = nowTimeCycle;
            
            while(unprocessedTicks >= 1){
                ticks++;
                unprocessedTicks--;
                this.update();
            }
            FPS++;
            this.render(g);
            this.gameFunctions();

            if(System.currentTimeMillis() - lastTimeOutput > 1000){
                lastTimeOutput += 1000;
                System.out.println("Ticks: " + ticks + ", FPS: " + FPS);
                this.FPS = FPS;
                FPS = 0;
                ticks = 0;
                this.everySecondFunctions();
            }
        }
    } 
    // vyrendrování
    private void render(Graphics g) {
         BufferStrategy buffer = this.getBufferStrategy();
         if( buffer == null){
             this.createBufferStrategy( 3 );
             return;
         }
        g = buffer.getDrawGraphics();
        // vykreslení
        this.paint(g);
        // vyprázdnění grafiky
        g.dispose();
        buffer.show();
    } 
    
    // vykreslení
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        // Nastavení renderování
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // vykreslení pozadí
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        //g.drawImage(this.backgroundImage, 0, 30, null);
        
           
        // food
        for(Food e: this.food)
            e.render(g2d);
        // kurzor
        mouse.render(g2d);
        // nepřátelé
        // walls
        for(Wall e: this.walls)
            e.render(g2d);  
        
        for(Entity e: this.enemies)
            e.render(g2d);
        // hráč
        player.render(g);
        // topPanel
        g2d.setFont(this.myFont); // font
        this.topPanel.paint(g2d);
        // Autor
        g.drawString("David Hříbek 2015", 10, this.resolutionY+5);
        // version
        g.drawString(this.version, this.resolutionX - 70, this.resolutionY + 5 );
        g.dispose();
    } 
    // pohyb
    private void update(){
        // hráč
        player.update();
        // nepřátelé
        for(Entity e: this.enemies)
            e.update();
        // jídlo
        for(Entity e: this.food)
            e.update();
        // hráč - myš
        mouse.update();
        // kolize
        this.collisions();
        // kontrola HRÁČ - DÍRA
        if(player.walking == true && player.getNextKey() > 0)
            player.run();
        // výhra
        if(this.getFoodNumber()  - this.getScore() == 0){
            this.setIsWinner(true);
        }
    }
    // vytvoření nového vlákna
    public void start(){
        this.isRunning = true;
        Thread t = new Thread (this);
        t.start();
    }
    // kolize
    private void collisions(){  
        this.foodWallHit();
        this.playerWallHit();
        this.playerRandomWay();
        this.playerFoodHit();
        this.enemyWallHit();
        this.enemyRandomWay();
        this.enemyPlayerHit();
    }
    // funkce hry, které se vykonávají pořád
    private void gameFunctions(){
        // Konec hry
        this.isGameOver();
        // Výhra
        this.isWinner();  
    }
    // funkce, které se vykonávají každou sekundu
    private void everySecondFunctions(){
        // vybíjení štítu
        player.playerShieldDischarging();
        // infused player
        this.playerInfusion();
        // přebarvování panelu
        this.topPanel.setPlayerFoodCollision(false);
        this.topPanel.setPlayerEnemyCollision(false);
        this.topPanel.setEnemyEaten(false);
    }  
    // kontrola nakaženosti hráče
    private void playerInfusion(){
        if(player.getInfused()){
           if(player.getInfusedDuration() > 0)
               player.setInfusedDuration(player.getInfusedDuration() - 1);
           if(player.getInfusedDuration() == 0)
               player.setInfused(false);
        }else{
            if(player.getInfusedCounter() != player.getInfusedCooldown())
                player.setInfusedCounter(player.getInfusedCounter() + 1);
            if(player.getInfusedCounter() == player.getInfusedCooldown()){
                player.setInfused(true);
                player.setInfusedCounter(0);
                player.setInfusedDuration(player.getInfusedDurationFinal());
            } 
        }
    }
    // vytvoření zdí
    private void wallSpawn(){
        for(int a = 0;a < map.getwallCount();a++){
            this.walls.add(new Wall(this,map.getX(a),map.getY(a),map.getWidth(a),map.getHeight(a)));
        }
    }
    // vytvoření nepřátel
    private void enemySpawn(){
        for(int a = 0;a < this.getEnemyNumber();a++){
            this.enemies.add(new Enemy(this, this.getEnemySpeed()));
        }
    }
    // vytvoření jídla
    private void foodSpawn(){        
        for(int y = 30;y < this.getyResolution()-30;y++){
            if(y <= this.getyResolution()){
                if(y % 40 == 0){
                    y+=10;
                    for(int x = 0;x<this.getxResolution();x++){
                        if(x <= this.getxResolution()-30){
                            if(x % 40 == 0){
                                x+=20;
                                this.food.add(new Food(this,x,y));
                                this.setFoodNumber(this.getFoodNumber() + 1);
                            }
                        }  
                    }
                }
            }
        }
                
    }
    // Chození nepřátel do děr
    private void enemyRandomWay(){
        int frequency = 1;
        
        int counterRight = 0;
        int counterLeft = 0;
        int counterTop = 0;
        int counterBottom = 0;
        
        boolean right = false;
        boolean left = false;
        boolean bot = false;
        boolean top = false;
        
        for (Entity e: this.enemies){
            for(Wall wall: this.walls){
                if(!(e.collisionWall(wall, 20, 0)))
                    counterRight++;
                if(!(e.collisionWall(wall, -20, 0)))
                    counterLeft++;
                if(!(e.collisionWall(wall, 0, 20)))
                    counterBottom++;
                if(!(e.collisionWall(wall, 0, -20)))
                    counterTop++;
            }
            if(counterRight == map.getwallCount())
                right = true;
            if(counterLeft == map.getwallCount())
                left = true;
            if(counterBottom == map.getwallCount())
                bot = true;
            if(counterTop == map.getwallCount())
                top = true;
            if((bot && top && left && right) == true){
                e.setWay(rnd.nextInt(2));
            }
            else if((bot && top && right) || (bot && top && left)){
                if(rnd.nextInt(2) == 0)
                    e.setWay(0);
            }
            else if((left && right && bot) || (left && right && top)){
                if(rnd.nextInt(2) == 0)
                    e.setWay(1);
            }
        }
    }
    // Kolize jídla se zdí
    private void foodWallHit(){
        int counter = 0;
        for(Entity e: this.food){
            for(Wall wall : this.walls){
                if(e.collisionWall(wall,0,0)){
                    counter++;
                }
            }
            if(counter > 0){
                this.food.remove(e);
                this.setFoodNumber(this.getFoodNumber() - 1);
                break;
            }
        }
    }
    // Kolize nepřítele se zdí
    private void enemyWallHit(){        
        for(Entity e: this.enemies){
            for(Wall wall : this.walls){
                if(e.collisionWall(wall,0,0)){
                    switch(e.getWay()){
                        case 0: if(e.getxPosition() + e.getSpeed()> e.getxPosition())
                                   e.setxPosition(wall.getxPosition()-e.getWidth());
                                else
                                   e.setxPosition(wall.getxPosition()+wall.getWidth());
                                if(rnd.nextInt(2) == 0)
                                    e.setSpeed(e.getSpeed()*-1);
                                e.setWay(1);
                                break;
                        case 1: if(e.getyPosition() + e.getSpeed()> e.getyPosition())
                                   e.setyPosition(wall.getyPosition()-e.getHeight());
                                else
                                    e.setyPosition(wall.getyPosition()+wall.getHeight());
                                if(rnd.nextInt(2) == 0)
                                    e.setSpeed(e.getSpeed()*-1);
                                e.setWay(0);
                                break;
                    }
                }
            }
        }
    }
    // Kolize zdí s hráčem
    private void playerWallHit(){       
            for(Wall wall : this.walls){
                if(player.collisionWall(wall,0,0)){
                    switch(player.getWay()){
                        case 0: if(player.getxPosition() + player.getSpeed()> player.getxPosition())
                                   player.setxPosition(wall.getxPosition()-player.getWidth());
                                else
                                   player.setxPosition(wall.getxPosition()+wall.getWidth());
                                
                                break;
                        case 1: if(player.getyPosition() + player.getSpeed()> player.getyPosition())
                                   player.setyPosition(wall.getyPosition()-player.getHeight());
                                else
                                    player.setyPosition(wall.getyPosition()+wall.getHeight());
                                break;
                    }
                    player.setWalking(false);
                    player.setNextKey(0);
                    player.setSpeed(0);
                }
            }
    }
    // Chození hráče do děr
    private void playerRandomWay(){
        int counterRight = 0;
        int counterLeft = 0;
        int counterTop = 0;
        int counterBottom = 0;
        
        boolean right = false;
        boolean left = false;
        boolean bot = false;
        boolean top = false;
        
        for(Wall wall: this.walls){
            if(!(player.collisionWall(wall, 20, 0)))
                counterRight++;
            if(!(player.collisionWall(wall, -20, 0)))
                counterLeft++;
            if(!(player.collisionWall(wall, 0, 20)))
                counterBottom++;
            if(!(player.collisionWall(wall, 0, -20)))
                counterTop++;
        }  
        if(counterRight == map.getwallCount())
            player.setRightWay(true);
        else
            player.setRightWay(false);
        
        if(counterLeft == map.getwallCount())
            player.setLeftWay(true);
        else
            player.setLeftWay(false);
        
        if(counterBottom == map.getwallCount())
            player.setDownWay(true);
        else
            player.setDownWay(false);
        
        if(counterTop == map.getwallCount())
            player.setUpWay(true);
        else
            player.setUpWay(false);
    }
    // Kolize hráče s jídlem
    private void playerFoodHit(){
        for(Food food : this.food){
            if(player.collisionFood(food, 0, 0)){
                this.food.remove(food);
                this.setScore(this.getScore() + 1);
                this.topPanel.setPlayerFoodCollision(true);
                return;
            }            
        }
    }
    // Kolize nepřítele s hráčem
    private void enemyPlayerHit(){
        for(Enemy e: this.enemies){
            if(e.getBounds(0, 0).intersects(player.getBounds(0, 0))){
                if(player.getInfused()){
                    this.enemies.remove(e);
                    this.enemyNumber--;
                    this.topPanel.setEnemyEaten(true);
                    return;
                }else{
                    if(player.getShield() == 0){
                        player.setHP(player.getHP() - 1);
                        this.topPanel.setPlayerEnemyCollision(true);
                        player.setShield(1);
                        if(player.getHP() == 0)
                            this.isGameOver = true;
                    }
                    else{
                        player.setShield(1);
                        return;
                    }
                }
            }
        }       
    }

    public int getxResolution(){
        return this.resolutionX;
    }
    public int getyResolution(){
        return this.resolutionY;
    }
    public boolean getFullScreen(){
        return this.fullScreen;
    }   
    public int getScore(){
        return this.score;
    }
    public int getFPS(){
        return this.FPS;
    }
    public int getFoodNumber(){
        return this.FoodNumber;
    }
    private void isGameOver(){
            if(this.isGameOver){
                JOptionPane.showMessageDialog(this, "Game Over YOUR LEVEL: " + this.LEVEL, "Game Over", JOptionPane.YES_NO_OPTION);
                System.exit(ABORT);
            }
    }
    
    private void isWinner(){
        if(this.getIsWinner()){
            //JOptionPane.showMessageDialog(this, "You have completed the level", "NEXT LEVEL: " + this.LEVEL + 1, JOptionPane.OK_OPTION);
            int nextLevel = this.LEVEL + 1;
            JOptionPane.showMessageDialog(this, "You have completed the level", "Next Level: " + nextLevel, JOptionPane.INFORMATION_MESSAGE, this.icon);
            //JOptionPane.showMessageDialog(this,"Eggs are not supposed to be green.","Inane custom dialog",JOptionPane.INFORMATION_MESSAGE, icon);
            this.LEVEL++;
            this.LVL.setLevel(this.LEVEL);
            this.LVL.updateLevel();
            this.enemies.clear();
            this.enemySpawn();
            this.foodSpawn();
            this.isWinner = false;
        }
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }
    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
    public int getLEVEL() {
        return this.LEVEL;
    }
    public void setLEVEL(int LEVEL) {
        this.LEVEL = LEVEL;
    }
    public int getEnemyNumber() {
        return this.enemyNumber;
    }
    public void setEnemyNumber(int enemyNumber) {
        this.enemyNumber = enemyNumber;
    }
    public int getEnemySpeed() {
        return this.enemySpeed;
    }
    public void setEnemySpeed(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }
    public int getPlayerHP() {
        return this.playerHP;
    }
    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
    }
    public int getPlayerInfusedCooldownFinal() {
        return this.playerInfusedCooldownFinal;
    }
    public void setPlayerInfusedCooldownFinal(int playerInfusedCooldownFinal) {
        this.playerInfusedCooldownFinal = playerInfusedCooldownFinal;
    }
    public int getPlayerInfusedDurationFinal() {
        return this.playerInfusedDurationFinal;
    }
    public void setPlayerInfusedDurationFinal(int playerInfusedDurationFinal) {
        this.playerInfusedDurationFinal = playerInfusedDurationFinal;
    }
    public int getPlayerSaveSpeed() {
        return this.playerSaveSpeed;
    }
    public void setPlayerSaveSpeed(int playerSaveSpeed) {
        this.playerSaveSpeed = playerSaveSpeed;
    }
    public boolean getIsWinner() {
        return this.isWinner;
    }
    public void setIsWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setFoodNumber(int FoodNumber) {
        this.FoodNumber = FoodNumber;
    }
}
