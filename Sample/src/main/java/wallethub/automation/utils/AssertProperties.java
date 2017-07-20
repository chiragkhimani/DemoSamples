package wallethub.automation.utils;

/**
 * This class contains properties which we are using in assertion
 * 
 */
public interface AssertProperties {
	String screenshot = "&nbsp;<a href=\"%s\">Screenshot</a><br />";

	String passText = "<font color=\"green\" face=\"verdana\">%s</font><br />";
	String failText = "<font color=\"red\" face=\"verdana\">%s</font>&nbsp;";

	String failImage = "<img width=\"10px\" height=\"10px\" margin= \"4 10px\" float=\"left\" src=\"../images/cross.png\"/>&nbsp;";
	String passImage = "<img width=\"10px\" height=\"10px\" margin= \"4 10px\" float=\"left\" src=\"../images/download.png\"/>&nbsp;";

	String verifyTruePass = passImage + passText;
	String verifyTrueFail = failImage + failText + screenshot;

	String verifyPresentPass = passImage
			+ String.format(passText, "Expected %s should be present : Actual %s is present");
	String verifyPresentFail = failImage
			+ String.format(failText, "Expected %s should be present : Actual %s is NOT present");

	String verifyVisiblePass = passImage
			+ String.format(passText, "Expected %s should be visible :Actual %s is visible");
	String verifyVisibleFail = failImage
			+ String.format(failText, "Expected %s should be visible :Actual %s is NOT visible");

	String verifyTextPass = passImage
			+ String.format(passText, "Expected %s should have text %s :Actual %s have text %s");
	String verifyTextFail = failImage
			+ String.format(failText, "Expected %s should have text %s :Actual %s have text %s");

	String verifyTextContainsPass = passImage
			+ String.format(passText, "Expected %s should contains text %s :Actual %s contains text %s");
	String verifyTextContainsFail = failImage
			+ String.format(failText, "Expected %s should contains text %s :Actual %s contains text %s");
}
