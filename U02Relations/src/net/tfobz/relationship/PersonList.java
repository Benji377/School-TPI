package net.tfobz.relationship;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import net.tfobz.relationship.Person.Gender;

public class PersonList extends ArrayList<Person>
{
	public PersonList() {
	}
	
	public PersonList(BufferedReader reader) throws IOException {
		readPersons(reader);
	}
	
	public PersonList(String filename) throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		readPersons(reader);
	}
	
	private void readPersons(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		int counter = 0;
		if (line != null && !line.isEmpty() && !line.equalsIgnoreCase(";;;") && !line.equalsIgnoreCase("")) {
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == ';')
					counter++;
			}
			if (counter%2 == 0) {
				throw new IllegalArgumentException("Ungültiger fileString");
			} else {
				String l = line;
				for (int j = 0; j < counter; j++) {
					String name = l.substring(0, l.indexOf(';'));
					l = l.substring(l.charAt(';'));
					String gender = l.substring(0, l.indexOf(';'));
					System.out.println("Person: "+name+" "+gender);
					if (gender.equals("MALE"))
						this.add(new Person(name, Gender.MALE));
					else if (gender.equals("FEMALE"))
						this.add(new Person(name, Gender.FEMALE));
					else
						throw new IllegalArgumentException("Ungültige fileString");
						
				}
			}
		} else {
			throw new IllegalArgumentException("Ungültige fileString");
		}
	}

}
