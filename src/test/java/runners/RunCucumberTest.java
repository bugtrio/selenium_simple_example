package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"json:target/cucumber-report.json" }, 
		features = { "classpath:features/" }, 
		glue = { "steps", "hooks" },
		tags = { "@Smoke" })
public class RunCucumberTest {

}