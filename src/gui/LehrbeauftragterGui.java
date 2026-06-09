/**
 * Klasse für die Darstellung von Lehrbeauftragten.
 */
package gui;

import personen.Lehrbeauftragter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diese Klasse ist für die Darstellung der Lehrbeauftragten zuständig.
 *
 * @author konrad
 */
public class LehrbeauftragterGui extends InternalFrameElternklasse {

    /** Referenz auf das Feld der Lehrbeauftragten. */
    private Lehrbeauftragter[] lehrbeauftragtenFeld;

    // Graphische Elemente der Oberfläche
    private JTable table;
    private DefaultTableModel model;
    private JPanel panel;
    private JScrollPane scrollPane;

    /**
     * @param title                 Titel im Kopf des Fensters.
     * @param lehrbeauftragtenFeld  Referenz auf das Feld der Lehrbeauftragten.
     * @param desktop               Referenz auf die Arbeitsfläche.
     * @param gui                   Referenz auf die zentrale GUI-Klasse.
     */
    public LehrbeauftragterGui(String title, Lehrbeauftragter[] lehrbeauftragtenFeld,
                               JDesktopPane desktop, Gui gui) {
        this(title, true, lehrbeauftragtenFeld, desktop, gui);
    }

    /**
     * @param title                 Titel im Kopf des Fensters.
     * @param resizable             Flag, ob die Größe des Fensters änderbar ist.
     * @param lehrbeauftragtenFeld  Referenz auf das Feld der Lehrbeauftragten.
     * @param desktop               Referenz auf die Arbeitsfläche.
     * @param gui                   Referenz auf die zentrale GUI-Klasse.
     */
    public LehrbeauftragterGui(String title, boolean resizable,
                               Lehrbeauftragter[] lehrbeauftragtenFeld,
                               JDesktopPane desktop, Gui gui) {
        this(title, resizable, true, lehrbeauftragtenFeld, desktop, gui);
    }

    /**
     * @param title                 Titel im Kopf des Fensters.
     * @param resizable             Flag, ob die Größe des Fensters änderbar ist.
     * @param closable              Flag, ob das Fenster ganz geschlossen werden kann.
     * @param lehrbeauftragtenFeld  Referenz auf das Feld der Lehrbeauftragten.
     * @param desktop               Referenz auf die Arbeitsfläche.
     * @param gui                   Referenz auf die zentrale GUI-Klasse.
     */
    public LehrbeauftragterGui(String title, boolean resizable, boolean closable,
                               Lehrbeauftragter[] lehrbeauftragtenFeld,
                               JDesktopPane desktop, Gui gui) {
        this(title, resizable, closable, true, lehrbeauftragtenFeld, desktop, gui);
    }

    /**
     * @param title                 Titel im Kopf des Fensters.
     * @param resizable             Flag, ob die Größe des Fensters änderbar ist.
     * @param closable              Flag, ob das Fenster ganz geschlossen werden kann.
     * @param maximizable           Flag, ob das Fenster maximiert werden kann.
     * @param lehrbeauftragtenFeld  Referenz auf das Feld der Lehrbeauftragten.
     * @param desktop               Referenz auf die Arbeitsfläche.
     * @param gui                   Referenz auf die zentrale GUI-Klasse.
     */
    public LehrbeauftragterGui(String title, boolean resizable, boolean closable,
                               boolean maximizable,
                               Lehrbeauftragter[] lehrbeauftragtenFeld,
                               JDesktopPane desktop, Gui gui) {
        this(title, resizable, closable, maximizable, true, lehrbeauftragtenFeld, desktop, gui);
    }

    /**
     * Umfangreichster Konstruktor zum Erzeugen eines Fensters für Lehrbeauftragte,
     * das als JInternalFrame einem JDesktop hinzugefügt werden kann.
     *
     * @param title                 Titel im Kopf des Fensters.
     * @param resizable             Flag, ob die Größe des Fensters änderbar ist.
     * @param closable              Flag, ob das Fenster ganz geschlossen werden kann.
     * @param maximizable           Flag, ob das Fenster maximiert werden kann.
     * @param iconifiable           Flag, ob das Fenster zum Icon minimiert werden kann.
     * @param lehrbeauftragtenFeld  Referenz auf das Feld der Lehrbeauftragten.
     * @param desktop               Referenz auf die Arbeitsfläche.
     * @param gui                   Referenz auf die zentrale GUI-Klasse.
     */
    public LehrbeauftragterGui(String title, boolean resizable, boolean closable,
                               boolean maximizable, boolean iconifiable,
                               Lehrbeauftragter[] lehrbeauftragtenFeld,
                               JDesktopPane desktop, Gui gui) {
        super(title, resizable, closable, maximizable, iconifiable, desktop, gui);

        this.lehrbeauftragtenFeld = lehrbeauftragtenFeld;

        this.model = new DefaultTableModel();
        this.table = new JTable(this.model);
        // 7.3: Tabelle über die Spaltenköpfe sortierbar machen
        this.table.setAutoCreateRowSorter(true);

        this.panel = new JPanel(new BorderLayout());
        this.scrollPane = new JScrollPane(this.table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton speichernButton = new JButton("Änderungen speichern");
        // 7.3: Tooltip vorsehen
        speichernButton.setToolTipText("Änderungen ins Datenmodell übernehmen");
        speichernButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aenderungenSpeichern();
            }
        });
        panel.add(speichernButton, BorderLayout.SOUTH);

        this.add(panel);

        // x, y, breite, hoehe – schmaler, da nur eine Spalte
        this.setBounds(33, 47, 192, 150);

        String[] spaltenNamen = { "Name" };
        erzeugeTabelle(lehrbeauftragtenFeld, spaltenNamen);
        this.setVisible(true);
    }

    /**
     * Wird aufgerufen, wenn das Fenster geschlossen wird.
     * Setzt die Referenz in der Gui-Klasse auf null.
     */
    @Override
    protected void handleFrameClosed() {
        if (mainGuiRef != null) {
            mainGuiRef.setLehrbeauftragterGuiToNull();
        }
    }

    /**
     * Das Datenmodell der Tabelle mit Daten befüllen.
     *
     * @param datenQuelle  1D-Feld mit Lehrbeauftragten-Objekten.
     * @param spaltenNamen 1D-Feld mit den Spaltenüberschriften.
     */
    private void erzeugeTabelle(Object[] datenQuelle, String[] spaltenNamen) {
        Object[][] daten = new Object[datenQuelle.length][spaltenNamen.length];
        for (int zeile = 0; zeile < datenQuelle.length; zeile++) {
            daten[zeile][0] = ((Lehrbeauftragter)datenQuelle[zeile]).getName();
        }
        this.model.setDataVector(daten, spaltenNamen);
        this.setVisible(true);
    }

    /**
     * Die interaktiven Änderungen in der Tabelle im Feld der Lehrbeauftragten speichern.
     */
    private void aenderungenSpeichern() {
        int zeilenN = this.model.getRowCount();
        for (int zeile = 0; zeile < zeilenN; zeile++) {
            String name = (String) this.model.getValueAt(zeile, 0);
            lehrbeauftragtenFeld[zeile].setName(name);
        }
    }
}
