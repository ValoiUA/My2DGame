package Inventory;
import java.util.List;import java.util.ArrayList;
public class CraftingRecipe {
    private List<Item> requiredItems;    private Item result;
    public CraftingRecipe(List<Item> requiredItems, Item result) {
        this.requiredItems = requiredItems;        this.result = result;
    }
    public List<Item> getRequiredItems() {
        return requiredItems;    }
    public Item getResult() {
        return result;
    }
    public boolean canCraft(Invent inventory) {        List<Item> inventoryItems = new ArrayList<>(inventory.getItems());
        for (Item item : requiredItems) {            if (!inventoryItems.remove(item)) {
            System.out.println("cant");                return false;
        }
        }        System.out.println("can");
        return true;    }
    public void craft(Invent inventory) {
        if (canCraft(inventory)) {            for (Item item : requiredItems) {
            inventory.removeItem(item);            }
            inventory.addItem(result);        }
    }}