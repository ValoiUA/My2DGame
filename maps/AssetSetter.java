package maps;

import entity.NPC_OldMan;
import main.GamePanel;
import object.OBJ_Boots;
import object.OBJ_Key;
import object.Stick;
import object.SuperObject;

import java.util.Random;

public class AssetSetter {

    GamePanel gp;
    Random random = new Random();

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    // Встановлення об'єктів з випадковим розташуванням для палок
    public void setObject(){
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_Boots();
        gp.obj[2].worldX = 37 * gp.tileSize;
        gp.obj[2].worldY = 42 * gp.tileSize;

        // Випадкове розташування для палок
        for (int i = 3; i < 20; i++) {
            gp.obj[i] = new Stick();
            gp.obj[i].worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;
            gp.obj[i].worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;
        }
    }
    public void setNPC(){

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldx = gp.tileSize*21;
        gp.npc[0].worldy = gp.tileSize*21;
    }
}
