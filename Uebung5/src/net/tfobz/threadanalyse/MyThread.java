package net.tfobz.threadanalyse;

public class MyThread extends Thread {
	
	@Override
	public void run() {
		while (!this.isInterrupted()) {
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				this.interrupt();
			}
			
			int i = 0;
			while (i < 1000) {
				double b = Math.sqrt(Math.pow(123, 20)) + Math.random();
				//System.out.println("Value: "+b);
				i++;
			}
		}
	}
	
}
