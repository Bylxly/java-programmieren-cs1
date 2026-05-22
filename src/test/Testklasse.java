package test;

import gui.Gui;
import personen.*;
import personen.studenten.DHStudent;
import personen.studenten.Student;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Testklasse mit Testdaten und der {@code main}-Methode zum Erproben
 * der Personen-Klassen, der Sortierung und der grafischen Oberfläche.
 */
public class Testklasse {
    /** Testdaten fuer Studenten. */
    static Student studentenFeld[] = {
            new Student("Emil"),
            new Student("Emil"),
            new Student("Armin"),
            new Student("Fritz"),
            new Student("Ernst"),
            new Student("Erna"),
    };

    /** Testdaten fuer DH-Studenten */
    static DHStudent dhStudentenFeld[] = {
            new DHStudent("Anton", "TINF24CS1"),
            new DHStudent("Michael", "TINF24CS1"),
            new DHStudent("Uwe", "TINF24CS1"),
            new DHStudent("Christian", "TINF24CS1"),
            new DHStudent("Christian", "TINF25CS1"),
            new DHStudent("Christiane", "TINF25CS1"),
            new DHStudent("Uwe", "TINF25CS1"),
            new DHStudent("Michaela", "TINF25CS1"),
    };
    /** Testdaten fuer Angestellte */
    static Angestellter angestelltenFeld[] = {
            new Angestellter("Meier", 101000),
            new Angestellter("Schulze", 101351),
            new Angestellter("Hartmann", 102605),
            new Angestellter("Grosskopf", 103731),
            new Angestellter("Haudegen", 104566),
    };
    /** Testdaten fuer Lehrbeauftragter */
    static Lehrbeauftragter lehrbeauftragtenFeld[] = {
            new Lehrbeauftragter("Dr. Schlau"),
            new Lehrbeauftragter("Prof. Einfallsreich"),
            new Lehrbeauftragter("Dipl.-Ing. Pfiffikus"),
            new Lehrbeauftragter("OStR Lehrreich"),
    };

    /**
     * Einstiegspunkt des Programms; erprobt die Personen-Klassen und startet die GUI.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     */
    public static void main(String[] args) {
        Person.druckeAnzahl();
        // 4.3 a) Das liegt daran, da bevor die main Methode aufgerufen wird, der Compiler alle Objekte intialisiert
        // und auch alle Attribute der Klasse Testklasse als Objekte erzeugt werden.
        Testklasse testklasse = new Testklasse();

        // 4.3 b)
        Firma firma = new Firma("DH-Partnerfirma GmbH");
        firma.druckeName();

        // 4.3 c)
        int mitarbeiterzahl = dhStudentenFeld.length + angestelltenFeld.length;
        System.out.println("Mitarbeiterzahl: " + mitarbeiterzahl);

        // 4.3 d)
        IMitarbeiter[] mitarbeiterFeld = new IMitarbeiter[mitarbeiterzahl];
        System.arraycopy(dhStudentenFeld, 0, mitarbeiterFeld, 0, dhStudentenFeld.length);
        System.arraycopy(angestelltenFeld, 0, mitarbeiterFeld, dhStudentenFeld.length, angestelltenFeld.length);

        // 4.3 e)
        for (IMitarbeiter mitarbeiter : mitarbeiterFeld) {
            mitarbeiter.arbeitenOhneMurren();
        }

        // 4.3 f)
        System.out.println(Arrays.asList(lehrbeauftragtenFeld));

        // 4.3 g)
        Lehrbeauftragter einfallsreich = lehrbeauftragtenFeld[1];
        ArrayList<IKlausurSchreiber> arrayList = new ArrayList<IKlausurSchreiber>();
        for (DHStudent dhStudent : dhStudentenFeld) {
            if (dhStudent.getSemester() <= 2) {
                arrayList.add(dhStudent);
            }
        }
        System.out.println("Anzahl der Prueflinge: " + arrayList.toArray().length);

        IKlausurSchreiber prueflinge[] = new IKlausurSchreiber[arrayList.size()];
        prueflinge = arrayList.toArray(prueflinge);

        // 4.3 h)
        if (prueflinge.length == 0) {
            throw new Feldgroesse0Exception("Keine Prueflinge vorhanden! :-(");
        }
        //Anzahl der Prueflinge: 0
        //Exception in thread "main" test.Feldgroesse0Exception: Keine Prueflinge vorhanden! :-(
        //	at tinf25cs1.java.burke.lehmann/test.Testklasse.main(Testklasse.java:88)

        einfallsreich.setPrueflinge(prueflinge);

        // 4.3 i)
        System.out.println(einfallsreich.getName());
        System.out.println("Lehrbeauftragter " + einfallsreich.getName() + " laesst eine Klausur schreiben:");
        einfallsreich.lasseKlausurSchreiben();

        // Prueflinge sind vom Typ IKlausurSchreiber. In dem Interface gibt es keinen Namen, den der
        // Lehrbeauftrage auslesen kann. Man könnte aber einen Cast hinzufügen und dann einen Studenten
        // auch als Student interpretieren
        ((Student) prueflinge[0]).druckeObjekt(true);

        // 4.3 j)
        IKlausurSchreiber nachZuegler[] = new IKlausurSchreiber[1];
        System.out.println("Ein Nachzuegler wird geclont und muss gleich die Klausur schreiben:");
        nachZuegler[0] = ((Student) prueflinge[0]).clone();
        einfallsreich.setPrueflinge(nachZuegler);
        einfallsreich.lasseKlausurSchreiben();

        // 4.3 k)
        System.out.println("druckeObjekt() bei allen DH-Studenten testen:");
        for (DHStudent dhStudent : dhStudentenFeld) {
            dhStudent.druckeObjekt();
        }

        // 4.3 l)
        System.out.println("druckeObjekt() bei allen Studenten testen:");
        for (Student student : studentenFeld) {
            student.druckeObjekt();
        }

        // 4.3 m)
        System.out.println("Die DH-Studenten nach Kurs und Namen sortieren ...");
        Arrays.sort(dhStudentenFeld, (dhStudentenFeld[0]).new ComparatorKursName());

        // 4.3 n)
        System.out.println("Die sortierten DH-Studenten:");
        System.out.println(Arrays.asList(dhStudentenFeld));

        // 4.3 o)
        System.out.println("Die DH-Studenten nach dem Semester, dem Namen und dem Kurs sortieren ...");
        Arrays.sort(dhStudentenFeld, (dhStudentenFeld[0]).new ComparatorSemesterNameKurs());
        System.out.println(Arrays.asList(dhStudentenFeld));

        // 4.3 p)
        System.out.println("Die Personenzahl aus Sicht der Klasse Person: ");
        Person.druckeAnzahl();
        System.out.println("Die Personenzahl aus Sicht der Klasse DHStudent: ");
        DHStudent.druckeAnzahl();

        // 4.3 q)
        // anzahl muss auf protected geändert werden

        // 5.1)
        System.out.println("Alle Mitarbeiter essen in der Kantine:");
        for (IMitarbeiter mitarbeiter : mitarbeiterFeld) {
            mitarbeiter.inKantineEssen();
        }

        // GUI starten
        IKlausurSchreiber[] finalPrueflinge = prueflinge;
        SwingUtilities.invokeLater(() -> {
            new gui.Gui(
                angestelltenFeld,
                mitarbeiterFeld,
                lehrbeauftragtenFeld,
                    finalPrueflinge,
                studentenFeld,
                dhStudentenFeld
            );
        });
    }
}
