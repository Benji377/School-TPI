package net.tfobz.clock;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Klasse zur visuellen Darstellung der Uhr.
 * Funktioniert durch das Steuern des DigitalClockLabel
 */
public class DigitalClockGUI extends JFrame {
	private DigitalClockLabel clock;
	private JButton stop;
	private JButton resume;
	private Thread tclock;
	
	public static void main(String args[]) {
		DigitalClockGUI dg = new DigitalClockGUI();
		dg.setVisible(true);
	}
	
	public DigitalClockGUI() {
		this.setTitle("DigitalClock");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 500, 400, 200);
		this.setResizable(false);
		
		// Stellt das Runnable und die Uhr dar
		this.clock = new DigitalClockLabel();
		this.clock.setBounds(0, 5, 400, 100);
		this.clock.setFont(new Font("Arial", Font.BOLD, 60));
		this.clock.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Erstellt ein Thread aus dem Runnable und startet es
		this.tclock = new Thread(clock);
		this.tclock.start();
		
		// Knopf zum stoppen der Uhr
		this.stop = new JButton("Stop");
		this.stop.setBounds(100, 115, 100, 30);
		this.stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Stoppt die Uhr und aktviert/deaktiviert bestimmte Knöpfe
				if (!clock.getStopped()) {
					clock.setStopped(true);
					stop.setEnabled(false);
					resume.setEnabled(true);
				}
			}
		});
		// Knopf um die Zeit weiterlaufen zu lassen
		this.resume = new JButton("Continue");
		this.resume.setBounds(205, 115, 100, 30);
		this.resume.setEnabled(false);
		this.resume.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clock.getStopped()) {
					clock.setStopped(false);
					resume.setEnabled(false);
					stop.setEnabled(true);
				}
			}
		});
		
		Container c = this.getContentPane();
		c.setLayout(null);
		c.add(this.clock);
		c.add(this.stop);
		c.add(this.resume);
	}
}
