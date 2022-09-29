package net.tfobz.state.ampel.rohdateien;

public class Blinkend implements Zustand {
	private Ampel a;
	
	public Blinkend(Ampel a) {
		this.a = a;
	}

	@Override
	public void ein() {
		a.orangeBlinkenEin();
	}

	@Override
	public void aus() {
		a.orangeEin();
	}

	@Override
	public void automatischSchalten() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manuellSchalten() {
		// TODO Auto-generated method stub
		
	}

}
