package net.tfobz.digitalclock;
import java.time.temporal.ChronoUnit;

import javax.swing.JLabel;

/**
 * Eine Klasse die Sekundentakt die aktuelle Uhrzeit ausgibt
 */
public class DigitalClockLabel extends JLabel implements Runnable {
	private boolean stopped = false;
	// Liest die Zeit aus, entfernt die Millisekunden und wandelt es in ein String um
	private String time = java.time.LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
	
	@Override
	public void run() {
		// Unendliche Schleife die immer kontrolliert obe Zeit gestoppt ist oder nicht
		while (true) {
			if (!stopped)
				// Aktualisiert die Zeit
				time = java.time.LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
			this.setText(time);
		}
	}
	
	public void setStopped(boolean state) {
		this.stopped = state;
	}
	
	public boolean getStopped() {
		return this.stopped;
	}
	
	
}
