package net.tfobz.relationship;

import java.util.ArrayList;

public class Person
{
	public enum Gender { FEMALE, MALE };
	
	protected Gender gender = null;
	protected String name = null;
	protected Person mother = null;
	protected Person father = null;
	protected ArrayList<Person> children = new ArrayList();
	
	public Person(String name, Gender gender) {
		if (name == null || gender == null || name.length() == 0)
			throw new IllegalArgumentException("Name und Gender müssen gefüllt sein");
		setName(name);
		setGender(gender);
	}

	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		if (gender == null)
			throw new IllegalArgumentException("Gender kann nicht leer sein");
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name.length() == 0 || name == null)
			throw new IllegalArgumentException("Name kann nicht leer sein!");
		this.name = name;
	}
	
	public Person getMother() {
		return mother;
	}
	public void setMother(Person mother) {
		if (mother == null || mother.getGender() == Gender.FEMALE) {
			if (mother != null) {
				this.mother = mother;
				mother.getChildren().add(this);
			} else if (this.mother != null) {
				this.mother.getChildren().remove(this);
				this.mother = mother;
			}
		} else {
			throw new IllegalArgumentException("Mutter muss weiblich sein");
		}	
	}
	
	public Person getFather() {
		return father;
	}
	public void setFather(Person father) {
		if (father == null || father.getGender() == Gender.MALE) {
			if (father != null) {
				this.father = father;
				father.getChildren().add(this);
			} else if (this.father != null) {
				this.father.getChildren().remove(this);
				this.father = father;
			}
		} else {
			throw new IllegalArgumentException("Vater muss männlich sein");
		}	
	}
	
	public ArrayList<Person> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Person> children) {
		this.children = children;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o == null)
			throw new IllegalArgumentException("Person kann nicht null sein");
		if (o instanceof Person) {
			Person p = (Person) o;
			if (getName().equals(p.getName()) && getGender().equals(p.getGender()))
				ret = true;
		}
		return ret;
	}
}
