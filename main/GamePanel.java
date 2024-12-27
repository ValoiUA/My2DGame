package main;


import main.InventoryGUI;
import Inventory.Invent;
import entity.Entity;
import entity.Player;
import maps.AssetSetter;
import object.Stick;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // ggg
    final int originalTileSize = 16;
    final int scale = 3;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    int FPS = 60; Invent invent = new Invent();
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(); Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread; public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[100];
    public Entity npc[] = new Entity[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {


            update();

            repaint();
            try {
                double remaingingTime = nextDrawTime - System.nanoTime();
                remaingingTime = remaingingTime / 1000000;

                if (remaingingTime < 0) {
                    remaingingTime = 0;
                }
                Thread.sleep((long) remaingingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
        for (int i = 0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].update();
            }
        }


    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        for(int i = 0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }

        player.draw(g2);

        g2.dispose();
    }

    public void openInventory() {
        InventoryGUI inventoryGUI = new InventoryGUI(this, invent);
        inventoryGUI.setVisible(true);
        keyH.setInventoryOpen(true);
    }

    public void closeInventory() {
        keyH.setInventoryOpen(false);
    }


    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();

    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}