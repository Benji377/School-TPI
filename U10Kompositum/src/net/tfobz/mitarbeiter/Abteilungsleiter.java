package net.tfobz.mitarbeiter;

import java.util.Vector;

public class Abteilungsleiter extends Mitarbeiter {
	private String department;
	private Vector<Mitarbeiter> employee = new Vector<>();

	public Abteilungsleiter(String name, String phone, String department) {
		super(name, phone);
		this.department = department;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void add(Mitarbeiter m) {
		employee.add(m);
	}

	public void remove(Mitarbeiter m) {
		employee.remove(m);
	}
	
	public Mitarbeiter getEmployee(int index) {
		return employee.get(index);
	}
	
	public int getEmployeeCount() {
		return employee.size();
	}

	@Override
	public String toString() {
		return this.getName() + ", " + this.getPhoneNumber() + ", " + this.getDepartment() + "\n";
	}

}
