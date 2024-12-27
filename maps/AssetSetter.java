package maps;
import entity.NPC_OldMan;import main.GamePanel;
import object.OBJ_Boots;import object.OBJ_Key;
import object.Stick;import object.SuperObject;
import java.util.ArrayList;
import java.util.Random;
public class AssetSetter {
    GamePanel gp;    Random random = new Random();
    public static ArrayList<Integer> wx = new ArrayList<>();    public static ArrayList<Integer> wy = new ArrayList<>();
    public AssetSetter(GamePanel gp) {
        this.gp = gp;    }
    public void setObject() {
        gp.obj[0] = new OBJ_Key();        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;
        gp.obj[1] = new OBJ_Key();        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;
        gp.obj[2] = new OBJ_Boots();
        gp.obj[2].worldX = 37 * gp.tileSize;        gp.obj[2].worldY = 42 * gp.tileSize;

        for (int i = 3; i < 20; i++) {
            boolean isCollision;            int attempts = 0;
            do {
                isCollision = false;                gp.obj[i] = new Stick();
                gp.obj[i].worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;                gp.obj[i].worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;
                wx.add(gp.obj[i].worldX);                wy.add(gp.obj[i].worldY);
                for (int j = 0; j < i; j++) {
                    if (checkCollision(gp.obj[i], gp.obj[j])) {                        isCollision = true;
                        wx.remove(wx.size() - 1);                        wy.remove(wy.size() - 1);
                        break;
                    }                }
                attempts++;
                if (attempts > 100) {                    System.out.println("Unable to place object without collision");
                    break;                }
            } while (isCollision);
        }    }
    public static ArrayList<Integer> getWorldx() {
        return wx;    }
    public static ArrayList<Integer> getWorldy() {
        return wy;    }
    public boolean checkCollision(SuperObject obj1, SuperObject obj2) {
        int obj1Left = obj1.worldX;        int obj1Right = obj1.worldX + gp.tileSize;
        int obj1Top = obj1.worldY;        int obj1Bottom = obj1.worldY + gp.tileSize;
        int obj2Left = obj2.worldX;
        int obj2Right = obj2.worldX + gp.tileSize;        int obj2Top = obj2.worldY;
        int obj2Bottom = obj2.worldY + gp.tileSize;
        return obj1Right > obj2Left &&                obj1Left < obj2Right &&
                obj1Bottom > obj2Top &&                obj1Top < obj2Bottom;
    }    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldx = gp.tileSize*21;        gp.npc[0].worldy = gp.tileSize*21;
    }}