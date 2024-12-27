package Inventory;
public class Item {    private String name;
    private int count;
    public Item(String name, int count)    {
        this.name = name;        this.count = count;
    }
    public String getName() {
        return name;
    }    public int getCount(){
        return count;    }

    @Override
    public int hashCode() {        return name.hashCode();
    }}