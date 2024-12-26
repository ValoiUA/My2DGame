package maps;

import main.GamePanel;
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

        // Випадкове розташування для палок
        for (int i = 2; i < 20; i++) {
            gp.obj[i] = new Stick();
            gp.obj[i].worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;
            gp.obj[i].worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;
        }
    }
}
