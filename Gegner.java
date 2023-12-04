public class Gegner {
    private int hp;
    private String name;
    private int schaden;

    public Gegner(String name, int hp, int schaden) {
        this.name = name;
        this.hp = hp;
        this.schaden = schaden;
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
}
