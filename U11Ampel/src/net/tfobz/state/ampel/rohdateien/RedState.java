package net.tfobz.state.ampel.rohdateien;

public class RedState implements Zustand {
	private Ampel a;
	
	public RedState(Ampel a) {
		this.a = a;
	}

	@Override
	public void ein() {
		a.rotEin();
	}

	@Override
	public void aus() {
		a.orangeBlinkenEin();
	}

	@Override
	public void manuellSchalten() {
		a.gruenEin();
	}

	@Override
	public void automatischSchalten() {
		// TODO Auto-generated method stub
		
	}

}
