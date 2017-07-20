package wallethub.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import wallethub.automation.locator.FacebookHomePageLoc;
import wallethub.automation.utils.DriverUtil;
import wallethub.automation.utils.VerificationUtil;
import wallethub.automation.utils.WebElementUtil;

public class FacebookHomePage extends FacebookHomePageLoc {
	WebDriver driver;

	public FacebookHomePage() {
		driver = DriverUtil.getWebDriver();
		PageFactory.initElements(driver, this);
	}

	public void verifyHomePage() {
		WebElementUtil.waitForElement(getHomepageLogo());
		WebElementUtil.clickElementUsingJs(getHomepageLogo());
		VerificationUtil.verifyVisible(getStatusUpdateTextArea(), "Status update textbox");
	}

	public void postStatus(String msg) {
		WebElementUtil.waitForElement(getStatusUpdateTextArea());
		getStatusUpdateTextArea().sendKeys(msg);
		WebElementUtil.waitForElement(getPostBtn());
		getPostBtn().click();
	}

	public void verifyStatus(String statusMsg) {
		WebElementUtil.waitForElement(getStatusMsgText());
		VerificationUtil.verifyText(getStatusMsgText(), statusMsg, "Status message text");
		deleteStatus();
	}

	public void deleteStatus() {
		WebElementUtil.waitForElement(getDeleteStatusDropDown());
		getDeleteStatusDropDown().click();
		WebElementUtil.waitForElement(getDeleteStatusLink());
		getDeleteStatusLink().click();
		WebElementUtil.waitForElement(getDeleteConfirmBtn());
		getDeleteConfirmBtn().click();
	}

}
