package net.tfobz.increase;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Gui extends JFrame {
	private JButton start;
	public static JProgressBar progress;
	private JLabel label;
	private JTextField result;
	
	public Gui() {
		this.setTitle("IncrementAtomic");
		this.setBounds(300, 300, 380, 220);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		start = new JButton("Start incrementation");
		start.setBounds(90, 10, 200, 30);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Int i = new Int();
				Thread t = new Thread() {
					
					@Override
					public void run() {
						i.i = 0;
						Thread t1 = new Increment(i);
						Thread t2 = new Increment(i);
						t1.start();
						t2.start();
						
						while(t1.isAlive() || t2.isAlive()) {
							start.setEnabled(false);
							progress.setValue(i.i);
						}
						start.setEnabled(true);
					}
				};
				t.start();
				try {
					t.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				result.setText(String.valueOf(i.i));
			}
		});
		
		progress = new JProgressBar();
		progress.setStringPainted(true);
		progress.setBounds(5, 50, 365, 100);
		progress.setMaximum(1_000_000);
		progress.setMinimum(0);
		
		label = new JLabel("Result:");
		label.setBounds(10, 150, 70, 30);
		
		result = new JTextField();
		result.setBounds(90, 150, 280, 30);
		result.setEditable(false);
		
		Container c = this.getContentPane();
		c.setLayout(null);
		c.add(start);
		c.add(progress);
		c.add(label);
		c.add(result);
	}
}
