import java.util.Scanner;
import java.util.ArrayList;
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
        
    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public static void main(String[] args)
    {
        Spiel spiel = new Spiel();
    }

    public Spiel() 
    {
        raeumeAnlegen();
        parser = new Parser();
        charakter = new Spieler(nameGeben());
        charakter.gibInventar().addItem("Schlüssel");
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
        draussen = new Raum("im nebeligen Wald", "im nebeligen Wald, wo das Rauschen der Blätter im Wind zu hören ist", new String[]{"Laubpuster"}, new ArrayList<Gegner>());
        eingangshof = new Raum("im verfallenen Eingangshof", "im verfallenen Eingangshof der alten Burgruine, umgeben von hohen Mauern und dichtem Nebel", new String[]{}, new ArrayList<Gegner>());
        flur = new Raum("im düsteren Flur", "im düsteren Flur zwischen dem Eingangshof und dem Thronsaal", new String[]{}, new ArrayList<Gegner>());
        verwinkelteBibliothek = new Raum("in einer verwinkelten Bibliothek", "in einer verwinkelten Bibliothek, in der vergilbte Bücher und geheime Schriften verstauben", new String[]{}, new ArrayList<Gegner>());
        thronsaal = new Raum("im majestätischen Thronsaal", "im majestätischen Thronsaal, wo einst der König herrschte, nun aber der große böse Rittergeist Sir Moros sein Unwesen treibt", new String[]{}, new ArrayList<Gegner>());
        verfallenerKerker = new Raum("im verfallenen Kerker", "in einem verfallenen Kerker, wo düstere Gestalten ihre Spuren hinterlassen haben", new String[]{}, new ArrayList<Gegner>());
        keller = new Raum("im kalten Kellergang", "in einem kalten Kellergang unter der alten Burgruine, mit feuchten Steinwänden", new String[]{}, new ArrayList<Gegner>());


        
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
            System.out.println("Du findest:");
        }
        for (String i : aktuellerRaum.gibRumliegendeItems()) {
            charakter.gibInventar().addItem(i);
            System.out.println(" - " + i);
        }
        aktuellerRaum.schreibeRaumInfo();
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
                if (!charakter.gibInventar().useItem("Schlüssel"))
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
