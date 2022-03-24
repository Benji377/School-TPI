package net.tfobz.clock;
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
	public synchronized void run() {		
		// Unendliche Schleife die immer kontrolliert ob Zeit gestoppt ist oder nicht
		while (true) {
			if (getStopped()) {
				try {
					// Stoppt den Thread
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// Aktualisiert die Zeit
			time = java.time.LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
			this.setText(time);
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
		// Startet den Thread wieder
		if (!state) {
			synchronized (this) {
				this.notify();
			}
		}
	}
	
	public boolean getStopped() {
		return this.stopped;
	}
}
