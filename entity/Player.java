package entity;

import Inventory.Invent;
import Inventory.Item;
import main.GamePanel;
import main.InventoryGUI;
import main.KeyHandler;
import maps.AssetSetter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;
    Invent invent;
    InventoryGUI inventoryGUI;
    public final int screenX;
    public final int screenY;
    private maps.AssetSetter AssetSetter;
    public boolean showPickupMessage = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        this.invent = gp.invent;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldx = gp.tileSize * 23;
        worldy = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/images/res/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.inventoryopen) {
            keyH.downPressed = false;
            keyH.upPressed = false;
            keyH.leftPressed = false;
            keyH.rightPressed = false;
        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldy -= speed; break;
                    case "down": worldy += speed; break;
                    case "right": worldx += speed; break;
                    case "left": worldx -= speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
        if (keyH.inventoryopen) {
            gp.openInventory();
            keyH.inventoryopen = false;
        }

        int tolerance = gp.tileSize * 2; // Допуск для перевірки колізії

        showPickupMessage = false;
        for (int i = 0; i < AssetSetter.getWorldx().size(); i++) {
            if (Math.abs(AssetSetter.getWorldx().get(i) - worldx) < tolerance &&
                    Math.abs(AssetSetter.getWorldy().get(i) - worldy) < tolerance) {
                showPickupMessage = true;
                break;
            }
        }

        if (showPickupMessage && keyH.presse) {
            // Логіка підбору об'єкта
            invent.addItem(new Item("Stick", 1));
            keyH.presse = false;
        }
    }

    public void draw(Graphics g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2; break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2; break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2; break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2; break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        if (showPickupMessage) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.drawString("Press E to pick up", screenX - 50, screenY - 20);
        }
    }
}
