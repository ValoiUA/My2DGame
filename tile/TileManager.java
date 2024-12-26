package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public  tile[] tile;
    public int mapTileNum [][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new tile[10];
        mapTileNum = new int[gp.maxWorldCol] [gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/map01");
    }

    public void getTileImage(){
        try {
            tile[0] = new tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/images/entitis/grass.png"));

            tile[1] = new tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/images/entitis/wall.png"));
            tile[1].collision = true;

            tile[2] = new tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/images/entitis/water.png"));
            tile[2].collision = true;

            tile[3] = new tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/images/entitis/earth.png"));

            tile[4] = new tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/images/entitis/tree.png"));
            tile[4].collision = true;

            tile[5] = new tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/images/entitis/sand.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while (col < gp.maxWorldCol){

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }

            }
            br.close();
        }catch (Exception e){

        }
    }
    public void draw(Graphics2D g2){
        int worldcol = 0;
        int worldrow = 0;


        while (worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldcol][worldrow];

            int worldX = worldcol * gp.tileSize;
            int worldY = worldrow * gp.tileSize;
            int screenX = worldX - gp.player.worldx + gp.player.screenX;
            int screenY = worldY - gp.player.worldy + gp.player.screenY;


            if(worldX + gp.tileSize> gp.player.worldx - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldx + gp.player.screenX &&
                    worldY + gp.tileSize> gp.player.worldy - gp.player.screenY &&
                    worldY - gp.tileSize< gp.player.worldy + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }



            worldcol++;


            if(worldcol == gp.maxWorldCol){
                worldcol=0;

                worldrow++;
            }
        }
    }
}