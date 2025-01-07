package main;

import entity.Entity;
import object.SuperObject;

public class CollisionChecker {

    GamePanel gp;
    Entity entity = new Entity(gp);

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldx + entity.solidArea.x;
        int entityRightWorldX = entity.worldx + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldy + entity.solidArea.y;
        int entityBottomWorldY = entity.worldy + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

        }
    }

    public void checkObject(SuperObject object) {
        int objectLeftWorldX = object.worldX + object.solidArea.x;
        int objectRightWorldX = object.worldX + object.solidArea.x + object.solidArea.width;
        int objectTopWorldY = object.worldY + object.solidArea.y;
        int objectBottomWorldY = object.worldY + object.solidArea.y + object.solidArea.height;

        int objectLeftCol = objectLeftWorldX / gp.tileSize;
        int objectRightCol = objectRightWorldX / gp.tileSize;
        int objectTopRow = objectTopWorldY / gp.tileSize;
        int objectBottomRow = objectBottomWorldY / gp.tileSize;

        if (objectLeftCol < 0 || objectRightCol >= gp.maxWorldCol || objectTopRow < 0 || objectBottomRow >= gp.maxWorldRow) {
            object.collision = true;
            return;
        }

        int tileNum1, tileNum2;

        // Перевірка чотирьох кутів bounding box об'єкта
        tileNum1 = gp.tileM.mapTileNum[objectLeftCol][objectTopRow];
        tileNum2 = gp.tileM.mapTileNum[objectRightCol][objectTopRow];
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            object.collision = true;
        }

        tileNum1 = gp.tileM.mapTileNum[objectLeftCol][objectBottomRow];
        tileNum2 = gp.tileM.mapTileNum[objectRightCol][objectBottomRow];
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            object.collision = true;
        }
    }
}
