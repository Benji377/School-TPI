package net.tfobz.state.ampel.rohdateien;

public class GreenState implements Zustand {
	private Ampel a;
	
	public GreenState(Ampel a) {
		this.a = a;
	}

	@Override
	public void ein() {
		a.gruenEin();
	}

	@Override
	public void aus() {
		a.orangeEin();
		
	}

	@Override
	public void manuellSchalten() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void automatischSchalten() {
		// TODO Auto-generated method stub
		
	}

}
