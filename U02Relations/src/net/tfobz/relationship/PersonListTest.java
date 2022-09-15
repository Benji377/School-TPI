package net.tfobz.relationship;

import org.junit.jupiter.api.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class PersonListTest {
	PersonList pList = new PersonList();
	String fileString = "";
	BufferedReader reader = new BufferedReader(new StringReader(fileString));
	
	@Test
	public void listIsEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pList = new PersonList(reader);
			reader = new BufferedReader(new StringReader(";;;"));
			pList = new PersonList(reader);
			reader = new BufferedReader(new StringReader("null;null;null;null"));
			pList = new PersonList(reader);
			reader = new BufferedReader(new StringReader(";;null;null"));
		});
	}
	
	@Test
	public void genderTest() {
		try {
			reader = new BufferedReader(new StringReader("Sepp;MALE"));
			pList = new PersonList(reader);
			reader = new BufferedReader(new StringReader("Mica;FEMALE;Zago;MALE"));
			pList = new PersonList(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			reader = new BufferedReader(new StringReader("Mick;blabla"));
			pList = new PersonList(reader);
		});
	}
	
	@Test
	public void nameTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			reader = new BufferedReader(new StringReader(";MALE"));
			pList = new PersonList(reader);
			reader = new BufferedReader(new StringReader(";FEMALE"));
			pList = new PersonList(reader);
			reader = new BufferedReader(new StringReader(";"));
			pList = new PersonList(reader);
		});
	}
}
