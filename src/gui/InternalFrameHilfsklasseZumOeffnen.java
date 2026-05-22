/**
 * Hilfsklasse zum Öffnen der einzelnen Fenster.
 */
package gui;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

/**
 * Hilfsklasse zum Öffnen der einzelnen Fenster.
 * 
 * Die Handhabung der Fenster ist besonders dadurch kompliziert,
 * weil sie in diesem Programm alle Optionen haben:
 * - Erstmals ganz neu nach Programmstart öffnen.
 * - Erneut öffnen nach vorherigem Schließen.
 * - Öffnen nach voherigem Umwandeln in Icon und Klicken auf das Icon.
 * - Öffnen über das Menü nach voherigem Umwandeln in Icon.
 */
public class InternalFrameHilfsklasseZumOeffnen<T extends JInternalFrame> implements ActionListener {

	private boolean DEBUG = false; 
	
	private JDesktopPane desktop;
	/** Die Referenz zum spezifischen JInternalFrame (z.B. studentGui) */
	private T frameToManage;
	/** Referenz zur Haupt-GUI-Klasse */
	private Gui mainGui; 

	/** Funktionales Interface zum Erzeugen der JInternalFrame-Instanzen */
	@FunctionalInterface
	public interface InternalFrameFactory<F extends JInternalFrame> {
		F create();
	}

	private InternalFrameFactory<T> frameFactory;

	/**
	 * Konstruktor.
	 * 
	 * @param desktop
	 * @param mainGui
	 * @param factory
	 */
	public InternalFrameHilfsklasseZumOeffnen(JDesktopPane desktop, Gui mainGui, InternalFrameFactory<T> factory) {
		this.desktop = desktop;
		this.mainGui = mainGui;
		this.frameFactory = factory;
	}

	
	/**
	 * Diese Methode MUSS von der Gui-Klasse aufgerufen werden,
	 * nachdem der Opener ein neues Fenster erzeugt hat.
	 * Das ist nicht mehr direkt notwendig, da der Lambda-Ausdruck
	 * in Gui.java die Referenz direkt aktualisiert.
	 * Diese Methode dient hauptsächlich dazu, die 
	 * frameToManage-Referenz in diesem Opener zu setzen.
	 * @param frame
	 */
	public void setManagedFrame(T frame) {
		this.frameToManage = frame;
	}

	
	/**
	 * Methode, um die Instanz auf null zu setzen, wenn das Fenster geschlossen
	 * wird. Wird von den setXXXGuiToNull() Methoden in Gui.java aufgerufen.
	 */
	public void clearManagedFrame() {
		this.frameToManage = null;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Prüfen, ob das Fenster bereits im Desktop ist (inkl. minimierter Zustand)
				boolean frameFound = false;
				if (frameToManage != null) {
					JInternalFrame[] allFrames = desktop.getAllFrames();
					for (JInternalFrame frame : allFrames) {
						if (frame == frameToManage) {
							frameFound = true;
							break;
						}
					}
				}

				if (!frameFound) { 
					// Wenn das Fenster NICHT gefunden wurde
					if(DEBUG) System.out.println("DEBUG (Opener): Neues Fenster wird erzeugt.");
					// Fenster über die Factory-Methode erstellen
					T newFrame = frameFactory.create();
					// Referenz im Opener aktualisieren
					//  (optional, aber zur Klarstellung)
					setManagedFrame(newFrame); 

					desktop.add(newFrame);
					// Optional: Feste Startposition für neue Fenster.
					if(false) newFrame.setLocation(50, 50);
					try {
						// Direkt in den Vordergrund bringen
						newFrame.setSelected(true); 
					} catch (PropertyVetoException pve) {
						System.err.println(
								"Fehler beim Setzen des neuen Fensters in den Vordergrund: "+
								pve.getMessage());
					}
				} else { 
					// Fenster existiert bereits im Desktop
					if(DEBUG) System.out.println(
							"DEBUG (Opener): Bestehendes Fenster wird behandelt.");
					try {
						if (frameToManage.isIcon()) {
							if(DEBUG) System.out.println(
									"DEBUG (Opener): Fenster war minimiert, wird wiederhergestellt.");
							frameToManage.setIcon(false);
						}
						frameToManage.setVisible(true);
						frameToManage.setSelected(true);
					} catch (PropertyVetoException pve) {
						System.err.println(
								"Fehler beim Setzen des bestehenden Fensters in den Vordergrund: " + 
										pve.getMessage());
					}
				}

				desktop.revalidate();
				desktop.repaint();
			}
		});
	}
}
