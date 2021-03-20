package hooks;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import core.DataLakeClient;
import core.DriverManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hook {
	@Before
	public void setUp(Scenario scenario) {
		System.setProperty("logback.configurationFile", System.getProperty("user.dir") + File.separator + "logback.xml");
		DataLakeClient.getData();
	}
	
	@After(order = 1)
	public void takeScreenshot(Scenario scenario) {
		byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
		scenario.embed(screenshot, "image/png");
	}
	
	@After(order = 0)
	public void tearDown(Scenario scenario) {
		DriverManager.destroy();
	}
}
