package wallethub.automation.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import wallethub.automation.utils.DriverUtil;
import wallethub.automation.utils.ReportUtil;

public class CommonSteps {

	@Before
	public void setUP(Scenario scenario) {
		System.out.println("Initializing driver.......");
		setUp(scenario);
	}

	private void setUp(Scenario scenario) {
		ReportUtil.setScenario(scenario);
		DriverUtil.initDriver();
	}

	@After
	public void quit() {
		DriverUtil.quit();
	}

}
