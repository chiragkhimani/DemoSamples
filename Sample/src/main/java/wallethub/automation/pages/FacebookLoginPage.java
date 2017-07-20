package wallethub.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import wallethub.automation.locator.FacebookLoginPageLoc;
import wallethub.automation.utils.DriverUtil;
import wallethub.automation.utils.PropertyUtil;
import wallethub.automation.utils.VerificationUtil;

public class FacebookLoginPage extends FacebookLoginPageLoc {
	WebDriver driver;

	public FacebookLoginPage() {
		driver = DriverUtil.getWebDriver();
		PageFactory.initElements(driver, this);
	}

	public void openFacebookWebsite() {
		driver.get(PropertyUtil.getProperty("facebook.baseurl"));
		driver.manage().window().maximize();
	}

	public void doLogin(String username, String password) {
		getLoginEmail().sendKeys(username);
		getLoginPassword().sendKeys(password);
		getLoginBtn().click();
	}

	public void veirfyLoginPage() {
		VerificationUtil.verifyVisible(getLoginEmail(), "Login email text box");
		VerificationUtil.verifyVisible(getLoginPassword(), "Login password text box");
		VerificationUtil.verifyVisible(getLoginBtn(), "Login button");
	}

}
