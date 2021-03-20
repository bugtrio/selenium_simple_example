package validators;


import static org.testng.Assert.assertTrue;

public class BasicAssert {

	public static void containsMessage(String obtainedMessage, String expectedMessage) {
		assertTrue(obtainedMessage.contains(expectedMessage));
	}

}
