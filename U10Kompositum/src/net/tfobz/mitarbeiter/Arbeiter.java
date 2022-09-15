package net.tfobz.mitarbeiter;

public class Arbeiter extends Mitarbeiter {

	public Arbeiter(String name, String phone) {
		super(name, phone);
	}

	@Override
	public String toString() {
		return this.getName() + ", " + this.getPhoneNumber();
	}

	@Override
	public int compareTo(Mitarbeiter o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
