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
            while (attempts < 100) {
                int wxPos = random.nextInt(gp.maxWorldCol) * gp.tileSize;
                int wyPos = random.nextInt(gp.maxWorldRow) * gp.tileSize;

                if (gp.cChecker.canSpawnObject(wxPos, wyPos)) {
                    Stick stick = new Stick();
                    stick.worldX = wxPos;
                    stick.worldY = wyPos;

                    gp.obj[3 + addedSticks] = stick;
                    wx.add(wxPos);
                    wy.add(wyPos);
                    addedSticks++;
                    break;
                }
                attempts++;
            }
            if (attempts >= 100) {
                System.out.println("No free space");
                break;
            }
        }
        int addedStones = 0;
        while (addedStones < maxStones) {
            int att = 0;
            while (att < 100) {
                int wxPoss = random.nextInt(gp.maxWorldCol) * gp.tileSize;
                int wyPoss = random.nextInt(gp.maxWorldRow) * gp.tileSize;

                if (gp.cChecker.canSpawnObject(wxPoss, wyPoss)) {
                    Stone stone = new Stone();
                    stone.worldX = wxPoss;
                    stone.worldY = wyPoss;

                    gp.obj[3 + maxSticks + addedStones] = stone;
                    wxstone.add(wxPoss);
                    wystone.add(wyPoss);
                    addedStones++;
                    break;
                }
                att++;
            }
            if (att >= 100) {
                System.out.println("No free space");
                break;
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