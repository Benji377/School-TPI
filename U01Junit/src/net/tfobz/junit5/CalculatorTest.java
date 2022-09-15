package net.tfobz.junit5;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import org.junit.jupiter.api.*;


import net.tfobz.calc.Calculator;

public class CalculatorTest {
	private static Calculator c = null;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		c = new Calculator();
	}
	
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		c = null;
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		c.clear();
	}
	
	@Test
	public void creation() {
		assertEquals(0, c.getResult());
	}
	
	@Test
	public void add() {
		c.add(2); c.add(2);
		assertEquals(4, c.getResult());
	}
	
	@Test
	public void subtract() {
		c.subtract(2); c.subtract(2);
		assertEquals(-4, c.getResult());
	}
	
	@Test
	@Disabled
	public void multiply() {
		c.add(2); c.multiply(2);
		assertEquals(4, c.getResult());
	}
	
	@Test
	public void divide() {
		c.divide(2);
		assertEquals(0, c.getResult());
		c.add(2); c.divide(2);
		assertEquals(1, c.getResult());
	}
	
	
	public void divideByZero() {
	    Assertions.assertThrows(ArithmeticException.class, () -> {
	    	c.divide(0); 
	    });
	}
	
	
	public void squareRoot() {
		Assertions.assertTimeout(Duration.ofSeconds(1), () -> {
			c.squareRoot(4);
			assertEquals(2, c.getResult());
		});
	}
}
