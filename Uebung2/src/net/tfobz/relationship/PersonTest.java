package net.tfobz.relationship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.*;

import net.tfobz.relationship.Person;
import net.tfobz.relationship.Person.Gender;


public class PersonTest {
	private static Person p = null;
	
	@BeforeEach
	public void creation() {
		p = new Person("Sepp", Gender.MALE);
		assertEquals("Sepp", p.getName());
		assertEquals(Gender.MALE, p.getGender());
	}
	
	@Test
	public void nameGenderEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p.setGender(null);
			p.setName("");
			p.setName(null);
		});
	}
	
	@Test
	public void nameEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p.setName("");
			p.setName(null);
		});
	}
	
	@Test
	public void genderEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p.setGender(null);
		});
	}
	
	@Test
	public void creationNameGenderEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p = new Person(null, null);
			p = new Person("", null);
		});
	}
	
	@Test
	public void creationNameEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p = new Person("", Gender.MALE);
			p = new Person(null, Gender.FEMALE);
		});
	}
	
	@Test
	public void creationGenderEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p = new Person("Sepp", null);
		});
	}
	
	@Test
	public void equals() {
		p.equals(new Person("Sepp", Gender.MALE));
		p.equals(new Person("Lisa", Gender.FEMALE));
		p.equals(new String[0]);
	}
	
	@Test
	public void equalsNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p.equals(null);
		});
	}
	
	@Test
	public void parent() {
		p = new Person("Sepp", Gender.MALE);
		Person f = new Person("Bobby", Gender.MALE);
		Person m = new Person("Mummy", Gender.FEMALE);
		p.setFather(f);
		p.setMother(m);
		assertEquals(f, p.getFather());
		assertEquals(m, p.getMother());
		
		p.setFather(null);
		p.setMother(null);
		assertEquals(p.getFather(), null);
		assertEquals(p.getMother(), null);
	}
	
	@Test
	public void parentIncorrectGender() {
		p = new Person("Sepp", Gender.MALE);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p.setFather(new Person("Bobby", Gender.FEMALE));
			p.setMother(new Person("Mommy", Gender.MALE));
		});
	}
	
	@Test
	public void children() {
		Person father = new Person("Bobby", Gender.MALE);
		Person mother = new Person("Mommy", Gender.FEMALE);
		p = new Person("Sepp", Gender.MALE);
		p.setFather(father);
		p.setMother(mother);
		assertTrue(father.getChildren().contains(p));
		assertTrue(mother.getChildren().contains(p));
		p.setFather(null);
		p.setMother(null);
		assertFalse(father.getChildren().contains(p));
		assertFalse(mother.getChildren().contains(p));
		Person mother2 = new Person("Mooomm", Gender.FEMALE);
		p.setMother(mother2);
		assertFalse(mother.getChildren().contains(p));
		assertTrue(mother2.getChildren().contains(p));
		Person father2 = new Person("Bobboo", Gender.MALE);
		p.setFather(father2);
		assertFalse(father.getChildren().contains(p));
		assertTrue(father2.getChildren().contains(p));
	}
	
	@Test
	public void daughtersSons() {
		p = new Person("Sepp", Gender.MALE);
		Person tiggy = new Person("Tiggy", Gender.FEMALE);
		Person father = new Person("Bobby", Gender.MALE);
		p.setFather(father);
		assertTrue(father.getSons().contains(p));
		assertNotNull(father.getDaughters());
		assertFalse(father.getDaughters().contains(p));
		
		tiggy.setFather(father);
		assertTrue(father.getDaughters().contains(tiggy));
	}
	
	@Test
	public void sistersBrothers() {
		p = new Person("Sepp", Gender.MALE);
		Person father = new Person("Bobby", Gender.MALE);
		Person mother = new Person("Mommy", Gender.FEMALE);
		Person tiggy = new Person("Tiggy", Gender.FEMALE);
		Person mick = new Person("Michael", Gender.MALE);
		
		p.setFather(father);
		p.setMother(mother);
		tiggy.setFather(father);
		tiggy.setMother(mother);
		mick.setFather(father);
		
		assertTrue(p.getSisters().contains(tiggy));
		assertFalse(p.getBrothers().contains(mick));
		
		mick.setMother(mother);
		assertTrue(p.getBrothers().contains(mick));
	}
	
	@Test
	public void descendants() {
		Collection<Person> desc = new ArrayList<>();
		p = new Person("Sepp", Gender.MALE);
		Person father = new Person("Bobby", Gender.MALE);
		Person mother = new Person("Mommy", Gender.FEMALE);
		
		Person tiggy = new Person("Tiggy", Gender.FEMALE);
		Person mick = new Person("Michael", Gender.MALE);
		
		Person tigga = new Person("Tigga", Gender.FEMALE);
		Person micko = new Person("Micko", Gender.MALE);
		desc.add(father);
		desc.add(mother);
		desc.add(tiggy);
		desc.add(mick);
		desc.add(tigga);
		desc.add(micko);
		
		p.setFather(father);
		p.setMother(mother);
		mother.setFather(mick);
		mother.setMother(tiggy);
		father.setFather(micko);
		father.setMother(tigga);
		
		assertTrue(p.getDescendants().containsAll(desc));
	}
	
	@Test
	public void parentOfHimself() {
		p = new Person("Sepp", Gender.MALE);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			p.setMother(p);
			p.setFather(p);
		});
	}
	
	@Test
	public void parentIsDescendant() {
		p = new Person("Sepp", Gender.MALE);
		Person f = new Person("Federica", Gender.FEMALE);
		Person father = new Person("Bobby", Gender.MALE);
		Person mother = new Person("Mommy", Gender.FEMALE);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			father.setFather(p);
			p.setFather(father);
			father.setMother(f);
			f.setFather(father);
			
			mother.setMother(f);
			f.setMother(mother);
			mother.setFather(p);
			p.setMother(mother);
		});
	}
}
