package personen.studenten;

import personen.IMitarbeiter;

import java.util.Comparator;

/**
 * Repräsentiert einen dualen Hochschulstudenten, der auch als Mitarbeiter tätig ist.
 */
public class DHStudent extends Student implements IMitarbeiter {

    /** Kurs des DH-Studenten. */
    private String kurs;

    /**
     * Erstellt einen neuen DHStudenten.
     *
     * @param name der Name des Studenten
     * @param kurs der Kurs des Studenten
     */
    public DHStudent(String name, String kurs) {
        super(name);
        this.kurs = kurs;
    }

    /**
     * Gibt den Kurs und eine Objektbeschreibung auf der Konsole aus.
     */
    void druckeKurs() {
        System.out.print(this.kurs);
        this.druckeObjekt(true);
    }

    /**
     * Führt Arbeit ohne zu murren aus.
     */
    @Override
    public void arbeitenOhneMurren() {
        System.out.println("Ich bin der DHStudent " + this.name + " und gehe daher immer freudig meine Arbeit an!");
    }

    /**
     * @return Kurs, Name und Semester des DH-Studenten als String
     */
    @Override
    public String toString() {
        return this.kurs + ": " + this.name + " (" + this.semester + ".Sem.)";
    }

    /**
     * Gibt die Anzahl aller erstellten DH-Studenten auf der Konsole aus.
     */
    public static void druckeAnzahl() {
        System.out.println("DHStudent.anzahl: " + DHStudent.anzahl);
    }

    /**
     * Gibt aus, dass dem DH-Studenten das Essen der Ausbildungsfirma schmeckt.
     */
    @Override
    public void inKantineEssen() {
        System.out.println("Wie lecker doch das Essen bei meiner Ausbildungsfirma schmeckt!");
    }

    /**
     * Setzt den Kurs des DH-Studenten.
     *
     * @param kurs der neue Kurs
     */
    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    /**
     * Liefert den Kurs des DH-Studenten.
     *
     * @return der Kurs des DH-Studenten
     */
    public String getKurs() {
        return kurs;
    }

    /**
     * Vergleicht DH-Studenten zuerst nach Kurs, dann nach Name.
     */
    public class ComparatorKursName implements Comparator<DHStudent> {

        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * The implementor must ensure that {@link Integer#signum
         * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * compare(x, y)} must throw an exception if and only if {@code
         * compare(y, x)} throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
         * {@code compare(x, z)>0}.<p>
         * <p>
         * Finally, the implementor must ensure that {@code compare(x,
         * y)==0} implies that {@code signum(compare(x,
         * z))==signum(compare(y, z))} for all {@code z}.
         *
         * @param dhs1 the first object to be compared.
         * @param dhs2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(DHStudent dhs1, DHStudent dhs2) {
            int kursVergleich = dhs1.kurs.compareTo(dhs2.kurs);
            if (kursVergleich != 0) {
                return kursVergleich;
            }
            return dhs1.name.compareTo(dhs2.name);
        }
    }

    /**
     * Vergleicht DH-Studenten zuerst nach Semester, dann nach Name, dann nach Kurs.
     */
    public class ComparatorSemesterNameKurs implements Comparator<DHStudent> {

        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * The implementor must ensure that {@link Integer#signum
         * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * compare(x, y)} must throw an exception if and only if {@code
         * compare(y, x)} throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
         * {@code compare(x, z)>0}.<p>
         * <p>
         * Finally, the implementor must ensure that {@code compare(x,
         * y)==0} implies that {@code signum(compare(x,
         * z))==signum(compare(y, z))} for all {@code z}.
         *
         * @param dhs1 the first object to be compared.
         * @param dhs2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(DHStudent dhs1, DHStudent dhs2) {
            int semesterVergleich = Integer.compare(dhs1.semester, dhs2.semester);
            if (semesterVergleich != 0) {
                return semesterVergleich;
            }
            int nameVergleich = dhs1.name.compareTo(dhs2.name);
            if (nameVergleich != 0) {
                return nameVergleich;
            }
            return dhs1.kurs.compareTo(dhs2.kurs);
        }
    }
}
