package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame menuFrame = new JFrame();
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setResizable(false);
            menuFrame.setTitle("My 2D Game");


            MainMenu mainMenu = new MainMenu(menuFrame);
            menuFrame.add(mainMenu);

            menuFrame.pack();
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);
        });
    }
}