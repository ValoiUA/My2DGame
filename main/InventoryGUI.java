package main;

import Inventory.CraftingRecipe;
import Inventory.Invent;
import Inventory.Item;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryGUI extends JFrame {
    private GamePanel gamePanel;
    private Invent inventory;
    private List<CraftingRecipe> recipes;
    private JPanel gridPanel;
    private JTextArea recipeDetails;
    private DefaultListModel<String> recipeListModel;
    private Timer messageTimer;
    private JDialog currentMessageDialog;
    private Timer fadeOutTimer; // Окремий таймер для анімації зникнення

    private final int GRID_SIZE = 8;

    public InventoryGUI(GamePanel gamePanel, Invent inventory) {
        this.gamePanel = gamePanel;
        this.inventory = inventory;
        this.recipes = new ArrayList<>();

        // Прозорий, безрамковий інвентар
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(1000, 700);
        setLayout(new BorderLayout(15, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gamePanel.closeInventory();
            }
        });

        // Панель для сітки інвентаря
        gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(30, 33, 40, 180));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gridPanel.setOpaque(false);
        updateInventoryGrid();

        // Панель рецептів
        recipeListModel = new DefaultListModel<>();
        JList<String> recipeList = new JList<>(recipeListModel);
        recipeList.setBackground(new Color(50, 54, 63, 180));
        recipeList.setForeground(Color.WHITE);
        recipeList.setFont(new Font("Verdana", Font.BOLD, 14));

        recipeDetails = new JTextArea();
        recipeDetails.setEditable(false);
        recipeDetails.setBackground(new Color(50, 54, 63, 180));
        recipeDetails.setForeground(Color.WHITE);
        recipeDetails.setFont(new Font("Verdana", Font.PLAIN, 14));
        recipeDetails.setLineWrap(true);
        recipeDetails.setWrapStyleWord(true);
        recipeDetails.setMargin(new Insets(10, 10, 10, 10));

        recipeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayRecipeDetails(recipeList.getSelectedValue());
            }
        });

        // Створюємо панель з правильним розподілом висоти
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        JScrollPane recipeListScroll = new JScrollPane(recipeList);
        recipeListScroll.setPreferredSize(new Dimension(250, (int)(600 * 0.65)));
        recipeListScroll.setMaximumSize(new Dimension(250, (int)(600 * 0.65)));
        recipeListScroll.setMinimumSize(new Dimension(250, (int)(600 * 0.65)));
        recipeListScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Recipes List",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Verdana", Font.BOLD, 14),
                Color.WHITE
        ));

        JScrollPane detailsScroll = new JScrollPane(recipeDetails);
        detailsScroll.setPreferredSize(new Dimension(250, (int)(600 * 0.35)));
        detailsScroll.setMaximumSize(new Dimension(250, (int)(600 * 0.35)));
        detailsScroll.setMinimumSize(new Dimension(250, (int)(600 * 0.35)));
        detailsScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Recipe Details",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Verdana", Font.BOLD, 14),
                Color.WHITE
        ));

        rightPanel.add(recipeListScroll);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(detailsScroll);
        rightPanel.setPreferredSize(new Dimension(250, 600));

        // Кнопки
        JButton craftItemButton = makeButton("Craft Item", e -> {
            String selected = recipeList.getSelectedValue();
            craftItem(selected);
        });

        JButton closeButton = makeButton("Close", e -> {
            closeAllMessages();
            setVisible(false);
            gamePanel.closeInventory();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(craftItemButton);
        buttonPanel.add(closeButton);

        add(gridPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        addRecipes();
        updateRecipeList();
    }

    private JButton makeButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 149, 237, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Verdana", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return button;
    }

    private void updateInventoryGrid() {
        gridPanel.removeAll();
        List<Item> items = inventory.getItems();
        int totalCells = GRID_SIZE * GRID_SIZE;

        for (int i = 0; i < totalCells; i++) {
            JPanel cell = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(new Color(60, 63, 70, 180));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
            };
            cell.setOpaque(false);

            if (i < items.size()) {
                Item item = items.get(i);
                JLabel iconLabel = new JLabel();
                iconLabel.setHorizontalAlignment(JLabel.CENTER);
                iconLabel.setVerticalAlignment(JLabel.CENTER);

                try {
                    ImageIcon icon = new ImageIcon("images/objimg/" + item.getName() + ".png");
                    Image scaled = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                    iconLabel.setIcon(new ImageIcon(scaled));
                } catch (Exception e) {
                    iconLabel.setText(item.getName());
                    iconLabel.setForeground(Color.WHITE);
                }

                JLabel countLabel = new JLabel("x" + item.getCount(), JLabel.RIGHT);
                countLabel.setForeground(Color.WHITE);
                countLabel.setFont(new Font("Verdana", Font.PLAIN, 12));

                cell.add(iconLabel, BorderLayout.CENTER);
                cell.add(countLabel, BorderLayout.SOUTH);

                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gamePanel.player.holdItem(item);
                        showItemTakenMessage(item);
                    }
                });
            }

            gridPanel.add(cell);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void craftItem(String recipeName) {
        if (recipeName == null) {
            showCraftingMessage("Select a recipe first!", false);
            return;
        }

        CraftingRecipe selectedRecipe = null;
        for (CraftingRecipe recipe : recipes) {
            if (recipe.getResult().getName().equals(recipeName)) {
                selectedRecipe = recipe;
                break;
            }
        }

        if (selectedRecipe != null) {
            if (selectedRecipe.canCraft(inventory)) {
                selectedRecipe.craft(inventory);
                showCraftingMessage("Successfully crafted: " + recipeName + "!", true);
                updateInventoryGrid();
            } else {
                showCraftingMessage("Missing materials for: " + recipeName + "!", false);
            }
        }
    }

    private void addRecipes() {
        List<Item> recipeWorkbench = new ArrayList<>();
        recipeWorkbench.add(new Item("Stick", 2));
        recipes.add(new CraftingRecipe(recipeWorkbench, new Item("Workbench", 1)));

        List<Item> recipeSword = new ArrayList<>();
        recipeSword.add(new Item("Stick", 3));
        recipes.add(new CraftingRecipe(recipeSword, new Item("Sword", 1)));

        List<Item> recipeAxe = new ArrayList<>();
        recipeAxe.add(new Item("Stick", 2));
        recipeAxe.add(new Item("Stone", 1));
        recipes.add(new CraftingRecipe(recipeAxe, new Item("Axe", 1)));

        List<Item> recipeKir = new ArrayList<>();
        recipeKir.add(new Item("Stick", 2));
        recipeKir.add(new Item("Stone", 2));
        recipes.add(new CraftingRecipe(recipeKir, new Item("Kir", 1)));
    }

    private void updateRecipeList() {
        recipeListModel.clear();
        for (CraftingRecipe recipe : recipes) {
            recipeListModel.addElement(recipe.getResult().getName());
        }
    }

    private void displayRecipeDetails(String recipeName) {
        recipeDetails.setText("");

        if (recipeName == null) {
            return;
        }

        for (CraftingRecipe recipe : recipes) {
            if (recipe.getResult().getName().equals(recipeName)) {
                StringBuilder details = new StringBuilder();
                details.append("To craft ").append(recipeName).append(" you need:\n\n");
                for (Item item : recipe.getRequiredItems()) {
                    details.append("• ").append(item.getName()).append(" x").append(item.getCount()).append("\n");
                }
                recipeDetails.setText(details.toString());
                recipeDetails.setCaretPosition(0);
                return;
            }
        }
        recipeDetails.setText("");
    }

    private void showItemTakenMessage(Item item) {
        showMessage("✓ You took " + item.getName() + " in hand!", true, 1500);
    }

    private void showCraftingMessage(String message, boolean success) {
        showMessage(message, success, 2000);
    }

    private void showMessage(String message, boolean success, int duration) {
        // Закриваємо попереднє сповіщення
        closeAllMessages();

        // Створюємо нове сповіщення
        currentMessageDialog = new JDialog(this, "", false);
        currentMessageDialog.setUndecorated(true);
        currentMessageDialog.setSize(350, 80);
        currentMessageDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(success ? new Color(30, 120, 60, 200) : new Color(120, 30, 60, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(success ? new Color(50, 200, 100) : new Color(200, 50, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);

        JLabel label = new JLabel(message, JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        panel.add(label, BorderLayout.CENTER);

        currentMessageDialog.add(panel);
        currentMessageDialog.setOpacity(0f);

        // Анімація появи
        Timer fadeInTimer = new Timer(20, new ActionListener() {
            float opacity = 0f;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Перевіряємо, чи сповіщення ще існує
                if (currentMessageDialog == null) {
                    ((Timer)e.getSource()).stop();
                    return;
                }

                opacity += 0.1f;
                if (opacity >= 1f) {
                    opacity = 1f;
                    ((Timer)e.getSource()).stop();
                }
                currentMessageDialog.setOpacity(opacity);
            }
        });
        fadeInTimer.start();

        currentMessageDialog.setVisible(true);

        // Таймер для автоматичного закриття
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
        }

        messageTimer = new Timer(duration, e -> {
            closeCurrentMessageWithAnimation();
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    private void closeAllMessages() {
        // Зупиняємо всі таймери
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
        }
        if (fadeOutTimer != null && fadeOutTimer.isRunning()) {
            fadeOutTimer.stop();
        }

        // Закриваємо сповіщення без анімації
        if (currentMessageDialog != null) {
            currentMessageDialog.dispose();
            currentMessageDialog = null;
        }
    }

    private void closeCurrentMessageWithAnimation() {
        if (currentMessageDialog == null) {
            return;
        }

        // Зупиняємо основний таймер
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
        }

        // Анімація зникнення
        if (fadeOutTimer != null && fadeOutTimer.isRunning()) {
            fadeOutTimer.stop();
        }

        fadeOutTimer = new Timer(20, new ActionListener() {
            float opacity = 1f;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Перевіряємо, чи сповіщення ще існує
                if (currentMessageDialog == null) {
                    ((Timer)e.getSource()).stop();
                    return;
                }

                opacity -= 0.1f;
                if (opacity <= 0f) {
                    opacity = 0f;
                    ((Timer)e.getSource()).stop();
                    // Останній крок - закриваємо сповіщення
                    if (currentMessageDialog != null) {
                        currentMessageDialog.dispose();
                        currentMessageDialog = null;
                    }
                } else {
                    // Безпечно встановлюємо прозорість
                    if (currentMessageDialog != null) {
                        currentMessageDialog.setOpacity(opacity);
                    }
                }
            }
        });
        fadeOutTimer.start();
    }
}