package net.tfobz.mitarbeiter;

public abstract class Mitarbeiter extends Object {
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
}
