package net.tfobz.mitarbeiter;

import java.util.Vector;

public class Abteilungsleiter extends Mitarbeiter {
	private String department;
	private Vector<Arbeiter> employee = new Vector<>();

	public Abteilungsleiter(String name, String phone, String department) {
		super(name, phone);
		this.department = department;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void add(Arbeiter a) {
		employee.add(a);
	}

	public void remove(Arbeiter a) {
		employee.remove(a);
	}
	
	public Arbeiter getEmployee(int index) {
		return employee.get(index);
	}

	@Override
	public String toString() {
		return this.getName() + ", " + this.getPhoneNumber() + ", " + this.getDepartment() + "\n";
	}

}
