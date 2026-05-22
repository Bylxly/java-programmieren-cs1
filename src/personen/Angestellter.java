package personen;

/**
 * Repräsentiert einen Angestellten mit einer Personalnummer.
 */
public class Angestellter extends Person implements IMitarbeiter {

    /** Personalnummer des Angestellten. */
    private int personalNr;

    /**
     * Erstellt einen neuen Angestellten.
     *
     * @param name       der Name des Angestellten
     * @param personalNr die Personalnummer des Angestellten
     */
    public Angestellter(String name, int personalNr) {
        super(name);
        this.name = name;
        this.personalNr = personalNr;
    }

    /**
     * Gibt die Personalnummer auf der Konsole aus.
     */
    public void druckePersonalNr() {
        System.out.println("Personalnr.: " + this.personalNr);
    }

    /**
     * Gibt eine Beschreibung des Objekts auf der Konsole aus.
     */
    @Override
    public void druckeObjekt() {
        System.out.println("Der Angestellte heisst " +
                this.name + ", hat die Personalnr. " +
                this.personalNr + " und ist einer von " +
                Person.anzahl);
    }

    /**
     * Führt Arbeit ohne zu murren aus.
     */
    @Override
    public void arbeitenOhneMurren() {
        System.out.println("Ich bin Angestellter " + this.name +
                " und arbeite ohne Murren und Knurren!");
    }

    /**
     * Setzt die Personalnummer des Angestellten.
     *
     * @param personalNr die neue Personalnummer
     */
    public void setPersonalNr(int personalNr) {
        this.personalNr = personalNr;
    }

    /**
     * Liefert die Personalnummer des Angestellten.
     *
     * @return die Personalnummer des Angestellten
     */
    public int getPersonalNr() {
        return personalNr;
    }
}
