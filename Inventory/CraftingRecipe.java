package Inventory;

import java.util.ArrayList;
import java.util.List;

public class CraftingRecipe {
    private List<Item> requiredItems;
    private Item result;

    public CraftingRecipe(List<Item> requiredItems, Item result) {
        this.requiredItems = requiredItems;
        this.result = result;
    }

    public List<Item> getRequiredItems() {
        return requiredItems;
    }

    public Item getResult() {
        return result;
    }

    public boolean canCraft(Invent inventory) {
        List<Item> inventoryItems = new ArrayList<>(inventory.getItems());
        for (Item requiredItem : requiredItems) {
            boolean found = false;
            for (Item inventoryItem : inventoryItems) {
                if (requiredItem.equals(inventoryItem) && inventoryItem.getCount() >= requiredItem.getCount()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public List<Item> getMissingItems(Invent inventory) {
        List<Item> missingItems = new ArrayList<>();
        List<Item> inventoryItems = new ArrayList<>(inventory.getItems());

        for (Item requiredItem : requiredItems) {
            boolean found = false;
            for (Item inventoryItem : inventoryItems) {
                if (requiredItem.equals(inventoryItem)) {
                    if (inventoryItem.getCount() < requiredItem.getCount()) {
                        missingItems.add(new Item(requiredItem.getName(), requiredItem.getCount() - inventoryItem.getCount()));
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingItems.add(new Item(requiredItem.getName(), requiredItem.getCount()));
            }
        }
        return missingItems;
    }

    public void craft(Invent inventory) {
        if (canCraft(inventory)) {
            for (Item item : requiredItems) {
                inventory.removeItem(new Item(item.getName(), item.getCount()));
            }
            inventory.addItem(result);
        }
    }
}
