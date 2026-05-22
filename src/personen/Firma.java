package personen;

/**
 * Repräsentiert eine Firma mit einem Namen.
 */
public class Firma {

    /** Name der Firma. */
    private String name;

    /**
     * Erstellt eine neue Firma mit dem angegebenen Namen.
     *
     * @param name der Name der Firma
     */
    public Firma(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen der Firma auf der Konsole aus.
     */
    public void druckeName() {
        System.out.println("Der Firmenname lautet: " + this.name);
    }
}
