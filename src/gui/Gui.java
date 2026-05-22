/**
 * Zentrale Klassee für das Erstellen des GUI.
 */
package gui;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import personen.Angestellter;
import personen.IKlausurSchreiber;
import personen.IMitarbeiter;
import personen.Lehrbeauftragter;
import personen.studenten.DHStudent;
import personen.studenten.Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;

/**
 * Diese Klasse ist die zentrale Klasse zum Erstellen des GUI
 * 
 * @author konrad
 */
public class Gui extends JFrame {
	
	private IMitarbeiter mitarbeiterFeld[];
	private Lehrbeauftragter lehrbeauftragtenFeld[];
	private IKlausurSchreiber prueflinge[];
	private DHStudent dhStudentenFeld[];
	private Student studentenFeld[];
	
	private JDesktopPane desktop;
	private JMenuBar menuBar;
	
	private AngestellterGui angestellterGui;
	private StudentGui studentGui;
	private DHStudentGui dhStudentGui;
	private LehrbeauftragterGui lehrbeauftragterGui;


	// Instanzen der Klasse InternalFrameHilfsklasseZumOeffnen für jeden Fenstertyp
    private InternalFrameHilfsklasseZumOeffnen<StudentGui> studentenOpener;
    private InternalFrameHilfsklasseZumOeffnen<DHStudentGui> dhStudentenOpener;
    private InternalFrameHilfsklasseZumOeffnen<AngestellterGui> angestelltenOpener;
	private InternalFrameHilfsklasseZumOeffnen<LehrbeauftragterGui> lehrbeauftragterOpener;


    /**
	 * Konstruktor.
	 * 
     * @param angestelltenFeld
     * @param mitarbeiterFeld
     * @param lehrbeauftragtenFeld
     * @param prueflinge
     * @param studentenFeld
     * @param dhStudentenFeld
     */
	public Gui(
			Angestellter angestelltenFeld[],
			IMitarbeiter mitarbeiterFeld[],
			Lehrbeauftragter lehrbeauftragtenFeld[],
			IKlausurSchreiber prueflinge[],
			Student studentenFeld[],
			DHStudent dhStudentenFeld[]) {
		// Konstruktor von JFrame mit Fenstertitel
		super("TINF25CS1");

		this.mitarbeiterFeld = mitarbeiterFeld;
		this.lehrbeauftragtenFeld = lehrbeauftragtenFeld;
		this.prueflinge = prueflinge;
		this.studentenFeld = studentenFeld;
		this.dhStudentenFeld = dhStudentenFeld;
		
		// Erstellen des Fensters
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);

		// Erstellen der Menüleiste
		this.menuBar = new JMenuBar();

		// Erstellen des "Datei"-Menüs
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem newMenuItem = new JMenuItem("Neu");
		JMenuItem openMenuItem = new JMenuItem("Öffnen");
		JMenuItem saveMenuItem = new JMenuItem("Speichern");
		JMenuItem exitMenuItem = new JMenuItem("Beenden");

		// Hinzufügen der Menüelemente zum "Datei"-Menü
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		// Erstellen des "Bearbeiten"-Menüs
		JMenu editMenu = new JMenu("Bearbeiten");
		JMenuItem cutMenuItem = new JMenuItem("Ausschneiden");
		JMenuItem copyMenuItem = new JMenuItem("Kopieren");
		JMenuItem pasteMenuItem = new JMenuItem("Einfügen");

		// Hinzufügen der Menüelemente zum "Bearbeiten"-Menü
		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		
		// Erstellen des "Anzeigen"-Menüs
		JMenu anzeigeMenu = new JMenu("Anzeigen");
		JMenuItem angestelltenMenuItem = new JMenuItem("Angestellte");
		JMenuItem mitarbeiterMenuItem = new JMenuItem("Mitarbeiter");
		JMenuItem lehrbeauftragterMenuItem = new JMenuItem("Lehrbeauftragte");
		JMenuItem prueflingeMenuItem = new JMenuItem("Prüflinge");
		JMenuItem studentenMenuItem = new JMenuItem("Studenten");
		JMenuItem dhStudentenMenuItem = new JMenuItem("DH-Studenten");

		// Hinzufügen der Menüelemente zum "Anzeigen"-Menü
		anzeigeMenu.add(angestelltenMenuItem);
		anzeigeMenu.add(mitarbeiterMenuItem);
		anzeigeMenu.add(lehrbeauftragterMenuItem);
		anzeigeMenu.add(prueflingeMenuItem);
		anzeigeMenu.add(studentenMenuItem);
		anzeigeMenu.add(dhStudentenMenuItem);
		
		// Hinzufügen der Menüs zur Menüleiste
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(anzeigeMenu);

