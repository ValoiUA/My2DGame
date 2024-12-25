package main;

import Inventory.CraftingRecipe;
import Inventory.Invent;
import Inventory.Item;

import javax.swing.*;
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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        JScrollPane recipeDetailsScrollPane = new JScrollPane(recipeDetails);
        recipeDetailsScrollPane.setBorder(BorderFactory.createTitledBorder("Recipe Details"));

        // Додавання кнопок
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        JButton craftItemButton = new JButton("Craft Item");
        craftItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                craftItem();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addItemButton);
        buttonPanel.add(craftItemButton);

        // Розташування панелей
        setLayout(new BorderLayout());
        add(inventoryScrollPane, BorderLayout.WEST);
        add(recipeDetailsScrollPane, BorderLayout.CENTER);
        add(recipeScrollPane, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Додавання рецептів
        addRecipes();

        updateInventoryList();
        updateRecipeList();
    }

    private void addItem() {
        String name = JOptionPane.showInputDialog(this, "Enter item name:");
        Item item = new Item(name);
        inventory.addItem(item);
        updateInventoryList();
    }

    private void craftItem() {
        boolean crafted = false;
        for (CraftingRecipe recipe : recipes) {
            if (recipe.canCraft(inventory)) {
                recipe.craft(inventory);
                JOptionPane.showMessageDialog(this, "Item crafted: " + recipe.getResult().getName());
                updateInventoryList();
                crafted = true;
                break;
            }
        }
        if (!crafted) {
            JOptionPane.showMessageDialog(this, "No valid crafting recipes found.");
        }
    }


    private void addRecipes() {
        // Додавання рецепту крафту для створення верстаку з двох дерев
        List<Item> recipeWorkbench = new ArrayList<>();
        recipeWorkbench.add(new Item("Wood"));
        recipeWorkbench.add(new Item("Wood"));
        recipes.add(new CraftingRecipe(recipeWorkbench, new Item("Workbench")));
    }

    private void updateInventoryList() {
        inventoryListModel.clear();
        for (Item item : inventory.getItems()) {
            inventoryListModel.addElement(item.getName());
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
                    details.append("- ").append(item.getName()).append("\n");
                }
                recipeDetails.setText(details.toString());
                return;
            }
        }
        recipeDetails.setText("");
    }
}
