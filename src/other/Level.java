package other;

import game.RenderLayer;
import game.entities.Enemy;
import game.entities.Player;
/**
 *
 * @author David
 */
public class Level {
    private int LEVEL;
    private RenderLayer layer;
    private Enemy enemy;
    private Player player;
    
    public Level(int LEVEL, RenderLayer l, Player player ){
        this.LEVEL = LEVEL;
        this.layer = l;
        this.player = player;
        
        this.updateLevel();
        }
    
    public void setLevel(int level){
        this.LEVEL = level;
    }
    
    public void updateLevel(){
        layer.setEnemyNumber(this.LEVEL);
        if(this.LEVEL < 5)
            layer.setEnemySpeed(this.LEVEL);
        else
            layer.setEnemySpeed(5);
        
        layer.setScore(0);
        layer.setFoodNumber(0);
        
        player.setHP(3);
        player.setInfusedCooldownFinal(20);
        player.setInfusedDurationFinal(5);
        player.setShield(5);
        
        
    }
}
