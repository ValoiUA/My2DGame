package main;



import Inventory.Invent;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // ggg
    final int originalTileSize = 16;
    final int scale = 3;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    Invent invent = new Invent();
    InventoryGUI inventoryGUI = new InventoryGUI(this, invent);
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this,keyH);
    public final int worldWith = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread () {
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
                remaingingTime = remaingingTime/1000000;

                if(remaingingTime < 0){
                    remaingingTime = 0;
                }
                Thread.sleep((long)remaingingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();


    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
    public void openInventory() {
        InventoryGUI inventoryGUI = new InventoryGUI(this, invent);
        inventoryGUI.setVisible(true);
        keyH.setInventoryOpen(true);
    }
    public void closeInventory() { keyH.setInventoryOpen(false); }
}