package maps;

import Monster.MON_GreenSlime;
import entity.NPC_OldMan;
import main.GamePanel;
import object.OBJ_Boots;
import object.OBJ_Key;
import object.Stick;
import object.Stone;
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

        int maxObjects = 30;
        int addedObjects = 0;
        int maxstone = 30;
        int addedstone = 0;

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


        while (addedstone < maxstone) {
            boolean isCollision;
            int attempts = 0;
            SuperObject stone = null;

            do {
                isCollision = false;
                stone = new Stone();
                stone.worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;
                stone.worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;

                gp.cChecker.checkObject(stone);

                if (stone.collision) {
                    isCollision = true;
                }

                attempts++;
                if (attempts > 100) {
                    System.out.println("Unable to place object without collision");
                    break;
                }

            } while (isCollision);

            if (!isCollision) {
                gp.obj[4 + addedObjects + addedstone] = stone;
                wx.add(stone.worldX);
                wy.add(stone.worldY);
                addedstone++;
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

    public void SetMonster(){
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldx = gp.tileSize*23;
        gp.monster[0].worldy = gp.tileSize*23;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldx = gp.tileSize*26;
        gp.monster[1].worldy = gp.tileSize*26;


    }
}