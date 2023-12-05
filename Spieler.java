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
    public void heilen(int hp) {
        this.hp += hp;
        if (this.hp > 10) {
            this.hp = 10;
        }
    }
    public Inventar gibInventar() {
        return inventar;
    }
}
