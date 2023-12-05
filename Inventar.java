import java.util.HashMap;
import java.util.List;

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
    public boolean selectItem(Item item)
    {
        if (inventory.containsKey(item))
        {
            selectedItem = item;
            return true;
        }
        return false;
    }
    public boolean useItem(Item item)
    {
        inventory.put(item, inventory.get(item) - 1);
        if (item.gibSingleUse())
        {
            if (inventory.get(item) == -1)
            {
                inventory.put(item, 0);
                return false;
            }
        }
        return true;
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