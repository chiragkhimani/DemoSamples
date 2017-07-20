package wallethub.automation.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = false, plugin = { "pretty", "html:target/cucumber-reports",
		"json:target/cucumber.json" }, glue = "assignment.automation.steps", features = "src\\test\\java\\assignment\\automation\\feature\\Facebook_Status_Update.feature")
public class Runner {

}
