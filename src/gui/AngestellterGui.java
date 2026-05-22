/**
 * Klasse für die Darstellung von Angestellten.
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

import personen.Angestellter;
import personen.Person;

/**
 * Diese Klasse ist für die Darstellung der Angestellten
 * zuständig.
 * 
 * @author konrad
 */
public class AngestellterGui extends InternalFrameElternklasse {

	/** Referenz auf das Feld der Angestellten. */
	private Angestellter angestelltenFeld[];

	// Graphische Elemente der Oberfläche
	private JTable table;
	private DefaultTableModel model;
	private JPanel panel;
	private JScrollPane scrollPane;

	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param angestelltenFeld Referenz auf das Feld der Angestellten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public AngestellterGui(String title, Angestellter angestelltenFeld[],
		JDesktopPane desktop, Gui gui) {
		this(title, true, angestelltenFeld, desktop, gui);
	}

	
	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param angestelltenFeld Referenz auf das Feld der Angestellten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public AngestellterGui(String title, boolean resizable,
			Angestellter angestelltenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, true, angestelltenFeld, desktop, gui);
	}

	
	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param angestelltenFeld Referenz auf das Feld der Angestellten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public AngestellterGui(String title, boolean resizable, boolean closable,
			Angestellter angestelltenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, closable, true, angestelltenFeld, desktop, gui);
	}

	
	/**
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param maximizable Flag, ob das Fenster maximiert werden kann.
	 * @param angestelltenFeld Referenz auf das Feld der Angestellten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public AngestellterGui(String title, boolean resizable, boolean closable, boolean maximizable,
			Angestellter angestelltenFeld[],
			JDesktopPane desktop, Gui gui) {
		this(title, resizable, closable, maximizable, true, angestelltenFeld, desktop, gui);
	}


	/**
	 * Umfangreichster Konstruktor zum Erzeugen eines Fensters
	 * für Angestellte, das als JInternalFrame einem JDesktop
	 * hinzugefügt werden kann.
	 * @param title Titel im Kopf des Fensters.
	 * @param resizable Flag, ob die Größe des Fensters änderbar ist.
	 * @param closable Flag, ob das Fenster ganz geschlossen werden kann.
	 * @param maximizable Flag, ob das Fenster maximiert werden kann.
	 * @param iconifiable Flag, ob das Fenster zum Icon minimiert werden kann.
	 * @param angestelltenFeld Referenz auf das Feld der Angestellten.
	 * @param desktop Referenz auf die Arbeitsfläche.
	 * @param gui Referenz auf die zentrale GUI-Klasse.
	 */
	public AngestellterGui(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable,
			Angestellter angestelltenFeld[],
			JDesktopPane desktop, Gui gui) {
		super(title, resizable, closable, maximizable, iconifiable,
					desktop, gui);
		
		this.angestelltenFeld = angestelltenFeld;
		
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
		this.setBounds(33, 47, 192, 202);
		this.setVisible(true);
		String spaltenNamen[] = { "Name", "Personalnummer" };
		erzeugeTabelle(angestelltenFeld, spaltenNamen);
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
            mainGuiRef.setAngestelltenGuiToNull();
        }
    }

    
    /**
	 * Das Datenmodell der Tabelle mit Daten befüllen.
	 * @param datenQuelle 1D-Feld mit Angestellten-Objekten.
	 * @param spaltenNamen 1D-Feld mit den Namen bzw. 
	 * 		Überschriften der Spalten.
	 */
	private void erzeugeTabelle(Object datenQuelle[], String spaltenNamen[]) {
		// Erstellen der Daten für die Tabelle
		Object[][] daten = new Object[datenQuelle.length][spaltenNamen.length];
		for(int zeile = 0; zeile < datenQuelle.length; zeile++) {
			int spalte = 0;
			daten[zeile][spalte] = ((Person)datenQuelle[zeile]).getName();
			spalte++;
			daten[zeile][spalte] = ((Angestellter)datenQuelle[zeile]).getPersonalNr();
		}
		this.model.setRowCount(datenQuelle.length);
		this.model.setDataVector(daten, spaltenNamen);
		this.setVisible(true);
	}

	
	/**
	 * Die interaktiven Änderungen in der Tabelle
	 * im Feld der Angestellten speichern. 
	 */
	private void aenderungenSpeichern() {
		int zeilenN = this.model.getRowCount();
		int spaltenN = this.model.getColumnCount();
		String name; 
		int personalNr;
		for(int zeile = 0; zeile < zeilenN; zeile++) {
			name = (String)(this.model.getValueAt(zeile, 0));
			Object o = this.model.getValueAt(zeile, 1);
			personalNr = 0;
			// Greift, sobald einmal eine Zelle geändert worden ist
			if(o instanceof String) {
				String text = (String)o;
				personalNr = Integer.parseInt(text);
			}
			// Greift, solange eine Zelle noch nicht geändert worden ist
			if(o instanceof Integer) {
				personalNr = (Integer)(this.model.getValueAt(zeile, 1));
			}
			angestelltenFeld[zeile].setName(name);
			angestelltenFeld[zeile].setPersonalNr(personalNr);
		}
	}
	
}
