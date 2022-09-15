package net.tfobz.digitalclock;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

/**
 * Eine Klasse die Sekundentakt die aktuelle Uhrzeit ausgibt
 */
public class DigitalClockLabel extends JLabel implements Runnable {
	private boolean stopped = false;
	// Liest die Zeit aus, entfernt die Millisekunden und wandelt es in ein String um
	private String time = java.time.LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
	
	public DigitalClockLabel() {
		this.setText(time);
	}
	
	@Override
	public void run() {
		// Unendliche Schleife die immer kontrolliert obe Zeit gestoppt ist oder nicht
		while (true) {
			if (!stopped) {
				// Aktualisiert die Zeit
				time = java.time.LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
				this.setText(time);
			}
			try {
				// Lässt das Programm für eine kurze Zeit stehen bleiben um Prozessorauslastung zu verringern
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setStopped(boolean state) {
		this.stopped = state;
	}
	
	public boolean getStopped() {
		return this.stopped;
	}
}
