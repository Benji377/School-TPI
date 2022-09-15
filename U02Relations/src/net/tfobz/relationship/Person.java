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
	protected ArrayList<Person> desc = new ArrayList<>();
	
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
		if ((mother == null || mother.getGender() == Gender.FEMALE) && mother != this) {
			if (mother != null) {
				if (!mother.getDescendants().contains(this)) {
					this.mother = mother;
					mother.getChildren().add(this);
				} else {
					throw new IllegalArgumentException("Mutter kann nicht sich selbsts sein oder ein Nachkommen davon");
				}
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
		if ((father == null || father.getGender() == Gender.MALE) && father != this) {
			if (father != null) {
				if (!father.getDescendants().contains(this)) {
					this.father = father;
					father.getChildren().add(this);
				} else {
					throw new IllegalArgumentException("Vater kann nicht sich selbsts sein oder ein Nachkommen davon");
				}
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
	
	public ArrayList<Person> getSons() {
		ArrayList<Person> sons = new ArrayList<>();
		for (int i = 0; i < getChildren().size(); i++) {
			if (getChildren().get(i).gender == Gender.MALE)
				sons.add(getChildren().get(i));
		}
		return sons;
	}
	public ArrayList<Person> getDaughters() {
		ArrayList<Person> daughters = new ArrayList<>();
		for (int i = 0; i < getChildren().size(); i++) {
			if (getChildren().get(i).gender == Gender.FEMALE)
				daughters.add(getChildren().get(i));
		}
		return daughters;
	}
	
	public ArrayList<Person> getBrothers() {
		ArrayList<Person> brothers = new ArrayList<>();
		ArrayList<Person> temp = getFather().getSons();
		for (int i = 0; i < getMother().getSons().size(); i++) {
			if (temp.contains(getMother().getSons().get(i)))
				brothers.add(getMother().getSons().get(i));
		}
		return brothers;
	}
	public ArrayList<Person> getSisters() {
		ArrayList<Person> sisters = new ArrayList<>();
		ArrayList<Person> temp = getFather().getDaughters();
		for (int i = 0; i < getMother().getDaughters().size(); i++) {
			if (temp.contains(getMother().getDaughters().get(i)))
				sisters.add(getMother().getDaughters().get(i));
		}
		return sisters;
	}
	/*
	 * Rekursive Methode zum herholen aller Nachkommen
	 */
	public void rekursiveDesc(Person p) {
		if (p != null && !desc.contains(p)) {
			desc.add(p);
			rekursiveDesc(p.getFather());
			rekursiveDesc(p.getMother());
		}
	}
	
	public ArrayList<Person> getDescendants() {
		rekursiveDesc(this);
		ArrayList<Person> desc = this.desc;
		desc.remove(this);
		
		return desc;
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
