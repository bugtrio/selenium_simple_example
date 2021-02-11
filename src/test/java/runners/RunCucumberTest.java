package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"json:target/cucumber-report.json" }, 
		features = { "classpath:features/" }, 
		glue = { "steps", "hooks" },
		tags = { "@Smoke or @Regression" })
public class RunCucumberTest extends AbstractTestNGCucumberTests {
	
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		checkParallelExecution();
		return super.scenarios();
	}

	private void checkParallelExecution(){
		System.setProperty("dataproviderthreadcount",
					StringUtils.isBlank(System.getProperty("threads"))? "1" : System.getProperty("threads"));
	}
}