package core;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import enums.DriverType;

/**
 * Classe singleton para gerenciamento do driver
 */
public class DriverManager {
	private static WebDriver driver;
	
	/**
	 * Cria e/ou retorna o driver.
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		if (driver == null) {
			if (StringUtils.isNotBlank(System.getProperty("browser")) && System.getProperty("browser").equalsIgnoreCase("firefox"))
				driver = DriverFactory.createDriver(DriverType.FIREFOX);
			else
				driver = DriverFactory.createDriver(DriverType.CHROME);
		}
		return driver;
	}
	
	/**
	 * Destroi o driver.
	 */
	public static void destroy() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
