package Monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;


public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp){
        super(gp);

        name = "Green Slime";
        speed = 1;
        maxLife = 5;
        Life = maxLife;

        solidArea = new Rectangle();
        solidArea.width = 12;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getMonImage();
        setMoAction();




    }

}
