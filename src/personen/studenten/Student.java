package personen.studenten;

import personen.IKlausurSchreiber;
import personen.Person;

import java.util.Random;

/**
 * Repräsentiert einen Studenten mit Semester und BAföG-Schulden.
 */
public class Student extends Person implements IKlausurSchreiber, Cloneable {

    /** Semesterzahl des Studenten. */
    int semester;
    /** BAföG-Schulden des Studenten in Euro. */
    private double bafoegSchulden;

    /**
     * Erstellt einen neuen Studenten mit zufälliger Semesterzahl.
     *
     * @param name der Name des Studenten
     */
    public Student(String name) {
        super(name);
        Random rand = new Random();
        if (this instanceof DHStudent) {
            this.semester = rand.nextInt(6) + 1;     // 1 bis 6
            this.bafoegSchulden = 0.0;
        } else {
            this.semester = rand.nextInt(12) + 1;    // 1 bis 12
            this.bafoegSchulden = this.semester * 3000.00;
        }
    }

    /**
     * Gibt die Semesterzahl auf der Konsole aus.
     */
    public void druckeSemester() {
        System.out.println("Semesterzahl: " + this.semester);
    }

    /**
     * Gibt eine Beschreibung des Objekts auf der Konsole aus.
     */
    @Override
    public void druckeObjekt() {
        System.out.println("Der Student heisst " + this.name +
                ", ist im " + this.semester + ". Sem. und hat " +
                this.bafoegSchulden + " EUR Schulden wegen Bafoeg.");
    }

    /**
     * Gibt eine Kurzform oder vollständige Beschreibung des Objekts aus.
     *
     * @param kurz wenn {@code true}, wird nur Name und Semester ausgegeben
     */
    public void druckeObjekt(boolean kurz) {
        if (kurz) {
            System.out.println("Der Student heisst " + this.name + " (" + this.semester + ". Sem.)");
            return;
        }
        druckeObjekt();
    }

    /**
     * Führt das Schreiben einer Klausur aus.
     */
    @Override
    public void klausurSchreiben() {
        String dh = (this instanceof DHStudent) ? "DH-" : "";
        System.out.println("Ich bin ein " + dh + "Student namens " + this.name +
                " und schreibe meine Klausuren perfekt!");
    }

    /**
     * Liefert die aktuelle Semesterzahl.
     *
     * @return die aktuelle Semesterzahl
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Erstellt eine flache Kopie dieses Studenten.
     *
     * @return geklontes {@link Student}-Objekt
     */
    @Override
    public Student clone() {
        try {
            Student clone = (Student) super.clone();
            anzahl++;
            return clone;
        } catch (CloneNotSupportedException _) {
        }
        return null;
    }

    /**
     * Setzt die BAföG-Schulden des Studenten.
     *
     * @param bafoegSchulden die neuen BAföG-Schulden in Euro
     */
    public void setBafoegSchulden(double bafoegSchulden) {
        this.bafoegSchulden = bafoegSchulden;
    }

    /**
     * Setzt die Semesterzahl des Studenten.
     *
     * @param semester die neue Semesterzahl
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * Liefert die BAföG-Schulden des Studenten.
     *
     * @return die BAföG-Schulden des Studenten in Euro
     */
    public double getBafoegSchulden() {
        return bafoegSchulden;
    }

}
