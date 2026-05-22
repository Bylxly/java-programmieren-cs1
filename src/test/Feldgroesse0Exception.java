package test;

/**
 * Wird geworfen, wenn ein Feld unerwartet die Größe 0 hat
 * (z.&nbsp;B. wenn es keine Prüflinge gibt).
 */
public class Feldgroesse0Exception extends RuntimeException {
    /**
     * Erstellt die Exception mit einer erläuternden Nachricht.
     *
     * @param message der Hinweistext zur Ausnahme
     */
    public Feldgroesse0Exception(String message) {
        super(message);
    }
}