		// Hinzufügen der Menüleiste zum JFrame
		this.setJMenuBar(menuBar);

        // Desktop-Fläche für die "JInternalFrame"-Fenster erstellen
        this.desktop = new JDesktopPane();
        setContentPane(desktop);

        // Erzeuge die 3 Instanzen der InternalFrameHilfsklasseZumOeffnen 
        
        studentenOpener = new InternalFrameHilfsklasseZumOeffnen<>(desktop, this, () -> {
            String title = "Studenten (KEINE DH!)";
            boolean resizable = true;
            boolean closable = true;
            boolean maximizable = true;
            boolean iconifiable = true;
            StudentGui newStudentGui = new StudentGui(
                    title, resizable, closable, maximizable,
                    iconifiable, studentenFeld, desktop, this);
            // Referenz in Gui.java aktualisieren
            this.studentGui = newStudentGui;
            return newStudentGui;
        });

        dhStudentenOpener = new InternalFrameHilfsklasseZumOeffnen<>(desktop, this, () -> {
            String title = "DHBW-Studenten";
            boolean resizable = true;
            boolean closable = true;
            boolean maximizable = true;
            boolean iconifiable = true;
            DHStudentGui newDhStudentGui = new DHStudentGui(
                    title, resizable, closable, maximizable,
                    iconifiable, dhStudentenFeld, desktop, this);
            // Referenz in Gui.java aktualisieren
            this.dhStudentGui = newDhStudentGui; 
            return newDhStudentGui;
        });

        angestelltenOpener = new InternalFrameHilfsklasseZumOeffnen<>(desktop, this, () -> {
            String title = "Angestellte";
            boolean resizable = true;
            boolean closable = true;
            boolean maximizable = true;
            boolean iconifiable = true;
            AngestellterGui newAngestellterGui = new AngestellterGui(
                    title, resizable, closable, maximizable,
                    iconifiable, angestelltenFeld, desktop, this);
            // Referenz in Gui.java aktualisieren
            this.angestellterGui = newAngestellterGui;
            return newAngestellterGui;
        });

		lehrbeauftragterOpener = new InternalFrameHilfsklasseZumOeffnen<>(desktop, this, () -> {
			LehrbeauftragterGui newLehrbeauftragterGui = new LehrbeauftragterGui(
					"Lehrbeauftragte", true, true, true, true,
					lehrbeauftragtenFeld, desktop, this);
			this.lehrbeauftragterGui = newLehrbeauftragterGui;
			return newLehrbeauftragterGui;
		});

		// Hinzufügen der ActionListener zu den Menüelementen
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Gui.this, "Neu ausgewählt");
			}
		});

		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Gui.this, "Öffnen ausgewählt");
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Gui.this, "Speichern ausgewählt");
			}
		});

		cutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Gui.this, "Ausschneiden ausgewählt");
			}
		});

		copyMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Gui.this, "Kopieren ausgewählt");
			}
		});

		pasteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Gui.this, "Einfügen ausgewählt");
			}
		});

		lehrbeauftragterMenuItem.addActionListener(lehrbeauftragterOpener);

		// Hier ganz kurzer Quelltext dank der Hilfsklassenobjekte!
		angestelltenMenuItem.addActionListener(angestelltenOpener);

		// Hier ganz kurzer Quelltext dank der Hilfsklassenobjekte!
		studentenMenuItem.addActionListener(studentenOpener);
		
		// Hier ganz kurzer Quelltext dank der Hilfsklassenobjekte!
		dhStudentenMenuItem.addActionListener(dhStudentenOpener);

		// Anzeigen des Fensters
		this.setVisible(true);
	}

	
    /**
     * Dient zum Informieren des Openers.
     */
    public void setStudentGuiToNull() {
        this.studentGui = null;
        // Sicherstellen, dass der Opener schon initialisiert ist
        if (studentenOpener != null) { 
            studentenOpener.clearManagedFrame();
        }
    }

    /**
     * Dient zum Informieren des Openers.
     */
    public void setDhStudentGuiToNull() {
        this.dhStudentGui = null;
        if (dhStudentenOpener != null) {
            dhStudentenOpener.clearManagedFrame();
        }
    }

    /**
     * Dient zum Informieren des Openers.
     */
    public void setAngestelltenGuiToNull() {
        this.angestellterGui = null;
        if (angestelltenOpener != null) {
            angestelltenOpener.clearManagedFrame();
        }
    }

	public void setLehrbeauftragterGuiToNull() {
		this.lehrbeauftragterGui = null;
		if (lehrbeauftragterOpener != null) {
			lehrbeauftragterOpener.clearManagedFrame();
		}
	}
	
}


