import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
/**
 *  Dies ist die Hauptklasse der Anwendung "Die Welt von Zuul".
 *  "Die Welt von Zuul" ist ein sehr einfaches, textbasiertes
 *  Adventure-Game. Ein Spieler kann sich in einer Umgebung bewegen,
 *  mehr nicht. Das Spiel sollte auf jeden Fall ausgebaut werden,
 *  damit es interessanter wird!
 * 
 *  Zum Spielen muss eine Instanz dieser Klasse erzeugt werden und
 *  an ihr die Methode "spielen" aufgerufen werden.
 * 
 *  Diese Instanz erzeugt und initialisiert alle anderen Objekte
 *  der Anwendung: Sie legt alle R�ume und einen Parser an und
 *  startet das Spiel. Sie wertet auch die Befehle aus, die der
 *  Parser liefert, und sorgt f�r ihre Ausf�hrung.
 * 
 * @author  Michael K�lling und David J. Barnes
 * @version 2016.02.29
 */

public class Spiel 
{
    private Parser parser;
    private Raum aktuellerRaum;
    private Spieler charakter;

    private Raum draussen, eingangshof, verwinkelteBibliothek, thronsaal, verfallenerKerker, flur, keller;
    private Item schluessel, schwert, laubpuster, heiltrank;
        
    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public static void main(String[] args)
    {
        Spiel spiel = new Spiel();
    }

