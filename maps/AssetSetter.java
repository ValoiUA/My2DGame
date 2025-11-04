package maps;

import entity.NPC_OldMan;
import main.GamePanel;
import object.*;
import java.util.ArrayList;
import java.util.Random;

public class AssetSetter {

    GamePanel gp;
    Random random = new Random();

    public static ArrayList<Integer> wx = new ArrayList<>();
    public static ArrayList<Integer> wy = new ArrayList<>();
    public static ArrayList<Integer> wxstone = new ArrayList<>();
    public static ArrayList<Integer> wystone = new ArrayList<>();

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

        int maxSticks = 20; // кількість палок
        int maxStones = 20; // кількість каменів

        // --- Генерація палок ---
        int addedSticks = 0;
        while (addedSticks < maxSticks) {
            boolean isCollision;
            int attempts = 0;
            SuperObject stick;

            do {
                isCollision = false;
                stick = new Stick();
                stick.worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;
                stick.worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;

                gp.cChecker.checkObject(stick);
                if (stick.collision) isCollision = true;

                attempts++;
                if (attempts > 100) {
                    System.out.println("Unable to place stick without collision");
                    break;
                }

            } while (isCollision);

            if (!isCollision) {
                gp.obj[3 + addedSticks] = stick;
                wx.add(stick.worldX);
                wy.add(stick.worldY);
                addedSticks++;
            }
        }

        // --- Генерація каменів ---
        int addedStones = 0;
        while (addedStones < maxStones) {
            boolean isCollision;
            int attempts = 0;
            SuperObject stone;

            do {
                isCollision = false;
                stone = new Stone();
                stone.worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;
                stone.worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;

                gp.cChecker.checkObject(stone);
                if (stone.collision) isCollision = true;

                attempts++;
                if (attempts > 100) {
                    System.out.println("Unable to place stone without collision");
                    break;
                }

            } while (isCollision);

            if (!isCollision) {
                gp.obj[3 + maxSticks + addedStones] = stone;
                wxstone.add(stone.worldX);
                wystone.add(stone.worldY);
                addedStones++;
            }
        }
    }

    public static ArrayList<Integer> getWorldx() { return wx; }
    public static ArrayList<Integer> getWorldy() { return wy; }

    public static ArrayList<Integer> getWxstone() { return wxstone; }
    public static ArrayList<Integer> getWystone() { return wystone; }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldx = gp.tileSize * 21;
        gp.npc[0].worldy = gp.tileSize * 21;
    }
}
