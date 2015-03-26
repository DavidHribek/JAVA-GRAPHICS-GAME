/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class Game extends JFrame {

    private static final String GAME_TITLE = "Pampuch";
    private RenderLayer layer;
    
    public Game(){
        this.layer = new RenderLayer(this);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.init();
    }
    
    public void init(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(layer);
        this.pack();
        this.setTitle(Game.GAME_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        layer.start();
    }
}
