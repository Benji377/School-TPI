package net.tfobz.mitarbeiter;

public class Arbeiter extends Mitarbeiter {

	public Arbeiter(String name, String phone) {
		super(name, phone);
	}

	@Override
	public String toString() {
		return this.getName() + ", " + this.getPhoneNumber();
	}
}
