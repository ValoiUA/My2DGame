new Item(name, 1);
            inventory.addItem(item);            updateInventoryList();
        }    }
private void craftItem() {
    boolean crafted = false;        for (CraftingRecipe recipe : recipes) {
        if (recipe.canCraft(inventory)) {                recipe.craft(inventory);
            JOptionPane.showMessageDialog(this, "Item crafted: " + recipe.getResult().getName());                updateInventoryList();
            crafted = true;                break;
        }        }
    if (!crafted) {            JOptionPane.showMessageDialog(this, "No valid crafting recipes found.");
    }    }
private void addRecipes() {
    // Додавання рецепту крафту для створення верстаку з двох дерев        List<Item> recipeWorkbench = new ArrayList<>();
    recipeWorkbench.add(new Item("Wood", 1));        recipeWorkbench.add(new Item("Wood", 1));
    recipes.add(new CraftingRecipe(recipeWorkbench, new Item("Workbench", 1)));    }
private void updateInventoryList() {
    inventoryListModel.clear();        for (Item item : inventory.getItems()) {
        inventoryListModel.addElement(item.getName());        }
}
private void updateRecipeList() {        recipeListModel.clear();
    for (CraftingRecipe recipe : recipes) {            recipeListModel.addElement(recipe.getResult().getName());
    }    }
private void displayRecipeDetails(String recipeName) {
    for (CraftingRecipe recipe : recipes) {            if (recipe.getResult().getName().equals(recipeName)) {
        StringBuilder details = new StringBuilder();                details.append("To craft ").append(recipeName).append(", you need:\n");
        for (Item item : recipe.getRequiredItems()) {                    details.append("- ").append(item.getName()).append("\n");
        }                recipeDetails.setText(details.toString());
        return;            }
    }        recipeDetails.setText("");
}}