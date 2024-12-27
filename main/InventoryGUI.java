package main;

import Inventory.CraftingRecipe;
import Inventory.Invent;
import Inventory.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class InventoryGUI extends JFrame {
    private GamePanel gamePanel;
    private Invent inventory;
    private DefaultListModel<String> inventoryListModel;
    private DefaultListModel<String> recipeListModel;
    private List<CraftingRecipe> recipes;
    private JTextArea recipeDetails;

    public InventoryGUI(GamePanel gamePanel, Invent inventory) {
        this.gamePanel = gamePanel;
        this.inventory = inventory;
        this.recipes = new ArrayList<>();

        setTitle("Inventory & Crafting");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gamePanel.closeInventory();
            }
        });

        // Ініціалізація інвентаря
        inventoryListModel = new DefaultListModel<>();
        JList<String> inventoryList = new JList<>(inventoryListModel);
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryList);
        inventoryScrollPane.setBorder(BorderFactory.createTitledBorder("Inventory"));

        // Ініціалізація рецептів
        recipeListModel = new DefaultListModel<>();
        JList<String> recipeList = new JList<>(recipeListModel);
        JScrollPane recipeScrollPane = new JScrollPane(recipeList);
        recipeScrollPane.setBorder(BorderFactory.createTitledBorder("Recipes"));

        // Додавання слухача для вибору рецепту
        recipeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayRecipeDetails(recipeList.getSelectedValue());
            }
        });

        // Ініціалізація деталей рецепту
        recipeDetails = new JTextArea();
        recipeDetails.setEditable(false);
        recipeDetails.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        recipeDetails.setFont(new Font("Serif", Font.PLAIN, 14));
        JScrollPane recipeDetailsScrollPane = new JScrollPane(recipeDetails);
        recipeDetailsScrollPane.setBorder(BorderFactory.createTitledBorder("Recipe Details"));

        // Додавання кнопок
        JButton addItemButton = new JButton("Add Item");
        styleButton(addItemButton);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        JButton craftItemButton = new JButton("Craft Item");
        styleButton(craftItemButton);
        craftItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                craftItem(recipeList.getSelectedValue());
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addItemButton);
        buttonPanel.add(craftItemButton);

        // Розташування панелей з однаковою шириною
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);
        inventoryPanel.setPreferredSize(new Dimension(200, 600));

        JPanel recipePanel = new JPanel(new BorderLayout());
        recipePanel.add(recipeScrollPane, BorderLayout.CENTER);
        recipePanel.setPreferredSize(new Dimension(200, 600));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(recipeDetailsScrollPane, BorderLayout.CENTER);
        detailsPanel.setPreferredSize(new Dimension(200, 600));

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        centerPanel.add(inventoryPanel);
        centerPanel.add(recipePanel);
        centerPanel.add(detailsPanel);

        setLayout(new BorderLayout(10, 10));
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Додавання рецептів
        addRecipes();

        updateInventoryList();
        updateRecipeList();
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    private void addItem() {
        String name = JOptionPane.showInputDialog(this, "Enter item name:");
        if (name != null && !name.trim().isEmpty()) {
            Item item = new Item(name, 1);
            inventory.addItem(item);
            updateInventoryList();
        }
    }

    private void craftItem(String recipeName) {
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
                JOptionPane.showMessageDialog(this, "Item crafted: " + selectedRecipe.getResult().getName());
                updateInventoryList();
            } else {
                List<Item> missingItems = selectedRecipe.getMissingItems(inventory);
                StringBuilder missing = new StringBuilder("You are missing:\n");
                for (Item item : missingItems) {
                    missing.append("- ").append(item.getName()).append(" x").append(item.getCount()).append("\n");
                }
                JOptionPane.showMessageDialog(this, missing.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a recipe to craft.");
        }
    }

    private void addRecipes() {
        // Додавання рецепту крафту для створення верстака з двох палок
        List<Item> recipeWorkbench = new ArrayList<>();
        recipeWorkbench.add(new Item("Stick", 2)); // дві палки
        recipes.add(new CraftingRecipe(recipeWorkbench, new Item("Workbench", 1)));

        // Додавання рецепту крафту для створення меча з трьох палок
        List<Item> recipeSword = new ArrayList<>();
        recipeSword.add(new Item("Stick", 3)); // три палки
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

    private void updateInventoryList() {
        inventoryListModel.clear();
        for (Item item : inventory.getItems()) {
            inventoryListModel.addElement(item.toString());
        }
    }

    private void updateRecipeList() {
        recipeListModel.clear();
        for (CraftingRecipe recipe : recipes) {
            recipeListModel.addElement(recipe.getResult().getName());
        }
    }

    private void displayRecipeDetails(String recipeName) {
        for (CraftingRecipe recipe : recipes) {
            if (recipe.getResult().getName().equals(recipeName)) {
                StringBuilder details = new StringBuilder();
                details.append("To craft ").append(recipeName).append(", you need:\n");
                for (Item item : recipe.getRequiredItems()) {
                    details.append("- ").append(item.getName()).append(" x").append(item.getCount()).append("\n");
                }
                recipeDetails.setText(details.toString());
                return;
            }
        }
        recipeDetails.setText("");
    }
}