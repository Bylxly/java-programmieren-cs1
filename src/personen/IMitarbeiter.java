package personen;

/**
 * Repräsentiert einen Mitarbeiter, der klaglos arbeiten kann.
 */
public interface IMitarbeiter {

    /**
     * Führt Arbeit ohne zu murren aus.
     */
    void arbeitenOhneMurren();

    /**
     * Standardverhalten: Der Mitarbeiter isst in der Kantine.
     */
    default void inKantineEssen() {
        System.out.println("Ich esse seit 30 Jahren jeden Werktag in der Kantine!");
    }
}
