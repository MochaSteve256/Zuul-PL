import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
/**
 * Diese Klasse modelliert R�ume in der Welt von Zuul.
 * 
 * Diese Klasse ist Teil der Anwendung "Die Welt von Zuul".
 * "Die Welt von Zuul" ist ein sehr einfaches textbasiertes 
 * Adventure-Game.
 * 
 * Ein "Raum" repr�sentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen R�umen �ber Ausg�nge verbunden.
 * M�gliche Ausg�nge liegen im Norden, Osten, S�den und Westen.
 * F�r jede Richtung h�lt ein Raum eine Referenz auf den 
 * benachbarten Raum.
 * 
 * @author  Michael K�lling und David J. Barnes
 * @version 2016.02.29
 */
public class Raum 
{

    private String beschreibung;
    private String langeBeschreibung;
    private HashMap<String, Raum> ausgaenge;
    private HashMap <String, Boolean> schluesselGebraucht;
    private boolean umgeschaut = false;
    private String[] rumliegendeItems;
    private ArrayList<Gegner> gegner;

    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausg�nge. Eine Beschreibung hat die Form 
     * "in einer K�che" oder "auf einem Sportplatz".
     * @param beschreibung  die Beschreibung des Raums
     */
    public Raum(String beschreibung, String langeBeschreibung, String[] rumliegendeItems, ArrayList<Gegner> gegner) 
    {
        this.beschreibung = beschreibung;
        this.langeBeschreibung = langeBeschreibung;
        this.ausgaenge = new HashMap<>();
        this.schluesselGebraucht = new HashMap<>();
        this.rumliegendeItems = rumliegendeItems;
        this.gegner = gegner;
    }

    public void setzeAusgang(String richtung, Raum raum, boolean brauchtSchluessel)
    {
        if (raum != null)
        {
            ausgaenge.put(richtung, raum);
            schluesselGebraucht.put(richtung, brauchtSchluessel);
        }
    }
    public boolean istSchluesselGebraucht(String richtung)
    {
        return schluesselGebraucht.get(richtung);
    }
    public void schreibeRaumInfo()
    {
        String ergebnis = "Ausgänge:";
        Set<String> keys = ausgaenge.keySet();
        for(String ausgang : keys)
            ergebnis += " " + ausgang;
        System.out.println(ergebnis);
    }

    /**
     * @return  die Beschreibung dieses Raums
     */
    public String gibBeschreibung()
    {
        return beschreibung;
    }
    public String gibLangeBeschreibung()
    {
        umgeschaut = true;
        return langeBeschreibung;
    }
    public boolean wurdeUmgeschaut()
    {
        return umgeschaut;
    }
    public Raum gibAusgang(String richtung)
    {
        return ausgaenge.get(richtung);
    }
    public ArrayList<Gegner> gibGegner()
    {
        return gegner;
    }
    public String[] gibRumliegendeItems()
    {
        return rumliegendeItems;
    }
}
