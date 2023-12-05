import java.util.HashMap;

public class Inventar {
    HashMap<Item, Integer> inventory;
    Item selectedItem = null;

    public Inventar() {
        this.inventory = new HashMap<>();
    }

    public void addItem(Item item)
    {
        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
        selectedItem = item;
    }
    public boolean selectItem(String itemName) {
        for (Item item : inventory.keySet()) {
            if (item.gibName().equals(itemName)) {
                selectedItem = item;
                return true;
            }
        }
        return false;
    }
    
    public boolean useItem()
    {
        inventory.put(selectedItem, inventory.get(selectedItem) - 1);
        if (selectedItem.gibSingleUse())
        {
            if (inventory.get(selectedItem) == -1)
            {
                inventory.put(selectedItem, 0);
                return false;
            }
        }
        return true;
    }
    public Item gibGewaehltesItem()
    {
        return selectedItem;
    }
    public void schreibeInventar()
    {
        if (inventory.isEmpty()) {
            System.out.println("Du hast keine Gegenstände im Inventar!");
            return;
        }
        System.out.println("Du hast folgende Gegenstände bei dir:");
        for (Item i : inventory.keySet()) {
            System.out.println(inventory.get(i) + "x " + i.gibName());
        }
    }
}