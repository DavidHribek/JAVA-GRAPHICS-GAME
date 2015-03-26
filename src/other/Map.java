package other;

import game.RenderLayer;
import java.util.Random;



/**
 *
 * @author David
 */
public class Map {
    private int pocet;
    private int [] x;
    private int [] y;
    private int [] width;
    private int [] height;
    private Random rnd;
    private int wallCount;
    
    private RenderLayer layer;
    public Map(RenderLayer l){
        this.layer = l;
        this.rnd = new Random();
        this.wallCount = 0;
        
        pocet = 500;
        x = new int[pocet];
        y = new int[pocet];
        width = new int [pocet];
        height = new int [pocet];
        
        this.createWorld();
    }
    
    public int getX(int position){
        return this.x[position];
    }
    public int getY(int position){
        return this.y[position];
    }
    public int getWidth(int position){
        return this.width[position];
    }
    public int getHeight(int position){
        return this.height[position];
    }
    public int getwallCount(){
        return this.wallCount;
    }
        
    public void createWorld(){
        this.createBorderWalls(0,1);
        for(int i = 0;i<10;i++)
            this.createBorderWalls(i*40,2);
        //this.createMap();
    }
    
    public void createBorderWalls(int shift, int divisor){
        int space = 20;
        //left
        this.y[this.wallCount] = shift+30;
        this.x[this.wallCount] = shift;
        this.height[this.wallCount] = (this.layer.getyResolution()-2*(shift+30)+space)/divisor;
        this.width[this.wallCount] = 20;
        this.wallCount++;
        
        this.y[this.wallCount] = this.y[this.wallCount-1]+space+this.height[this.wallCount-1];
        this.x[this.wallCount] = shift;
        this.height[this.wallCount] = (this.layer.getFullScreen())?this.layer.getyResolution()-this.height[this.wallCount-1]-space-2*(shift+30)-10:this.layer.getyResolution()-this.height[this.wallCount-1]-space-2*(shift + 15);
        this.width[this.wallCount] = 20;
        this.wallCount++;
        // right
        this.y[this.wallCount] = shift+30;
        this.x[this.wallCount] = (this.layer.getFullScreen())?this.layer.getxResolution()-space-shift:this.layer.getxResolution()-10-shift;
        this.height[this.wallCount] = (this.layer.getyResolution()-2*(shift+30)+space)/divisor;
        this.width[this.wallCount] = 20;
        this.wallCount++;
        
        this.y[this.wallCount] = this.y[this.wallCount-1] + this.height[this.wallCount-1]+space;
        this.x[this.wallCount] = (this.layer.getFullScreen())?this.layer.getxResolution()-space-shift:this.layer.getxResolution()-10-shift;
        this.height[this.wallCount] = (this.layer.getFullScreen())?this.layer.getyResolution()-this.height[this.wallCount-1]-space-2*(shift+30)-10:this.layer.getyResolution()-this.height[this.wallCount-1]-space-2*(shift+15);
        this.width[this.wallCount] = 20;
        this.wallCount++;
        // top
        this.y[this.wallCount] = shift+30;
        this.x[this.wallCount] = shift;
        this.height[this.wallCount] = 20;
        this.width[this.wallCount] = (this.layer.getFullScreen())?(this.layer.getxResolution()-2*(shift))/divisor:(this.layer.getxResolution()-2*(shift)+10)/divisor;
        this.wallCount++;
        
        this.y[this.wallCount] = shift + 30;
        this.x[this.wallCount] = this.x[this.wallCount-1] + this.width[this.wallCount-1] + space;
        this.height[this.wallCount] = 20;
        this.width[this.wallCount] = (this.layer.getFullScreen())? layer.getxResolution() - this.width[this.wallCount-1] - 2*(shift):layer.getxResolution() - this.width[this.wallCount-1] - 2*(shift)-30;
        this.wallCount++;
        // bot
        this.y[this.wallCount] = (this.layer.getFullScreen())?this.layer.getyResolution()-space-shift:this.layer.getyResolution()-10-shift;
        this.x[this.wallCount] = shift;
        this.height[this.wallCount] = 20;
        this.width[this.wallCount] = (this.layer.getFullScreen())?(this.layer.getxResolution()-2*(shift))/divisor:(this.layer.getxResolution()-2*shift+10)/divisor;
        this.wallCount++;
        
        this.y[this.wallCount] = (this.layer.getFullScreen())?this.layer.getyResolution()-space-shift:this.layer.getyResolution()-10-shift;
        this.x[this.wallCount] = this.x[this.wallCount-1] + this.width[this.wallCount-1] + space;
        this.height[this.wallCount] = 20;
        this.width[this.wallCount] = (this.layer.getFullScreen())? layer.getxResolution() - this.width[this.wallCount-1] - 2*(shift + space/2):layer.getxResolution() - this.width[this.wallCount-1] - 2*(shift)-10;
        this.wallCount++;
    }
 
    public void createMap(){
        int e = 0;
        int ex=210;
        while(ex % 20 ==0){
            ex = rnd.nextInt(this.layer.getyResolution());
        }
        
        while(e < 20){
            this.wallCount++;
            this.y[this.wallCount] = ex;
            this.x[this.wallCount] = rnd.nextInt(this.layer.getxResolution());
            this.height[this.wallCount] = 20;
            this.width[this.wallCount] = 20;
            this.wallCount++;
            e++;
        }
    }  
}
