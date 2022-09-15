package net.tfobz.philosopher;

public class ForkControl {
	private Fork left, right = null;
	
	/*
	 * Dem Konstruktor werden die beiden Forks übergeben.
	 * ForkControl hat dann die Aufgabe die Forks gleichzeitig
	 * aufzuheben und niederzustellen
	 */
	public ForkControl(Fork right, Fork left) {
		this.left = left;
		this.right = right;
	}
	
	/*
	 * Hebt, wenn möglich, beide Forks auf.
	 * Ansonsten wird nichts getan
	 */
	public void get(Philosopher phil) {
		if (left.isAvailable() && right.isAvailable()) {
			left.get(phil);
			right.get(phil);
		}
	}
	/*
	 * Legt beide Forks wieder ab
	 */
	public void put(Philosopher phil) {
		right.put(phil);
		left.put(phil);
	}
}
