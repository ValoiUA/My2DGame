package entity;

import Inventory.Invent;
import Inventory.Item;
import main.GamePanel;
import main.InventoryGUI;
import main.KeyHandler;
import main.UtilityTool;
import maps.AssetSetter;
import object.Stick;
import object.Stone;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Player extends Entity {

    KeyHandler keyH;
    Invent invent;
    InventoryGUI inventoryGUI;
    public final int screenX;
    public final int screenY;
    private maps.AssetSetter AssetSetter;
    public boolean showPickupMessage = false;
    public boolean showTalkMessage = false;

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

    public void pickUpObject(int i) {
        if (i != 999) {
            // Код для підбору об'єктів
        }
    }

    public void getPlayerImage() {
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/res/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        if (gp.gameState == gp.playState) {
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
                        case "up":
                            worldy -= speed;
                            break;
                        case "down":
                            worldy += speed;
                            break;
                        case "right":
                            worldx += speed;
                            break;
                        case "left":
                            worldx -= speed;
                            break;
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
            showTalkMessage = false;

            for (SuperObject obj : gp.obj) {
                if (obj instanceof Stick) {
                    if (Math.abs(obj.worldX - worldx) < tolerance &&
                            Math.abs(obj.worldY - worldy) < tolerance) {
                        showPickupMessage = true;
                        if (keyH.presse) {
                            invent.addItem(new Item("Stick", 1));
                            relocateObject(obj);
                            keyH.presse = false;
                            showPickupMessage = false;
                            break;
                        }
                    }
                }
                if (obj instanceof Stone) {
                    if (Math.abs(obj.worldX - worldx) < tolerance &&
                            Math.abs(obj.worldY - worldy) < tolerance) {
                        showPickupMessage = true;
                        if (keyH.presse) {
                            invent.addItem(new Item("Stone", 1));
                            relocateObject(obj);
                            keyH.presse = false;
                            showPickupMessage = false;
                            break;
                        }
                    }
                }
            }

            for (Entity entity : gp.npc) {
                if (entity instanceof NPC_OldMan) {
                    if (Math.abs(entity.worldx - worldx) < tolerance &&
                            Math.abs(entity.worldy - worldy) < tolerance) {
                        showTalkMessage = true;
                        if (keyH.presse) {
                            // Ваш код для початку діалогу
                            keyH.presse = false;
                            showTalkMessage = false;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void relocateObject(SuperObject object) {
        Random random = new Random();
        boolean isCollision;
        int attempts = 0;

        do {
            isCollision = false;
            object.worldX = random.nextInt(gp.maxWorldCol) * gp.tileSize;
            object.worldY = random.nextInt(gp.maxWorldRow) * gp.tileSize;

            // Перевіряємо колізію з іншими об'єктами
            for (SuperObject obj : gp.obj) {
                if (obj != null && obj != object) {
                    if (object.worldX == obj.worldX && object.worldY == obj.worldY) {
                        isCollision = true;
                        break;
                    }
                }
            }

            // Перевіряємо колізію з гравцем
            if (object.worldX == gp.player.worldx && object.worldY == gp.player.worldy) {
                isCollision = true;
            }

            // Перевіряємо колізію з плитками (tiles)
            gp.cChecker.checkObject(object);

            if (!isCollision && !object.collision) {
                object.collision = false;
                break;
            }

            attempts++;
            if (attempts > 100) {
                System.out.println("Unable to relocate object without collision");
                break;
            }

        } while (isCollision);
    }


}