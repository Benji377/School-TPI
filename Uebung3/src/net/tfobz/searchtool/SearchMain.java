package net.tfobz.searchtool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SearchMain extends JFrame {
	JLabel min;
	JLabel max;
	JLabel avg;
	JTextField mintext;
	JTextField maxtext;
	JTextField avgtext;
	JProgressBar minpro;
	JProgressBar maxpro;
	JProgressBar avgpro;
	JButton submit;
	FindMin fmin;
	FindMax fmax;
	FindAvg favg;
	final int arrayRange = 1000000;
	
	public static void main(String[] args) {
		SearchMain s = new SearchMain();
		s.setVisible(true);
	}
	
	public SearchMain() {
		this.setTitle("MinMaxAvgSearch");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 500, 700, 200);
		this.setResizable(false);
		
		min = new JLabel("Min:");
		min.setBounds(5, 45, 50, 30);
		max = new JLabel("Max:");
		max.setBounds(5, 85, 50, 30);
		avg = new JLabel("Avg:");
		avg.setBounds(5, 125, 50, 30);
		
		mintext = new JTextField();
		mintext.setBounds(45, 45, 150, 30);
		mintext.setEditable(false);
		maxtext = new JTextField();
		maxtext.setBounds(45, 85, 150, 30);
		maxtext.setEditable(false);
		avgtext = new JTextField();
		avgtext.setBounds(45, 125, 150, 30);
		avgtext.setEditable(false);
		
		minpro = new JProgressBar();
		minpro.setBounds(205, 45, 450, 30);
		minpro.setStringPainted(true);
		maxpro = new JProgressBar();
		maxpro.setBounds(205, 85, 450, 30);
		maxpro.setStringPainted(true);
		avgpro = new JProgressBar();
		avgpro.setBounds(205, 125, 450, 30);
		avgpro.setStringPainted(true);
		
		submit = new JButton("Start searching");
		submit.setBounds(this.getWidth()/2-100, 5, 200, 30);
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				minpro.setValue(0);
				maxpro.setValue(0);
				avgpro.setValue(0);
				
				mintext.setText("");
				maxtext.setText("");
				avgtext.setText("");
				
				int[] genarray = new int[arrayRange];
				for (int i = 0; i < genarray.length; i++) {
					genarray[i] = (int)(Math.random()*(Integer.MAX_VALUE-1));
				}
				fmin = new FindMin(genarray, SearchMain.this);
				fmax = new FindMax(genarray, SearchMain.this);
				favg = new FindAvg(genarray, SearchMain.this);
				
				fmin.start();
				fmax.start();
				favg.start();
				
				new Thread() {
					@Override
					public void run() {
						while (fmin.isAlive() || fmax.isAlive() || favg.isAlive())
							submit.setEnabled(false);
						submit.setEnabled(true);
					}
				}.start();
			
			}});
		
		Container c = this.getContentPane();
		c.setLayout(null);
		c.add(min);
		c.add(max);
		c.add(avg);
		c.add(mintext);
		c.add(maxtext);
		c.add(avgtext);
		c.add(minpro);
		c.add(maxpro);
		c.add(avgpro);
		c.add(submit);
	}
}
