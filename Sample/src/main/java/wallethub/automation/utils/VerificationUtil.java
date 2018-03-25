package wallethub.automation.utils;

import org.openqa.selenium.WebElement;

public class VerificationUtil implements AssertProperties {
	// public static final SoftAssert softly = new SoftAssert();

	// public static SoftAssert getAssert() {
	// return softly;
	// }

	public static void verifyTrue(boolean condition, String passMsg, String failMsg) {
		// TestNG test = new TestNG();
		// test.setVerbose(0);
		// if (condition) {
		// ReportUtil.getScenario().write(String.format(verifyTruePass,
		// passMsg));
		// } else {
		// ReportUtil.getScenario().write(String.format(verifyTrueFail, failMsg,
		// DriverUtil.getScreenshot()));
		// System.setProperty("IS_FAILED", "true");
		// }
	}

	public static void verifyPresent(WebElement ele, String label) {

	}

	public static void verifyVisible(WebElement ele, String label) {
		WebElementUtil.waitForElement(ele);
		if (ele.isDisplayed()) {
			ReportUtil.getScenario().write(String.format(verifyVisiblePass, label, label));
		} else {
			ReportUtil.getScenario().write(String.format(verifyVisibleFail, label, label));
		}
	}

	public static void verifyText(WebElement ele, String expectedText, String label) {
		WebElementUtil.waitForElement(ele);
		if (ele.getText().equalsIgnoreCase(expectedText)) {
			ReportUtil.getScenario().write(String.format(verifyTextPass, label, expectedText, label, ele.getText()));
		} else {
			ReportUtil.getScenario().write(String.format(verifyTextFail, label, expectedText, label, ele.getText()));
		}
	}

	public static void verifyTextContains(WebElement ele, String expectedText, String label) {
		WebElementUtil.waitForElement(ele);
		if (ele.getText().toLowerCase().contains(expectedText.toLowerCase())) {
			ReportUtil.getScenario()
					.write(String.format(verifyTextContainsPass, label, expectedText, label, ele.getText()));
		} else {
			ReportUtil.getScenario()
					.write(String.format(verifyTextContainsFail, label, expectedText, label, ele.getText()));
		}
	}

}
