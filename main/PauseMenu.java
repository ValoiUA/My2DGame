package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseMenu {
    private GamePanel gamePanel;
    private JDialog pauseDialog;
    private JButton resumeButton;
    private JButton settingsButton;
    private JButton mainMenuButton;
    private JButton exitButton;

    public PauseMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        createPauseDialog();
    }

    private void createPauseDialog() {
        // Отримуємо батьківське вікно
        Window parentWindow = SwingUtilities.getWindowAncestor(gamePanel);

        pauseDialog = new JDialog((Frame)parentWindow, "Пауза", true);
        pauseDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        pauseDialog.setResizable(false);
        pauseDialog.setSize(400, 500);
        pauseDialog.setLocationRelativeTo(gamePanel);
        pauseDialog.getContentPane().setBackground(new Color(30, 30, 30));

        initializeMenu();
    }

    private void initializeMenu() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Заголовок
        JLabel titleLabel = new JLabel("ПАУЗА", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 15, 15));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        resumeButton = createMenuButton("▶ Продовжити гру", new Color(46, 204, 113));
        settingsButton = createMenuButton("⚙ Налаштування", new Color(52, 152, 219));
        mainMenuButton = createMenuButton("🏠 Головне меню", new Color(155, 89, 182));
        exitButton = createMenuButton("🚪 Вихід", new Color(231, 76, 60));

        buttonPanel.add(resumeButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(exitButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        pauseDialog.add(mainPanel);
        setupButtonListeners();
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 2),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void setupButtonListeners() {
        resumeButton.addActionListener(e -> {
            pauseDialog.dispose();
            gamePanel.togglePause();
        });

        settingsButton.addActionListener(e -> showSettings());

        mainMenuButton.addActionListener(e -> returnToMainMenu());

        exitButton.addActionListener(e -> exitGame());
    }

    public void showPauseMenu() {
        pauseDialog.setVisible(true);
    }

    private void showSettings() {
        JOptionPane.showMessageDialog(pauseDialog,
                "Налаштування будуть доступні в наступних оновленнях",
                "Налаштування",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void returnToMainMenu() {
        int result = JOptionPane.showConfirmDialog(pauseDialog,
                "Ви впевнені, що хочете повернутися в головне меню?\nНе збережений прогрес буде втрачено.",
                "Повернення в меню",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            pauseDialog.dispose();
            gamePanel.returnToMainMenu();
        }
    }

    private void exitGame() {
        int result = JOptionPane.showConfirmDialog(pauseDialog,
                "Ви впевнені, що хочете вийти?\nНе збережений прогрес буде втрачено.",
                "Вихід з гри",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}