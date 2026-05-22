package personen;

/**
 * Abstrakte Basisklasse für alle Personen.
 */
public abstract class Person {

    /** Mögliche Arten der Synchronisation. */
    private enum synchronisationsArtTyp { KLASSENMETHODE, MONITOR };
    /** Aktuell gewählte Art der Synchronisation. */
    private static synchronisationsArtTyp synchronisationsArt = synchronisationsArtTyp.MONITOR;

    /** Verzögerung in Millisekunden zum Vergrößern des kritischen Abschnitts. */
    private static final int VERZOEGERUNG_MS = 500;
    /** Monitor-Objekt zum Synchronisieren der Zugriffe auf {@link #anzahl}. */
    private static Object monitor;

    /** Name der Person. */
    protected String name;

    /** Anzahl aller erstellten Personen-Instanzen. */
    protected static int anzahl;

    static {
        anzahl = 0;
        monitor = new Object();
    }

    /**
     * Erstellt eine neue Person und erhöht den Instanzzähler.
     *
     * @param name der Name der Person
     */
    public Person(String name) {
        this.name = name;
        if (synchronisationsArt == synchronisationsArtTyp.MONITOR) {
            synchronized (monitor) {
                anzahl++;
            }
        } else {
            anzahlInkrementieren();
        }
    }

    /**
     * Gibt die Anzahl aller erstellten Personen auf der Konsole aus.
     */
    public static void druckeAnzahl() {
        System.out.println("Person.anzahl: " + Person.anzahl);
    }

    /**
     * Liefert den Namen der Person.
     *
     * @return der Name der Person
     */
    // muss für gui public sein
    public String getName() {
        return this.name;
    }

    /**
     * Setzt den Namen der Person.
     *
     * @param name der neue Name der Person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt eine Beschreibung des Objekts auf der Konsole aus.
     */
    public abstract void druckeObjekt();

    // 6.1 finalize ist deprecated


    /**
     * Erhöht den Personenzähler {@link #anzahl} synchronisiert um eins.
     * Die wartenden Threads werden dabei in den Zustand „blockiert" versetzt.
     */
    private static synchronized void anzahlInkrementieren() {
        int anzahl = Person.anzahl;
        try {Thread.sleep(VERZOEGERUNG_MS);
            anzahl++;
            Thread.sleep(VERZOEGERUNG_MS);
        } catch (InterruptedException _) {
        }
        Person.anzahl = anzahl;
    }

    /**
     * Erniedrigt den Personenzähler {@link #anzahl} synchronisiert um eins.
     */
    private static synchronized void anzahlDekrementieren() {
        int anzahl = Person.anzahl;
        try {Thread.sleep(VERZOEGERUNG_MS);
            anzahl--;
            Thread.sleep(VERZOEGERUNG_MS);
        } catch (InterruptedException _) {
        }
        Person.anzahl = anzahl;
    }

}
