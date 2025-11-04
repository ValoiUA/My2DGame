package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private JFrame parentFrame;
    private JButton startButton;
    private JButton exitButton;
    private JButton settingsButton;
    private JButton loadButton;

    public MainMenu(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        initializeMenu();
    }

    public void initializeMenu() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));

        startButton = createMenuButton("Нова гра");
        loadButton = createMenuButton("Завантажити");
        settingsButton = createMenuButton("Налаштування");
        exitButton = createMenuButton("Вихід");

        buttonPanel.add(startButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(exitButton);

        JLabel titleLabel = new JLabel("МОЯ ГРА", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 100, 0));

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setupButtonListeners();
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }

    private void setupButtonListeners() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettings();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
    }

    private void startGame() {
        parentFrame.dispose();

        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setResizable(false);
            gameFrame.setTitle("Моя гра");

            GamePanel gamePanel = new GamePanel();
            gameFrame.add(gamePanel);

            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);

            gamePanel.startGameThread();
            gamePanel.setupGame();
        });
    }

    private void loadGame() {
        JOptionPane.showMessageDialog(this, "Функція завантаження гри буде реалізована пізніше");
    }

    private void showSettings() {
        JOptionPane.showMessageDialog(this, "Налаштування будуть доступні пізніше");
    }

    private void exitGame() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Ви впевнені, що хочете вийти?",
                "Вихід з гри",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(0, 0, 50),
                getWidth(), getHeight(), new Color(0, 0, 100)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(255, 255, 255, 50));
        for (int i = 0; i < getWidth(); i += 50) {
            for (int j = 0; j < getHeight(); j += 50) {
                g2d.drawRect(i, j, 1, 1);
            }
        }
    }
}