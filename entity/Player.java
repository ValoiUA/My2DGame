package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;
    }
    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed  = 4;
    }
    public void update() {
        if(keyH.upPressed == true) {
            p
        }
    }
    public void draw() {

    }
}