    public Spiel() 
    {
        itemsAnlegen();
        raeumeAnlegen();
        parser = new Parser();
        charakter = new Spieler(nameGeben());
        spielen();
    }
    private String nameGeben()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gib deinen Namen ein: ");
        String name = scanner.nextLine();
        //scanner.close();
        return name;
    }

    /**
     * Erzeuge alle R�ume und verbinde ihre Ausg�nge miteinander.
     */
    private void raeumeAnlegen()
    {

        // die R�ume erzeugen
        draussen = new Raum("im nebeligen Wald", "im nebeligen Wald, wo das Rauschen der Blätter im Wind zu hören ist", new Item[]{laubpuster}, new ArrayList<Gegner>());
        eingangshof = new Raum("im verfallenen Eingangshof", "im verfallenen Eingangshof der alten Burgruine, umgeben von hohen Mauern und dichtem Nebel", new Item[]{}, new ArrayList<Gegner>(List.of(new Gegner("Hanswurst", 3, 1, schluessel))));
        flur = new Raum("im düsteren Flur", "im düsteren Flur zwischen dem Eingangshof und dem Thronsaal", new Item[]{}, new ArrayList<Gegner>());
        verwinkelteBibliothek = new Raum("in einer verwinkelten Bibliothek", "in einer verwinkelten Bibliothek, in der vergilbte Bücher und geheime Schriften verstauben", new Item[]{}, new ArrayList<Gegner>());
        thronsaal = new Raum("im majestätischen Thronsaal", "im majestätischen Thronsaal, wo einst der König herrschte, nun aber der große böse Rittergeist Sir Moros sein Unwesen treibt", new Item[]{}, new ArrayList<Gegner>(List.of(new Gegner("Sir Moros", 10, 4, null))));
        verfallenerKerker = new Raum("im verfallenen Kerker", "in einem verfallenen Kerker, wo düstere Gestalten ihre Spuren hinterlassen haben", new Item[]{}, new ArrayList<Gegner>());
        keller = new Raum("im kalten Kellergang", "in einem kalten Kellergang unter der alten Burgruine, mit feuchten Steinwänden", new Item[]{}, new ArrayList<Gegner>());


        
        // die Ausgänge verbinden
        draussen.setzeAusgang("north", eingangshof, false);
        eingangshof.setzeAusgang("south", draussen, false);
        eingangshof.setzeAusgang("north", flur, false);
        flur.setzeAusgang("south", eingangshof, false);
        flur.setzeAusgang("north", thronsaal, true);
        thronsaal.setzeAusgang("south", flur, false);
        flur.setzeAusgang("east", verwinkelteBibliothek, false);
        verwinkelteBibliothek.setzeAusgang("west", flur, false);
        flur.setzeAusgang("down", verfallenerKerker, false);
        verfallenerKerker.setzeAusgang("west", keller, true);
        keller.setzeAusgang("east", verfallenerKerker, true);
        keller.setzeAusgang("up", flur, false);
        flur.setzeAusgang("down", keller, false);

        aktuellerRaum = draussen;  // das Spiel startet draussen
    }
    private void itemsAnlegen()
    {
        schwert = new Item("Schwert", 3, false);
        schluessel = new Item("Schlüssel", 0, true);
        laubpuster = new Item("Laubpuster", 1, false);
        heiltrank = new Item("Heiltrank", 2, true);
    }

    /**
     * Die Hauptmethode zum Spielen. L�uft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen() 
    {            
        willkommenstextAusgeben();
        raumInfoAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
        // und f�hren sie aus, bis das Spiel beendet wird.
                
        boolean beendet = false;
        while (! beendet) {
            Befehl befehl = parser.liefereBefehl();
            beendet = verarbeiteBefehl(befehl);
        }
        System.out.println("Danke fürs Spielen. Auf Wiedersehen.");
    }

    /**
     * Einen Begr��ungstext f�r den Spieler ausgeben.
     */
    private void willkommenstextAusgeben()
    {
        System.out.println();
        System.out.println("Willkommen bei Zuul, " + charakter.gibName() + "!");
        System.out.println("Zuul ist ein fertiges, unglaublich spannendes Spiel.");
        System.out.println("Tippe 'help', wenn Sie Hilfe brauchen.");
        System.out.println();
    }

    private void raumInfoAusgeben()
    {
        System.out.println("Du bist " + aktuellerRaum.gibBeschreibung());
        aktuellerRaum.schreibeRaumInfo();
    }
    private void langeBeschreibungAusgeben()
    {
        System.out.println("Du bist " + aktuellerRaum.gibLangeBeschreibung());
        if (aktuellerRaum.gibRumliegendeItems().length > 0)
        {
            System.out.println("Hier liegen folgende Gegenstände herum:");
        }
        for (Item i : aktuellerRaum.gibRumliegendeItems()) {
            System.out.println(" - " + i.gibName());
        }
        if (aktuellerRaum.gibGegner().size() > 0)
        {
            System.out.println("In diesem Raum gibt es folgende Gegner:");
            for (Gegner g : aktuellerRaum.gibGegner()) {
                System.out.println(" - " + g.gibName() + ": " + g.gibHP() + " HP, " + g.gibSchaden() + " DMG");
            }
        }
        aktuellerRaum.schreibeRaumInfo();
    }
    private void take()
    {
        for (Item i : aktuellerRaum.nehmeRumliegendeItems()) {
            charakter.gibInventar().addItem(i);
            System.out.println(" - " + i.gibName());
        }
    }
    private boolean attack(Befehl befehl)
    {
        int schaden = 0;
        if (charakter.gibInventar().useItem())
        {
            schaden = charakter.gibInventar().gibGewaehltesItem().gibSchaden();
        }
        else
        {
            System.out.println("Der ausgewählte Gegenstand ist ungültig, die Attacke wir abgebrochen");
            return false;
        }
        //spieler attackiert alle Gegner im Raum
        Iterator<Gegner> it = aktuellerRaum.gibGegner().iterator();
        while (it.hasNext())
        {
            Gegner g = it.next();
            g.wirdAngegriffen(schaden);
            if (g.gibHP() > 0)
                System.out.println(g.gibName() + " wurde mit " + schaden + " DMG angegriffen, er hat nun noch " + g.gibHP() + " HP");
            else
            {
                if (g.gibDrop() == null)
                {
                    System.out.println(g.gibName() + " wurde mit " + schaden + " DMG angegriffen und besiegt");
                }
                else
                {
                    System.out.println(g.gibName() + " wurde mit " + schaden + " DMG angegriffen und besiegt, wobei er " + g.gibDrop().gibName() + " fallen gellassen hat");
                    aktuellerRaum.setzeRumliegendeItems(Arrays.asList(g.gibDrop()).toArray(new Item[0]));
                }
                //entferne den gestorbenen Gegner aus dem Array
                ArrayList<Gegner> alteGegner = aktuellerRaum.gibGegner();
                it.remove();
                aktuellerRaum.setzeGegner(alteGegner);
            }
        }
        //alle Gegner attackieren spieler
        for (Gegner g : aktuellerRaum.gibGegner()) {
            if (g.gibHP() > 0)
            {
                g.angreifen(charakter);
                System.out.println(charakter.gibName() + " wurde von " + g.gibName() + " mit " + g.gibSchaden() + " DMG angegriffen");
            }
        }
        if (charakter.gibHP() <= 0)
        {
            System.out.println("Du bist gestorben. :(");
            return true;
        }
        System.out.println("Du hast nun " + charakter.gibHP() + " HP");
        return false;
    }
    public void select(Befehl befehl)
    {
        String desiredItem = befehl.gibZweitesWort();
        if (charakter.gibInventar().selectItem(desiredItem)) {
            System.out.println("Der Gegenstand wurde ausgewaehlt.");
        }
        else
        {
            System.out.println("Der genannte Gegenstand befindet sich nicht im Inventar.");
        }
    }
    public void healthAusgeben()
    {
        System.out.println("Du hast aktuell " + charakter.gibHP() + " HP");
    }

    /**
     * Verarbeite einen gegebenen Befehl (f�hre ihn aus).
     * @param befehl   der zu verarbeitende Befehl.
     * @return true    wenn der Befehl das Spiel beendet, false sonst
     */
    private boolean verarbeiteBefehl(Befehl befehl) 
    {
        boolean moechteBeenden = false;

        if(befehl.istUnbekannt()) {
            System.out.println("Ich weiss nicht, was Sie meinen ...");
            return false;
        }
        String befehlswort = befehl.gibBefehlswort();
        if (befehlswort.equals("help")) {
            hilfstextAusgeben();
        }
        else if (befehlswort.equals("go")) {
            wechsleRaum(befehl);
        }
        else if (befehlswort.equals("quit")) {
            moechteBeenden = beenden(befehl);
        }
        else if (befehlswort.equals("look")) {
            langeBeschreibungAusgeben();
        }
        else if (befehlswort.equals("inventory")) {
            charakter.gibInventar().schreibeInventar();
        }
        else if (befehlswort.equals("take")) {
            take();
        }
        else if (befehlswort.equals("attack")) {
            moechteBeenden = attack(befehl);
        }
        else if (befehlswort.equals("health")) {
            healthAusgeben();
        }
        else if (befehlswort.equals("select")) {
            select(befehl);
        }

        return moechteBeenden;
    }

    private boolean versucheFalltuer(Raum aktuellerRaum, Raum naechsterRaum)
    {
        if (aktuellerRaum.gibBeschreibung().toLowerCase().contains("flur"))
        {
            if (naechsterRaum.gibBeschreibung().toLowerCase().contains("biblio"))
            {
                if (!aktuellerRaum.wurdeUmgeschaut())
                {
                    System.out.println("Oh nein, "+ charakter.gibName() + "! Du bist durch eine Falltür geplumpst!");
                    return true;
                }
            }
        }
        return false;
    }
    // Implementierung der Benutzerbefehle:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlsw�rter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Du bist in einem Märchenwald. Du bist allein.");
        System.out.println("Du siehst deine Umgebung und kannst mit ihr interagieren.");
        System.out.println();
        System.out.println("Dazu stehen dir folgende Befehle zur Verfügung:");
        for (String befehlswort : parser.gibMoeglicheBefehle()) {
            System.out.println("    " + befehlswort);
        };
    }

    /**
     * Versuche, in eine Richtung zu gehen. Wenn es einen Ausgang gibt,
     * wechsele in den neuen Raum, ansonsten gib eine Fehlermeldung
     * aus.
     */
    private void wechsleRaum(Befehl befehl) 
    {
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Sag doch wohin!!");
            return;
        }

        String richtung = befehl.gibZweitesWort();

        
        Raum naechsterRaum = aktuellerRaum.gibAusgang(richtung);

        if (versucheFalltuer(aktuellerRaum, naechsterRaum))
        {
            aktuellerRaum = verfallenerKerker;
            raumInfoAusgeben();
            return;
        }

        if (naechsterRaum == null || richtung.equals("down")) {
            System.out.println("Dort ist keine Tür!");
        }
        else {
            if (aktuellerRaum.istSchluesselGebraucht(richtung))
            {
                charakter.gibInventar().selectItem("Schlüssel");
                if (!charakter.gibInventar().useItem())
                {
                    System.out.println("Du hast keinen Schlüssel!");
                    raumInfoAusgeben();
                    return;
                }
            }
            aktuellerRaum = naechsterRaum;
            raumInfoAusgeben();
        }
        
    }

    /**
     * "quit" wurde eingegeben. �berpr�fe den Rest des Befehls,
     * ob das Spiel wirklich beendet werden soll.
     * @return true  wenn der Befehl das Spiel beendet, false sonst
     */
    private boolean beenden(Befehl befehl) 
    {
        if(befehl.hatZweitesWort()) {
            System.out.println("Was soll beendet werden?");
            return false;
        }
        else {
            return true;  // Das Spiel soll beendet werden.
        }
    }
}
