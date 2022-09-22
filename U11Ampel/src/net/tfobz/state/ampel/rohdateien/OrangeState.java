package net.tfobz.state.ampel.rohdateien;

public class OrangeState implements Zustand {
	private Ampel a;
	
	public OrangeState(Ampel a) {
		this.a = a;
		this.a.state = this;
	}

	@Override
	public void ein() {
		a.orangeEin();
	}

	@Override
	public void aus() {
		a.rotEin();
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
