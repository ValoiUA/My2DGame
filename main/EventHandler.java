package main;

import java.awt.Rectangle;

public class EventHandler{

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;


    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent(){

        if(hit(25, 26, "any") == true)damagePit(gp.gameState);
        if(hit(25, 23, "any") == true)HealingPool(gp.gameState);

    }
    public boolean hit(int eventCol, int eventRow, String reqDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldx + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldy + gp.player.solidArea.y;
        eventRect.x = eventCol*gp.tileSize + eventRect.x;
        eventRect.y = eventRow*gp.tileSize + eventRect.y;


        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;

            }
        }


        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.y = eventRectDefaultY;
        eventRect.x = eventRectDefaultX;


        return hit;

    }
    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.player.Life -= 1;

        //gp.ui.currentDialogue = "You gay";
    }
    public void HealingPool(int gameState) {
        if(gp.keyH.enterPressed){
            gp.gameState = gameState;
            //gp.ui.currentDialogue = "You normies";
            gp.player.Life += 1;
        }
        gp.keyH.enterPressed = false;
    }

}
