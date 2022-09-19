package net.tfobz.mitarbeiter;

public abstract class Mitarbeiter implements Comparable<Mitarbeiter> {
	private String name;
	private String phone;
	
	public Mitarbeiter(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhoneNumber() {
		return phone;
	}
	
	public abstract String toString();
	
	@Override
	public int compareTo(Mitarbeiter o) {
		return this.getName().compareTo(o.getName());
	}
}
