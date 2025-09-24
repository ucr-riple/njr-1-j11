package assignments.frs.chap2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEmail {
	 /**
	   * Verify that a string is an email address.
	   * The string must obey the following (somewhat strict format)
	   * <email>      ::= <identifier> @ <identifier> {. <identifier>}
	   * <identifier> ::= letter { letter | digit }
	   * 
	   * Example: abc@somewhere12.mail.com is correct whereas the
	   * following are incorrect: 
	   *
	   * 13a@s.m.com (identifier starting with digit)
	   * a-c@s.m.com (non letter in identifier)
	   * a.b.c (missing @)
	   * abc@ (missing identifier after @)
	   * @return true if the address obeys this format.
	  */
	
	@Test
	public void testForIdentifierThatStartsWithDigit() {
		EmailAddress emailAddress = new EmailAddress("0testCase@email.com");
		boolean expected = false;
		boolean actual = emailAddress.isValid();
		assertEquals(expected, actual);
	}
	

	@Test
	public void testForIdentifierThatContainsIllegalCharacterInIdentifier() {
		EmailAddress emailAddress = new EmailAddress("test_Email@test.co.au");
		boolean expected = false;
		boolean actual = emailAddress.isValid();
		assertEquals(expected, actual);
	}
	

	@Test
	public void testForNoPeriod() {
		EmailAddress emailAddress = new EmailAddress("test@");
		boolean expected = false;
		boolean actual = emailAddress.isValid();
		assertEquals(expected, actual);
	}
	

	@Test
	public void testForNoAtSymbol() {
		EmailAddress emailAddress = new EmailAddress("test.com");
		boolean expected = false;
		boolean actual = emailAddress.isValid();
		assertEquals(expected, actual);
	}
	

	@Test
	public void testForNoDigits() {
		EmailAddress emailAddress = new EmailAddress("testEmail@provider.com");
		boolean expected = true;
		boolean actual = emailAddress.isValid();
		assertEquals(expected, actual);
	}
	

	@Test
	public void testForIdentifierThatEndssWithDigit() {
		EmailAddress emailAddress = new EmailAddress("ideNTifiEr@pro.vi.der.no1");
		boolean expected = true;
		boolean actual = emailAddress.isValid();
		assertEquals(expected, actual);
	}
	

	@Test
	public void testForIdentifierThatContainsDigit() {
		EmailAddress emailAddress = new EmailAddress("test@pr0vider.com");
		boolean expected = true;
		boolean actual = emailAddress.isValid();
		assertEquals(expected, actual);
	}

}
