package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, presse, inventoryopen, enterPressed;
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setInventoryOpen(boolean inventoryOpen) {
        this.inventoryopen = inventoryOpen;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_I){
            inventoryopen = true;
        }
        if(code == KeyEvent.VK_P){
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }
            else if(gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }
        if (code == KeyEvent.VK_E){
            presse = true;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        // DEBUG

        if (code == KeyEvent.VK_T){
            if(checkDrawTime == false){
             checkDrawTime = true;
            }
            else if(checkDrawTime == true){
                checkDrawTime = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_I){
            inventoryopen = false;
        }
        if (code == KeyEvent.VK_E){
            presse = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }

    }
}