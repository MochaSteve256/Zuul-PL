public class Gegner {
    private int hp;
    private String name;
    private int schaden;
    private Item drop;

    public Gegner(String name, int hp, int schaden, Item drop) {
        this.name = name;
        this.hp = hp;
        this.schaden = schaden;
        this.drop = drop;
    }
    public void wirdAngegriffen(int schaden) {
        hp -= schaden;
        if (hp <= 0) {
            hp = 0;
        }
    }
    public void angreifen(Spieler spieler) {
        spieler.schaden(schaden);
    }
    public int gibHP() {
        return hp;
    }
    public String gibName() {
        return name;
    }
    public int gibSchaden() {
        return schaden;
    }
    public Item gibDrop() {
        return drop;
    }
}
