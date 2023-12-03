import java.util.HashMap;
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
    private HashMap<String, Raum> ausgaenge;

    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausg�nge. Eine Beschreibung hat die Form 
     * "in einer K�che" oder "auf einem Sportplatz".
     * @param beschreibung  die Beschreibung des Raums
     */
    public Raum(String beschreibung) 
    {
        this.beschreibung = beschreibung;
        this.ausgaenge = new HashMap<>();
    }

    /**
     * Definiere die Ausg�nge dieses Raums. Jede Richtung
     * f�hrt entweder in einen anderen Raum oder ist 'null'
     * (kein Ausgang).
     * @param norden  der Nordausgang
     * @param osten   der Ostausgang
     * @param sueden  der S�dausgang
     * @param westen  der Westausgang
     */
    public void setzeAusgang(String richtung, Raum raum)
    {
        if (raum != null) 
        {
            ausgaenge.put(richtung, raum);
        }
    }

    /**
     * @return  die Beschreibung dieses Raums
     */
    public String gibBeschreibung()
    {
        return beschreibung;
    }

    public Raum gibAusgang(String richtung)
    {
        return ausgaenge.get(richtung);
    }
}
