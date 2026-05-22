/**
 * Klasse für die Darstellung von Studenten.
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
import personen.studenten.Student;

/**
 * Diese Klasse ist für die Darstellung der Studenten
 * zuständig.
 * 
 * @author konrad
 */
public class StudentGui extends InternalFrameElternklasse {

	/** Referenz auf das Feld der Studenten. */
	private Student studentenFeld[];

	// Graphische Elemente der Oberfläche
	private JTable table;
	private DefaultTableModel model;
	private JPanel panel;
	private JScrollPane scrollPane;
	
	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param studentenFeld Referenz auf das Feld der Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public StudentGui(String title, Student studentenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, true, studentenFeld, desktop, gui);
	}

	
	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param studentenFeld Referenz auf das Feld der Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public StudentGui(String title, boolean resizable,
			Student studentenFeld[], JDesktopPane desktop, Gui gui) {
		this(title, resizable, true, studentenFeld, desktop, gui);
	}


	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param studentenFeld Referenz auf das Feld der Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public StudentGui(String title, boolean resizable, boolean closable,
			Student studentenFeld[], 
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, closable, true, studentenFeld, desktop, gui);
	}


	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param maximizable Flag, ob das Fenster maximiert werden kann.
	 * @param studentenFeld Referenz auf das Feld der Studenten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public StudentGui(String title, boolean resizable, boolean closable, boolean maximizable,
			Student studentenFeld[], 
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, closable, maximizable, true, 
				studentenFeld, desktop, gui);
	}


	/**
	 * Umfangreichster Konstruktor zum Erzeugen eines Fensters
	 * für Studenten, das als JInternalFrame einem JDesktop
	 * hinzugefügt werden kann.
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param maximizable Flag, ob das Fenster maximiert werden kann.
	 * @param iconifiable Flag, ob das Fenster zum Icon minimiert werden kann.
	 * @param studentenFeld Referenz auf das Feld der Angestellten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public StudentGui(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable,
			Student studentenFeld[], 
			JDesktopPane desktop, Gui gui) {
		super(title, resizable, closable, maximizable, iconifiable,
				desktop, gui);
		
		this.studentenFeld = studentenFeld;
		
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
		this.setBounds(250, 20, 300, 200);
		this.setVisible(true);
		String spaltenNamen[] = { "Name", "Semester", "BAföG-Schulden [€]" };
		erzeugeTabelle(studentenFeld, spaltenNamen);
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
            mainGuiRef.setStudentGuiToNull();
        }
    }
	
	
	/**
	 * Das Datenmodell der Tabelle mit Daten befüllen.
	 * @param datenQuelle 1D-Feld mit Studenten-Objekten.
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
			daten[zeile][spalte] = ((Student)datenQuelle[zeile]).getBafoegSchulden();
		}
		this.model.setRowCount(datenQuelle.length);
		this.model.setDataVector(daten, spaltenNamen);
		this.setVisible(true);
	}


	/**
	 * Die interaktiven Änderungen in der Tabelle
	 * im Feld der Studenten speichern. 
	 */
	private void aenderungenSpeichern() {
		int zeilenN = this.model.getRowCount();
		int spaltenN = this.model.getColumnCount();
		String name; 
		int semester;
		double bafoegSchulden;
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
			o = this.model.getValueAt(zeile, 2);
			bafoegSchulden = 0.0;
			if(o instanceof String) {
				String text = (String)o;
				bafoegSchulden = Double.parseDouble(text);
			}
			if(o instanceof Double) {
				bafoegSchulden = (double)(this.model.getValueAt(zeile, 2));
			}
			studentenFeld[zeile].setName(name);
			studentenFeld[zeile].setSemester(semester);
			studentenFeld[zeile].setBafoegSchulden(bafoegSchulden);
		}
	}
	
}
