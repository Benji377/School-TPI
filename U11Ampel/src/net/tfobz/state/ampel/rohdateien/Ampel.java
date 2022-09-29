package net.tfobz.state.ampel.rohdateien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Ampel extends AmpelComponent implements Zustand {
	public boolean automatisch = false;
	
	public Ampel() {
		super();
		
		this.addEinKnopfListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				automatisch = false;
				ein();
			}
		});
		
		this.addAusKnopfListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				automatisch = false;
				aus();
			}
		});
		
		this.addManuellKnopfListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				automatisch = false;
				manuellSchalten();
			}
		});
		
		this.addAutomatischKnopfListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				automatischSchalten();
			}
		});
		
	}

	@Override
	public void ein() {
		new OrangeState(this).ein();
		
		Timer timer = new Timer(3000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new RedState(Ampel.this).ein();
				
			}
		});
		
		timer.setRepeats(false);
		timer.start();
		
	}

	@Override
	public void aus() {
		new Blinkend(this).ein();	
	}

	@Override
	public void manuellSchalten() {
		new GreenState(this).ein();
			
		Timer timer = new Timer(5000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ein();
				
			}
		});
		
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	public void automatischSchalten() {
		
	}

}
