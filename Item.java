public class Item {

    private String name;
    private int schaden;
    private boolean singleUse;

    public Item(String name, int schaden, boolean singleUse)
    {
        this.name = name;
        this.schaden = schaden;
        this.singleUse = singleUse;
    }

    public String gibName()
    {
        return name;
    }
    public int gibSchaden()
    {
        return schaden;
    }
    public boolean gibSingleUse()
    {
        return singleUse;
    }
}
