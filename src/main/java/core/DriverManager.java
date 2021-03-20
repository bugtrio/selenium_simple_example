package core;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import enums.DriverType;

/**
 * Classe singleton para gerenciamento do driver
 */
public class DriverManager {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	/**
	 * Cria e/ou retorna o driver.
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		if (driver.get() == null)
			driver.set(DriverFactory.createDriver(getDriverType()));
		return driver.get();
	}
	
	/**
	 * Destroi o driver.
	 */
	public static void destroy() {
		if (driver != null) {
			driver.get().quit();
			driver.set(null);
		}
	}

	/**
	 * Verifica o tipo do driver a ser instanciado
	 * @return tipo do driver desejado para execução
	 */
	private static DriverType getDriverType() {
		return (StringUtils.isNotBlank(System.getProperty("browser")) && System.getProperty("browser").equalsIgnoreCase("firefox"))?
				DriverType.FIREFOX : DriverType.CHROME;
	}
}
