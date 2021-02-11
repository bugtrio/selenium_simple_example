package core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import enums.DriverType;

/**
 * Classe Factory responsavel por criar os drivers.
 *
 */
public class DriverFactory {
	private static final Map<DriverType, Supplier<WebDriver>> driverMap = new HashMap<>();
	private static final String BASE_DIR = System.getProperty("user.dir") + File.separator + "drivers" + File.separator;
	private static final String FIREFOX_BIN_WIN = "C:" + File.separator + "Program Files" + File.separator + "Mozilla Firefox" + File.separator + "firefox.exe";
	private static final String FIREFOX_BIN_LINUX = "";
	private static final String REMOTE_DRIVER_URL = "http://localhost:4444/wd/hub";

	/**
	 * Utiliza o mecanismo de Supplier do Java8 para deixar os drivers do Chrome e
	 * do Firefox estruturados.
	 */
	private static final Supplier<WebDriver> chromeDriverSupplier = () -> {
		ChromeDriverService service = (ChromeDriverService) createDriverService(DriverType.CHROME);
		ChromeOptions options = new ChromeOptions();
		addBasicChromeOptions(options);
		addHeadlessChromeOptions(options);
		return  isRemote()? createRemoteDriver(options) : new ChromeDriver(service, options);
	};

	private static final Supplier<WebDriver> firefoxDriverSupplier = () -> {
		GeckoDriverService service = (GeckoDriverService) createDriverService(DriverType.FIREFOX);
		FirefoxOptions options = new FirefoxOptions();
		return  isRemote()? createRemoteDriver(options) : new FirefoxDriver(service);
	};
	static {
		driverMap.put(DriverType.CHROME, chromeDriverSupplier);
		driverMap.put(DriverType.FIREFOX, firefoxDriverSupplier);
	}

	/**
	 * Verifica se a execucao corrente eh remota
	 * @return se remoto
	 */
	private static Boolean isRemote() {
		return StringUtils.isNotBlank(System.getProperty("remote")) && System.getProperty("remote").equalsIgnoreCase("true");
	}

	/**
	 * Cria uma instancia do driver remoto de acordo com o parametro options
	 * @param options
	 * @return remote driver
	 */
	private static WebDriver createRemoteDriver(MutableCapabilities options){
		try {
			return new RemoteWebDriver(new URL(REMOTE_DRIVER_URL), options);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Recebe o tipo de driver que deve ser criado e retorna a instancia desejada.
	 * 
	 * @param type
	 * @return driver
	 */
	public static final WebDriver createDriver(DriverType type) {
		return driverMap.get(type).get();
	}

	/**
	 * Retorna o path do driver de acordo com o driverType e o sistema operacional
	 * 
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
	 * 
	 * @param options
	 */
	private static void addBasicChromeOptions(ChromeOptions options) {
		options.setAcceptInsecureCerts(true);
		options.addArguments("enable-automation");
		options.addArguments("start-maximized");
		options.addArguments("--disable-extensions");
		options.addArguments("--log-level=3");
		options.addArguments("--silent");
	}

	/**
	 * Caso necessario, adiciona as options headless ao ChromeOptions
	 * 
	 * @param options
	 */
	private static void addHeadlessChromeOptions(ChromeOptions options) {
		if (StringUtils.isNotBlank(System.getProperty("headless"))
				&& System.getProperty("headless").equalsIgnoreCase("true")) {
			options.addArguments("--disable-gpu");
			options.addArguments("--headless");
		}
	}

	/**
	 * Cria o DriverService com as configurações desejadas.
	 * 
	 * @param driverType
	 */

	private static DriverService createDriverService(DriverType driverType) {
		File driverPath = new File(getDriverVersion(driverType));
		if (!driverPath.exists()) {
			throw new RuntimeException(driverType.name() + " could not be located!");
		}
		switch (driverType) {
		case CHROME:
		default:
			return new ChromeDriverService.Builder().withVerbose(false).withSilent(true).usingAnyFreePort()
					.usingDriverExecutable(driverPath).build();
		case FIREFOX:
			return new GeckoDriverService.Builder().usingDriverExecutable(driverPath).usingAnyFreePort()
					.usingFirefoxBinary(getFirefoxBinary()).build();
		}
	}

	/**
	 * Retorna o FirefoxBinary usado para criar o GeckoDriverService de acordo com o sistema operacional
	 * 
	 * @return firefoxBinary
	 */
	private static FirefoxBinary getFirefoxBinary() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return new FirefoxBinary(new File(FIREFOX_BIN_WIN));
		} else {
			return new FirefoxBinary(new File(FIREFOX_BIN_LINUX));
		}
	}

}
