package validators;

import static org.junit.Assert.assertTrue;

public class BasicAssert {

	public static void containsMessage(String obtainedMessage, String expectedMessage) {
		assertTrue(obtainedMessage.contains(expectedMessage));
	}

}
