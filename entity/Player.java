package entity;

import Inventory.Invent;
import main.GamePanel;
import main.InventoryGUI;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    Invent invent;
    InventoryGUI inventoryGUI;
    public final int screenX;
    public final int screenY;
    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize / 2);
        screenY = gp.screenHeight/2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {

        worldx = gp.tileSize * 23;
        worldy = gp.tileSize * 21;
        speed  = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        }


    public void update() {
        if(keyH.inventoryopen){
            keyH.downPressed = false;
            keyH.upPressed = false;
            keyH.leftPressed = false;
            keyH.rightPressed = false;
        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if (keyH.upPressed == true){
                direction = "up";

            }else if (keyH.downPressed == true){
                direction = "down";

            }else if (keyH.leftPressed == true){
                direction = "left";

            }else if (keyH.rightPressed == true){
                direction = "right";

            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (collisionOn == false){
                switch (direction){
                    case "up": worldy -= speed; break;
                    case "down": worldy += speed; break;
                    case "right": worldx += speed; break;
                    case "left": worldx -= speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (keyH.inventoryopen) {
            gp.openInventory();
            keyH.inventoryopen = false;
        }

    }
    public void draw(Graphics g2) {
        BufferedImage image = null;

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                } break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                } break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                } break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                } break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
