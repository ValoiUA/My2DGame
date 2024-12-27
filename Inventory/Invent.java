package Inventory;
import java.util.ArrayList;import java.util.List;
public class Invent {
    private List<Item> items;
    public Invent() {        items = new ArrayList<>();
    }
    public void addItem(Item item) {        items.add(item);
    }
    public void removeItem(Item item) {        items.remove(item);
    }
    public List<Item> getItems() {        return items;
    }}