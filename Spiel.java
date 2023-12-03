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
        
    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public static void main(String[] args)
    {
        Spiel spiel = new Spiel();
        spiel.spielen();
    }
    public Spiel() 
    {
        raeumeAnlegen();
        parser = new Parser();
    }

    /**
     * Erzeuge alle R�ume und verbinde ihre Ausg�nge miteinander.
     */
    private void raeumeAnlegen()
    {
        Raum draussen, hoersaal, cafeteria, labor, buero;

        // die R�ume erzeugen
        draussen = new Raum("vor dem Haupteingang der Universit�t");
        hoersaal = new Raum("in einem Vorlesungssaal");
        cafeteria = new Raum("in der Cafeteria der Uni");
        labor = new Raum("in einem Rechnerraum");
        buero = new Raum("im Verwaltungsbüro der Informatik");
        
        // die Ausg�nge initialisieren
        //north east süden west
        draussen.setzeAusgang("north", null);
        draussen.setzeAusgang("east", hoersaal);
        draussen.setzeAusgang("south", labor);
        draussen.setzeAusgang("west", cafeteria);

        hoersaal.setzeAusgang("north", null);
        hoersaal.setzeAusgang("east", null);
        hoersaal.setzeAusgang("south", null);
        hoersaal.setzeAusgang("west", draussen);

        cafeteria.setzeAusgang("north", null);
        cafeteria.setzeAusgang("east", draussen);
        cafeteria.setzeAusgang("south", null);
        cafeteria.setzeAusgang("west", null);

        labor.setzeAusgang("north", draussen);
        labor.setzeAusgang("east", buero);
        labor.setzeAusgang("south", null);
        labor.setzeAusgang("west", null);

        buero.setzeAusgang("north", null);
        buero.setzeAusgang("east", null);
        buero.setzeAusgang("south", null);
        buero.setzeAusgang("west", labor);


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
        System.out.println("Willkommen bei Zuul!");
        System.out.println("Zuul ist ein fertiges, unglaublich spannendes Spiel.");
        System.out.println("Tippen Sie 'help', wenn Sie Hilfe brauchen.");
        System.out.println();
    }

    private void raumInfoAusgeben()
    {
        System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
        System.out.print("Ausgänge: ");
        if(aktuellerRaum.gibAusgang("north") != null) {
            System.out.print("north ");
        }
        if(aktuellerRaum.gibAusgang("east") != null) {
            System.out.print("east ");
        }
        if(aktuellerRaum.gibAusgang("south") != null) {
            System.out.print("south ");
        }
        if(aktuellerRaum.gibAusgang("west") != null) {
            System.out.print("west ");
        }
        System.out.println();
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
        else if (befehlswort.equals("map")) {
            raumInfoAusgeben();
        }
        
        return moechteBeenden;
    }

    // Implementierung der Benutzerbefehle:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlsw�rter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Sie haben sich verlaufen. Sie sind allein.");
        System.out.println("Sie irren auf dem Unigelände herum.");
        System.out.println();
        System.out.println("Ihnen stehen folgende Befehle zur Verfügung:");
        System.out.println("   go quit help map");
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
            System.out.println("Sagen Sie doch wohin!!");
            return;
        }

        String richtung = befehl.gibZweitesWort();

        // Wir versuchen, den Raum zu verlassen.
        Raum naechsterRaum = null;
        if(richtung.equals("north")) {
            naechsterRaum = aktuellerRaum.gibAusgang("north");
        }
        if(richtung.equals("east")) {
            naechsterRaum = aktuellerRaum.gibAusgang("east");
        }
        if(richtung.equals("south")) {
            naechsterRaum = aktuellerRaum.gibAusgang("south");
        }
        if(richtung.equals("west")) {
            naechsterRaum = aktuellerRaum.gibAusgang("west");
        }

        if (naechsterRaum == null) {
            System.out.println("Dort ist keine Tür!");
        }
        else {
            aktuellerRaum = naechsterRaum;
            System.out.println("Sie sind " + aktuellerRaum.gibBeschreibung());
            System.out.print("Ausg�nge: ");
            if(aktuellerRaum.gibAusgang("north") != null) {
                System.out.print("north ");
            }
            if(aktuellerRaum.gibAusgang("east") != null) {
                System.out.print("east ");
            }
            if(aktuellerRaum.gibAusgang("south") != null) {
                System.out.print("south ");
            }
            if(aktuellerRaum.gibAusgang("west") != null) {
                System.out.print("west ");
            }
            System.out.println();
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
