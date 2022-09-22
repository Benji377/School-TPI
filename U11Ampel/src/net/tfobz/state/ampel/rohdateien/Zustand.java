package net.tfobz.state.ampel.rohdateien;

public interface Zustand {
	
	static final int orangeIntervall = 3000;
	static final int gruenIntervall = 5000;
	static final int rotIntervall = 2000;
	
	void ein();
	void aus();
	void manuellSchalten();
	void automatischSchalten();
}
