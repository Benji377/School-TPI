package net.tfobz.state.ampel.rohdateien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Ampel extends AmpelComponent implements Zustand {
	/*
	 * Mögliche Zustände:
	 * 0 - Orange blinkend
	 * 1 - Orange
	 * 2 - Rot
	 * 3 - Grün 
	 */
	public Zustand state;
	
	public Ampel() {
		super();
		
		this.addEinKnopfListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ein();
			}
		});
		
		this.addAusKnopfListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aus();
			}
		});
		
		this.addManuellKnopfListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manuellSchalten();
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
		// TODO Auto-generated method stub
		
	}

}
