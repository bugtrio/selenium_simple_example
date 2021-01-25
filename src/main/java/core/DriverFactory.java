package core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import enums.DriverType;

/**
 * Classe Factory responsavel por criar os drivers.
 *
 */
public class DriverFactory {
	private static final Map<DriverType, Supplier<WebDriver>> driverMap = new HashMap<>();
	private static final String BASE_DIR = System.getProperty("user.dir") + File.separator + "drivers" + File.separator;
	
	/**
	 * Utiliza o mecanismo de Supplier do Java8 para deixar os drivers do Chrome e do Firefox estruturados.
	 */
	private static final Supplier<WebDriver> chromeDriverSupplier = () -> {
		System.setProperty("webdriver.chrome.driver", getDriverVersion(DriverType.CHROME));
		ChromeOptions options = new ChromeOptions();
		addBasicChromeOptions(options);
		addHeadlessChromeOptions(options);
		return new ChromeDriver(options);
	};
	private static final Supplier<WebDriver> firefoxDriverSupplier = () -> {
		System.setProperty("webdriver.gecko.driver", getDriverVersion(DriverType.FIREFOX));
		return new FirefoxDriver();
	};
	static {
		driverMap.put(DriverType.CHROME, chromeDriverSupplier);
		driverMap.put(DriverType.FIREFOX, firefoxDriverSupplier);
	}
	
	/**
	 * Recebe o tipo de driver que deve ser criado e retorna a instancia desejada.
	 * @param type
	 * @return driver
	 */
	public static final WebDriver createDriver(DriverType type) {
		return driverMap.get(type).get();
	}
	
	/**
	 * Retorna o path do driver de acordo com o driverType e o sistema operacional
	 * @param driverType
	 * @return path do driver
	 */
	private static String getDriverVersion(DriverType driverType) {
		String driverPath = BASE_DIR;
		switch (driverType) {
			case CHROME:
			default:
				driverPath = driverPath.concat("chromedriver");
				if (SystemUtils.IS_OS_WINDOWS)
					driverPath = driverPath.concat(".exe");
				break;
			case FIREFOX:
				driverPath = driverPath.concat("geckodriver");
				if (SystemUtils.IS_OS_WINDOWS)
					driverPath = driverPath.concat(".exe");
				break;
		}
		return driverPath;
	}
	
	/**
	 * Adiciona options basicas ao ChromeOptions
	 * @param options
	 */
	private static void addBasicChromeOptions(ChromeOptions options) {
		options.setAcceptInsecureCerts(true);
		options.addArguments("enable-automation");
		options.addArguments("start-maximized");
		options.addArguments("--disable-extensions");
	}
	
	/**
	 * Caso necessario, adiciona as options headless ao ChromeOptions
	 * @param options
	 */
	private static void addHeadlessChromeOptions(ChromeOptions options) {
		if(StringUtils.isNotBlank(System.getProperty("headless")) && System.getProperty("headless").equalsIgnoreCase("true")) {
			options.addArguments("--disable-gpu");
			options.addArguments("--headless");
		}
	}
}
