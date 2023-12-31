public class Spieler {

    private int hp = 10;
    private String name;
    private Inventar inventar;

    public Spieler(String name) {
        this.name = name;
        this.inventar = new Inventar();
    }
    public String gibName() {
        return name;
    }
    public int gibHP() {
        return hp;
    }
    public void schaden(int schaden) {
        hp -= schaden;
        if (hp < 0) {
            hp = 0;
        }
    }
    public Inventar gibInventar() {
        return inventar;
    }
}
