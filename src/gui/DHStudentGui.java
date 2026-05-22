/**
 * Klasse für die Darstellung von DH-Studenten.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import personen.Person;
import personen.studenten.DHStudent;
import personen.studenten.Student;

/**
 * Diese Klasse ist für die Darstellung der DH-Studenten
 * zuständig.
 * 
 * @author konrad
 */
public class DHStudentGui extends InternalFrameElternklasse {

	/** Referenz auf das Feld der DH-Studenten. */
	private DHStudent dhStudentenFeld[];

	// Graphische Elemente der Oberfläche
	private JTable table;
	private DefaultTableModel model;
	private JPanel panel;
	private JScrollPane scrollPane;

	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param dhStudentenFeld Referenz auf das Feld der DH-Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public DHStudentGui(String title, DHStudent dhStudentenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, true, dhStudentenFeld, desktop, gui);
	}

	
	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param dhStudentenFeld Referenz auf das Feld der DH-Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public DHStudentGui(String title, boolean resizable,
			DHStudent dhStudentenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, true, dhStudentenFeld, desktop, gui);
	}


	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param dhStudentenFeld Referenz auf das Feld der DH-Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public DHStudentGui(String title, boolean resizable, boolean closable,
			DHStudent dhStudentenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, closable, true, dhStudentenFeld, desktop, gui);
	}

	
	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param maximizable Flag, ob das Fenster maximiert werden kann.
	 * @param dhStudentenFeld Referenz auf das Feld der DH-Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public DHStudentGui(String title, boolean resizable, boolean closable, boolean maximizable,
			DHStudent dhStudentenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, closable, maximizable, true,
				dhStudentenFeld, desktop, gui);
	}


	/**
	 * Umfangreichster Konstruktor zum Erzeugen eines Fensters
	 * für DH-Studenten, das als JInternalFrame einem JDesktop
	 * hinzugefügt werden kann.
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param maximizable Flag, ob das Fenster maximiert werden kann.
	 * @param iconifiable Flag, ob das Fenster zum Icon minimiert werden kann.
	 * @param dhStudentenFeld Referenz auf das Feld der Angestellten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public DHStudentGui(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable,
			DHStudent dhStudentenFeld[],
			JDesktopPane desktop, Gui gui) {
		super(title, resizable, closable, maximizable, iconifiable,
				desktop, gui);
		
		this.dhStudentenFeld = dhStudentenFeld;
		
		this.model = new DefaultTableModel();
		this.table = new JTable(this.model);
		
		// Erstellen eines Panels für den Inhalt des internen Frames
        this.panel = new JPanel(new BorderLayout());
		
		this.scrollPane = new JScrollPane(this.table);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Knopf zum Speichern von Änderungen erstellen
		JButton speichernButton = new JButton("Änderungen speichern");
		speichernButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aenderungenSpeichern();
			}
		});		
		panel.add(speichernButton, BorderLayout.SOUTH);

		this.add(panel);

		// x, y, breite, hoehe
		this.setBounds(180, 250, 400, 250);
		this.setVisible(true);
		String spaltenNamen[] = { "Name", "Semester", "Kurs" };
		erzeugeTabelle(dhStudentenFeld, spaltenNamen);
	}


    /**
     * Implementierung der abstrakten Methode aus InternalFrameElternklasse.
     * Diese Methode wird aufgerufen, wenn das Fenster geschlossen wird.
     * Sie ist dafür zuständig, die spezifische Referenz in der Gui-Klasse
     * auf null zu setzen.
     */
    @Override
    protected void handleFrameClosed() {
        if (mainGuiRef != null) { // mainGuiRef kommt von InternalFrameElternklasse
            mainGuiRef.setDhStudentGuiToNull();
        }
    }

    
    /**
	 * Das Datenmodell der Tabelle mit Daten befüllen.
	 * @param datenQuelle 1D-Feld mit DH-Studenten-Objekten.
	 * @param spaltenNamen 1D-Feld mit den Namen bzw. 
	 * 		Überschriften der Spalten.
	 */
	private void erzeugeTabelle(Object datenQuelle[], String spaltenNamen[]) {
		// Erstellen der Daten für die Tabelle
		Object[][] daten = new Object[datenQuelle.length][spaltenNamen.length];
		for(int zeile = 0; zeile < datenQuelle.length; zeile++) {
			int spalte = 0;
			daten[zeile][spalte++] = ((Person)datenQuelle[zeile]).getName();
			daten[zeile][spalte++] = ((Student)datenQuelle[zeile]).getSemester();
			daten[zeile][spalte] = ((DHStudent)datenQuelle[zeile]).getKurs();
		}
		this.model.setRowCount(datenQuelle.length);
		this.model.setDataVector(daten, spaltenNamen);
		this.setVisible(true);
	}


	/**
	 * Die interaktiven Änderungen in der Tabelle
	 * im Feld der DH-Studenten speichern. 
	 */
	private void aenderungenSpeichern() {
		int zeilenN = this.model.getRowCount();
		int spaltenN = this.model.getColumnCount();
		String name; 
		int semester;
		String kurs;
		for(int zeile = 0; zeile < zeilenN; zeile++) {
			name = (String)(this.model.getValueAt(zeile, 0));
			Object o = this.model.getValueAt(zeile, 1);
			semester = 0;
			// Greift, sobald einmal eine Zelle geändert worden ist
			if(o instanceof String) {
				String text = (String)o;
				semester = Integer.parseInt(text);
			}
			// Greift, solange eine Zelle noch nicht geändert worden ist
			if(o instanceof Integer) {
				semester = (Integer)(this.model.getValueAt(zeile, 1));
			}
			kurs = (String)(this.model.getValueAt(zeile, 2));
			dhStudentenFeld[zeile].setName(name);
			dhStudentenFeld[zeile].setSemester(semester);
			dhStudentenFeld[zeile].setKurs(kurs);
		}
	}
	
}
