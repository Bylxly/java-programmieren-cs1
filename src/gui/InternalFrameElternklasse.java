/**
 * Elternklasse für die einzelnen Fenster auf dem Desktop
 * der Anwendung.
 */
package gui;

/**
 * 
 */
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * Elternklasse für die einzelnen Fenster auf dem Desktop
 * der Anwendung.
 */
public abstract class InternalFrameElternklasse extends JInternalFrame {

	/** Referenz auf den Desktop. */
	protected JDesktopPane desktopRef;
	/** Referenz zur Haupt-GUI-Klasse */
	protected Gui mainGuiRef; 

	/**
	 * Konstruktor.
	 * 
	 * @param title
	 * @param resizable
	 * @param closable
	 * @param maximizable
	 * @param iconifiable
	 * @param desktop
	 * @param mainGui
	 */
	public InternalFrameElternklasse(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable,
			JDesktopPane desktop, Gui mainGui) {
		super(title, resizable, closable, maximizable, iconifiable);
		this.desktopRef = desktop;
		this.mainGuiRef = mainGui;

		// Füge den Listener für das Schließen des Fensters hinzu
		this.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				// Diese Methode muss in der abgeleiteten Klasse implementiert 
				// oder überschrieben werden, oder man verlässt sich darauf, dass
				// mainGuiRef die entsprechende setXxxGuiToNull aufruft.
				handleFrameClosed();

				// Allgemeine Desktop-Aktualisierung nach dem Schließen
				if (desktopRef != null) {
					SwingUtilities.invokeLater(() -> {
						desktopRef.revalidate();
						desktopRef.repaint();
					});
				}
			}
		});

		// Füge den ComponentListener für das Beseitigen von möglichem
		// "Pixel-Schmutz" beim Verschieben hinzu
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				if (desktopRef != null) {
					SwingUtilities.invokeLater(() -> desktopRef.repaint());
				}
			}
		});
	}

	/**
	 *  Abstrakte Methode, die von den Unterklassen implementiert werden muss,
	 * um die spezifische Referenz in der Gui-Klasse auf null zu setzen.
	 */
	protected abstract void handleFrameClosed();
	
}

