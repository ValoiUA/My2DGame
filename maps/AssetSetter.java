package maps;

import entity.NPC_OldMan;
import main.GamePanel;
import object.OBJ_Boots;
import object.OBJ_Key;
import object.Stick;
import object.SuperObject;

import java.util.ArrayList;
import java.util.Random;

public class AssetSetter {

    GamePanel gp;
    Random random = new Random();
    public static ArrayList<Integer> wx = new ArrayList<>();
    public static ArrayList<Integer> wy = new ArrayList<>();

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_Boots();
        gp.obj[2].worldX = 37 * gp.tileSize;
        gp.obj[2].worldY = 42 * gp.tileSize;

        int maxObjects = 30; // Максимальна кількість палок, яку ви хочете додати
        int addedObjects = 0;

        while (addedObjects < maxObjects) {
            boolean isCollision;
            int attempts = 0;
            SuperObject stick = null;

            do {
                isCollision = false;
                stick = new Stick();
                stick.worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;
                stick.worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;

                gp.cChecker.checkObject(stick);

                if (stick.collision) {
                    isCollision = true;
                }

                attempts++;
                if (attempts > 100) {
                    System.out.println("Unable to place object without collision");
                    break;
                }

            } while (isCollision);

            if (!isCollision) {
                gp.obj[3 + addedObjects] = stick;
                wx.add(stick.worldX);
                wy.add(stick.worldY);
                addedObjects++;
            }
        }
    }

    public static ArrayList<Integer> getWorldx() {
        return wx;
    }

    public static ArrayList<Integer> getWorldy() {
        return wy;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldx = gp.tileSize * 21;
        gp.npc[0].worldy = gp.tileSize * 21;
    }
}