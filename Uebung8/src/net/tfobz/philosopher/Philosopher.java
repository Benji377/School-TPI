package net.tfobz.philosopher;


public class Philosopher extends Thread {
	public static final int MAX_THINK_TIME = 2000;
	public static final int MAX_EAT_TIME = 1000;
	private Fork left, right = null;
	private ForkControl fc = null;
	
	public Philosopher(String name, Fork left, Fork right) {
		setName(name); 
		this.left = left; 
		this.right = right;
		fc = new ForkControl(right, left);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				sleep((int)(Math.random() * MAX_THINK_TIME));
			} catch (InterruptedException e) { ; }
			fc.get(this);
			try {
				sleep((int)(Math.random() * MAX_EAT_TIME));
			} catch (InterruptedException e) { ; }
			fc.put(this);
		}
	}
}
