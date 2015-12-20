package test;

import static org.junit.Assert.*;

import org.junit.Test;

import greatSuccess.HelperClass;

public class HelperCapitalizeTest {

	@Test
	public void testOneWord() {
		
		String testStr1 = "BATMAN";		
		assertEquals(HelperClass.capitalize(testStr1), "Batman");
	}
	
	@Test
	public void testTwoWords() {
		String testStr2 = "matttt dAAAAmoonnnn";
		assertEquals(HelperClass.capitalize(testStr2), "Matttt Daaaamoonnnn");
	}
	
	@Test
	public void testSentenceMixedCase() {
		
		String testStr3 = "wE aRe tHe kNiGhTs wHo sAy 'KNEE'";
		assertEquals(HelperClass.capitalize(testStr3), "We Are The Knights Who Say 'knee'");		
	}
	
	@Test
	public void testSentenceNullCase() {
		assertEquals(HelperClass.capitalize(null), null);
	}

}
