package Inventory;

import java.util.ArrayList;
import java.util.List;

public class Invent {
    private List<Item> items;

    public Invent() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        for (Item i : items) {
            if (i.equals(item)) {
                i.increaseCount(item.getCount());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        for (Item i : items) {
            if (i.equals(item)) {
                i.decreaseCount(item.getCount());
                if (i.getCount() <= 0) {
                    items.remove(i);
                }
                return;
            }
        }
    }

    public int getItemCount(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item.getCount();
            }
        }
        return 0;
    }

    public List<Item> getItems() {
        return items;
    }
}
