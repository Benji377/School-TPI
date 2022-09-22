package net.tfobz.state.ampel.rohdateien;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Diese Komponente stellt eine Ampel dar mit vier Knöpfen zum Ein- und Ausschalten der
 * Ampel sowie zum manuellen und automatischen Beschalten der Ampel. Die Ampel hat eine
 * fixe Größe. Es gibt Methoden, welche die einzelnen Farben der Ampel ein- bzw. 
 * ausschalten sowie die Ampel orange blinken lassen. Weiters können über spezielle
 * Methoden den Bedienknöpfen der Ampel Ereignisbehandlungsmethoden zugeordnet werden
 * @author Michael Wild
 *
 */
public class AmpelComponent extends JComponent
{
	private static final int WIDTH = 60;
	private static final int HEIGHT = 180;
	private static final int KNOPF_HOEHE = 20;
	private static final int ORANGE_BLINKEN_INTERVALL = 1000;
	
	private boolean rotEin = false;
	private boolean orangeEin = true;
	private boolean gruenEin = false;

	private BlinkenThread blinkenThread = null;
	
	private JButton einKnopf = null;
	private JButton ausKnopf = null;
	private JButton manuellKnopf = null;
	private JButton automatischKnopf = null;
	
	/** 
	 * Wird die Ampel instanziiert, so beginnt diese orange zu blinken
	 */
	public AmpelComponent() {
		setSize(WIDTH, HEIGHT + KNOPF_HOEHE * 4);
		setLayout(null);
		einKnopf = new JButton();
		einKnopf.setText("Ein");
		einKnopf.setBounds(0, HEIGHT, WIDTH, KNOPF_HOEHE);
		add(einKnopf);
		ausKnopf = new JButton();
		ausKnopf.setText("Aus");
		ausKnopf.setBounds(0, HEIGHT + KNOPF_HOEHE, WIDTH, KNOPF_HOEHE);
		add(ausKnopf);
		manuellKnopf = new JButton();
		manuellKnopf.setText("Man");
		manuellKnopf.setBounds(0, HEIGHT + KNOPF_HOEHE * 2, WIDTH, KNOPF_HOEHE);
		add(manuellKnopf);
		automatischKnopf = new JButton();
		automatischKnopf.setText("Auto");
		automatischKnopf.setBounds(0, HEIGHT + KNOPF_HOEHE * 3, WIDTH, KNOPF_HOEHE);
		add(automatischKnopf);
		orangeBlinkenEin();
	}
	
	private class BlinkenThread extends Thread
	{
		private boolean stopped = false;
		
		public void stoppen() {
			this.stopped = true;
			interrupt();
		}
		@Override
		public void run() {
			rotEin = false;
			orangeEin = false;
			gruenEin = false;
			while (!stopped) {
				orangeEin = !orangeEin;
				repaint();
				try {
					Thread.sleep(ORANGE_BLINKEN_INTERVALL);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	/**
	 * Die nachfolgenden Methoden sind protected und dienen dazu
	 * die verschiedenen Farben in der Ampel ein- oder auszuschalten
	 */
	protected void rotEin() {
		if (blinkenThread != null && blinkenThread.isAlive()) {
			blinkenThread.stoppen();
			try {
				blinkenThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.rotEin = true;
		this.orangeEin = false;
		this.gruenEin = false;
		repaint();
	}
	protected void orangeEin() {
		if (blinkenThread != null && blinkenThread.isAlive()) {
			blinkenThread.stoppen();
			try {
				blinkenThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.rotEin = false;
		this.orangeEin = true;
		this.gruenEin = false;
		repaint();
	}
	protected void gruenEin() {
		if (blinkenThread != null && blinkenThread.isAlive()) {
			blinkenThread.stoppen();
			try {
				blinkenThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.rotEin = false;
		this.orangeEin = false;
		this.gruenEin = true;
		repaint();
	}
	protected void orangeBlinkenEin() {
		if (blinkenThread != null && blinkenThread.isAlive()) {
			blinkenThread.stoppen();
			try {
				blinkenThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		blinkenThread = new BlinkenThread();
		blinkenThread.start();
		repaint();
	}

	/**
	 * Diese Methoden dienen dazu die Ereignisabhörer an die
	 * Knöpfe zu hängen bzw. die gesetzten Ereignisabhörer der einzelnen
	 * Knöpfe zu erfragen
	 */
	public void addEinKnopfListener(ActionListener a) {
		this.einKnopf.addActionListener(a);
	}
	public ActionListener[] getEinKnopfListeners() {
		return this.einKnopf.getActionListeners();
	}
	public void addAusKnopfListener(ActionListener a) {
		this.ausKnopf.addActionListener(a);
	}
	public ActionListener[] getAusKnopfListeners() {
		return this.ausKnopf.getActionListeners();
	}
	public void addManuellKnopfListener(ActionListener a) {
		this.manuellKnopf.addActionListener(a);
	}
	public ActionListener[] getManuellKnopfListeners() {
		return this.manuellKnopf.getActionListeners();
	}
	public void addAutomatischKnopfListener(ActionListener a) {
		this.automatischKnopf.addActionListener(a);
	}
	public ActionListener[] getAutomatischKnopfListeners() {
		return this.automatischKnopf.getActionListeners();
	}
	
	/**
	 * Damit die Größe der Ampel nicht geändert werden kann werden
	 * die Standardmethoden von JComponent überschrieben
	 */
	@Override
	public void setSize(int x, int y) {
		super.setSize(WIDTH, HEIGHT + KNOPF_HOEHE * 4);
	}
	@Override
	public void setSize(Dimension d) {
		super.setSize(WIDTH, HEIGHT + KNOPF_HOEHE * 4);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, WIDTH, HEIGHT + KNOPF_HOEHE * 4);
	}
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r.x, r.y, WIDTH, HEIGHT + KNOPF_HOEHE * 4);
	}
	
	/**
	 * Diese Methode sorgt dafür, dass die Ampel korrekt angezeigt wird
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
		if (rotEin) {
			g.setColor(Color.red);
			g.fillOval(1, 1, WIDTH - 3, WIDTH - 3);
		} else {
			g.setColor(Color.black);
			g.drawOval(1, 1, WIDTH - 3, WIDTH - 3);
		}
		if (orangeEin) {
			g.setColor(Color.orange);
			g.fillOval(1, WIDTH + 1, WIDTH - 3, WIDTH - 3);
		} else {
			g.setColor(Color.black);
			g.drawOval(1, WIDTH + 1, WIDTH - 3, WIDTH - 3);
		}
		if (gruenEin) {
			g.setColor(Color.green);
			g.fillOval(1, WIDTH * 2 + 1, WIDTH - 3, WIDTH - 3);
		} else {
			g.setColor(Color.black);
			g.drawOval(1, WIDTH * 2 + 1, WIDTH - 3, WIDTH - 3);
		}
	}
}
