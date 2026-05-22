package personen;

/**
 * Repräsentiert einen Lehrbeauftragten, der Prüflinge eine Klausur schreiben lässt.
 */
public class Lehrbeauftragter {

    /** Name des Lehrbeauftragten. */
    private String name;
    /** Feld der in der nächsten Klausur zu prüfenden Studenten. */
    private IKlausurSchreiber[] prueflinge;

    /**
     * Erstellt einen neuen Lehrbeauftragten mit dem angegebenen Namen.
     *
     * @param name der Name des Lehrbeauftragten
     */
    public Lehrbeauftragter(String name) {
        this.name = name;
        this.prueflinge = null;
    }

    /**
     * Lässt alle gesetzten Prüflinge eine Klausur schreiben.
     */
    public void lasseKlausurSchreiben() {
        for (IKlausurSchreiber pruefling : prueflinge) {
            pruefling.klausurSchreiben();
        }
    }

    /**
     * Liefert den Namen des Lehrbeauftragten.
     *
     * @return der Name des Lehrbeauftragten
     */
    public String getName() {
        return name;
    }

    /**
     * Übernimmt die Referenz auf das Feld der in der nächsten Klausur zu prüfenden Studenten.
     *
     * @param prueflinge die Prüflinge, die eine Klausur schreiben sollen
     */
    public void setPrueflinge(IKlausurSchreiber[] prueflinge) {
        this.prueflinge = prueflinge;
    }

    /**
     * @return der Name des Lehrbeauftragten
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Setzt den Namen des Lehrbeauftragten.
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }
}
