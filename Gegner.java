public class Gegner {
    private int hp;
    private String name;
    private int schaden;
    private Item drop;
    private float multiplySchwert;
    private float multiplyLaubpuster;

    public Gegner(String name, int hp, int schaden, Item drop, float multiplySchwert, float multiplyLaubpuster) {
        this.name = name;
        this.hp = hp;
        this.schaden = schaden;
        this.drop = drop;
        this.multiplySchwert = multiplySchwert;
        this.multiplyLaubpuster = multiplyLaubpuster;
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
    public float gibMultiplySchwert() {
        return multiplySchwert;
    }
    public float gibMultiplyLaubpuster() {
        return multiplyLaubpuster;
    }
}
