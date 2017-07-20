package wallethub.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import wallethub.automation.locator.WalletHubLoginPageLoc;
import wallethub.automation.utils.DriverUtil;
import wallethub.automation.utils.VerificationUtil;
import wallethub.automation.utils.WebElementUtil;

public class WalletHubLoginPage extends WalletHubLoginPageLoc {
	WebDriver driver;

	public WalletHubLoginPage() {
		driver = DriverUtil.getWebDriver();
		PageFactory.initElements(driver, this);
	}

	public void doLogin(String username, String password) {
		getLoginEmail().sendKeys(username);
		getLoginPassword().sendKeys(password);
		getLoginBtn().click();
		verifyLoginSuccessful();
	}

	public void verifyLoginPage() {
		VerificationUtil.verifyVisible(getLoginEmail(), "Login email text box");
		VerificationUtil.verifyVisible(getLoginPassword(), "Login password text box");
		VerificationUtil.verifyVisible(getLoginBtn(), "Login button");
	}

	public void verifyLoginSuccessful() {
		WebElementUtil.waitForElementNotVisible(getLoginEmail());
		VerificationUtil.verifyTrue(!WebElementUtil.isDisplayed(getLoginEmail()), "Login successful",
				"Unable to login");
	}
}
