import java.util.HashMap;
import java.util.List;

public class Inventar {
    HashMap<String, Integer> inventory;
    List<String> possibleItems;

    public Inventar(List<String> possibleItems) {
        this.inventory = new HashMap<>();
        this.possibleItems = possibleItems;
    }

    public void addItem(String itemName)
    {
        if (possibleItems.contains(itemName)) {
            inventory.put(itemName, inventory.getOrDefault(itemName, 0) + 1);
        }
    }
    public boolean useItem(String itemName)
    {
        if (inventory.containsKey(itemName)) {
            if (inventory.get(itemName) > 0) {
                inventory.put(itemName, inventory.get(itemName) - 1);
                return true;
            }
        }
        return false;
    }
    public List<String> schreibeInventar()
    {
        if (inventory.isEmpty()) {
            System.out.println("Du hast keine Gegenstände im Inventar!");
            return possibleItems;
        }
        System.out.println("Du hast folgende Gegenstände bei dir:");
        for (String i : inventory.keySet()) {
            System.out.println(inventory.get(i) + "x " + i);
        }
        return possibleItems;
    }
}