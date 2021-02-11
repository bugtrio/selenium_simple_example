package exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This exception wraps all checked standard Java exception and enriches them with a user friendly message.
 * 
 */

public class PageException extends Exception {

	
	private static final long serialVersionUID = -1407020231402319183L;
	private static final Logger logger = LoggerFactory.getLogger(PageException.class);

	
	public PageException(String message, Throwable cause) {
		super(message, cause);
		logger.error(this.getMessage() + " - " + cause.getMessage());
	}

}
