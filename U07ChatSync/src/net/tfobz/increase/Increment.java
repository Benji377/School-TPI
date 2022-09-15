package net.tfobz.increase;

public class Increment extends Thread {
	private Int it;

	public Increment(Int i) {
		this.it = i;
	}

	@Override
	public void run() {
		for (int j = 0; j < 1000000; j++)
			it.i += 1;
	}
}
