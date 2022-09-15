package net.tfobz.threadanalyse;

public class MyThreadTerminator extends Thread {
	Thread thread;
	
	public MyThreadTerminator(Thread t) {
		this.thread = t;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread.interrupt();
	}
}
